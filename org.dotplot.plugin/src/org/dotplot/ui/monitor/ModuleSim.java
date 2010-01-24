/*
 * Created on 19.06.2004
 */
package org.dotplot.ui.monitor;

/**
 * <code>ModuleSim</code> just a demo! Do NOT USE!
 */
public class ModuleSim implements MonitorablePlotUnit {
	private int prog = 0;

	public ModuleSim() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.monitor.MonitorablePlotUnit#cancel()
	 */
	public void cancel() {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.monitor.MonitorablePlotUnit#getMonitorMessage()
	 */
	public String getMonitorMessage() {
		return new String("worked " + prog + " %");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.monitor.MonitorablePlotUnit#getProgress()
	 */
	public int getProgress() {
		return prog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.monitor.MonitorablePlotUnit#nameOfUnit()
	 */
	public String nameOfUnit() {
		return new String("Simulated Module");
	}

	/**
	 * simulated progress.
	 */
	public void run() {
		for (int i = 0; i < 20; ++i) {
			for (int j = 0; j < 50000000; ++j) {
				; // wait
			}

			this.prog += 5;
			DotPlotProgressMonitor.getInstance().update();
		}
	}
}
