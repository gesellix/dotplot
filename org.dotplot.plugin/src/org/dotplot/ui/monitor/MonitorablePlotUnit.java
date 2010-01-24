/*
 * Created on 18.06.2004
 */
package org.dotplot.ui.monitor;

/**
 * <code>MonitorablePlotUnit</code> must be implemented by anyone who wants to
 * give information about progress.
 * 
 * @author Sascha Hemminger
 */
public interface MonitorablePlotUnit {
	/**
	 * user cancelled progress unit should immediately stop working!
	 */
	public void cancel();

	/**
	 * monitor uses this one to get a message.
	 * 
	 * @return what is the unit doing by know
	 */
	public String getMonitorMessage();

	/**
	 * monitor uses this one to get percentage of work done.
	 * 
	 * @return work done in percent
	 */
	public int getProgress();

	/**
	 * gives the name of the unit.
	 * 
	 * @return the name of the unit providing work progress
	 */
	public String nameOfUnit();
}
