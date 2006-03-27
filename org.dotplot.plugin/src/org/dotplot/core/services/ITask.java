/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface ITask {

	/**
	 * Returns the <code>Task</code>'s id.
	 * @return The id.
	 */
	public abstract String getID();

	/**
	 * Adds a <code>TaskPart</code> to the <code>Task</code>.
	 * @param part The <code>TaskPart</code> to be added.
	 */
	public abstract void addPart(ITaskPart part);

	/**
	 * Removes a <code>TaskPart</code> from the <code>Task</code>.
	 * @param part The <code>TaskPart</code> to be removed.
	 */
	public abstract void removePart(ITaskPart part);

	/**
	 * Returns the <code>Task</code>'s <code>TaskParts</code>. 
	 * @return The <code>TaskParts</code>.
	 */
	public abstract Collection<ITaskPart> getParts();

	/**
	 * Indicates if the <code>Task</code> is partless.
	 * @return <code>true</code> if the <code>Task</code> has no <code>TaskParts</code>, <code>false</code> otherwise.
	 */
	public abstract boolean isPartless();

	/**
	 * Counts the <code>Task</code>'s <code>TaskParts</code>.
	 * @return The number of parts.
	 */
	public abstract int countParts();

	/**
	 * Indicates of the <code>Task</code> is done.
	 * @return <code>true</code> if the task is done, <code>false</code> otherwise.
	 */
	public abstract boolean isDone();

	/**
	 * Sets the <code>Task</code> done-state to <code>true</code>.
	 */
	public abstract void done();

	/**
	 * Indicates that each <code>TaskPart</code> could be processed seperately. 
	 * @return  - <i>true</i>, if the <code>TaskParts</code> could be processed seperately.
	 */
	public boolean isPartAble();
	
	/**
	 * Returns the <code>Task</code>'s <code>TaskResultMarshaler</code>.
	 * @return The <code>TaskResultMarshaler</code>.
	 */
	public ITaskResultMarshaler getResultMarshaler();
}