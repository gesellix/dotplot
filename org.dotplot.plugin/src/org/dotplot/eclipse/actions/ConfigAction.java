package org.dotplot.eclipse.actions;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.eclipse.EclipseConstants;
import org.dotplot.eclipse.EclipseUIService;
import org.dotplot.eclipse.perspective.DotPlotPerspective;
import org.dotplot.eclipse.views.DotPlotNavigator;
import org.dotplot.ui.ConfigurationDialog;
import org.dotplot.ui.ConfigurationViews;
import org.dotplot.util.UnknownIDException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.WorkbenchException;

/**
 * <code>ConfigAction</code> is there to provide configured plotting using plot
 * action.
 * 
 * @author Sascha Hemminger
 * @see org.dotplot.eclipse.actions.PlotAction
 */
public class ConfigAction implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow window;

    /**
     * empty implementation.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {
    }

    /**
     * .
     * 
     * @param window
     *            the window that provides the context for this delegate
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window) {
	this.window = window;
    }

    /**
     * Opens the configuration window.
     * 
     * @param iaction
     *            the action proxy
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction iaction) {
	try {
	    window.getWorkbench().showPerspective(
		    EclipseConstants.ID_PERSPECTIVE_DOTPLOT, window);
	} catch (WorkbenchException e) {
	    e.printStackTrace();
	}

	Shell shell = new Shell(window.getShell());
	shell.setSize(200, 200);
	shell.setLayout(new FillLayout());
	shell.setText("Dotplot Configuration");

	final PlotAction action = new PlotAction();
	action.init(window);

	final DotplotContext context = ContextFactory.getContext();
	// die aktuelle registry als basis
	final IConfigurationRegistry registry = context
		.getConfigurationRegistry();

	// eine copy der registry als model für den dialog
	// falls der dialog abgebrochen wird, ist die original registry
	// nicht davon betroffen
	final IConfigurationRegistry registryCopy = registry.copy();

	ConfigurationViews views;

	try {
	    views = ((EclipseUIService) context.getServiceRegistry().get(
		    "org.dotplot.standard.EclipseUI")).getConfigurationViews();
	} catch (UnknownIDException e1) {
	    views = new ConfigurationViews();
	}

	// views = (ConfigurationViews) GlobalConfiguration.getInstance().get(
	// GlobalConfiguration.KEY_CONFIGURATION_VIEWS);

	ConfigurationDialog config = new ConfigurationDialog(registryCopy,
		views);
	config.setPlotListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		// die veränderungen die im dialog gemacht wurden auf die
		// original registry anwenden
		registry.getAll().putAll(registryCopy.getAll());
		action.plot();
		Control control = (Control) e.widget;
		if (!control.isDisposed()) {
		    Shell shell = control.getShell();
		    if (!shell.isDisposed()) {
			shell.dispose();
		    }
		}
	    }

	});

	DotPlotNavigator navigatorV = (DotPlotNavigator) window.getActivePage()
		.findView(DotPlotPerspective.DOTPLOTNAV);

	if (!navigatorV.isEmpty() && navigatorV.isDirty()) {
	    context.setSourceList(navigatorV.getFileList());
	    navigatorV.setNotDirty();
	}

	config.draw(shell);

	shell.pack(true);
	shell.open();
    }

    /**
     * empty implementation.
     * 
     * @param action
     *            the action proxy that handles presentation portion of the
     *            action
     * @param selection
     *            the current selection, or null if there is no selection
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }
}
