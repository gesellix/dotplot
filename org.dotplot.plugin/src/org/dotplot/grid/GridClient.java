package org.dotplot.grid;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.grid.framework.Identity;
import org.dotplot.grid.framework.MessageCollaborator;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.Util;
import org.dotplot.util.FileUtil;

/**
 * Represents a Client for the grid.
 * 
 * @author Tobias Gesellchen
 */
public class GridClient extends MessageCollaborator {
	private static boolean scaleImage(final Dimension targetSize,
			final ITypeTableNavigator navigator) {
		boolean scale = false;
		if (!new Rectangle(targetSize)
				.equals(new Rectangle(navigator.getSize()))) {
			logger.info("scale to target size " + targetSize);
			scale = true;
		}
		return scale;
	}

	private PlotJob plotjob;

	private final static Logger logger = Logger.getLogger(GridClient.class
			.getName());

	/**
	 * Tag to label a message containing a part of an image.
	 */
	public final static String TAG_IMAGE = "plotimg";

	/**
	 * Tag to label a message containg a PlotJob.
	 */
	public final static String TAG_PLOTJOB = "plotjob";

	/**
	 * Provides the possibility to create a GridClient from the console.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws ConnectionException
	 *             if an error occurred
	 */
	public static void main(String[] args) throws ConnectionException {
		new GridClient(args[0], args[1], Integer.parseInt(args[2]));
	}

	/**
	 * Constructs a new GridClient.
	 * 
	 * @param name
	 *            the client's name
	 * @param host
	 *            server/mediator address
	 * @param port
	 *            port on the server
	 * 
	 * @throws ConnectionException
	 *             if connection fails.
	 */
	public GridClient(String name, String host, int port)
			throws ConnectionException {
		super(name, host, port);

		while (getIdentity() == null) {
			; // wait for ID
		}

		System.out.println("connected as " + getIdentity() + " to " + host
				+ ":" + port);
		logger.debug("Collaborator started, Identity: " + getIdentity());
	}

	private synchronized boolean export(final ITypeTableNavigator navigator,
			final Dimension targetSize, final Rectangle roi,
			final String filename, boolean scale,
			final IQImageConfiguration config) {
		try {
			if (scale) {
				Util.exportDotplotInROIByInfoMural(navigator, targetSize, roi,
						filename, config);
			}
			else {
				Util.exportDotplotInROI(navigator, roi, filename, config);
			}
		}
		catch (Exception e) {
			logger.error("Error exporting Dotplot", e);
			return false;
		}

		logger.debug("done. " + getIdentity());
		return true;
	}

	@Override
	public boolean notify(String tag, Object data, Identity src)
			throws IOException {
		super.notify(tag, data, src);
		return onNotify(tag, src, data);
	}

	private boolean onNotify(String tag, Identity src, Object data) {
		if (tag.equals(TAG_PLOTJOB)) {
			logger.debug("got plotjob from " + src);
			if (src.getName().equals(GridPlotter.getBigBossID())) {
				plotjob = FileUtil.importPlotJob((File) data);
			}
			else {
				logger.warn("got plotjob from wrong plotter. ID not correct!");
				return false;
			}
		}

		if (plotjob == null) {
			logger.debug("waiting for more...");
			return false;
		}

		File target = null;
		try {
			target = File.createTempFile("gridpart_" + getIdentity().getId()
					+ "_", ".jpeg");
			target.deleteOnExit();
		}
		catch (IOException e) {
			logger.error("error creating temporary file for export", e);
			return false;
		}

		if (!startPlotExport(target)) {
			return false;
		}

		try {
			if (!send(TAG_IMAGE, target, getMediatorIdentity())) {
				logger.error("trying broadcast...");
				if (broadcast(TAG_IMAGE, target)) {
					logger.error("broadcast ok!");
				}
				else {
					logger.error("broadcast failed!");
				}
			}
		}
		catch (IOException e) {
			logger.error("error sending plot image back to " + src);
			return false;
		}

		return true;
	}

	private void reset() {
		plotjob = null;
	}

	private boolean startPlotExport(File target) {
		// if (plotjob.getImageConfig() != null)
		// {
		// GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_IMG_CONFIGURATION,
		// plotjob.getImageConfig());
		// }

		final Dimension targetSize = plotjob.getTargetSize();
		final ITypeTableNavigator navigator = plotjob.getTypeTable()
				.createNavigator();

		final Identity identity = getIdentity();
		final int index = plotjob.getCollaborators().indexOf(identity);
		if (index == -1) {
			logger.warn("did not find id " + identity
					+ " in list of collaborators!");
			return false;
		}

		final int count = plotjob.getCollaborators().size();
		final int width = targetSize.width / count;
		final Rectangle roi = new Rectangle(index * width, 0, width,
				targetSize.height);

		final int notAssigned = targetSize.width % count;
		// could the image completely be divided?
		if (notAssigned != 0) {
			// no, check if i am the very right part of the image
			if (((index * width) + width + notAssigned) == targetSize.width) {
				// yes, so I will do the notAssigned part
				roi.width = width + notAssigned;
			}
		}

		logger.debug("ID: " + identity.getId() + ", ROI: " + (index + 1) + " "
				+ roi);
		logger.debug("starting export...");

		if (!export(navigator, targetSize, roi, target.getAbsolutePath(),
				scaleImage(targetSize, navigator), plotjob.getImageConfig())) {
			return false;
		}

		// reset data, so that I won't plot without all variables to be updated
		reset();

		return true;
	}
}
