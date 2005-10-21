/*
 * Created on 26.05.2004
 */
package org.dotplot.ui.configuration;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.DotplotCreator;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.ui.configuration.controller.ConfigGridController;
import org.dotplot.ui.configuration.controller.ConfigQImageController;
import org.dotplot.ui.configuration.controller.FMatrixViewController;
import org.dotplot.ui.configuration.controller.SelectTokenScannerController;
import org.dotplot.ui.configuration.controller.SelectTokenTypesController;
import org.dotplot.ui.configuration.views.ConfigGridView;
import org.dotplot.ui.configuration.views.ConfigQImageView;
import org.dotplot.ui.configuration.views.FMatrixConfigurationView;
import org.dotplot.ui.configuration.views.SelectTokenScannerView;
import org.dotplot.ui.configuration.views.SelectTokenTypesView;

/**
 * A Singleton-Class for global configuration settings.
 *
 * @author Christian Gerhardt <case@gmx.net>
 */
public class GlobalConfiguration extends TreeMap implements IGlobalConfiguration
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = -4537406651074473668L;

   /**
    * The to be used dotplotCreator.
    * This is the model in the Model-View-Controller pattern.
    */
   private DotplotCreator dotplotCreator;

   /**
    * Saves the one and only instance of this class.
    */
   private static GlobalConfiguration instance;

   private final static Logger logger = Logger.getLogger(GlobalConfiguration.class.getName());

   /**
    * Creats a new GlobalConfiguration and aktivates the defaultconfiguration.
    * A dotplotCreator is also be created.
    */
   private GlobalConfiguration()
   {
      dotplotCreator = new DotplotCreator();
      initDefaultConfiguration();
   }

   /**
    * Returns the one and only instance of this class.
    * During its first call it creates a new globalconfiguration instance.
    * This instance creates a new dotplotCreatorator for its use. This dotplotCreatorator could
    * be changed afterwards, but this should be used with caution, becouse it could
    * create inconsistency within the system. To prevent that, the configuration
    * must be rebuild bottom up. Use the <code>initDefaultConfiguration()</code>
    * method to restore your basic configuration and add additional canges.
    * Maybe it is a good idea to save your currend configuration bevor calling
    * the <code>initDefaultConfiguration()</code> method.
    *
    * @return the GlobalConfiguration instance
    *
    * @see #initDefaultConfiguration()
    */
   public static GlobalConfiguration getInstance()
   {
      if (instance == null)
      {
         instance = new GlobalConfiguration();
      }
      return instance;
   }

   /**
    * Activates the defaultconfiguration.
    */
   public void initDefaultConfiguration()
   {
      /*********** Modules */
      initGrid();
      initQImage();
      initFMatrix();
      initTokenizer();

      /*********** Global
       *
       * Die Views Einbinden.
       *
       * Das muss als letztes passieren, da sich einzelne
       * Views bereits auf die Konfiguration beziehen könnten.
       */

      // "headless", if started from the command line (without having started eclipse)
      // we don't need ConfigurationViews for this case
      if (false == Boolean.getBoolean(VM_PARAM_HEADLESS))
      {
         try
         {
            initConfigurationViews();
         }
         catch (NoClassDefFoundError e)
         {
            logger.warn("Error initializing Configuration: " + e);
            logger.info("You should try '-D" + VM_PARAM_HEADLESS + "=true' at the commandline!");
            logger.info("I won't exit now - but you have been warned! :-)");
         }
      }
   }

   private void initConfigurationViews()
   {
      //controller und views für den tokenizer
      SelectTokenTypesView sTTypesV = new SelectTokenTypesView(dotplotCreator);
      SelectTokenScannerView sTScannerV = new SelectTokenScannerView(dotplotCreator);
      SelectTokenScannerController sTScannerC = new SelectTokenScannerController(dotplotCreator, sTScannerV);
      SelectTokenTypesController sTTypesC = new SelectTokenTypesController(dotplotCreator, sTTypesV);

      // Controller and View for FMatrix
      FMatrixConfigurationView fMatrixConfigurationView = new FMatrixConfigurationView(dotplotCreator);
      FMatrixViewController fMatrixViewCtrl = new FMatrixViewController(dotplotCreator, fMatrixConfigurationView);

      // Controller and View for QImage
      ConfigQImageView qImageView = new ConfigQImageView(dotplotCreator);
      ConfigQImageController qImageViewCtrl = new ConfigQImageController(dotplotCreator, qImageView);

      // Controller and View for Grid
      ConfigGridView gridView = new ConfigGridView(dotplotCreator);
      ConfigGridController gridViewCtrl = new ConfigGridController(dotplotCreator, gridView);

      //Die Tokentypen werden auch von der Scannerart beeinflußt.
      sTTypesV.addObserver(sTTypesC);
      sTScannerV.addObserver(sTTypesC);
      sTScannerV.addObserver(sTScannerC);

      fMatrixConfigurationView.addObserver(fMatrixViewCtrl);

      qImageView.addObserver(qImageViewCtrl);

      gridView.addObserver(gridViewCtrl);

      //views in "configViews" einbinden
      ConfigurationViews configViews = new ConfigurationViews();
      configViews.put(VIEW_01_SCANNER, sTScannerV);
      configViews.put(VIEW_02_TOKENTYPES, sTTypesV);
      configViews.put(VIEW_03_FMATRIX, fMatrixConfigurationView);
      configViews.put(VIEW_05_QIMAGE, qImageView);
      configViews.put(VIEW_06_GRID, gridView);

      //speichern der views in der globalconfiguration
      this.put(KEY_CONFIGURATION_VIEWS, configViews);
   }

   private void initGrid()
   {
      this.put(KEY_GRID_CONFIGURATION, new GridConfiguration(false, 88, "0.0.0.0", 88));
   }

   private void initQImage()
   {
      this.put(KEY_IMG_CONFIGURATION,
            new QImageConfiguration(false, 1, "/dotplot", true, "inverted_gray", null, null, 1, true, true, true));
   }

   private void initFMatrix()
   {
      Vector tokenWeighted = new Vector();
      Vector regularExpressions = new Vector();
      this.put(KEY_FMATRIX_TOKEN_WEIGHTS, tokenWeighted);
      this.put(KEY_FMATRIX_REGULAR_EXPRESSIONS, regularExpressions);
   }

   private void initTokenizer()
   {
      //Scannerarten
      TreeMap scannerTypes = new TreeMap();
      scannerTypes.put(new Integer(0), "TextScanner");
      scannerTypes.put(new Integer(1), "JavaScanner");
      scannerTypes.put(new Integer(2), "CScanner");
      scannerTypes.put(new Integer(3), "CPlusPlusScanner");
      scannerTypes.put(new Integer(4), "PHPScanner");
      this.put(KEY_TOKENIZER_SCANNER_TYPES, scannerTypes);
      this.put(KEY_TOKENIZER_DEFAULT_SCANNER, "DefaultScanner");

      //textarten
      TreeMap textTypes = new TreeMap();
      textTypes.put(".c", "CScanner");
      textTypes.put(".h", "CScanner");
      textTypes.put(".cpp", "CPlusPlusScanner");
      textTypes.put(".java", "JavaScanner");
      textTypes.put(".php", "PHPScanner");
      textTypes.put(".txt", "TextScanner");
      this.put(KEY_TOKENIZER_TEXT_TYPES, textTypes);

      //Textkonverter
      TreeMap textConverters = new TreeMap();
      textConverters.put(".pdf", "PDFtoTxtConverter");
      this.put(KEY_TOKENIZER_TEXT_CONVERTERS, textConverters);
      this.put(KEY_TOKENIZER_SAVE_CONVERTED_FILES, Boolean.FALSE);
      this.put(KEY_TOKENIZER_SAVING_DIRECTORY, new File(""));
      IConfiguration config = this.dotplotCreator.getTokenizerConfiguration();
      config.setConvertDirectory((File) this.get(KEY_TOKENIZER_SAVING_DIRECTORY));
      config.setConvertFiles(((Boolean) this.get(KEY_TOKENIZER_SAVE_CONVERTED_FILES)).booleanValue());
   }

   /**
    * Returns the actual dotplotCreator.
    *
    * @return the dotplotCreator
    */
   public DotplotCreator getDotplotCreator()
   {
      return dotplotCreator;
   }
}
