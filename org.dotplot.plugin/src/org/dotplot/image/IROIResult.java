/*
 * Created on 01.07.2004
 */
package org.dotplot.image;

import java.io.File;

/**
 * Implementing objects are created when the user has selected a region of interest.
 * Given this roi, the corresponding files and lines are returned by the DotPlot.
 *
 * @author Tobias Gesellchen
 */
public interface IROIResult
{
   /**
    * File on the x-axis.
    *
    * @return a File object representing the XFile value
    */
   public File getXFile();

   /**
    * File on the y-axis.
    *
    * @return a File object representing the YFile value
    */
   public File getYFile();

   /**
    * Line in getXFile().
    *
    * @return an int representing the XLine number value
    */
   public int getXLineNumber();

   /**
    * Line in getYFile().
    *
    * @return an int representing the YLine number value
    */
   public int getYLineNumber();
}
