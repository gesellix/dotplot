package org.dotplot.plugin;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class DotplotApplication implements IPlatformRunnable {

	public Object run(Object args) throws Exception {
		Display display = PlatformUI.createDisplay();
		int returnCode = PlatformUI.createAndRunWorkbench(display, new DotplotAdvisor());
		return (returnCode == PlatformUI.RETURN_RESTART)
			? IPlatformRunnable.EXIT_RESTART
			: IPlatformRunnable.EXIT_OK;		
	}
}
