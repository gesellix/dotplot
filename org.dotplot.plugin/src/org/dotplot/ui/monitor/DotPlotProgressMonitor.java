/*
 * Created on 18.06.2004
 */
package org.dotplot.ui.monitor;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * <code>DotPlotProgressMonitor</code> is a Singleton that manages the
 * DotPlotProgressDialog.
 * 
 * @author Sascha Hemminger
 * @see org.dotplot.ui.monitor.DotPlotProgressDialog
 */
public class DotPlotProgressMonitor implements SelectionListener {
    private static DotPlotProgressDialog dialog;

    private static DotPlotProgressMonitor instance;

    private static boolean isUnderControl;

    private static MonitorablePlotUnit unit;

    private static int units;

    private static int unitsControlled;

    /**
     * <code>DotPlotProgressMonitor</code> is a Singleton. Use
     * <code>getInstance</code> to access it.
     * 
     * @return the one and only instance
     */
    public static DotPlotProgressMonitor getInstance() {
	if (DotPlotProgressMonitor.instance == null) {
	    DotPlotProgressMonitor.instance = new DotPlotProgressMonitor();
	}
	return DotPlotProgressMonitor.instance;
    }

    private DotPlotProgressMonitor() {
	isUnderControl = false;
	unit = null;
	dialog = null;
	units = 0;
	unitsControlled = 0;
    }

    /**
     * cancels the current action and closes the dialog.
     */
    public void close() {
	if (isUnderControl) {
	    unit.cancel();
	}
	dialog.close();
    }

    /**
     * use <code> getControl</code> to access monitoring with your PlotUnit.
     * 
     * @param plotUnit
     *            your PlotUnit (most likely a controller) that can tell its
     *            progress
     * 
     * @return true if you have monitoring now else false
     */
    public boolean getControl(MonitorablePlotUnit plotUnit) {
	// if (dialog == null)
	// {
	// showProgress(1);
	// }

	if (!isUnderControl && unitsControlled <= units) {
	    isUnderControl = true;
	    unit = plotUnit;
	    dialog.setModule(plotUnit.nameOfUnit() + ":");

	    return true;
	}

	return false;
    }

    /**
     * starts a new dialog.
     * 
     * @param maxUnits
     *            number of modules to monitor
     */
    public void showProgress(int maxUnits) {
	isUnderControl = false;
	units = maxUnits;
	unitsControlled = 0;
	dialog = new DotPlotProgressDialog();
	dialog.open();
    }

    /**
     * use this to tell the monitor you have worked an amount it will ask your
     * unit for messages and percentage of work done.
     */
    public void update() {
	if (!isUnderControl) {
	    return;
	}

	dialog.setMessage(unit.getMonitorMessage());
	dialog.stepModule(unit.getProgress());
	dialog.step(unit.getProgress() / units + (100 / units)
		* unitsControlled);
	if (unit.getProgress() == 100) {
	    isUnderControl = false;
	    unitsControlled++;
	    if (unitsControlled == units) {
		dialog.close();
	    }
	}
    }

    /**
     * empty implementation.
     * 
     * @param event
     *            the event
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent event) {
    }

    /**
     * closes the progress dialog.
     * 
     * @param event
     *            the event proxy
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent event) {
	close();
    }
}
