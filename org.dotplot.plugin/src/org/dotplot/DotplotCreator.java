/*
 * Created on 12.05.2004
 */
package org.dotplot;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.Enumeration;

import org.eclipse.swt.graphics.ImageData;

import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.TypeTable;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.GridPlotter;
import org.dotplot.grid.PlotJob;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.image.IDotplot;
import org.dotplot.image.IROIResult;
import org.dotplot.image.QImage;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.Util;
import org.dotplot.tokenizer.DefaultConfiguration;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.IFileList;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.Tokenizer;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.monitor.DotPlotProgressMonitor;
import org.dotplot.util.FileUtil;

/**
 * Erstellt Dotplots.
 * Der DotplotCreator sorgt fuer die harmonische Zusammenarbeit der einzelnen Komponenten beim
 * Erzeugen eines Dotplots.
 *
 * @author Christian Gerhardt <Christian Gerhardt>
 */
public class DotplotCreator implements IDotplotCreator
{
   /**
    * Der Logger zur Ausgabe von Debug-Informationen
    */
   private static Logger logger = Logger.getLogger(DotplotCreator.class.getName());

   /**
    * Der Tokenizer des Dotplotters
    */
   private Tokenizer tokenizer;

   /**
    * Die Konfiguration des Tokenizers.
    */
   private IConfiguration tokenizerConfiguration;

   /**
    * Eine Liste von Tokentypen und Tokenbezeichnungen.
    */
   private TokenType[] tokenTypes;

   /**
    * Navigator through the FMatrix.
    */
   private ITypeTableNavigator navigator;

   /**
    * Der aktuelle Dotplot.
    */
   private IDotplot dotplot;

   /**
    * <code>true</code>, wenn sich an den Einstellungen was geaendert hat
    */
   private boolean dirty;

   private FMatrixManager fMatrixController = null;
   private boolean navigatorReady = false;

   /**
    * Entry point to convert a given PlotJob directly to an image.
    *
    * @param args the command line arguments.
    */
   public static void main(String[] args)
   {
      if (args == null || args.length < 2)
      {
         System.err.println("Usage: " + DotplotCreator.class.getName() + " <PlotJob filename> <Destination image>");
         System.exit(1);
      }

      processPlotJob(args[0], args[1]);
   }

   /**
    * Converts a PlotJob in <code>source</code> into an image and saves the result in <code>destination</code>.
    *
    * @param source      filename of a plottable PlotJob
    * @param destination filename for the destination image
    */
   public static void processPlotJob(final String source, final String destination)
   {
      DotplotCreator creator = new DotplotCreator();

      final File plotjobFile = new File(source);
      PlotJob plot = FileUtil.importPlotJob(plotjobFile);
      logger.info("PlotJob: " + plotjobFile.getAbsolutePath());

      QImageConfiguration imgConfig = plot.getImageConfig();
      GridConfiguration gridConfig = plot.getGridConfig();

      if (destination == null || destination.equals("."))
      {
         String parent = plotjobFile.getParent();
         imgConfig.setExportFilename((parent != null ? parent : "") + "plotjob");
      }
      else
      {
         imgConfig.setExportFilename(destination);
      }

//      imgConfig.setExportFormat(2);
//      imgConfig.setLut("blue_yellow", null, null);
//      imgConfig.setLut("sin_rgb", null, null);

      imgConfig.setExportDotplotToFile(false);
      imgConfig.setOnlyExport(true);

      GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_IMG_CONFIGURATION, imgConfig);

      creator.setTypeTable(plot.getTypeTable());
      IDotplot dotplot = creator.getDotplot();
      if (dotplot != null)
      {
         if (gridConfig.isGridActive())
         {
            logger.debug("using grid");
            try
            {
               if (!GridPlotter.createPlot(plot, null))
               {
                  // Grid not ready - use local resources
                  QImage.saveDotplot(dotplot, false);
               }
            }
            catch (ConnectionException e)
            {
               logger.error("Error on trying to plot over grid", e);
            }
         }
         else
         {
            QImage.saveDotplot(dotplot, false);
         }
      }
   }

   /**
    * Erzeugt einen DotplotCreator.
    */
   public DotplotCreator()
   {
      this.dirty = true;
      this.tokenTypes = new TokenType[0];
      this.tokenizerConfiguration = new DefaultConfiguration();
      this.tokenizerConfiguration.setScanner(null);
      this.tokenizer = new Tokenizer();
   }

   /**
    * Setzt die aktuelle Dateiliste.
    *
    * @param fileList - die Dateiliste
    */
   public void setFileList(IFileList fileList)
   {
      GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_DOTPLOTTER_FILELIST, fileList);
      setDirty();
   }

   /**
    * Update Navigator and corresponding TypeTable.
    */
   public void setTypeTable(TypeTable typeTable)
   {
      if (typeTable != null)
      {
         navigatorReady = true;
         navigator = typeTable.getNavigator();
      }
      else
      {
         navigatorReady = false;
      }

      setDirty();
   }

   /**
    * Get details for user specific region of interest (rectangle).
    * The DotPlot has to be created before calling this method!
    *
    * @param roi     - selected region of interest
    * @param imgData - the displayed plot
    *
    * @return IROIResult - file information to the corresponding roi
    */
   public IROIResult getDetailsForROI(Rectangle roi, ImageData imgData)
   {
      return dotplot.getDetailsForROI(roi, imgData);
   }

   /**
    * Get details for user specific region of interest (single point).
    * The DotPlot has to be created before calling this method!
    *
    * @param location - selected point of interest
    * @param size     - size of the displayed plot
    * @param scale    - scale of the displayed plot relative to the complete plot
    *
    * @return IROIResult - file information to the corresponding roi
    */
   public IROIResult getDetailsForLoc(Point location, Dimension size, float scale)
   {
      return Util.getDetailsForLoc(location, size, scale, navigator);
   }

   private ITokenStream initTokenizer(IFileList fileList) throws TokenizerException
   {
      tokenizerConfiguration.setFileList(fileList);
      tokenizer.setConfiguration(tokenizerConfiguration);

      return tokenizer.getTokenStream();
   }

   private ITypeTableNavigator initFMatrix(IFileList fileList, ITokenStream tokenStream)
   {
      fMatrixController = new FMatrixManager(tokenStream);

      DotPlotProgressMonitor.getInstance().getControl(fMatrixController);

      fMatrixController.setFileList(fileList);
      fMatrixController.addTokens();

      return fMatrixController.getTypeTableNavigator();
   }

   private IDotplot initQImage(ITypeTableNavigator navigator)
   {
      // init with current navigator
      QImage qImage = new QImage(navigator);

      // add module to progress bar
      if (!((QImageConfiguration) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_IMG_CONFIGURATION))
            .isOnlyExport())
      {
         if (!DotPlotProgressMonitor.getInstance().getControl(qImage))
         {
            // registering control did not work
         }
      }

      // create image wrapper IDotplot
      return qImage.getDotplot();
   }

   /**
    * Generiert einen Dotplot.
    * Der Dotplot wird zwischengespeichert, und wieder ausgegeben solange
    * sich an den Einstellungen nichts geändert hat. Nur bei neuen Einstellungen
    * wird ein neuer Dotplot erstellt.
    *
    * @return der Dotplot
    */
   public IDotplot getDotplot()
   {
      if (dirty)
      {
         if (!navigatorReady)
         {
            logger.debug("init navigator...");

            // only 2 modules in progress bar - tokenizer is fast enough, doesn't need any progress indicator
            DotPlotProgressMonitor.getInstance().showProgress(2);

            initNavigator(); // throws TokenizerException
         }
         else
         {
            logger.debug("don't init navigator");

            // if not "headless", enable DotPlotProgressMonitor
            if (!((QImageConfiguration) GlobalConfiguration.getInstance().get(
                  GlobalConfiguration.KEY_IMG_CONFIGURATION)).isOnlyExport())
            {
               // only 1 module ("QImage"), since the others won't be used
               DotPlotProgressMonitor.getInstance().showProgress(1);
            }
         }

         /**********QImage*******/
         logger.info("QImage");
         dotplot = initQImage(navigator);

         dirty = false;
      }

      return dotplot;
   }

   private void initNavigator() throws TokenizerException
   {
      IFileList fileList = (IFileList) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_DOTPLOTTER_FILELIST);

      if (logger.isDebugEnabled())
      {
         logFileList(fileList);
      }

      /*********Tokenizer*****/
      logger.info("Tokenizer");
      ITokenStream tokenStream = initTokenizer(fileList);

      /*********FMatrix*******/
      logger.info("FMatrix");
      navigator = initFMatrix(fileList, tokenStream);
   }

   private static void logFileList(IFileList fileList)
   {
      Enumeration files = fileList.getEnumeration();
      logger.debug("Files:");

      if (!files.hasMoreElements())
      {
         logger.debug("none");
      }

      while (files.hasMoreElements())
      {
         logger.debug(files.nextElement());
      }
   }

   /**
    * Gibt die Tokentypen des verwendeten Scanners zurueck.
    * Sollte der Scanner nicht weiter spezifiziert sein, ist diese Liste leer!
    *
    * @return die TokenTypen.
    *
    * @see #setTokenTypes(org.dotplot.tokenizer.TokenType[])
    */
   public TokenType[] getTokenTypes()
   {
      return this.tokenTypes;
   }

   /**
    * Setzt die gerade aktuelle Liste an Tokentypen des verwendeten Scanners.
    * Sollte der Scanner nicht weiter spezifiziert sein, ist diese Liste leer!
    *
    * @param tokenTypes - eine Liste von TokenTypen
    *
    * @see #getTokenTypes()
    */
   public void setTokenTypes(TokenType[] tokenTypes)
   {
      this.tokenTypes = tokenTypes;
   }

   /**
    * Gibt die aktuelle Tokenizer - Konfiguration zurueck.
    *
    * @return die Konfiguration.
    *
    * @see org.dotplot.IDotplotCreator#getTokenizerConfiguration()
    * @see #setTokenizerConfiguration(org.dotplot.tokenizer.IConfiguration)
    */
   public IConfiguration getTokenizerConfiguration()
   {
      return this.tokenizerConfiguration;
   }

   /**
    * Setzt eine neue Tokenizer Konfiguration.
    *
    * @param configuration - die neue Konfiguration
    *
    * @see org.dotplot.IDotplotCreator#setTokenizerConfiguration(org.dotplot.tokenizer.IConfiguration)
    * @see #getTokenizerConfiguration()
    */
   public void setTokenizerConfiguration(IConfiguration configuration)
   {
      this.tokenizerConfiguration = configuration;
      this.tokenTypes = this.tokenizerConfiguration.getScanner().getTokenTypes();
   }

   /**
    * Setzt das Dirtybit.
    *
    * @see org.dotplot.IDotplotCreator#setDirty()
    */
   public void setDirty()
   {
      this.dirty = true;
   }

   /**
    * Prueft ob das Dirtybit gesetzt wurde.
    *
    * @return das Ergebnis.
    */
   public boolean isDirty()
   {
      return this.dirty;
   }

   /**
    * Gibt den aktuellen Tokenizer zurueck.
    *
    * @return der Tokenizer
    *
    * @see org.dotplot.IDotplotCreator#getTokenizer()
    */
   public Tokenizer getTokenizer()
   {
      return this.tokenizer;
   }

   /**
    * Delivers the current FMatrixManager-Object.
    *
    * @return the current FMatrixObject
    */
   public FMatrixManager getFMatrixController()
   {
      return this.fMatrixController;
   }
}
