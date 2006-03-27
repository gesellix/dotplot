/**
 * 
 */
package org.dotplot.eclipse;

import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * The <code>WorkbenchWindowAdvisor</code> of the dotplot system.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotWindowAdvisor extends WorkbenchWindowAdvisor {

	/**
	 * Creates a new <code>DotplotWindowAdvisor</code>.
	 * @param configurer - To configure the workbench window.
	 */	
	public DotplotWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(org.eclipse.ui.application.IActionBarConfigurer)
	 */
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer){
		return new DotplotActionBarAdvisor(configurer);
	}
}
