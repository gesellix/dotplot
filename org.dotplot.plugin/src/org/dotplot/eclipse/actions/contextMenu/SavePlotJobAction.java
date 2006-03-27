/*
 * Created on 14.12.2004
 */
package org.dotplot.eclipse.actions.contextMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import org.dotplot.eclipse.actions.PlotJobAction;

/**
 * Wrapper for the PlotJobAction to export a PlotJob.
 *
 * @author Tobias Gesellchen
 */
public class SavePlotJobAction extends Action
{
   private IWorkbenchWindow parent;

   /**
    * create the action.
    *
    * @param text    the title to be displayed on the context menu
    * @param creator link to get the current dotplot
    * @param parent  the parent
    */
   public SavePlotJobAction(String text, IWorkbenchWindow parent)
   {
      this.parent = parent;
      setText(text);
   }

   /**
    * starts the export.
    */
   public void run()
   {
      PlotJobAction.savePlotJob(parent.getShell());
   }
}
