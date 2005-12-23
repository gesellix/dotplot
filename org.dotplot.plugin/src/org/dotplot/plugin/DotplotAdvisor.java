package org.dotplot.plugin;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class DotplotAdvisor extends WorkbenchAdvisor {

	public String getInitialWindowPerspectiveId() {		
		return "org.dotplot.plugin.perspective1";
	}
	
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer){
		return new DotplotWindowAdvisor(configurer);
	}

}
