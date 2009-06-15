/*
 * Created on 14.12.2004
 */
package org.dotplot.eclipse.actions.contextMenu;

import org.dotplot.eclipse.actions.ConfigAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Wrapper for the ConfigAction to be used in the context menu.
 * 
 * @author Tobias Gesellchen
 */
public class ConfigureAction extends Action {
    IWorkbenchWindow parent;

    /**
     * create a new action.
     * 
     * @param text
     *            the title to be displayed on the context menu
     * @param parent
     *            the parent
     */
    public ConfigureAction(String text, IWorkbenchWindow parent) {
	this.parent = parent;
	setText(text);
    }

    /**
     * starts the ConfigAction.
     */
    @Override
    public void run() {
	ConfigAction action = new ConfigAction();
	action.init(parent);
	action.run(null);
    }
}
