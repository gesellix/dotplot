package org.dotplot.ui.actions;

import org.apache.log4j.Logger;

import java.io.File;

import org.eclipse.swt.widgets.Shell;

import org.dotplot.DotplotCreator;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.PlotJob;
import org.dotplot.image.IDotplot;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.views.DotPlotter;
import org.dotplot.util.FileUtil;

/**
 * <code>PlotJobAction</code> is there to provide the export of a PlotJob.
 *
 * @author Tobias Gesellchen
 */
public class PlotJobAction extends PlotAction
{
   private static final Logger logger = Logger.getLogger(PlotJobAction.class.getName());

   protected void createAndShowPlot(DotplotCreator dotplotCreator, DotPlotter view)
   {
      IDotplot dplot = initDotplot(dotplotCreator);

      if (dplot == null)
      {
         logger.error("Dotplot could not be created!");
      }
      else
      {
         savePlotJob(dotplotCreator, view.getParent().getShell());
      }
   }

   /**
    * exports the currently loaded TypeTable into a file. The user will be asked for the destination.
    *
    * @param dotplotCreator link to get the TypeTable through the FMatrixController
    * @param shell          to be used as parent for the file selection dialog
    */
   public static void savePlotJob(DotplotCreator dotplotCreator, Shell shell)
   {
      PlotJob plot = new PlotJob(
            (QImageConfiguration) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_IMG_CONFIGURATION),
            (GridConfiguration) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_GRID_CONFIGURATION),
            dotplotCreator.getFMatrixController().getTypeTableNavigator().getTypeTable());

      String filename = FileUtil.showFileDialog(shell,
            "Select a target file or enter a file name",
            new String[]{"*.dgz", "*"});
      if (filename != null)
      {
         File file = new File(filename);

         logger.info("export PlotJob to " + file.getAbsolutePath());
         FileUtil.exportPlotJob(plot, file);
      }
   }
}
