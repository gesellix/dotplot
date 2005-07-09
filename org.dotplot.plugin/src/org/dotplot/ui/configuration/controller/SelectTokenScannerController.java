/*
 * Created on 27.05.2004
 */
package org.dotplot.ui.configuration.controller;

import java.util.Observable;

import org.dotplot.DotplotCreator;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.scanner.IScanner;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.configuration.views.ConfigurationView;
import org.dotplot.ui.configuration.views.SelectTokenScannerView;

/**
 * Controller for the TokenScanner view.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenScannerController extends ViewController
{
   /**
    * creates the view controller.
    *
    * @param dotplotCreator the dotplot plugin "controller"
    * @param cv             the corresponding configuration view
    */
   public SelectTokenScannerController(DotplotCreator dotplotCreator, ConfigurationView cv)
   {
      super(dotplotCreator, cv);
   }

   public void update(Observable o, Object arg)
   {
      if (o instanceof SelectTokenScannerView)
      {
         SelectTokenScannerView stsv = (SelectTokenScannerView) o;
         IConfiguration config = this.getDotplotter().getTokenizerConfiguration();
         String selected = stsv.getSelectedScanner();

         if (selected.equals("none"))
         {
            config.setScanner(null);
            this.getDotplotter().setTokenTypes(new TokenType[0]);
         }
         else
         {
            try
            {
               IScanner newScanner = (IScanner) Class.forName("org.dotplot.tokenizer.scanner.".concat(selected))
                     .newInstance();
               config.setScanner(newScanner);
               this.getDotplotter().setTokenTypes(newScanner.getTokenTypes());
            }
            catch (InstantiationException e)
            {
               e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
               e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
               e.printStackTrace();
            }
         }
         config.setConvertFiles(stsv.getConvertFilesState());
         config.setConvertDirectory(stsv.getConversionDirectory());

         GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_TOKENIZER_SAVE_CONVERTED_FILES,
               new Boolean(stsv.getKeepConvertedFilesState()));

         this.getDotplotter().setDirty();
      }
   }
}
