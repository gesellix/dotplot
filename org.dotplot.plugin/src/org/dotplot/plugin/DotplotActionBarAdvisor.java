/**
 * 
 */
package org.dotplot.plugin;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class DotplotActionBarAdvisor extends ActionBarAdvisor {

	/**
	 * @param configurer
	 */
	public DotplotActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	public void makeActions(IWorkbenchWindow window){
		this.register(ActionFactory.QUIT.create(window));
		this.register(ActionFactory.ABOUT.create(window));
		this.register(ActionFactory.PREFERENCES.create(window));
	}

	
	public void fillMenuBar(IMenuManager menuBar){
		
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		
		menuBar.add(fileMenu);
		
		fileMenu.add(this.getAction(ActionFactory.PREFERENCES.getId()));
		fileMenu.add(this.getAction(ActionFactory.ABOUT.getId()));
		fileMenu.add(this.getAction(ActionFactory.QUIT.getId()));
	}
}
