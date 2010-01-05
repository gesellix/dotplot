package org.dotplot.dpaas;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.IDotplot;
import org.dotplot.core.ISourceList;
import org.dotplot.core.PlotterJob;
import org.dotplot.dpaas.wsdto.ErrorElementFault;
import org.dotplot.dpaas.wsdto.WSDotplotjob;
import org.dotplot.dpaas.wsdto.WSDotplotjobresponse;
import org.dotplot.dpaas.wsdto.WSFile;
import org.dotplot.dpaas.wsdto.WSFilter;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

@WebService
@SOAPBinding(style = Style.RPC)
public class WebServicePort {

	private static final Logger LOGGER = Logger.getLogger(WebServicePort.class
			.getName());

	private static final Object synci = new Object(); // @EJB it would be a

	private static BufferedImage convertToAWT(ImageData data) {
		ColorModel colorModel = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask,
					palette.greenMask, palette.blueMask);
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[3];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					RGB rgb = palette.getRGB(pixel);
					pixelArray[0] = rgb.red;
					pixelArray[1] = rgb.green;
					pixelArray[2] = rgb.blue;
					raster.setPixels(x, y, 1, 1, pixelArray);
				}
			}
			return bufferedImage;
		}
		else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte) rgb.red;
				green[i] = (byte) rgb.green;
				blue[i] = (byte) rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue, data.transparentPixel);
			}
			else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue);
			}
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}
			return bufferedImage;
		}
	}

	// stateless bean so we
	// need a singelton
	/*
	 * The magic_memory_number describes the relation between incoming bytes and
	 * needed memory incoming_bytes ^ magic_memory_number == needed_memory
	 */
	final float magic_memory_number = 1.67951907f;

	public WSDotplotjobresponse doDotPlot(WSDotplotjob dpr)
			throws ErrorElementFault {

		// /////// Syncing to one job
		WSDotplotjobresponse wsdpjr;
		synchronized (WebServicePort.synci) {

			// /////// Memory testing
			LOGGER.debug("Got new WS-Job! Size:"
					+ dpr.getFilelist().getSumFileSize() / 1024 + "KBytes");

			LOGGER.debug("Free Memory: " + Runtime.getRuntime().freeMemory()
					/ 1024 / 1024 + "MBytes");
			int retries = 0;
			while (!enoughMemoryForJob(dpr.getFilelist().getSumFileSize())) {
				retries++;
				System.gc();
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
				}
				if (retries >= 60) {
					throw new ErrorElementFault(
							"Running too low on memory.. cant process job");
				}
			}

			// /////// DOTPLotTing
			DotplotContext dpc = ContextFactory.getContext();
			ISourceList sl = new DefaultSourceList();

			Iterator<WSFile> i = dpr.getFilelist().getFile().iterator();
			while (i.hasNext()) {
				sl.add(i.next().toByteRessource());
			}
			PlotterJob pj = new PlotterJob();
			dpc.setSourceList(sl);
			IConfigurationRegistry icr = dpc.getConfigurationRegistry();
			try {
				IQImageConfiguration iqim = (IQImageConfiguration) icr
						.get("org.dotplot.qimage.Configuration");
				iqim.setLut(dpr.getConfiguration().getLUTs().value(), null,
						null);

				IFilterConfiguration itok = (IFilterConfiguration) icr
						.get("org.dotplot.filter.Configuration");
				List<String> strili = new ArrayList<String>();
				Iterator<WSFilter> wsfl = dpr.getConfiguration().getFilter()
						.getFilter().iterator();
				while (wsfl.hasNext()) {
					strili.add(wsfl.next().getID().value());
				}
				itok.setFilterList(strili);
			}
			catch (UnknownIDException e) {
				e.printStackTrace();
			}

			dpc.executeJob(pj);
			IDotplot idp = dpc.getCurrentDotplot();

			BufferedImage bi = convertToAWT((ImageData) idp
					.getImage(IDotplot.IMG_SWT_IMAGEDATA));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bi, "png", baos);
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			byte[] bytesOut = baos.toByteArray();
			wsdpjr = new WSDotplotjobresponse();
			WSFile wsf = new WSFile();
			wsf.setContent(bytesOut);
			wsf.setFilename("image.png");
			wsdpjr.setImage(wsf);
			LOGGER.debug("Webservice done!");
		}
		return wsdpjr;
	}

	private boolean enoughMemoryForJob(long sumFileSize) { // testing ^^
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Need memory:"
					+ Math.pow(sumFileSize, magic_memory_number)
					/ 1024
					/ 1024
					+ " MBytes"
					+ " avail: "
					+ (Runtime.getRuntime().freeMemory() - Math.pow(
							sumFileSize, magic_memory_number)) / 1024 / 1024
					+ " MBytes");
		}

		if (Runtime.getRuntime().freeMemory() >= Math.pow(sumFileSize,
				magic_memory_number)) {
			return true;
		}
		return false;
	}
}
