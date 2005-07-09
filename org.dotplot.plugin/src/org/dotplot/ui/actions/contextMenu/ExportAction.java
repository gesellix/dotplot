/*
 * Created on 14.12.2004
 */
package org.dotplot.ui.actions.contextMenu;

import org.apache.log4j.Logger;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import org.dotplot.DotplotCreator;
import org.dotplot.image.IDotplot;
import org.dotplot.image.JAITools;
import org.dotplot.image.QImage;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.util.FileUtil;

/**
 * Action to export a displayed image into an image file.
 *
 * @author Tobias Gesellchen
 */
public class ExportAction extends Action
{
   private DotplotCreator creator;
   private IWorkbenchWindow parent;

   /**
    * create the action.
    *
    * @param text    the title to be displayed on the context menu
    * @param creator link to get the current dotplot
    * @param parent  the parent
    */
   public ExportAction(String text, DotplotCreator creator, IWorkbenchWindow parent)
   {
      this.creator = creator;
      this.parent = parent;
      setText(text);
   }

   /**
    * asks the user for the target file and exports the dotplot.
    */
   public void run()
   {
      IDotplot dotplot = creator.getDotplot();
      if (dotplot != null)
      {
         String filename = FileUtil.showFileDialog(parent.getShell(),
               "Select a target file or enter a file name",
               new String[]{"." + JAITools.EXPORTFORMAT_JPEG});
         if (filename != null)
         {
            File file = new File(filename);

            Logger.getLogger(ExportAction.class.getName()).info("export image to " + file.getAbsolutePath());

            QImageConfiguration config = (QImageConfiguration) GlobalConfiguration.getInstance().get(
                  GlobalConfiguration.KEY_IMG_CONFIGURATION);

            final int endIndex = filename.lastIndexOf("." + JAITools.EXPORT_FORMATS[config.getExportFormat()]);
            String name = filename;
            if (endIndex != -1)
            {
               name = filename.substring(0, endIndex);
            }
            config.setExportFilename(name);
            config.setExportFormat(1); // JPEG

            QImage.saveDotplot(dotplot, false);
         }
      }
   }
}
