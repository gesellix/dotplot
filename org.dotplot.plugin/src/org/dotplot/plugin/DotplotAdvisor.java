package org.dotplot.plugin;

import org.eclipse.ui.application.WorkbenchAdvisor;

public class DotplotAdvisor extends WorkbenchAdvisor {

	public String getInitialWindowPerspectiveId() {
		
		return "org.dotplot.plugin.perspective1";
	}

}
