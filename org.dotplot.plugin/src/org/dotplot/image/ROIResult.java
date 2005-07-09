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
   File xFileInROI = null;
   File yFileInROI = null;

   int xLineNumber = 0;
   int yLineNumber = 0;

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
      xLineNumber = xLine;
      yLineNumber = yLine;
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
    * @see org.dotplot.image.IROIResult#getXLineNumber()
    */
   public int getXLineNumber()
   {
      return xLineNumber;
   }

   /**
    * @see org.dotplot.image.IROIResult#getYLineNumber()
    */
   public int getYLineNumber()
   {
      return yLineNumber;
   }

   /**
    * Overwritten to provide more information.
    */
   public String toString()
   {
      return "ROI " + "(" + xFileInROI.getAbsolutePath() + ":" + xLineNumber + ", " + yFileInROI.getAbsolutePath()
            + ":"
            + yLineNumber
            + ")";
   }
}
