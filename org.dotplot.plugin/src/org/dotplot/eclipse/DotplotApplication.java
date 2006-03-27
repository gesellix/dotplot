package org.dotplot.eclipse;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * The application of the dotplot system. 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotApplication implements IPlatformRunnable {

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
	 */
	public Object run(Object args) throws Exception {
		Display display = PlatformUI.createDisplay();
		int returnCode = PlatformUI.createAndRunWorkbench(display, new DotplotAdvisor());
		return (returnCode == PlatformUI.RETURN_RESTART)
			? IPlatformRunnable.EXIT_RESTART
			: IPlatformRunnable.EXIT_OK;		
	}
}
