/*
 * Created on 01.07.2004
 */
package org.dotplot.image;

import java.awt.Color;
import java.io.Serializable;

/**
 * Container for settings of the imaging module.
 *
 * @author Tobias Gesellchen
 */
public class QImageConfiguration implements Serializable
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = 67839459306018464L;

   // RegionOfInterest
//   private Rectangle roi;

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
    * Creates a basic configuration for the image module and uses the given settings.
    *
    * @param isExportDotplot     shall the dotplot be exported?
    * @param exportFormat        desired export format
    * @param exportFilename      desired export filename
    * @param useLUT              use grayscaling/colors
    * @param lutTitle            title of the desired LUT
    * @param background          "background" of the LUT
    * @param foreground          "foreground" of the LUT
    * @param scaleMode           desired scaling mode
    * @param doScaleUp           scale up small images
    * @param showFileSeparators  option to show or hide the file separators
    * @param useInformationMural option to use alternate algorithm
    */
   public QImageConfiguration(
         boolean isExportDotplot, int exportFormat, String exportFilename, boolean useLUT,
         String lutTitle, Color background, Color foreground, int scaleMode, boolean doScaleUp,
         boolean showFileSeparators, boolean useInformationMural)
   {
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

   /**
    * Export enabled?
    *
    * @return current setting
    *
    * @see #setExportDotplotToFile(boolean)
    */
   public boolean isExportDotplot()
   {
      return isExportDotplot;
   }

   /**
    * filename to export to.
    *
    * @return the filename
    *
    * @see #setExportFilename(String)
    */
   public String getExportFilename()
   {
      return exportFilename;
   }

   /**
    * image format to export to.
    *
    * @return the image format
    *
    * @see #setExportFormat(int)
    * @see JAITools.EXPORT_FORMATS
    */
   public int getExportFormat()
   {
      return exportFormat;
   }

   /**
    * If a plot is done from command line ("headless"), this will return true. For this case, e.g. no progress bar will be displayed.
    *
    * @return only do an export?
    *
    * @see #setOnlyExport(boolean)
    */
   public boolean isOnlyExport()
   {
      return isOnlyExport;
   }

   /**
    * activates the handling for a single export, without interaction on the GUI.
    *
    * @param onlyExport only do an export?
    *
    * @see #isOnlyExport()
    */
   public void setOnlyExport(boolean onlyExport)
   {
      isOnlyExport = onlyExport;
   }

   /**
    * use the lookup table to convert to a colored image?
    *
    * @return use the LUT?
    *
    * @see #setUseLUT(boolean)
    */
   public boolean useLUT()
   {
      return useLUT;
   }

   /**
    * returns the currently set LUT.
    *
    * @return the LUT
    *
    * @see #setLut(String, java.awt.Color, java.awt.Color)
    * @see LUTs
    */
   public int[][] getLut()
   {
      return useLUT() ? lut : LUTs.inverted_gray();
   }

   /**
    * the name of the LUT.
    *
    * @return the name of the LUT
    *
    * @see #setLut(String, java.awt.Color, java.awt.Color)
    * @see LUTs
    */
   public String getLutTitle()
   {
      return lutTitle;
   }

   /**
    * the user can set a background to change the current lut. this method will return the current setting in lut[x][0].
    *
    * @return the background of the current lut
    *
    * @see #setLutBackground(java.awt.Color)
    */
   public Color getLutBackground()
   {
      return new Color(lut[0][0], lut[1][0], lut[2][0]);
   }

   /**
    * the user can set a foreground to change the current lut. this method will return the current setting in lut[x][lut[x].length-1].
    *
    * @return the foreground of the current lut
    *
    * @see #setLutForeground(java.awt.Color)
    */
   public Color getLutForeground()
   {
      return new Color(lut[0][lut[0].length - 1], lut[1][lut[1].length - 1], lut[2][lut[2].length - 1]);
   }

   /**
    * returns the current setting for the scale mode. 0 means "no scaling", 1 means "scaling active".
    *
    * @return the current scale mode
    *
    * @see #setScaleMode(int)
    */
   public int getScaleMode()
   {
      return scaleMode;
   }

   /**
    * if scaleMode is set to 1, this setting tells whether to scale up small images or to only scale down.
    *
    * @return scale small images up?
    *
    * @see #setScaleUp(boolean)
    * @see #getScaleMode()
    */
   public boolean doScaleUp()
   {
      return doScaleUp;
   }

   /**
    * show the file borders in the dotplot?
    *
    * @return show the file borders?
    *
    * @see #setShowFileSeparators(boolean)
    */
   public boolean showFileSeparators()
   {
      return showFileSeparators;
   }

   /**
    * use Information Mural or the conventional methods?
    *
    * @return use IM?
    *
    * @see #setUseInformationMural(boolean)
    */
   public boolean useInformationMural()
   {
      return useInfoMural;
   }

   /**
    * enables/diables export.
    *
    * @param doExport flag
    *
    * @see #isExportDotplot()
    */
   public void setExportDotplotToFile(boolean doExport)
   {
      isExportDotplot = doExport;
   }

   /**
    * sets the target filename for export.
    *
    * @param string target filename
    *
    * @see #getExportFilename()
    */
   public void setExportFilename(String string)
   {
      exportFilename = string;
   }

   /**
    * the format to be used for export.
    *
    * @param i one of the options supported by the JAITools class
    *
    * @see #getExportFormat()
    * @see JAITools.EXPORT_FORMATS
    */
   public void setExportFormat(int i)
   {
      exportFormat = i;
   }

   /**
    * convert grayscale to colors?
    *
    * @param b flag
    *
    * @see #useLUT()
    */
   public void setUseLUT(boolean b)
   {
      useLUT = b;
   }

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
   public void setLut(String name, Color background, Color foreground)
   {
      lutTitle = name;

      try
      {
         lut = (int[][]) LUTs.class.getMethod(lutTitle, (Class[]) null).invoke(null, new Object[]{});
      }
      catch (Throwable exc)
      {
         exc.printStackTrace();
         System.err.flush();
      }

      setLutBackground(background);
      setLutForeground(foreground);
   }

   /**
    * sets the new background for the LUT.
    *
    * @param col the background color
    *
    * @see #getLutBackground()
    */
   public void setLutBackground(Color col)
   {
      if (col != null)
      {
         lut[0][0] = col.getRed();
         lut[1][0] = col.getGreen();
         lut[2][0] = col.getBlue();
      }
   }

   /**
    * sets the new foreground for the LUT.
    *
    * @param col the foreground color
    *
    * @see #getLutForeground()
    */
   public void setLutForeground(Color col)
   {
      if (col != null)
      {
         lut[0][lut[0].length - 1] = col.getRed();
         lut[1][lut[1].length - 1] = col.getGreen();
         lut[2][lut[2].length - 1] = col.getBlue();
      }
   }

   /**
    * sets the scale mode.
    *
    * @param i the scalemode
    *
    * @see #getScaleMode()
    */
   public void setScaleMode(int i)
   {
      scaleMode = i;
   }

   /**
    * activates upscaling of smaller images.
    *
    * @param b flag
    *
    * @see #doScaleUp()
    */
   public void setScaleUp(boolean b)
   {
      doScaleUp = b;
   }

   /**
    * enables/disables output of file borders in the dotplot.
    *
    * @param b flag
    *
    * @see #showFileSeparators()
    */
   public void setShowFileSeparators(boolean b)
   {
      showFileSeparators = b;
   }

   /**
    * enables/diables the use of the Information Mural algorithm.
    *
    * @param useInformationMural flag
    *
    * @see #useInformationMural()
    */
   public void setUseInformationMural(boolean useInformationMural)
   {
      this.useInfoMural = useInformationMural;
   }

//   public Rectangle getRoi()
//   {
//      return roi;
//   }

//   public void setRoi(Rectangle roi)
//   {
//      this.roi = roi;
//   }
}
