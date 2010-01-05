/**
 * 
 */
package org.dotplot.core.services;

/**
 * Handler for error which can occure in the service framework.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IErrorHandler {

    /**
     * Handles an error.
     * <p>
     * A error is usualy unexpected and interupts the program, which need
     * further processing, if it should resume its work or not.
     * </p>
     * 
     * @param j
     *            The source of the error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void error(IJob<?> j, Exception e);

    /**
     * Handles an error.
     * <p>
     * A error is usualy unexpected and interupts the program, which need
     * further processing, if it should resume its work or not.
     * </p>
     * 
     * @param s
     *            The source of the error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void error(IService<?, ?> s, Exception e);

    /**
     * Handles an error.
     * <p>
     * A error is usualy unexpected and interupts the program, which need
     * further processing, if it should resume its work or not.
     * </p>
     * 
     * @param t
     *            The source of the error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void error(ITaskPart t, Exception e);

    /**
     * Handles an error.
     * <p>
     * A error is usualy unexpected and interupts the program, which need
     * further processing, if it should resume its work or not.
     * </p>
     * 
     * @param t
     *            The source of the error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void error(ITaskProcessor<?> t, Exception e);

    /**
     * Handles a fatal error.
     * <p>
     * A fatal error is usualy unrecoverable an interupts the program
     * immediately.
     * </p>
     * 
     * @param j
     *            The source of the fatal error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void fatal(IJob<?> j, Exception e);

    /**
     * Handles a fatal error.
     * <p>
     * A fatal error is usualy unrecoverable an interupts the program
     * immediately.
     * </p>
     * 
     * @param s
     *            The source of the fatal error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void fatal(IService<?, ?> s, Exception e);

    /**
     * Handles a fatal error.
     * <p>
     * A fatal error is usualy unrecoverable an interupts the program
     * immediately.
     * </p>
     * 
     * @param t
     *            The source of the fatal error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void fatal(ITaskPart t, Exception e);

    /**
     * Handles a fatal error.
     * <p>
     * A fatal error is usualy unrecoverable an interupts the program
     * immediately.
     * </p>
     * 
     * @param t
     *            The source of the fatal error.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void fatal(ITaskProcessor<?> t, Exception e);

    /**
     * Handles a warning.
     * <p>
     * A warning is usualy no problem for the programm, but a note for the user.
     * </p>
     * 
     * @param j
     *            The source of the warning.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void warning(IJob<?> j, Exception e);

    /**
     * Handles a warning.
     * <p>
     * A warning is usualy no problem for the programm, but a note for the user.
     * </p>
     * 
     * @param s
     *            The source of the warning.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void warning(IService<?, ?> s, Exception e);

    /**
     * Handles a warning.
     * <p>
     * A warning is usualy no problem for the programm, but a note for the user.
     * </p>
     * 
     * @param t
     *            The source of the warning.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void warning(ITaskPart t, Exception e);

    /**
     * Handles a warning.
     * <p>
     * A warning is usualy no problem for the programm, but a note for the user.
     * </p>
     * 
     * @param t
     *            The source of the warning.
     * @param e
     *            The <code>Exception</code> which caused the error.
     */
    public void warning(ITaskProcessor<?> t, Exception e);
}
