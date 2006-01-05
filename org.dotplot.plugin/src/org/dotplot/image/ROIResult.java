/*
 * Created on 01.07.2004
 */
package org.dotplot.image;

import java.io.File;

/**
 * Region Of Interest - for usage with the diff file viewer.
 *
 * @author Tobias Gesellchen
 * @version 0.2
 * @see org.dotplot.image.IROIResult
 */
class ROIResult implements IROIResult
{
   private File xFileInROI = null;
   private File yFileInROI = null;

   private int xLineIndex = 0;
   private int yLineIndex = 0;

   private String xToken;
   private String yToken;

   /**
    * Constructs a ROIResult object from the given parameters.
    *
    * @param xFile file for the x-axis
    * @param yFile file for the y-axis
    * @param xLine line on the x-axis
    * @param yLine line on the y-axis
    */
   public ROIResult(File xFile, File yFile, int xLine, int yLine)
   {
      xFileInROI = xFile;
      yFileInROI = yFile;
      xLineIndex = xLine;
      yLineIndex = yLine;
   }

   /**
    * @see org.dotplot.image.IROIResult#getXFile()
    */
   public File getXFile()
   {
      return xFileInROI;
   }

   /**
    * @see org.dotplot.image.IROIResult#getYFile()
    */
   public File getYFile()
   {
      return yFileInROI;
   }

   /**
    * @see org.dotplot.image.IROIResult#getXLineIndex()
    */
   public int getXLineIndex()
   {
      return xLineIndex;
   }

   /**
    * @see org.dotplot.image.IROIResult#getYLineIndex()
    */
   public int getYLineIndex()
   {
      return yLineIndex;
   }

   public String getXToken()
   {
      return xToken;
   }

   public String getYToken()
   {
      return yToken;
   }

   public void setXToken(String xToken)
   {
      this.xToken = xToken;
   }

   public void setYToken(String yToken)
   {
      this.yToken = yToken;
   }

   /**
    * Overwritten to provide more information.
    */
   public String toString()
   {
      return "ROI " + "(" + xFileInROI.getAbsolutePath() + ":" + xLineIndex
            + ", " + yFileInROI.getAbsolutePath() + ":" + yLineIndex + ")";
   }
}
