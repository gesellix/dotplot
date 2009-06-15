/**
 * 
 */
package org.dotplot.core.services;

import java.util.Map;
import java.util.TreeMap;

/**
 * Default implementation of the interface <code>ITaskProcessor</code>.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class TaskProcessor implements ITaskProcessor<Object> {

    /**
     * Storrage for result of single taskparts.
     */
    private Map<String, Object> jobResults = new TreeMap<String, Object>();

    /**
     * Contains the result of the last processed <code>Job</code>.
     */
    private Object result = null;

    /**
     * The invoker of the <code>TaskProcessor</code>.
     * 
     * @see #processTask(ITask, Object)
     */
    private Object invoker;

    /**
     * Contains the current <code>ProcessingErrorHandler</code>.
     */
    private IErrorHandler handler = new DefaultErrorHandler();

    /**
     * Indicates is the processing is interuped.
     */
    private boolean interupted = false;

    /**
     * The currently processed <code>ITaslPart</code>.
     */
    private ITaskPart currentPart;

    /**
     * Returns the current errorhandler.
     * 
     * @return - the errorhandler.
     */
    public IErrorHandler getErrorHandler() {
	return handler;
    }

    /**
     * Returns the object which invokes the <code>TaskProcessor</code>.
     * 
     * @return - The invoking object.
     */
    public Object getInvokingObject() {
	return this.invoker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJobProcessor#getJobResult()
     */
    public Object getTaskResult() {
	return this.result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.services.IJobProcessor#processJob(org.dotplot.services.IJob)
     */
    public boolean process(ITask task) {
	boolean result = this.processTask(task);
	this.invoker = null; // nach process, falls task == null ist
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.ITaskProcessor#processTask(org.dotplot.core
     * .services.ITask, Invoker)
     */
    public boolean process(ITask task, Object invokingObject) {
	if (invokingObject == null) {
	    throw new NullPointerException();
	}
	boolean result = this.processTask(task);
	this.invoker = invokingObject; // nach process, falls task == null ist
	return result;
    }

    /**
     * 
     * @param task
     *            - the <code>Task</code> to be processed.
     * @return <code>true</code> if the processing was successfull,
     *         <code>false</code> otherwise.
     */
    private boolean processTask(ITask task) {
	if (task == null) {
	    throw new NullPointerException();
	}
	this.interupted = false;
	this.jobResults.clear();

	this.result = null;
	for (ITaskPart part : task.getParts()) {
	    if (this.interupted) {
		return false;
	    }
	    this.currentPart = part;
	    try {
		this.currentPart.setLocalRessources(this.currentPart
			.getRessources());
		this.currentPart.run();
		if (this.currentPart.errorOccured()) {
		    this.currentPart = null;
		    return false;
		}
		this.jobResults.put(this.currentPart.getID(), this.currentPart
			.getResult());
	    } catch (InsufficientRessourcesException e) {
		this.handler.fatal(this, e);
		this.currentPart = null;
		return false;
	    } catch (Exception e) {
		this.handler.fatal(this, new ServiceException(e.getClass()
			.getName()
			+ ":" + e.getMessage()));
		this.currentPart = null;
		return false;
	    }
	}
	this.result = task.getResultMarshaler().marshalResult(this.jobResults);
	task.done();
	this.currentPart = null;
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.services.IJobProcessor#setErrorHandler(org.dotplot.services
     * .IProcessingErrorHandler)
     */
    public void setErrorHandler(IErrorHandler handler) {
	if (handler == null) {
	    throw new NullPointerException();
	}
	this.handler = handler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskProcessor#stop()
     */
    public synchronized void stop() {
	this.interupted = true;
	if (this.currentPart != null) {
	    this.currentPart.stop();
	}
    }
}
