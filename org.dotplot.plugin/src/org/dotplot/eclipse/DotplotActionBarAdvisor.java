/**
 * 
 */
package org.dotplot.eclipse;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.util.UnknownIDException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * The <code>ActionBarAdvisor</code> of the dotplot system.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotActionBarAdvisor extends ActionBarAdvisor {

	/**
	 * Creates a new <code>DotplotActionBarAdvisor</code>.
	 * 
	 * @param configurer
	 *            - The <code>ActionBarConfigurer</code> to configure the
	 *            actionbar.
	 */
	public DotplotActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void fillMenuBar(IMenuManager menuBar) {

		MenuManager fileMenu = new MenuManager("&File",
				IWorkbenchActionConstants.M_FILE);

		menuBar.add(fileMenu);

		fileMenu.add(this.getAction(ActionFactory.PREFERENCES.getId()));
		fileMenu.add(this.getAction(ActionFactory.ABOUT.getId()));
		fileMenu.add(this.getAction(ActionFactory.QUIT.getId()));

		DotplotContext context = ContextFactory.getContext();
		try {
			EclipseUIService service = (EclipseUIService) context
					.getServiceRegistry().get(
							EclipseConstants.ID_SERVICE_ECLIPSE_UI);
			service.fillMenuBar(menuBar);
		}
		catch (UnknownIDException e) {
			// dann eben nicht
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui
	 * .IWorkbenchWindow)
	 */
	@Override
	public void makeActions(IWorkbenchWindow window) {
		this.register(ActionFactory.QUIT.create(window));
		this.register(ActionFactory.ABOUT.create(window));
		this.register(ActionFactory.PREFERENCES.create(window));
	}
}
