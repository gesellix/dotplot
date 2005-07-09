package org.dotplot.image;

import org.apache.log4j.Logger;

import java.io.File;

import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.monitor.DotPlotProgressMonitor;
import org.dotplot.ui.monitor.MonitorablePlotUnit;

/**
 * The QImage gets the 2D-Floatingpoint-Array of the QMatrix and paints
 * it on the screen. The maxX value represents the width of the Matrix,
 * the maxY value the heigth.
 *
 * @author Tobias Gesellchen
 */
public class QImage implements MonitorablePlotUnit
{
   private static Logger logger = Logger.getLogger(QImage.class.getName());

   private Dotplot dotplot = null;

   private int progress = 0;
   private int currentStep = 1;

   /**
    * Initialization of the QImage controller with an ITypeTableNavigator
    * containing the current Dotplot data.
    *
    * @param nav navigator for the current data
    */
   public QImage(ITypeTableNavigator nav)
   {
      logger.debug("lib-path:   " + System.getProperty("java.library.path"));
      logger.debug("user-dir:   " + System.getProperty("user.dir"));

      dotplot = new Dotplot(nav, this);

      runGC();
   }

   /**
    * returns the dotplot for the currently set ITypeTableNavigator.
    *
    * @return the dotplot
    */
   public IDotplot getDotplot()
   {
      return getDotplot(false);
   }

   private IDotplot getDotplot(boolean onlyExport)
   {
      QImageConfiguration config = (QImageConfiguration) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_IMG_CONFIGURATION);

      if (config.isExportDotplot())
      {
         // TODO remove "invalid thread access" on monitor when ownThread is set to true
         saveDotplot(dotplot, false);

         if (onlyExport)
         {
            update(100, IDotplot.STEP_CONVERT_DATA);
            return null;
         }
      }

      return dotplot;
   }

   /**
    * exports the dotplot to the file, that has been set in the plugin configuration.
    *
    * @param dotplot      the dotplot to be exported
    * @param useOwnThread export in an own Thread to minimize blocking of the whole application
    */
   public static void saveDotplot(IDotplot dotplot, boolean useOwnThread)
   {
      QImageConfiguration config = (QImageConfiguration) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_IMG_CONFIGURATION);

      String formatSelection = JAITools.EXPORT_FORMATS[config.getExportFormat()];
      String exportFileName = config.getExportFilename();
      int[][] currentLut = config.getLut();

      JAITools.saveDotplot(dotplot,
            new File(exportFileName + '.' + formatSelection),
            formatSelection,
            currentLut,
            useOwnThread);
   }

   /**
    * Invokes the Garbage Collector to get more available memory.
    */
   static void runGC()
   {
      Runtime rt = Runtime.getRuntime();
      rt.gc();
      if (logger.isDebugEnabled())
      {
         logger.debug("Free Mem: " + rt.freeMemory());
      }
   }

   ////////// MonitorablePlotUnit
   public String nameOfUnit()
   {
      return "Imaging";
   }

   public int getProgress()
   {
      return progress;
   }

   public String getMonitorMessage()
   {
      return IDotplot.STEPS[currentStep];
   }

   public void cancel()
   {
      // TODO implement cancel?!
   }

   void update(int diff, int curStep)
   {
      update(diff, curStep, null);
   }

   void update(int diff, int curStep, String msg)
   {
      this.currentStep = curStep;
      this.progress += diff;
      this.progress = Math.min(100, progress);

      if (logger.isDebugEnabled())
      {
         logger.debug(msg);
         logger.debug("Progress -- " + progress + " " + IDotplot.STEPS[currentStep]);
      }

      DotPlotProgressMonitor.getInstance().update();
   }
}
