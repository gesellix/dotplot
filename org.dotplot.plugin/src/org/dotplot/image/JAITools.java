/*
 * Created on 07.07.2004
 */
package org.dotplot.image;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.JPEGEncodeParam;
import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.media.jai.ImageLayout;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.ROI;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;

import org.dotplot.tokenizer.IFileList;
import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Class containing several tools handling images through JAI (imaging) and iText (PDF).
 *
 * @author Tobias Gesellchen
 */
final public class JAITools
{
   /**
    * for internal configuration of TiledImage.
    */
   private static final int MAX_TILE_EDGE = 2400; // must be dividable by 8
   /**
    * Fileformat .pdf.
    */
   public static final String EXPORTFORMAT_PDF = "pdf";
   /**
    * Fileformat .png.
    */
   public static final String EXPORTFORMAT_PNG = "png";
   /**
    * Fileformat .jpeg.
    */
   public static final String EXPORTFORMAT_JPEG = "jpeg";
   /**
    * Fileformat .tiff.
    */
   public static final String EXPORTFORMAT_TIFF = "tiff";
   /**
    * Collection of supported fileformats.
    */
   public static final String[] EXPORT_FORMATS = new String[]{
      EXPORTFORMAT_PDF, EXPORTFORMAT_JPEG, EXPORTFORMAT_PNG, EXPORTFORMAT_TIFF
   };

   private final static Logger logger = Logger.getLogger(JAITools.class.getName());

   static Raster getRasterWithLUT(TiledImage tiledImage, float[] scale, QImage qImage)
   {
      QImageConfiguration config = (QImageConfiguration) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_IMG_CONFIGURATION);

      int scaleMode = config.getScaleMode();
      boolean scaleUp = config.doScaleUp();
      int[][] lut = config.getLut();

      logger.debug("creating new Dotplot for screen");

      PlanarImage displayImage;
      Raster imageData;

      if (scaleMode == 0)
      {
         qImage.update(0, IDotplot.STEP_SCALING, "no scaling.");
         scale[0] = scale[1] = 1;

         // no scaling
         displayImage = tiledImage.createSnapshot();
      }
      else if ((scaleUp && scale[0] > 1 && scale[1] > 1) || (scale[0] < 1 && scale[1] < 1))
      {
         // apply targetSize/scale factor
         displayImage = getScaled(tiledImage, scale).createSnapshot();
      }
      else
      {
         // if nothing was true...
         displayImage = tiledImage.createSnapshot();
      }

      qImage.update(IDotplot.STEPS_WIDTH, IDotplot.STEP_LUT, "apply LUT");

      // apply LUT
      displayImage = makeTiledImage(displayImage);
      displayImage = getWithLUT(displayImage, lut).createSnapshot();

      qImage.update(IDotplot.STEPS_WIDTH / 2, IDotplot.STEP_CONVERT_DATA, "converting data for SWT");

      logger.debug("copying new data...");
      imageData = displayImage.getData();

      return imageData;
   }

   static RenderedOp getWithLUT(PlanarImage image, int[][] lut)
   {
      logger.debug("performing lookup (color conversion)...");

      if (lut == null)
      {
         logger.debug("using lut 'gray'");
         lut = LUTs.gray(); // "identity" / no color conversion
      }

      ParameterBlock pbLookup = new ParameterBlock();
      pbLookup.addSource(image);
      pbLookup.add(new LookupTableJAI(lut));

      RenderedOp luttedImage = JAI.create("lookup", pbLookup, null);

      logger.debug("lookup ready.");

      return luttedImage;
   }

   static RenderedOp makeTiledImage(PlanarImage img)
   {
      ImageLayout tileLayout = new ImageLayout(img);
      tileLayout.setTileWidth(MAX_TILE_EDGE);
      tileLayout.setTileHeight(MAX_TILE_EDGE);
      RenderingHints tileHints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, tileLayout);
      ParameterBlock pb = new ParameterBlock();
      pb.addSource(img);
      return JAI.create("format", pb, tileHints);
   }

   static ROI[] createROIFromImage(RenderedImage img)
   {
      int numbands = img.getSampleModel().getNumBands();
      ROI[] roi = new ROI[numbands];

      if (numbands == 1)
      {
         roi[0] = new ROI(img);
         return roi;
      }

      int[] bandindices = new int[1];
      for (int i = 0; i < numbands; i++)
      {
         bandindices[0] = i;
         RenderedOp opImage = JAI.create("bandselect", img, bandindices);
         roi[i] = new ROI((PlanarImage) opImage);
      }

      return roi;
   }

   static RenderedOp getScaled(PlanarImage image, float[] scale)
   {
      logger.debug("scaling...");

      ParameterBlock pbScale = new ParameterBlock();
      pbScale.addSource(image);
      pbScale.add(scale[0]);
      pbScale.add(scale[1]);
      pbScale.add(0.0F);
      pbScale.add(0.0F);
      pbScale.add(new InterpolationBilinear());
      RenderedOp scaledImage = JAI.create("scale", pbScale, null);

      logger.debug("scaling ready.");

      return scaledImage;
   }

   /**
    * Saves the given DotPlot to given file.
    *
    * @param dotplot   - the DotPlot image information, must not be <code>null</code>
    * @param target    - target file
    * @param format    - file format (jpeg, png, pdf, ...)
    * @param lut       - look up table for color conversion
    * @param useThread - save in an external thread (background)
    */
   static void saveDotplot(
         final IDotplot dotplot, final File target, final String format, final int[][] lut, boolean useThread)
   {
      if (useThread)
      {
         new Thread()
         {
            public void run()
            {
               save(dotplot, target, format, lut);
            }
         }.start();
      }
      else
      {
         save(dotplot, target, format, lut);
      }
   }

   private static void save(final IDotplot dotplot, final File target, final String format, final int[][] lut)
   {
      logger.debug("preparing image for file '" + target.getName() + "'...");

      final RenderedOp imageWithLUT = getWithLUT((PlanarImage) dotplot.getImage(IDotplot.IMG_JAI_PLANARIMAGE), lut);

      // if save as PDF ==> we need a temporary JPEG file
      if (format.equalsIgnoreCase(EXPORTFORMAT_PDF))
      {
         String filename = createFilename(target.getAbsolutePath(), EXPORTFORMAT_JPEG);

         saveJAI(imageWithLUT, filename, EXPORTFORMAT_JPEG);

         logger.debug("export to PDF...");
         savePDF(filename, EXPORTFORMAT_JPEG);

         return;
      }
      else
      {
         // save a simple image (no PDF)
         saveJAI(imageWithLUT, target.getAbsolutePath(), format);
      }
   }

   static void saveJAI(final PlanarImage image, final String target, final String format)
   {
      logger.debug("saving to " + target);

      if (format.equals(EXPORTFORMAT_JPEG))
      {
         saveJPEG(image, target);
      }
      else
      {
//      saveIIO(image, target, format);
         JAI.create("filestore", image, target, format);
      }

      logger.debug("file saved to " + new File(target).getAbsolutePath());
   }

   private static BufferedImage readJPEG(String filename) throws IOException
   {
      FileInputStream fileInputStream = new FileInputStream(filename);
      JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fileInputStream);
      return decoder.decodeAsBufferedImage();
   }

   private static void saveJPEG(final PlanarImage image, final String target)
   {
      FileOutputStream fileOutputStream = null;
      try
      {
         fileOutputStream = new FileOutputStream(new File(target));
      }
      catch (FileNotFoundException e)
      {
         logger.error("Output file could not be created.", e);
         return;
      }

      JPEGEncodeParam encodeParam = new JPEGEncodeParam();
      encodeParam.setQuality(0.90F);
      try
      {
         ImageCodec.createImageEncoder("JPEG", fileOutputStream, encodeParam).encode(image);
         fileOutputStream.close();
      }
      catch (IOException e)
      {
         logger.error("IOException while encoding image", e);
      }
   }

   private static void saveIIO(final RenderedOp image, final String target, final String format)
   {
      try
      {
         ImageIO.write(image, format, new File(target));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private static void savePDF(String file, String format)
   {
      final QImageConfiguration imageConfig = ((QImageConfiguration) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_IMG_CONFIGURATION));

      Document document = new Document();

      String filename = file.substring(0, file.lastIndexOf(format.toLowerCase()) - 1);

      try
      {
         // we create a writer that listens to the document
         // and directs a PDF-stream to a file
         PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filename));
         HtmlWriter htmlWriter = HtmlWriter.getInstance(document,
               new FileOutputStream(filename.substring(0, file.lastIndexOf(".pdf")) + ".html"));
         htmlWriter.setImagepath("./");

         pdfWriter.setViewerPreferences(PdfWriter.FitWindow);
         pdfWriter.setPageEvent(new PdfPageEventHelper()
         {
            public void onStartPage(PdfWriter pdfWriter, Document document)
            {
               // remove empty 2nd page
               if (document.getPageNumber() > 1)
               {
                  return;
               }

               super.onStartPage(pdfWriter, document);
            }
         });

         Image image = Image.getInstance(file);

         float xMargin = document.leftMargin() + document.rightMargin() + 10;
         float yMargin = document.topMargin() + document.bottomMargin() + 10;

         // date
         Calendar cal = Calendar.getInstance();
         int iMonth = cal.get(Calendar.MONTH) + 1;
         int iDay = cal.get(Calendar.DATE);

         String year = Integer.toString(cal.get(Calendar.YEAR));
         String month = (iMonth < 10) ? "0" + iMonth : "" + iMonth;
         String day = (iDay < 10) ? "0" + iDay : "" + iDay;

         Paragraph date = new Paragraph("DotPlot generated " + year + "-" + month + "-" + day,
               FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));

         // plotted files
         final IFileList iFileList = ((IFileList) GlobalConfiguration.getInstance().get(
               GlobalConfiguration.KEY_DOTPLOTTER_FILELIST));

         final Font fileNameFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
         final Paragraph files = new Paragraph();
         if (iFileList != null)
         {
            Enumeration fileList = iFileList.getEnumeration();

            while (fileList.hasMoreElements())
            {
               String fileName = '\n' + fileList.nextElement().toString();
               files.add(new Phrase(fileName, fileNameFont));
            }
         }
         else
         {
            files.add(new Phrase("No FileList provided.", fileNameFont));
            if (imageConfig.isOnlyExport())
            {
               files.add(new Phrase("\nThis file has been created outside the eclipse environment.", fileNameFont));
            }
         }

         // now the document is built using the values above

         //document.setPageSize(new Rectangle(PageSize.A0));
         document.setPageSize(new Rectangle(image.width() + xMargin, image.height() + yMargin));

         document.addTitle("DotPlot");
         document.addAuthor("matrix4.plot team");
         document.addCreator("org.dotplot.plugin (eclipse)");

         document.open();

         document.add(image);

         document.setPageSize(new Rectangle(PageSize.A4));
         document.newPage();

         document.add(date);
         document.add(files);
      }
      catch (Exception exc)
      {
         logger.error("error creating PDF", exc);
      }
      finally
      {
         if (document.isOpen())
         {
            document.close();
         }
      }

      logger.debug("PDF saved.");
   }

   private static String createFilename(String file, String extension)
   {
      if (file.endsWith(extension))
      {
         return file;
      }

      if (file.endsWith("."))
      {
         if (extension.startsWith("."))
         {
            return file + extension.substring(1);
         }
         else
         {
            return file + extension;
         }
      }

      if (extension.startsWith("."))
      {
         return file + extension;
      }
      else
      {
         return file + "." + extension;
      }
   }

   static int[] convert3BandTo1Band(int[] samples)
   {
      int[] ret = new int[samples.length / 3];

      int x = 0;

      for (int i = 0; i < samples.length; i += 3)
      {
         ret[x++] = 0xff000000 | ((samples[i] << 16) & 0xFF) | ((samples[i + 1] << 8) & 0xFF)
               | ((samples[i + 2] << 0) & 0xFF);
      }

      return ret;
   }

   /**
    * Exports the list of images, that needs to be aggregated.
    * The exported file will help the user to combine the images himself.
    */
   private static void exportImageMap(Map images, File target)
   {
      try
      {
         OutputStream fout = new FileOutputStream(target);

         Vector indexVector = new Vector();
         Iterator indexIter = images.keySet().iterator();
         while (indexIter.hasNext())
         {
            indexVector.add(indexIter.next());
         }
         Integer[] indices = (Integer[]) indexVector.toArray(new Integer[0]);
         Arrays.sort(indices);

         for (int i = 0; i < indices.length; i++)
         {
            Integer index = indices[i];
            fout.write((index + ": ").getBytes());
            fout.write(((File) images.get(index)).toString().getBytes());
            fout.write('\n');
         }

         fout.flush();
         fout.close();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Aggregates/Combines the images given by indexedImages. The size must be the correct size of the target image.
    *
    * @param indexedImages of the clients to their corresponding image part
    * @param size          the target size of the complete image
    *
    * @return a File, the target image has been saved to. It will be deleted on exit.
    *
    * @throws IOException if an error occurs
    */
   public static File aggregateImages(Map indexedImages, Dimension size) throws IOException
   {
      if (indexedImages.size() == 1)
      {
         // if only one file to process, we can use it directly
         return ((File) indexedImages.values().toArray()[0]);
      }

      File targetFile = File.createTempFile("grid_aggregated", ".jpeg");
      targetFile.deleteOnExit();

      exportImageMap(indexedImages, File.createTempFile("grid_imagelist", ".txt"));

      BufferedImage img;
      TiledImage targetImage = Util.createEmptyTiledImage(size);
      Graphics2D tg2d = targetImage.createGraphics();

//      img = ImageIO.read((File) indexedImages.get(new Integer(0)));
//      img = readJPEG(((File) indexedImages.get(new Integer(0))).getAbsolutePath());

      Vector indexVector = new Vector();
      Iterator indexIter = indexedImages.keySet().iterator();
      while (indexIter.hasNext())
      {
         indexVector.add(indexIter.next());
      }
      Integer[] indices = (Integer[]) indexVector.toArray(new Integer[0]);
      Arrays.sort(indices);

      double x = 0;
      for (int i = 0; i < indices.length; i++)
      {
         logger.debug("reading file " + (i + 1) + "/" + indices.length);
         final Object obj = indexedImages.get(indices[i]);
         img = readJPEG(((File) obj).getAbsolutePath());
         if (img != null)
         {
            logger.debug("file read, translate to target offset (x-axis): " + x);
            AffineTransform atx = new AffineTransform();
            atx.setToTranslation(x, 0);
            tg2d.drawRenderedImage(img, atx);
            x += img.getWidth();
         }
         else
         {
            logger.error("file " + obj + " could not be read!");
         }
      }

      logger.debug("saving aggregated image to " + targetFile.getAbsolutePath());
      saveJPEG(targetImage, targetFile.getAbsolutePath());

      return targetFile;
   }
}
