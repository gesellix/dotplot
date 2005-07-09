/*
 * Created on 28.12.2004
 */
package org.dotplot.ui.configuration.controller;

import java.util.Observable;

import org.dotplot.DotplotCreator;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.configuration.views.ConfigGridView;
import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * Controller for the configuration of the grid module.
 */
public class ConfigGridController extends ViewController
{
   /**
    * Create a new controller with the given DotplotCreator object and a special ConfigurationView.
    *
    * @param dotplotCreator the main controller
    * @param cv             the desired ConfigurationView
    */
   public ConfigGridController(DotplotCreator dotplotCreator, ConfigurationView cv)
   {
      super(dotplotCreator, cv);
   }

   public void update(Observable o, Object arg)
   {
      if (o instanceof ConfigGridView)
      {
         ConfigGridView gridView = (ConfigGridView) o;

         final GridConfiguration gridConfig = new GridConfiguration(
               gridView.isEnableGrid(),
               gridView.getLocalPort(),
               gridView.getMediatorAddress(),
               gridView.getMediatorPort());

         gridConfig.setGridClient(gridView.getGridClient());
         gridConfig.setGridServer(gridView.getGridServer());

         gridConfig.setEnableNotification(gridView.isEnableNotification());
         gridConfig.setNotifyAttachImage(gridView.isNotifyAttachImage());
         gridConfig.setNotifySMTPHost(gridView.getNotifySMTPHost());
         gridConfig.setNotifySMTPUser(gridView.getNotifySMTPUser());
         gridConfig.setNotifySMTPPass(gridView.getNotifySMTPPass());
         gridConfig.setNotifyEmailFrom(gridView.getNotifyEmailFrom());
         gridConfig.setNotifyEmailTo(gridView.getNotifyEmailTo());
         gridConfig.setNotifyEmailSubject(gridView.getNotifyEmailSubject());

         GlobalConfiguration.getInstance().put(GlobalConfiguration.KEY_GRID_CONFIGURATION, gridConfig);

         getDotplotter().setDirty();
      }
   }
}
