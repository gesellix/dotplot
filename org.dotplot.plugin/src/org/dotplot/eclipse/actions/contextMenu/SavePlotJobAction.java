/*
 * Created on 14.12.2004
 */
package org.dotplot.eclipse.actions.contextMenu;

import org.dotplot.eclipse.actions.PlotJobAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Wrapper for the PlotJobAction to export a PlotJob.
 * 
 * @author Tobias Gesellchen
 */
public class SavePlotJobAction extends Action {

	/**
	 * 
	 */
	private IWorkbenchWindow parent;

	/**
	 * create the action.
	 * 
	 * @param text
	 *            the title to be displayed on the context menu
	 * @param parentWindow
	 *            the parent
	 */
	public SavePlotJobAction(final String text,
			final IWorkbenchWindow parentWindow) {
		this.parent = parentWindow;
		setText(text);
	}

	/**
	 * starts the export.
	 */
	@Override
	public final void run() {
		PlotJobAction.savePlotJob(parent.getShell());
	}
}
