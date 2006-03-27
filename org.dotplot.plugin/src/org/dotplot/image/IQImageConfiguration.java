/**
 * 
 */
package org.dotplot.image;

import java.awt.Color;

import org.dotplot.core.IConfiguration;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface IQImageConfiguration extends IConfiguration{

	/**
	 * Export enabled?
	 *
	 * @return current setting
	 *
	 * @see #setExportDotplotToFile(boolean)
	 */
	public abstract boolean isExportDotplotToFile();

	/**
	 * filename to export to.
	 *
	 * @return the filename
	 *
	 * @see #setExportFilename(String)
	 */
	public abstract String getExportFilename();

	/**
	 * image format to export to.
	 *
	 * @return the image format
	 *
	 * @see #setExportFormat(int)
	 * @see JAITools.EXPORT_FORMATS
	 */
	public abstract int getExportFormat();

	/**
	 * If a plot is done from command line ("headless"), this will return true. For this case, e.g. no progress bar will be displayed.
	 *
	 * @return only do an export?
	 *
	 * @see #setOnlyExport(boolean)
	 */
	public abstract boolean isOnlyExport();

	/**
	 * activates the handling for a single export, without interaction on the GUI.
	 *
	 * @param onlyExport only do an export?
	 *
	 * @see #isOnlyExport()
	 */
	public abstract void setOnlyExport(boolean onlyExport);

	/**
	 * use the lookup table to convert to a colored image?
	 *
	 * @return use the LUT?
	 *
	 * @see #setUseLUT(boolean)
	 */
	public abstract boolean useLUT();

	/**
	 * returns the currently set LUT.
	 *
	 * @return the LUT
	 *
	 * @see #setLut(String, java.awt.Color, java.awt.Color)
	 * @see LUTs
	 */
	public abstract int[][] getLut();

	/**
	 * the name of the LUT.
	 *
	 * @return the name of the LUT
	 *
	 * @see #setLut(String, java.awt.Color, java.awt.Color)
	 * @see LUTs
	 */
	public abstract String getLutTitle();

	/**
	 * the user can set a background to change the current lut. this method will return the current setting in lut[x][0].
	 *
	 * @return the background of the current lut
	 *
	 * @see #setLutBackground(java.awt.Color)
	 */
	public abstract Color getLutBackground();

	/**
	 * the user can set a foreground to change the current lut. this method will return the current setting in lut[x][lut[x].length-1].
	 *
	 * @return the foreground of the current lut
	 *
	 * @see #setLutForeground(java.awt.Color)
	 */
	public abstract Color getLutForeground();

	/**
	 * returns the current setting for the scale mode. 0 means "no scaling", 1 means "scaling active".
	 *
	 * @return the current scale mode
	 *
	 * @see #setScaleMode(int)
	 */
	public abstract int getScaleMode();

	/**
	 * if scaleMode is set to 1, this setting tells whether to scale up small images or to only scale down.
	 *
	 * @return scale small images up?
	 *
	 * @see #setScaleUp(boolean)
	 * @see #getScaleMode()
	 */
	public abstract boolean doScaleUp();

	/**
	 * show the file borders in the dotplot?
	 *
	 * @return show the file borders?
	 *
	 * @see #setShowFileSeparators(boolean)
	 */
	public abstract boolean showFileSeparators();

	/**
	 * use Information Mural or the conventional methods?
	 *
	 * @return use IM?
	 *
	 * @see #setUseInformationMural(boolean)
	 */
	public abstract boolean useInformationMural();

	/**
	 * enables/diables export.
	 *
	 * @param doExport flag
	 *
	 * @see #isExportDotplotToFile()
	 */
	public abstract void setExportDotplotToFile(boolean doExport);

	/**
	 * sets the target filename for export.
	 *
	 * @param string target filename
	 *
	 * @see #getExportFilename()
	 */
	public abstract void setExportFilename(String string);

	/**
	 * the format to be used for export.
	 *
	 * @param i one of the options supported by the JAITools class
	 *
	 * @throws IllegalArgumentException - if the  is invalid
	 * 
	 * @see #getExportFormat()
	 * @see JAITools.EXPORT_FORMATS
	 */
	public abstract void setExportFormat(int i);

	/**
	 * convert grayscale to colors?
	 *
	 * @param b flag
	 *
	 * @see #useLUT()
	 */
	public abstract void setUseLUT(boolean b);

	/**
	 * sets the LUT to be used, if useLUT() returns true.
	 *
	 * @param name       name of the LUT
	 * @param background the background
	 * @param foreground the foreground
	 *
	 * @see LUTs
	 * @see #getLut()
	 * @see #useLUT()
	 * @see #setLutBackground(java.awt.Color)
	 * @see #setLutForeground(java.awt.Color)
	 */
	public abstract void setLut(String name, Color background, Color foreground);

	/**
	 * sets the new background for the LUT.
	 *
	 * @param col the background color
	 *
	 * @see #getLutBackground()
	 */
	public abstract void setLutBackground(Color col);

	/**
	 * sets the new foreground for the LUT.
	 *
	 * @param col the foreground color
	 *
	 * @see #getLutForeground()
	 */
	public abstract void setLutForeground(Color col);

	/**
	 * sets the scale mode.
	 *
	 * @param i the scalemode
	 *
	 * @see #getScaleMode()
	 */
	public abstract void setScaleMode(int i);

	/**
	 * activates upscaling of smaller images.
	 *
	 * @param b flag
	 *
	 * @see #doScaleUp()
	 */
	public abstract void setScaleUp(boolean b);

	/**
	 * enables/disables output of file borders in the dotplot.
	 *
	 * @param b flag
	 *
	 * @see #showFileSeparators()
	 */
	public abstract void setShowFileSeparators(boolean b);

	/**
	 * enables/diables the use of the Information Mural algorithm.
	 *
	 * @param useInformationMural flag
	 *
	 * @see #useInformationMural()
	 */
	public abstract void setUseInformationMural(boolean useInformationMural);

}