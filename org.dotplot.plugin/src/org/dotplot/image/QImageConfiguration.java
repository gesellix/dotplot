/*
 * Created on 01.07.2004
 */
package org.dotplot.image;

import java.awt.Color;
import java.io.Serializable;
import java.util.StringTokenizer;

import org.dotplot.core.IConfiguration;

/**
 * Container for settings of the imaging module.
 * 
 * @author Tobias Gesellchen
 */
public class QImageConfiguration implements Serializable, IQImageConfiguration {
	/**
	 * for being Serializable
	 */
	private static final long serialVersionUID = 67839459306018464L;

	// RegionOfInterest
	// private Rectangle roi;

	// only export and no plot for screen?
	private boolean isOnlyExport;

	// Export Dotplots yes/no
	private boolean isExportDotplot;

	// Export format
	private int exportFormat;

	// Export filename
	private String exportFilename;

	// LUT
	private String lutTitle;

	private int[][] lut;

	// Scaling mode
	private int scaleMode;

	// Scale up small images
	private boolean doScaleUp;

	// File separators (if #files > 1)
	private boolean showFileSeparators;

	// Use grayscaling and/or colors
	private boolean useLUT;

	// Use algorithm "Information Mural"
	private boolean useInfoMural;

	/**
	 * Creates a new <code>QImageConfiguration</code> with default values.
	 */
	public QImageConfiguration() {
		this(false, JAITools.JPG, "/dotplot", true, "inverted_gray", null,
				null, 1, true, true, true);
		// // Do Export
		// setExportDotplotToFile(false);
		//
		// // Export format
		// setExportFormat(JAITools.JPG);
		//
		// // Export filename
		// setExportFilename("/dotplot");
		//
		// // LUT
		// setUseLUT(true);
		// setLut("inverted_gray", null, null);
		//
		// // Scaling mode
		// setScaleMode(1);
		//
		// // Scale up small images
		// setScaleUp(true);
		//
		// // File separators
		// setShowFileSeparators(true);
		//
		// // Information Mural
		// setUseInformationMural(true);
	}

	/**
	 * Creates a basic configuration for the image module and uses the given
	 * settings.
	 * 
	 * @param isExportDotplot
	 *            shall the dotplot be exported?
	 * @param exportFormat
	 *            desired export format
	 * @param exportFilename
	 *            desired export filename
	 * @param useLUT
	 *            use grayscaling/colors
	 * @param lutTitle
	 *            title of the desired LUT
	 * @param background
	 *            "background" of the LUT
	 * @param foreground
	 *            "foreground" of the LUT
	 * @param scaleMode
	 *            desired scaling mode
	 * @param doScaleUp
	 *            scale up small images
	 * @param showFileSeparators
	 *            option to show or hide the file separators
	 * @param useInformationMural
	 *            option to use alternate algorithm
	 */
	public QImageConfiguration(boolean isExportDotplot, int exportFormat,
			String exportFilename, boolean useLUT, String lutTitle,
			Color background, Color foreground, int scaleMode,
			boolean doScaleUp, boolean showFileSeparators,
			boolean useInformationMural) {
		// Do Export
		setExportDotplotToFile(isExportDotplot);

		// Export format
		setExportFormat(exportFormat);

		// Export filename
		setExportFilename(exportFilename);

		// LUT
		setUseLUT(useLUT);
		setLut(lutTitle, background, foreground);

		// Scaling mode
		setScaleMode(scaleMode);

		// Scale up small images
		setScaleUp(doScaleUp);

		// File separators
		setShowFileSeparators(showFileSeparators);

		// Information Mural
		setUseInformationMural(useInformationMural);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#copy()
	 */
	public IConfiguration copy() {
		return new QImageConfiguration(this.isExportDotplot, this.exportFormat,
				this.exportFilename, this.useLUT, this.lutTitle, this
						.getLutBackground(), this.getLutForeground(),
				this.scaleMode, this.doScaleUp, this.showFileSeparators,
				this.useInfoMural);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#doScaleUp()
	 */
	public boolean doScaleUp() {
		return doScaleUp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getExportFilename()
	 */
	public String getExportFilename() {
		return exportFilename;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getExportFormat()
	 */
	public int getExportFormat() {
		return exportFormat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getLut()
	 */
	public int[][] getLut() {
		return useLUT() ? lut : LUTs.inverted_gray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getLutBackground()
	 */
	public Color getLutBackground() {
		return new Color(lut[0][0], lut[1][0], lut[2][0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getLutForeground()
	 */
	public Color getLutForeground() {
		return new Color(lut[0][lut[0].length - 1], lut[1][lut[1].length - 1],
				lut[2][lut[2].length - 1]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getLutTitle()
	 */
	public String getLutTitle() {
		return lutTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#getScaleMode()
	 */
	public int getScaleMode() {
		return scaleMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#isExportDotplot()
	 */
	public boolean isExportDotplotToFile() {
		return isExportDotplot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#isOnlyExport()
	 */
	public boolean isOnlyExport() {
		return isOnlyExport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#objectForm(java.lang.String)
	 */
	public IConfiguration objectForm(String serivalizedForm)
			throws UnsupportedOperationException {
		if (serivalizedForm == null) {
			throw new NullPointerException();
		}
		StringTokenizer tokenizer = new StringTokenizer(serivalizedForm, ";");
		boolean exportDotplot, useLut, scaleUp, showSeperators, useInfoMural;
		String exportFilename, lutTitle;
		int exportFormat, scaleMode;
		Color background, foreground;

		try {
			exportDotplot = new Boolean(tokenizer.nextToken());
			exportFormat = new Integer(tokenizer.nextToken());
			exportFilename = tokenizer.nextToken();
			useLut = new Boolean(tokenizer.nextToken());
			lutTitle = tokenizer.nextToken();
			String color = tokenizer.nextToken();
			if (color.equals("null")) {
				background = null;
			}
			else {
				background = new Color(new Integer(color));
			}
			color = tokenizer.nextToken();
			if (color.equals("null")) {
				foreground = null;
			}
			else {
				foreground = new Color(new Integer(color));
			}
			scaleMode = new Integer(tokenizer.nextToken());
			scaleUp = new Boolean(tokenizer.nextToken());
			showSeperators = new Boolean(tokenizer.nextToken());
			useInfoMural = new Boolean(tokenizer.nextToken());
			if (tokenizer.hasMoreElements()) {
				throw new IllegalArgumentException("too much tokens");
			}
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		QImageConfiguration config = new QImageConfiguration(exportDotplot,
				exportFormat, exportFilename, useLut, lutTitle, background,
				foreground, scaleMode, scaleUp, showSeperators, useInfoMural);
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#serializedForm()
	 */
	public String serializedForm() throws UnsupportedOperationException {

		StringBuffer buffer = new StringBuffer();
		buffer.append(this.isExportDotplot);
		buffer.append(";");
		buffer.append(this.exportFormat);
		buffer.append(";");
		buffer.append(this.exportFilename);
		buffer.append(";");
		buffer.append(this.useLUT);
		buffer.append(";");
		buffer.append(this.lutTitle);
		buffer.append(";");

		if (this.getLutBackground() == null) {
			buffer.append("null");
		}
		else {
			buffer.append(this.getLutBackground().getRGB());
		}

		buffer.append(";");

		if (this.getLutForeground() == null) {
			buffer.append("null");

		}
		else {
			buffer.append(this.getLutForeground().getRGB());
		}

		buffer.append(";");
		buffer.append(this.scaleMode);
		buffer.append(";");
		buffer.append(this.doScaleUp);
		buffer.append(";");
		buffer.append(this.showFileSeparators);
		buffer.append(";");
		buffer.append(this.useInfoMural);

		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setExportDotplotToFile(boolean)
	 */
	public void setExportDotplotToFile(boolean doExport) {
		isExportDotplot = doExport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setExportFilename(java.lang.String
	 * )
	 */
	public void setExportFilename(String string) {
		if (string == null) {
			throw new NullPointerException();
		}
		exportFilename = string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setExportFormat(int)
	 */
	public void setExportFormat(int i) {
		if (i < 0 || i >= JAITools.EXPORT_FORMATS.length) {
			throw new IllegalArgumentException("illegal format constant");
		}
		exportFormat = i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setLut(java.lang.String,
	 * java.awt.Color, java.awt.Color)
	 */
	public void setLut(String name, Color background, Color foreground) {
		lutTitle = name;

		try {
			lut = (int[][]) LUTs.class.getMethod(lutTitle, (Class[]) null)
					.invoke(null, new Object[] {});
		}
		catch (Throwable exc) {
			exc.printStackTrace();
			System.err.flush();
		}

		setLutBackground(background);
		setLutForeground(foreground);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setLutBackground(java.awt.Color)
	 */
	public void setLutBackground(Color col) {
		if (col != null) {
			lut[0][0] = col.getRed();
			lut[1][0] = col.getGreen();
			lut[2][0] = col.getBlue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setLutForeground(java.awt.Color)
	 */
	public void setLutForeground(Color col) {
		if (col != null) {
			lut[0][lut[0].length - 1] = col.getRed();
			lut[1][lut[1].length - 1] = col.getGreen();
			lut[2][lut[2].length - 1] = col.getBlue();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setOnlyExport(boolean)
	 */
	public void setOnlyExport(boolean onlyExport) {
		isOnlyExport = onlyExport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setScaleMode(int)
	 */
	public void setScaleMode(int i) {
		scaleMode = i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setScaleUp(boolean)
	 */
	public void setScaleUp(boolean b) {
		doScaleUp = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setShowFileSeparators(boolean)
	 */
	public void setShowFileSeparators(boolean b) {
		showFileSeparators = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.image.IQImageConfiguration#setUseInformationMural(boolean)
	 */
	public void setUseInformationMural(boolean useInformationMural) {
		this.useInfoMural = useInformationMural;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#setUseLUT(boolean)
	 */
	public void setUseLUT(boolean b) {
		useLUT = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#showFileSeparators()
	 */
	public boolean showFileSeparators() {
		return showFileSeparators;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#useInformationMural()
	 */
	public boolean useInformationMural() {
		return useInfoMural;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.image.IQImageConfiguration#useLUT()
	 */
	public boolean useLUT() {
		return useLUT;
	}

	// public Rectangle getRoi()
	// {
	// return roi;
	// }

	// public void setRoi(Rectangle roi)
	// {
	// this.roi = roi;
	// }
}
