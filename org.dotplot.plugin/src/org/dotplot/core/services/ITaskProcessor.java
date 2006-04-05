/**
 * 
 */
package org.dotplot.core.services;



/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface ITaskProcessor<Invoker> {
	
	/**
	 * Sets the <code>TaskMonitor</code>.
	 * @param monitor - the <code>TaskMonitor</code> to set.
	 */
	public void setTaskMonitor(ITaskMonitor monitor);
	
	/**
	 * Stops processing. 
	 */
	public void stop();
	
	/**
	 * Returns the result of the last processed <code>Task</code>.
	 * @return The last result, or <code>null</code> if no <code>Task</code> was processed yet.
	 */
	public Object getTaskResult();
	
	/**
	 * Sets the <code>TaskProcessor</code>'s <code>ErrorHandler</code>.
	 * @param handler The <code>ErrorHandler</code>.
	 */
	public void setErrorHandler(IErrorHandler handler);
	
	/**
	 * Processes a <code>Task</code>.
	 * @param task The <code>Task</code> to be processed.
	 * @return <code>true</code> if the <code>Task</code> was processed successfully, <code>false</code> otherwise.
	 * @throws NullPointerException if task is <code>null</code>.
	 */
	public boolean process(ITask task);
	
	/**
	 * Processes a <code>Task</code>.
	 * @param task The <code>Task</code> to be processed.
	 * @param invokingObject The invoker of the <code>TaskProcessor</code>.
	 * @return <code>true</code> if the <code>Task</code> was processed successfully, <code>false</code> otherwise.
	 * @throws NullPointerException if task is <code>null</code>.
	 * @throws NullPointerException if invokingObject is <code>null</code>.
	 */
	public boolean process(ITask task, Invoker invokingObject);
}
