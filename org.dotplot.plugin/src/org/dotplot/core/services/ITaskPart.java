/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;

import org.dotplot.util.ExecutionUnit;

/**
 * Interface for <code>TaskParts</code>.
 * <p>
 * A <code>TaskPart</code> is an independent execution unit of a
 * <code>Task</code>. Its independence is an existentiell part in the execution
 * prolicy of <code>TaskProcessors</code> and is realized by an differential
 * ressource policy. The ressources assigend to a <code>TaskPart</code> depent
 * on the execution context of the <code>TaskProcessor</code>. Its the
 * <code>TaskProcessor</code>'s duty to transform them into a localcontext for
 * the <code>TaskPart</code>, so that they could be used without thinking about
 * synchronization. Possible synchronization must be handled by the
 * <code>TaskProcessor</code>.
 * </p>
 * <p>
 * An id is assigned to a <code>TaskPart</code> for identification. This is
 * usefull for parallel processing of <code>TaskParts</code>.
 * </p>
 * <p>
 * This interface extends <code>Runnable</code> to be executed in a single
 * <code>Thread</code>.
 * </p>
 * <p>
 * Default invoking sequence:
 * <ol>
 * <li><code>setErrorHandler()</code></li>
 * <li><code>getRessources()</code></li>
 * <li><code>setLocalRessources()</code></li>
 * <li><code>run()</code></li>
 * <li><code>getResult()</code></li>
 * </ol>
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see ITask
 * @see ITaskProcessor
 */
public interface ITaskPart extends ExecutionUnit {

    /**
     * Indicates if an error occured during processing of the
     * <code>TaskPart</code>.
     * 
     * @return <code>true</code> if an error occured and <code>false</code>
     *         otherwhise.
     */
    public boolean errorOccured();

    /**
     * Returns the id of the <code>TaskPart</code>.
     * 
     * @return The id.
     */
    public String getID();

    /**
     * Returns the ressources of the <code>TaskPart</code>.
     * 
     * @return The ressources.
     */
    public Collection<? extends IRessource> getRessources();

    /**
     * Returns the result of processing of the <code>TaskPart</code>.
     * 
     * @return <code>null</code> if the <code>run()</code> method is not yet
     *         invoced and the result afterwards.
     */
    public Object getResult();

    /**
     * Sets the <code>ErrorHandler</code> of the <code>TaskPart</code>.
     * 
     * @param handler
     *            - the <code>ErrorHandler</code>.
     * @throws NullPointerException
     *             if handler is <code>null</code>.
     */
    public void setErrorHandler(IErrorHandler handler);

    /**
     * Sets the local ressources of the <code>TaskPart</code>.
     * 
     * @param ressouceList
     *            - the list of local ressources.
     * @throws InsufficientRessourcesException
     *             if the ressources are indufficient for the
     *             <code>TaskPart</code> to accomplish its result.
     */
    public void setLocalRessources(Collection<? extends IRessource> ressouceList)
	    throws InsufficientRessourcesException;
}
