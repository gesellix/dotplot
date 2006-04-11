/*
 * Created on 02.12.2004
 */
package org.dotplot.image;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import javax.media.jai.TiledImage;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;

import org.dotplot.core.IDotplot;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.Match;
import org.dotplot.fmatrix.TokenInformation;

/**
 * Container class for several "tools" used in the plugin.
 *
 * @author Tobias Gesellchen
 */
public final class Util {
   private static final Logger logger = Logger.getLogger(Util.class.getName());
   /**
    * Number of bands to be used.
    */
   public static final int NUM_BANDS = 3;
   /**
    * Color depth of the image per band.
    */
   public static final int NUM_BITS_PER_BAND_PER_PIXEL = 8;
   /**
    * Color depth of the image.
    */
   public static final int COLOR_DEPTH = NUM_BANDS * NUM_BITS_PER_BAND_PER_PIXEL;
   /**
    * Number of colors ("color depth").
    */
   public static final int COLOR_COUNT_PER_BAND = (int) Math.pow(2, NUM_BITS_PER_BAND_PER_PIXEL);

   private static final int MAX_COL_VAL = COLOR_COUNT_PER_BAND - 1;

   static void getDotplotForScreenSWT(Raster raster, ImageCallback image) {
      image.updateProgress(IDotplot.STEPS_WIDTH / 2, IDotplot.STEP_CONVERT_DATA, "creating screen data");
      logger.debug("creating buffer for screen...");

      Util.getSWTImage(image, raster);

      logger.debug("ImageData ready for screen");
   }

   private static void getSWTImage(ImageCallback image, Raster imageData) {
      /*
         TODO is it faster to save to a temp file and then load the image
              of this file to show it on screen?

               File tmpDP = new File("./tmp_dp.jpeg");
               JAITools.saveJAI(
                  displayImage,
                  tmpDP.getAbsolutePath(),
                  JAITools.EXPORT_FORMATS[1]);
               logger.debug("temporary file saved.");
      */
      //dotplotForScreen = new ImageData(tmpDP.getAbsolutePath());

      Dimension size = image.getSize();

      long count = size.width * size.height;
      long step = count / 100;
      int percent = -1;

      boolean showProgress = logger.isDebugEnabled();

      for (int x = 0; x < size.width; x++) {
         for (int y = 0; y < size.height; y++) {
            int red = imageData.getSample(x, y, 0);
            int green = imageData.getSample(x, y, 1);
            int blue = imageData.getSample(x, y, 2);

            image.setPixel(x, y, new Color(red, green, blue).getRGB());

            if (showProgress) {
               count--;
               percent = printProgress(percent, count, step, image);
            }
         }
      }
   }

   private static int printProgress(int percent, long count, long step, ImageCallback image) {
      if ((step != 0) && ((count % step) == 0) && (percent < 100)) {
         percent++;
         logger.debug("todo: " + count + " (" + percent + "% ready)");

         image.updateProgress((percent / 100) * IDotplot.STEPS_WIDTH, IDotplot.STEP_READING_MATCHES, null);
      }
      return percent;
   }

   private static int convertToColor(double weight, int maxVal, boolean useLut) {
      if (!useLut) {
         return maxVal;
      }

      if (weight < 0.0 || weight > 1.0) {
         return maxVal;
      }

//      return (int) ((weight) * maxVal);
//      return (int) ((weight / 2.0 + 0.5) * maxVal);
//      return (int) ((weight + 0.5) * maxVal) & maxVal;
      return (int) (Math.min(1.0, weight + 0.5) * maxVal);
   }

   synchronized static void createImage(
         ITypeTableNavigator navData, int[][] lut, ImageCallback image, final IQImageConfiguration imageConfig) {
      int[] fileIndices = navData.getTokenInformation().getAllStartIndices();

      long count = navData.getNumberOfAllMatches();
      long step = count / 100;
      int percent = -1;
      boolean showProgress = logger.isDebugEnabled();

      int col;
      Match match;

      image.updateProgress(IDotplot.STEPS_WIDTH, IDotplot.STEP_READING_MATCHES, null);

      if (lut == null) {
         if (imageConfig.useLUT()) {
//            System.err.println("lut: " + imageConfig.getLutTitle());
            lut = imageConfig.getLut();
         }
         else {
            lut = LUTs.gray();
         }
      }

      // if background color not "black", paint background color (lut[i][0]) on empty image
      final Dimension size = image.getSize();

      final long max = size.width * size.height;
      long k = 0;
      final long kstep = max / 25;

      if (!(lut[0][0] == 0 && lut[1][0] == 0 && lut[2][0] == 0)) {
         final int color = new Color(lut[0][0], lut[1][0], lut[2][0]).getRGB();
         for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
               image.setPixel(i, j, color);
               if (logger.isDebugEnabled()) {
                  k++;
                  if (kstep > 0 && (k % kstep) == 0) {
                     logger.debug("" + k);
                  }
               }
            }
         }
      }

      logger.debug("filling image...");
      navData.reset();
      while ((match = navData.getNextMatch()) != null) {
         // Convert the weight value to an rgb (grayscale) value
         col = convertToColor(match.getWeight(), MAX_COL_VAL, imageConfig.useLUT());

         // put current pixel into image
         image.setPixel(match.getX(), match.getY(), new Color(lut[0][col], lut[1][col], lut[2][col]).getRGB());

         if (showProgress) {
            count--;
            percent = printProgress(percent, count, step, image);
         }
      }
      logger.debug("*");

      final boolean showFileSeparators = imageConfig.showFileSeparators() && (fileIndices.length > 1);
      if (showFileSeparators) {
         invertLines(fileIndices, image);
      }

      logger.debug("image ready.");
   }

   static ImageData createEmptyImageData(Dimension size) {
      return new ImageData(size.width, size.height, COLOR_DEPTH, new PaletteData(0xFF0000, 0x00FF00, 0x0000FF));
   }

   static TiledImage createEmptyTiledImage(Dimension size) {
      logger.debug("creating TiledImage with size " + size);

      // only temporary to create the corresponding SampleModel and ColorModel
      BufferedImage bim = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

      return new TiledImage(0, 0, size.width, size.height, 0, 0, bim.getSampleModel(), bim.getColorModel());
   }

   static void invertLines(int[] fileIndices, ImageCallback image, double scale) {
      logger.debug("scale: " + scale);

//      if (Math.abs(scale - 1.001) > 0.01)
//      {
//         for (int i = 0; i < fileIndices.length; i++)
//         {
//            logger.debug("old: " + fileIndices[i]);
//            fileIndices[i] = (int) (fileIndices[i] * scale);
//            logger.debug("new: " + fileIndices[i]);
//         }
//      }

      final double width = image.getSize().getWidth();
      int lastIx = -1;
      for (int i = 0; i < fileIndices.length; i++) {
         if (Math.abs(fileIndices[i] - lastIx) > 5 && (width - (fileIndices[i] * scale)) > 2) {
            image.invertLine(fileIndices[i]);
            lastIx = fileIndices[i];

            if (logger.isDebugEnabled()) {
               logger.debug("file ix: " + fileIndices[i]);
            }
         }
      }
   }

   static void invertLines(int[] fileIndices, ImageCallback image) {
      invertLines(fileIndices, image, 1.0);
   }

   static void invertLines(TiledImage image, int line) {
      invertLineHorizontal(image, line);
      invertLineVertical(image, line);
   }

   static void invertLineHorizontal(TiledImage image, int line) {
      for (int i = 0; i < image.getWidth(); i++) {
         setPixel(image, i, line, getPixel(image, i, line, true));
      }
   }

   static void invertLineVertical(TiledImage image, int line) {
      for (int i = 0; i < image.getHeight(); i++) {
         setPixel(image, line, i, getPixel(image, line, i, true));
      }
   }

   static void setPixel(TiledImage image, int x, int y, Color val) {
      image.setSample(x, y, 0, val.getRed());
      image.setSample(x, y, 1, val.getGreen());
      image.setSample(x, y, 2, val.getBlue());
   }

   static Color getPixel(TiledImage image, int x, int y) {
      return getPixel(image, x, y, false);
   }

   static Color getPixel(TiledImage image, int x, int y, boolean inverted) {
      final int r = image.getSample(x, y, 0);
      final int g = image.getSample(x, y, 1);
      final int b = image.getSample(x, y, 2);

      if (inverted) {
         return new Color(255 - r, 255 - g, 255 - b);
      }
      else {
         return new Color(r, g, b);
      }
   }

   /**
    * Exports the part of the navigator, defined by the roi, to the given file.
    *
    * @param navigator   the data to be exported
    * @param roi         the ROI
    * @param filename    the target
    * @param imageConfig the config
    */
   public static void exportDotplotInROI(
         final ITypeTableNavigator navigator, final Rectangle roi, final String filename,
         final IQImageConfiguration imageConfig) {
      logger.debug(filename);

      int[][] lut = null;
      if (imageConfig.useLUT()) {
//         System.err.println("lut: " + imageConfig.getLutTitle());
         lut = imageConfig.getLut();
      }

      final TiledImage image = createEmptyTiledImage(roi.getSize());
      synchronized (navigator) {
         navigator.reset();
         navigator.setRegionOfInterest(roi.x, roi.y, roi.width, roi.height);

         logger.debug("reading matches in roi " + roi);

         createImage(navigator, lut, getImageCallback(image, roi), imageConfig);
      }

//      JAITools.saveJAI(JAITools.getWithLUT(image.createSnapshot(), lut), filename, JAITools.EXPORTFORMAT_JPEG);
      JAITools.saveJAI(image, filename, JAITools.EXPORTFORMAT_JPEG);
   }

   /**
    * Exports the part of the navigator, defined by the roi, to the given file
    * by using the Information Mural for the scaling to the given size.
    *
    * @param navigator  the data to be exported
    * @param targetSize the target size
    * @param roi        the ROI
    * @param filename   the target
    * @param config     the config
    */
   public static void exportDotplotInROIByInfoMural(
         final ITypeTableNavigator navigator, final Dimension targetSize, final Rectangle roi, final String filename,
         final IQImageConfiguration config) {
      logger.debug(filename);

      final TiledImage image = createEmptyTiledImage(roi.getSize());
      final ImageCallback imageCallback = getImageCallback(image, roi);

      navigator.reset();

      InformationMural.getMural(navigator, targetSize, imageCallback, config);

      final int[] fileIndices = navigator.getTokenInformation().getAllStartIndices();
      final boolean showFileSeparators = config.showFileSeparators()
            && (fileIndices.length > 1);

      if (showFileSeparators) {
         final double scale = (double) imageCallback.getSize().getWidth() / (double) navigator.getSize().width;
         invertLines(fileIndices, imageCallback, scale);
      }

//      JAITools.saveJAI(JAITools.getWithLUT(image.createSnapshot(), lut), filename, JAITools.EXPORTFORMAT_JPEG);
      JAITools.saveJAI(image, filename, JAITools.EXPORTFORMAT_JPEG);
   }

   private static ImageCallback getImageCallback(final TiledImage image, final Rectangle roi) {
      return new ImageCallback() {
         public void setPixel(int x, int y, int rgb) {
            if (roi.contains(x, y)) {
               Util.setPixel(image, x - roi.x, y - roi.y, new Color(rgb));
            }
         }

         public void updateProgress(int diff, int curStep, String msg) {
            // do nothing
         }

         public Dimension getSize() {
            return roi.getSize();
         }

         public int getPixel(int x, int y) {
            // TODO what about pixel offsets when using ROI??
            return Util.getPixel(image, x - roi.x, y - roi.y).getRGB();
         }

         public void invertLine(int index) {
            final int absX = index - roi.x;
            final int absY = index - roi.y;

            if (roi.contains(index, index) && image.getBounds().contains(absX, absY)) {
               logger.debug("invert lines " + index);
               Util.invertLines(image, index);
            }
            // Probably, only horizontal or vertical lines are possible
            else if (image.getWidth() > absX) {
               logger.debug("invert line(h) " + index);
               Util.invertLineHorizontal(image, index);
            }
            else if (image.getHeight() > absY) {
               logger.debug("invert line(v) " + index);
               Util.invertLineVertical(image, index);
            }
         }
      };
   }

   /**
    * returns some meta information for the given location.
    *
    * @param location  the location
    * @param imageSize the size of the corresponding image
    * @param scale     the scale factor of the original data to the currently displayed image
    * @param nav       the source data
    *
    * @return details for the location
    */
   public static IROIResult getDetailsForLoc(
         Point location, Dimension imageSize, float scale, ITypeTableNavigator nav) {
      final Rectangle rectangle = new Rectangle(imageSize);
      if (!(rectangle.contains(location))) {
         return null;
      }

      TokenInformation tokenInfo = nav.getTokenInformation();

      int xIndex = Math.max((int) (location.x / scale), 0);
      int yIndex = Math.max((int) (location.y / scale), 0);

      int xFileID = tokenInfo.getFileIndex(xIndex);
      int yFileID = tokenInfo.getFileIndex(yIndex);

      int xLineIndex = 0;
      int yLineIndex = 0;
      String xToken;
      String yToken;
      try {
         xLineIndex = tokenInfo.getLineIndex(xFileID, xIndex);
         yLineIndex = tokenInfo.getLineIndex(yFileID, yIndex);

         xToken = tokenInfo.getToken(xFileID, xIndex);
         yToken = tokenInfo.getToken(yFileID, yIndex);
      }
      catch (ArrayIndexOutOfBoundsException e) {
         // TODO doesn't work correctly, for a bigger 2nd plot
         return null;
      }

      final ROIResult roiResult = new ROIResult(
            new File(tokenInfo.getFileName(xFileID)),
            new File(tokenInfo.getFileName(yFileID)),
            xLineIndex,
            yLineIndex);
      roiResult.setXToken(xToken);
      roiResult.setYToken(yToken);
      return roiResult;
   }
}
