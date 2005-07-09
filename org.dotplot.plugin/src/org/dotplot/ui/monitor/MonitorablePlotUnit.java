/*
 * Created on 18.06.2004
 */
package org.dotplot.ui.monitor;

/**
 * <code>MonitorablePlotUnit</code> must be implemented by anyone who wants to give information about progress.
 *
 * @author Sascha Hemminger
 */
public interface MonitorablePlotUnit
{
   /**
    * gives the name of the unit.
    *
    * @return the name of the unit providing work progress
    */
   public String nameOfUnit();

   /**
    * monitor uses this one to get percentage of work done.
    *
    * @return work done in percent
    */
   public int getProgress();

   /**
    * monitor uses this one to get a message.
    *
    * @return what is the unit doing by know
    */
   public String getMonitorMessage();

   /**
    * user cancelled progress unit should immediately stop working!
    */
   public void cancel();
}
