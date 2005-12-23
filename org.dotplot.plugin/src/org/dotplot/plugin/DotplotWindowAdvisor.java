/**
 * 
 */
package org.dotplot.plugin;

import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class DotplotWindowAdvisor extends WorkbenchWindowAdvisor {

	/**
	 * @param configurer
	 */
	public DotplotWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}
	
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer){
		return new DotplotActionBarAdvisor(configurer);
	}
}
