/**
 * 
 */
package org.dotplot.core.services;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IJob<C extends IFrameworkContext> {

    /**
     * Returns the <code>ErrorHandler</code>.
     * 
     * @return The <code>ErrorHandler</code>.
     */
    public IErrorHandler getErrorHandler();

    /**
     * This method starts the processing of the <code>job</code>.
     * <p>
     * Invokers of this method should use <code>validatePreconditions()</code>
     * before invoking this method.
     * </p>
     * 
     * @param context
     *            The <code>FrameworkContext</code> for processing the
     *            <code>Job</code>.
     * @return - <code>true</code> if the job was successfully processed,
     *         <code>false</code> otherwise.
     * @see #validatePreconditions(IServiceRegistry)
     */
    public boolean process(C context);

    /**
     * Thats the <code>ErrorHandler</code>.
     * 
     * @param handler
     *            The <code>ErrorHandler</code>.
     */
    public void setErrorHandler(IErrorHandler handler);

    /**
     * Sets the <code>TaskProcessor</code>.
     * <p>
     * The set <code>TaskProcessor</code> is used for processing
     * <code>Services</code>.
     * </p>
     * 
     * @param processor
     *            The <code>TaskProcessor</code>.
     * @see IService
     * @see ITask
     * @see ITaskPart
     */
    public void setTaskProcessor(ITaskProcessor processor);

    /**
     * Validates the preconditions for processing the <code>Job</code>.
     * <p>
     * This method serves primary for checking if all nessesary
     * <code>Services</code> for processing the <code>Job</code> are registered
     * to the <code>ServiceRegistry</code>. But implementers should feel free to
     * check every precondition they linke.
     * </p>
     * 
     * @param registry
     *            The <code>ServiceRegistry</code> for validating.
     * @return <code>true</code> if the preconditions are validated,
     *         <code>false</code> otherwise.
     */
    public boolean validatePreconditions(IServiceRegistry registry);
}
