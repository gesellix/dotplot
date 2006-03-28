/*
 * Created on 28.12.2004
 */
package org.dotplot.grid.ui;

import java.util.Observable;

import org.dotplot.grid.GridConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.DuplicateRegistrationException;

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
   public ConfigGridController(ConfigurationView cv)
   {
      super(cv);
   }

   public void update(Observable o, Object arg)
   {
      if (o instanceof ConfigGridView)
      {
         ConfigGridView gridView = (ConfigGridView) o;
         gridView.getRegistry().unregister(QImageService.ID_CONFIGURATION_GRID);
         
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

         try {
			gridView.getRegistry().register(QImageService.ID_CONFIGURATION_GRID, gridConfig);
		}
		catch (DuplicateRegistrationException e) {
			//sollte nicht vorkommen
		}         
      }
   }
}
