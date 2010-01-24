package org.dotplot.eclipse;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * The application of the dotplot system. This is the entrypoint into the
 * system.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotApplication implements IApplication {

	/**
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
	 *      IApplicationContext )
	 */
	public Object start(IApplicationContext context) throws Exception {
		final Display display = PlatformUI.createDisplay();
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display,
					new DotplotAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			else {
				return IApplication.EXIT_OK;
			}
		}
		finally {
			display.dispose();
		}
	}

	/**
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return;
		}

		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed()) {
					workbench.close();
				}
			}
		});
	}
}
