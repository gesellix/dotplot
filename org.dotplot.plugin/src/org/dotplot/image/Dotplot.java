package org.dotplot.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.media.jai.TiledImage;

import org.apache.log4j.Logger;
import org.dotplot.core.IDotplot;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.TokenInformation;
import org.eclipse.swt.graphics.ImageData;

/**
 * Implementation of IDotplot using JAI and Information Mural.
 * 
 * @author Tobias Gesellchen
 */
class Dotplot implements IDotplot {
	private static Logger logger = Logger.getLogger(Dotplot.class.getName());

	/**
	 * is the current ImageData <code>dotplotForScreen</code> to be filled?
	 */
	private boolean isDirty = true;

	private QImage qImage = null;

	private static ITypeTableNavigator navigator;

	private static TiledImage tiledImage;

	private static ImageData dotplotForScreen;

	private static Dimension targetSize;

	private static float[] scale = { 1, 1 };

	// private IQImageConfiguration config;

	/**
	 * @param nav
	 *            - ITypeTableNavigator providing data
	 * @param monitor
	 *            - connection to the controller QImage
	 */
	Dotplot(ITypeTableNavigator nav, QImage monitor) {
		navigator = nav;
		Dimension originalSize = navigator.getSize();

		isDirty = true;

		qImage = monitor;
		setTargetSize(originalSize);

		logger.debug("originalSize: " + originalSize.width + "*"
				+ originalSize.height + "=" + originalSize.width
				* originalSize.height + " pixels...");
	}

	/**
	 * @see org.dotplot.core.IDotplot#getDetailsForROI(Rectangle, ImageData)
	 *      TODO move to Util-class
	 */
	public IROIResult getDetailsForROI(Rectangle roi, ImageData imgData) {
		if (dotplotForScreen == null) {
			if (imgData != null) {
				dotplotForScreen = imgData;
				scale = new float[] { 1, 1 };
			}
			else {
				return null;
			}
		}

		/*
		 * TODO scale ROI, if neccessary and applicable ROIShape roishape = new
		 * ROIShape(roi); this.roi = QImage.createROIFromImage(
		 * this.tiledImage.getSubImage(roi.x, roi.y, roi.width, roi.height));
		 */

		TokenInformation tokenInfo = navigator.getTokenInformation();

		int xIndex = Math.min(Math.max((int) (roi.x / scale[0]), 0),
				dotplotForScreen.width - 1);
		int yIndex = Math.min(Math.max((int) (roi.y / scale[1]), 0),
				dotplotForScreen.height - 1);

		logger.debug("new ix: " + xIndex + ", " + yIndex);

		int xFileID = tokenInfo.getFileIndex(xIndex);
		int yFileID = tokenInfo.getFileIndex(yIndex);

		logger.debug("fileIDs: " + xFileID + ", " + yFileID);

		String xFile = tokenInfo.getFileName(xFileID);
		String yFile = tokenInfo.getFileName(yFileID);

		int xLine = 0;
		int yLine = 0;
		try {
			xLine = tokenInfo.getLineIndex(xFileID, xIndex);
			yLine = tokenInfo.getLineIndex(yFileID, yIndex);
		}
		catch (Exception e) {
			// TODO übelst... was tun?
			return null;
		}

		logger.debug("ROI ix: " + roi.x + "-->" + xIndex + ", " + roi.y + "-->"
				+ yIndex);

		final File xFileHandle = new File(xFile);
		final File yFileHandle = new File(yFile);
		try {
			logger.debug("ROI File x: " + xFileHandle.getCanonicalPath());
			logger.debug("ROI File y: " + yFileHandle.getCanonicalPath());
		}
		catch (IOException e) {
			// not too bad, ignore error
			// logger.error(e.getMessage(), e);
		}

		IROIResult result = new ROIResult(xFileHandle, yFileHandle, xLine,
				yLine);
		logger.debug(result);

		return result;
	}

	private TiledImage getDotplotJAI(Dimension size) {
		if (isDirty || tiledImage == null) {
			logger.debug("allocating image buffer...");

			qImage.update(1, STEP_ALLOCATE_IMAGE);

			tiledImage = Util.createEmptyTiledImage(size);

			logger.debug("tiled image initiated.");

			qImage.update(STEPS_WIDTH, STEP_BACKGROUND);

			navigator.reset();

			Util.createImage(navigator, null, getImageCallback4JAI(tiledImage),
					qImage.getConfiguration());

			isDirty = false;
		}

		return tiledImage;
	}

	private ImageData getDotplotSWT() {
		if (isDirty || dotplotForScreen == null) {
			logger.debug("creating new Dotplot for screen");

			Dimension tSize;
			if (qImage.getConfiguration().getScaleMode() == 0) {
				tSize = navigator.getSize();
			}
			else {
				tSize = targetSize;
			}

			navigator.reset();

			if (qImage.getConfiguration().useInformationMural()) {
				dotplotForScreen = getImageDataScaledByInfoMural(tSize);
			}
			else {
				if (navigator.getSize().width * navigator.getSize().height < 0) {
					logger
							.error("Overflow, size too big! Use Information Mural!");
					qImage.update(100, STEP_CONVERT_DATA);
					return null;
				}

				try {
					if (Boolean.getBoolean("image.highquality")) {
						dotplotForScreen = getImageDataScaledByJAI();
					}
					else {
						dotplotForScreen = getImageDataScaledBySWT(targetSize);
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					logger
							.error("Overflow, size too big! Use Information Mural!");
					qImage.update(100, STEP_CONVERT_DATA);
					return null;
				}
			}

			isDirty = false;
		}

		qImage.update(100, STEP_CONVERT_DATA);

		return dotplotForScreen;
	}

	public Object getImage(Class imageClass) {
		Object image = null;

		if (imageClass == IMG_SWT_IMAGEDATA) {
			image = getDotplotSWT();
		}
		else if (imageClass == IMG_JAI_PLANARIMAGE) {
			image = getDotplotJAI(navigator.getSize());
		}
		else {
			throw new IllegalArgumentException("Image class not supported");
		}

		if (image == null) {
			throw new IllegalStateException("Image could not be created");
		}

		QImage.runGC();
		return image;
	}

	private ImageCallback getImageCallback4ImageData(final ImageData data) {
		return new ImageCallback() {
			private int getPixel(ImageData image, int x, int y, boolean inverted) {
				Color col = new Color(image.getPixel(x, y));
				if (inverted) {
					col = new Color(255 - col.getRed(), 255 - col.getGreen(),
							255 - col.getBlue());
				}
				return col.getRGB();
			}

			public int getPixel(int x, int y) {
				return data.getPixel(x, y);
			}

			public Dimension getSize() {
				return new Dimension(data.width, data.height);
			}

			public void invertLine(int index) {
				final int line = (int) (index * scale[0]);
				logger.debug("ix --> line: " + index + " --> " + line);
				if (new Rectangle(getSize()).contains(new Point(line, line))) {
					invertLineHorizontal(data, line);
					invertLineVertical(data, line);
				}
			}

			private void invertLineHorizontal(ImageData image, int line) {
				for (int i = 0; i < image.width; i++) {
					image.setPixel(i, line, getPixel(image, i, line, true));
				}
			}

			private void invertLineVertical(ImageData image, int line) {
				for (int i = 0; i < image.height; i++) {
					image.setPixel(line, i, getPixel(image, line, i, true));
				}
			}

			public void setPixel(int x, int y, int rgb) {
				data.setPixel(x, y, rgb);
			}

			public void updateProgress(int diff, int curStep, String msg) {
				qImage.update(diff, curStep, msg);
			}
		};
	}

	private ImageCallback getImageCallback4JAI(final TiledImage tiledImage) {
		return new ImageCallback() {
			public int getPixel(int x, int y) {
				return Util.getPixel(tiledImage, x, y).getRGB();
			}

			public Dimension getSize() {
				return tiledImage.getBounds().getSize();
			}

			public void invertLine(int index) {
				Util.invertLines(tiledImage, index);
			}

			public void setPixel(int x, int y, int rgb) {
				Util.setPixel(tiledImage, x, y, new Color(rgb));
			}

			public void updateProgress(int diff, int curStep, String msg) {
				qImage.update(diff, curStep, msg);
			}
		};
	}

	private ImageData getImageDataScaledByInfoMural(Dimension targetSize) {
		final ImageData data = Util.createEmptyImageData(targetSize);

		final ImageCallback imageCallback = getImageCallback4ImageData(data);
		InformationMural.getMural(navigator, targetSize, imageCallback, qImage
				.getConfiguration());

		int[] indices = null;

		if (navigator.getTokenInformation() == null) {
			indices = new int[] { 0 };
		}
		else {
			indices = navigator.getTokenInformation().getAllStartIndices();
		}

		final int[] fileIndices = indices;

		final boolean showFileSeparators = qImage.getConfiguration()
				.showFileSeparators()
				&& (fileIndices.length > 1);
		if (showFileSeparators) {
			final double scale = imageCallback.getSize().getWidth()
					/ navigator.getSize().width;
			Util.invertLines(fileIndices, imageCallback, scale);
		}

		return data;
	}

	private ImageData getImageDataScaledByJAI() {
		final IQImageConfiguration imageConfig = qImage.getConfiguration();
		imageConfig.setUseLUT(false);
		getDotplotJAI(navigator.getSize());
		imageConfig.setUseLUT(true);

		Raster raster = JAITools.getRasterWithLUT(tiledImage, scale, qImage);
		targetSize = raster.getBounds().getSize();

		final ImageData data = Util.createEmptyImageData(targetSize);

		Util.getDotplotForScreenSWT(raster, getImageCallback4ImageData(data));

		return data;
	}

	private ImageData getImageDataScaledBySWT(Dimension targetSize) {
		final ImageData data = Util.createEmptyImageData(navigator.getSize());

		Util.createImage(navigator, qImage.getConfiguration().getLut(),
				getImageCallback4ImageData(data), qImage.getConfiguration());

		int minQuad = Math.min(targetSize.width, targetSize.height);
		return data.scaledTo(minQuad, minQuad);
	}

	/**
	 * @see org.dotplot.core.IDotplot#setTargetSize(Dimension)
	 */
	public float setTargetSize(Dimension newSize) {
		final int min = Math.min(newSize.width, newSize.height);

		targetSize = new Dimension(min, min);

		// final int product = targetSize.width * targetSize.height;
		// if (product < 0 || product > Integer.MAX_VALUE)
		// {
		// //throw new IllegalStateException(
		// //"Size (" + product + " pixels) too big/overflow!");
		// }

		scale[0] = (float) min / (float) navigator.getSize().width;
		scale[1] = (float) min / (float) navigator.getSize().height;

		scale[0] = scale[1] = Math.min(scale[0], scale[1]);

		this.isDirty = true;

		logger.debug("new size --> scale: " + scale[0]);

		return scale[0];
	}

}
