/*
 * Created on 12.05.2004
 */
package org.dotplot.core;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.media.jai.PlanarImage;

import org.dotplot.image.IROIResult;
import org.eclipse.swt.graphics.ImageData;

/**
 * Interface and Constants for use in QImage and Dotplot.
 *
 * @author Tobias Gesellchen
 */
public interface IDotplot
{
   // fields used for the ProgressMonitor

   /**
    * Plotting the Dotplot consists of 6 steps.
    */
   public static final int STEPS_COUNT = 6; // == STEPS.length

   /**
    * Precalculation of the width for each step.
    */
   public static final int STEPS_WIDTH = 100 / STEPS_COUNT;

   /**
    * First step: allocate memory for the image.
    */
   public static final int STEP_ALLOCATE_IMAGE = 1;

   /**
    * Second step: initialize background.
    */
   public static final int STEP_BACKGROUND = 2;

   /**
    * Third step: read matches from FMatrix.
    */
   public static final int STEP_READING_MATCHES = 3;

   /**
    * Fourth step: scale (optional).
    */
   public static final int STEP_SCALING = 4;

   /**
    * Fifth step: apply LUT.
    */
   public static final int STEP_LUT = 5;

   /**
    * Sixth step: convert image to the "eclipse image" format.
    */
   public static final int STEP_CONVERT_DATA = 6;

   /**
    * Messages for progress monitor.
    */
   public static final String[] STEPS = {
      "Init...",
      "Allocating image buffer",
      "Setting background...",
      "Reading matches",
      "Scaling",
      "Color conversion",
      "Converting image data..."
   };

   /**
    * selector for the supported class types.
    *
    * @see #getImage(Class)
    */
   public static final Class IMG_SWT_IMAGEDATA = ImageData.class;
   /**
    * selector for the supported class types.
    *
    * @see #getImage(Class)
    */
   public static final Class IMG_JAI_PLANARIMAGE = PlanarImage.class;

   /**
    * Lets the Dotplot return a representation of the internal image,
    * using the desired class type.
    * If the given class type does not exist, <code>null</code> will be returned.
    *
    * @param imageClass one of the supported class types
    *
    * @return the image, or null if problems occured
    *
    * @see #IMG_SWT_IMAGEDATA
    * @see #IMG_JAI_PLANARIMAGE
    */
   public Object getImage(Class imageClass);

   /**
    * Tells the dotplot to scale images to the given <code>size</code>.
    * File exports are not affected.
    *
    * @param size a Dimension object specifying the target size value
    *
    * @return the new scale factor
    */
   public float setTargetSize(Dimension size);

   /**
    * Lets the dotplot zoom into the given region of interest <code>roi</code>
    * and returns corresponding file information.
    * <p />
    * TODO move method to image.Util
    *
    * @param roi     a Rectangle object specifying the ROI value
    * @param imgData a link to the currently displayed image
    *
    * @return an IROIResult object
    */
   public IROIResult getDetailsForROI(Rectangle roi, ImageData imgData);
}
