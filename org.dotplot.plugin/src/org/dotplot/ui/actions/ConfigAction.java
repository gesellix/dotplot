package org.dotplot.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.WorkbenchException;

import org.dotplot.ui.configuration.ConfigurationFramework;

/**
 * <code>ConfigAction</code> is there to provide configured plotting using plot action.
 *
 * @author Sascha Hemminger
 * @see org.dotplot.ui.actions.PlotAction
 */
public class ConfigAction implements IWorkbenchWindowActionDelegate
{
   private IWorkbenchWindow window;

   /**
    * Opens the configuration window.
    *
    * @param iaction the action proxy
    *
    * @see IWorkbenchWindowActionDelegate#run
    */
   public void run(IAction iaction)
   {
      try
      {
         window.getWorkbench().showPerspective("org.dotplot.plugin.perspective1", window);
      }
      catch (WorkbenchException e)
      {
         e.printStackTrace();
      }

      Shell shell = new Shell(window.getShell());
      shell.setSize(200, 200);
      shell.setLayout(new FillLayout());
      shell.setText("Dotplot Configuration");

      PlotAction action = new PlotAction();
      action.init(window);

      ConfigurationFramework config = new ConfigurationFramework();
      config.setPlotListener(action);
      config.draw(shell);

      shell.pack();
      shell.open();
   }

   /**
    * empty implementation.
    *
    * @param action    the action proxy that handles presentation portion of the action
    * @param selection the current selection, or null if there is no selection
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
}
