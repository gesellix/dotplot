/*
 * Created on 01.07.2004
 */
package org.dotplot.ui.configuration;

/**
 * Providing constants as Keys for use in the Configuration Framework.
 */
interface IGlobalConfiguration
{
   public final static String VIEW_01_SCANNER = "01_Scanner";
   public final static String VIEW_02_TOKENTYPES = "02_Tokentypen";
   public final static String VIEW_03_FMATRIX = "03_Fmatrix";
   public final static String VIEW_05_QIMAGE = "05_QImage";
   public final static String VIEW_06_GRID = "06_Grid";

   public final static String KEY_CONFIGURATION_VIEWS = "CONFIGURATION_VIEWS";

   public final static String KEY_DOTPLOTTER_FILELIST = "DOTPLOTTER_FILELIST";

   public final static String KEY_TOKENIZER_SCANNER_TYPES = "SCANNER_TYPES";
   public final static String KEY_TOKENIZER_DEFAULT_SCANNER = "DEFAULT_SCANNER";
   public final static String KEY_TOKENIZER_TEXT_TYPES = "TEXT_TYPES";
   public final static String KEY_TOKENIZER_TEXT_CONVERTERS = "TEXT_CONVERTERS";
   public final static String KEY_TOKENIZER_SAVE_CONVERTED_FILES = "SAVE_CONVERTED_FILES";
   public final static String KEY_TOKENIZER_SAVING_DIRECTORY = "SAVING_DIRECTORY";

   public final static String KEY_FMATRIX_TOKEN_WEIGHTS = "TOKEN_WEIGHTS";
   public final static String KEY_FMATRIX_REGULAR_EXPRESSIONS = "REGULAR_EXPRESSIONS";

   public final static String KEY_IMG_CONFIGURATION = "IMG_CONFIGURATION";

   public final static String KEY_GRID_CONFIGURATION = "GRID_CONFIGURATION";

   public final static String VM_PARAM_HEADLESS = "headless";
   public final static String VM_PARAM_GRID_NOTIFY = "grid.notify";
   public final static String VM_PARAM_GRID_ENABLED = "grid.enabled";
   public final static String VM_PARAM_GRID_MAXEDGE = "grid.maxedge";
   public final static String VM_PARAM_IMG_JAI_ENABLED = "image.highquality";
}
