/*
 * Created on 23.06.2004
 */
package org.dotplot.ui.configuration.controller;

import java.io.IOException;
import java.util.Observable;

import org.dotplot.DotplotCreator;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.configuration.views.ConfigQImageView;
import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * Controller for the configuration of the imaging module.
 */
public class ConfigQImageController extends ViewController
{
   /**
    * Create a new controller with the given DotplotCreator object and a special ConfigurationView.
    *
    * @param dotplotCreator the main controller
    * @param cv             the desired ConfigurationView
    */
   public ConfigQImageController(DotplotCreator dotplotCreator, ConfigurationView cv)
   {
      super(dotplotCreator, cv);
   }

   public void update(Observable o, Object arg)
   {
      if (o instanceof ConfigQImageView)
      {
         ConfigQImageView qImageView = (ConfigQImageView) o;

         try
         {
            GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_IMG_CONFIGURATION,
                  new QImageConfiguration(qImageView.isExportDotplot(),
                        qImageView.getSelectedFormat(),
                        qImageView.getExportFileName().getCanonicalPath(),
                        qImageView.useLUT(),
                        qImageView.getChoosenLut(),
                        qImageView.getBackground(),
                        qImageView.getForeground(),
                        qImageView.getScaleMode(),
                        qImageView.doScaleUp(),
                        qImageView.showFileSeparators(),
                        qImageView.useInfoMural()));

            getDotplotter().setDirty();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }
}
