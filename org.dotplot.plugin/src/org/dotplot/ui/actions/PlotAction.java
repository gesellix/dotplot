package org.dotplot.ui.actions;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.mail.MessagingException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.WorkbenchException;

import org.dotplot.DotplotCreator;
import org.dotplot.grid.GridCallback;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.GridPlotter;
import org.dotplot.grid.PlotJob;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.image.IDotplot;
import org.dotplot.image.JAITools;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.tokenizer.IFileList;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.ui.DotPlotPerspective;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.monitor.DotPlotProgressMonitor;
import org.dotplot.ui.views.DotPlotNavigator;
import org.dotplot.ui.views.DotPlotter;
import org.dotplot.ui.views.MergeView;
import org.dotplot.util.FileCopy;
import org.dotplot.util.FileUtil;
import org.dotplot.util.MailUtil;

/**
 * <code>PlotAction</code> creates a DotPlot from all checked files in the navigator.
 *
 * @author Sascha Hemminger & Roland Helmrich
 * @see IWorkbenchWindowActionDelegate
 */
public class PlotAction implements IWorkbenchWindowActionDelegate, SelectionListener, GridCallback
{
   private IWorkbenchWindow window;

   protected static final String EMPTY_SELECTION = "Nothing to plot, select a plottable file in the DotPlot Navigator first!";
   protected static final String ERROR_CREATING_VIEWS = "Error while creating views. Please try to close and reopen this perspective.";
   protected static final String ERROR_PARSING_FILES = "Error while parsing files.";

   private static final Logger logger = Logger.getLogger(PlotAction.class.getName());

   /**
    * Starts the plot.
    *
    * @param action the action proxy
    *
    * @see IWorkbenchWindowActionDelegate#run
    */
   public void run(IAction action)
   {
      plot();
   }

   /**
    * <code>plot</code> is THE action that generates the dotplot.
    */
   protected void plot()
   {
      logger.debug("starting plot");

      // initialise perspective with needed views
      if (!showViews())
      {
         // an error occurred, break operation
         return;
      }

      DotplotCreator dotplotCreator = GlobalConfiguration.getInstance().getDotplotCreator();

      DotPlotter plotterV = (DotPlotter) window.getActivePage().findView(DotPlotPerspective.DOTPLOTTER);
      MergeView mergerV = (MergeView) window.getActivePage().findView(DotPlotPerspective.DOTPLOTDIFF);

      plotterV.setMouseListener(new DotPlotMouseListener(window, dotplotCreator, mergerV));

      createAndShowPlot(dotplotCreator, plotterV);
   }

   protected IDotplot initDotplot(DotplotCreator dotplotCreator)
   {
      // update DotplotCreator with current file selection
      if (!updateFileList(dotplotCreator))
      {
         try
         {
            handleEmptyFileList(dotplotCreator);
         }
         catch (Exception e)
         {
            // loading failed -> tell user to select files in the navigator
            showMessage(EMPTY_SELECTION);
            return null;
         }
      }

      IDotplot dplot = null;
      try
      {
         // initialize IDotplot through file(s) or TypeTable
         dplot = dotplotCreator.getDotplot();
      }
      catch (TokenizerException te)
      {
         logger.error("error while parsing files", te);
         showMessage(ERROR_PARSING_FILES);
         return null;
      }

      return dplot;
   }

   private void handleEmptyFileList(DotplotCreator dotplotCreator)
         throws Exception
   {
      if (MessageDialog.openQuestion(window.getShell(), "No files selected",
            "Open PlotJob from file?"))
      {
         // no files selected -> try loading TypeTable from file
         String filename = FileUtil.showFileDialog(window.getShell(),
               "Select a PlotJob or press \"Cancel\"",
               new String[]{"*.dgz", "*"});

         if (filename != null)
         {
            File file = new File(filename);
            logger.info("using Plot from file " + file.getAbsolutePath());
            dotplotCreator.setTypeTable(FileUtil.importPlotJob(file).getTypeTable());
         }
         else
         {
            throw new Exception("empty selection");
         }
      }
      else
      {
         throw new Exception("empty selection");
      }
   }

   protected void createAndShowPlot(DotplotCreator dotplotCreator, DotPlotter view)
   {
      IDotplot dplot = initDotplot(dotplotCreator);

      if (dplot == null)
      {
         view.showImage(null);
      }
      else
      {
         final GridConfiguration gridConfig = (GridConfiguration) GlobalConfiguration.getInstance().get(
               GlobalConfiguration.KEY_GRID_CONFIGURATION);
         final QImageConfiguration imageConfig = (QImageConfiguration) GlobalConfiguration.getInstance().get(
               GlobalConfiguration.KEY_IMG_CONFIGURATION);

         boolean isGridWorking = false;
         if (gridConfig.isGridActive())
         {
            logger.info("grid active");

            PlotJob plotJob = new PlotJob(imageConfig,
                  dotplotCreator.getFMatrixController().getTypeTableNavigator().getTypeTable());

            try
            {
               isGridWorking = GridPlotter.createPlot(plotJob, this);
            }
            catch (ConnectionException e)
            {
               logger.error("Error on trying to plot over grid", e);
               isGridWorking = false;
            }
         }

         if (isGridWorking)
         {
            logger.info("waiting for image(s) from grid...");
         }
         else
         {
            logger.info("grid not active");

            // Grid not ready - use local resources
            float scale = 1;
            if (imageConfig.getScaleMode() == 0)
            {
               scale = dplot.setTargetSize(dotplotCreator.getFMatrixController().getTypeTableNavigator().getSize());
            }
            else
            {
               scale = dplot.setTargetSize(view.getSize());
            }

            final ImageData data = (ImageData) dplot.getImage(IDotplot.IMG_SWT_IMAGEDATA);
            // TODO enable only when no LINE-FILTER is active... or fix this bug ;-)
//            view.setMouseMoveListener(new DotPlotMouseMoveListener(window, dotplotCreator, data, view, scale));
            view.showImage(data);
         }
      }
   }

   /**
    * Will be called from the grid when all image parts are ready.
    *
    * @param gridImages Map of clients to their image parts
    * @param size       complete target size of the image
    */
   public void imagesReceived(final Map gridImages, final Dimension size)
   {
      logger.info("got complete image -> aggregate and show it");

      final DotplotCreator dotplotCreator = GlobalConfiguration.getInstance().getDotplotCreator();
      final GridConfiguration gridConfig = (GridConfiguration) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_GRID_CONFIGURATION);

      DotPlotProgressMonitor.getInstance().close();

      final Shell shell = window.getShell();

      if (gridConfig.isEnableNotification())
      {
         logger.debug("Using email notification.");

         // plotted files
         final IFileList iFileList = ((IFileList) GlobalConfiguration.getInstance().get(
               GlobalConfiguration.KEY_DOTPLOTTER_FILELIST));

         StringBuffer message = new StringBuffer("Your plot has been finished.");
         if (iFileList != null)
         {
            message.append("\n\nThe following files have been plotted:");

            Enumeration fileList = iFileList.getEnumeration();
            while (fileList.hasMoreElements())
            {
               message.append('\n').append(fileList.nextElement().toString());
            }
         }

         try
         {
            if (gridConfig.isNotifyAttachImage())
            {
               Vector indexVector = new Vector();
               Iterator indexIter = gridImages.keySet().iterator();
               while (indexIter.hasNext())
               {
                  indexVector.add(indexIter.next());
               }
               Integer[] indices = (Integer[]) indexVector.toArray(new Integer[0]);
               Arrays.sort(indices);

               File[] attachments = new File[indices.length];
               String[] filenames = new String[indices.length];
               for (int i = 0; i < indices.length; i++)
               {
                  Integer index = indices[i];
                  attachments[i] = (File) gridImages.get(index);
                  final String sourceFile = attachments[i].getAbsolutePath();
                  filenames[i] = "gridPlot_" + index + sourceFile.substring(sourceFile.lastIndexOf('.'));
               }

               MailUtil.postMailBySMTP(gridConfig.getNotifySMTPHost(), gridConfig.getNotifySMTPUser(),
                     gridConfig.getNotifySMTPPass(), gridConfig.getNotifyEmailFrom(),
                     gridConfig.getNotifyEmailTo(), gridConfig.getNotifyEmailSubject(),
                     message.toString(), attachments, filenames);
            }
            else
            {
               MailUtil.postMailBySMTP(gridConfig.getNotifySMTPHost(), gridConfig.getNotifySMTPUser(),
                     gridConfig.getNotifySMTPPass(), gridConfig.getNotifyEmailFrom(),
                     gridConfig.getNotifyEmailTo(), gridConfig.getNotifyEmailSubject(),
                     message.toString());
            }

            logger.info("Notification mail sent.");
         }
         catch (final MessagingException msgExc)
         {
            shell.getDisplay().syncExec(new Runnable()
            {
               public void run()
               {
                  MessageDialog.openError(shell, "Notification failed!", msgExc.getMessage());
               }
            });
            logger.warn("Mail notification failed", msgExc);
         }
      }

      shell.getDisplay().syncExec(new Runnable()
      {
         public void run()
         {
            if (MessageDialog.openQuestion(shell, "Plot finished",
                  "The image parts from grid are available. Aggregate them and show plot?\n\n"
                        + "Aggregation will need much memory and CPU time, "
                        + "if you've got an image manipulation program you may combine the parts yourself.\n\n"
                        + "In this case, choose \"no\" and you will be asked where to save the image parts. "
                        + "Attention: existing files (\"gridPlot_*\") will be overwritten!"))
            {
               aggregateAndShowPlot(dotplotCreator, gridImages, size);
            }
            else
            {
               moveImageParts(gridImages);
            }
         }
      });
   }

   private void moveImageParts(Map gridImages)
   {
      String targetfolder = FileUtil.showDirectoryDialog(window.getShell(),
            "Select a target folder or press \"Cancel\"");

      if (targetfolder != null)
      {
         File file = new File(targetfolder);
         logger.info("moving image(s) to " + file.getAbsolutePath());

         try
         {
            Vector indexVector = new Vector();
            Iterator indexIter = gridImages.keySet().iterator();
            while (indexIter.hasNext())
            {
               indexVector.add(indexIter.next());
            }
            Integer[] indices = (Integer[]) indexVector.toArray(new Integer[0]);
            Arrays.sort(indices);

            for (int i = 0; i < indices.length; i++)
            {
               Integer index = indices[i];

               final String sourceFile = ((File) gridImages.get(index)).getAbsolutePath();

               // break if a problem occurs, overwrite older files
               FileCopy.copy(sourceFile,
                     targetfolder + File.separatorChar + "gridPlot_" + index
                           + sourceFile.substring(sourceFile.lastIndexOf('.')),
                     false, 1);
            }
            logger.info("ready.");
         }
         catch (FileNotFoundException e)
         {
            logger.error("File not found!", e);
         }
         catch (IOException e)
         {
            logger.error("I/O Exception", e);
         }
      }
      else
      {
         // ignore
//            throw new IllegalArgumentException("empty selection");
      }
   }

   private void aggregateAndShowPlot(DotplotCreator dotplotCreator, Map gridImages, Dimension size)
   {
      DotPlotter plotter = (DotPlotter) window.getActivePage().findView(DotPlotPerspective.DOTPLOTTER);

      ImageData imgData = null;
      try
      {
         imgData = new ImageData(JAITools.aggregateImages(gridImages, size, false).getAbsolutePath());
      }
      catch (IOException e)
      {
         logger.error("Error aggregating image(s)", e);
      }

      if (imgData == null)
      {
         showMessage("An error occured when aggregating the image(s) from grid!");
      }

      final float scale = (float) size.width
            / (float) dotplotCreator.getFMatrixController().getTypeTableNavigator().getSize().width;

      plotter.showImage(imgData);
      plotter.setMouseMoveListener(new DotPlotMouseMoveListener(window, dotplotCreator, imgData, plotter, scale));
   }

   private boolean updateFileList(DotplotCreator dotplotCreator)
   {
      DotPlotNavigator navigatorV = (DotPlotNavigator) window.getActivePage().findView(DotPlotPerspective.DOTPLOTNAV);
      if (navigatorV.isEmpty())
      {
         return false;
      }

      if (navigatorV.isDirty())
      {
         dotplotCreator.setFileList(navigatorV.getFileList());
         navigatorV.setNotDirty();
      }

      return true;
   }

   private boolean showViews()
   {
      try
      {
         window.getWorkbench().showPerspective("org.dotplot.plugin.perspective1", window);

         window.getActivePage().showView(DotPlotPerspective.DOTPLOTNAV);
         window.getActivePage().showView(DotPlotPerspective.DOTPLOTDIFF);
         window.getActivePage().showView(DotPlotPerspective.DOTPLOTTER);
      }
      catch (WorkbenchException e)
      {
         logger.error("error while creating views", e);

         showMessage(ERROR_CREATING_VIEWS);
         return false;
      }

      return true;
   }

   private void showMessage(String text)
   {
      MessageDialog.openInformation(window.getShell(), "DotPlot", text);
   }

   /**
    * .
    *
    * @param window the window that provides the context for this delegate
    *
    * @see IWorkbenchWindowActionDelegate#init
    */
   public void init(IWorkbenchWindow window)
   {
      this.window = window;
   }

   /**
    * .
    *
    * @param event the event object
    *
    * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
    */
   public void widgetSelected(SelectionEvent event)
   {
      this.plot();
      Control control = (Control) event.widget;
      if (!control.isDisposed())
      {
         Shell shell = control.getShell();
         if (!shell.isDisposed())
         {
            shell.dispose();
         }
      }
   }

   /**
    * empty implementation.
    *
    * @param event the event object
    *
    * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
    */
   public void widgetDefaultSelected(SelectionEvent event)
   {
   }

   /**
    * empty implementation.
    *
    * @param action    an action
    * @param selection a selection
    *
    * @see IWorkbenchWindowActionDelegate#selectionChanged
    */
   public void selectionChanged(IAction action, ISelection selection)
   {
   }

   /**
    * empty implementation.
    *
    * @see IWorkbenchWindowActionDelegate#dispose
    */
   public void dispose()
   {
   }
}
