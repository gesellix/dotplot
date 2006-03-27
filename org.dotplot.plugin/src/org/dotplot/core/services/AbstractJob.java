/**
 * 
 */
package org.dotplot.core.services;

/**
 * Default implementation of the interface <code>IJob</code>.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public abstract class AbstractJob<C extends IFrameworkContext> implements IJob<C> {

	/**
	 * The <code>TaskProcessor</code> to process this <code>Job</code>.
	 */
	private ITaskProcessor processor;
	
	/**
	 * The <code>ErrorHandler/code> of the <code>Job</code>.
	 */
	private IErrorHandler handler;
	
	/**
	 * Creates a new <code>AbstractJob</code>.
	 */
	public AbstractJob(){
		this.handler = new DefaultErrorHandler();
		this.processor = new TaskProcessor();	
	}

	/**
	 * Returns the <code>ErrorHandler</code> of the <code>Job</code>.
	 */
	public IErrorHandler getErrorHandler(){
		return this.handler;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.services.IJob#setErrorHandler(org.dotplot.services.IProcessingErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {
		if(handler == null) throw new NullPointerException();
		this.handler = handler;
	}
		
	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#setTaskProcessor(org.dotplot.core.services.ITaskProcessor)
	 */
	public void setTaskProcessor(ITaskProcessor processor) {
		if(processor == null) throw new NullPointerException();
		this.processor = processor;
	}

	/**
	 * Returns the <code>TaskProcessor</code> of this <code>Job</code>.
	 * @return - the processor.
	 */
	public ITaskProcessor getTaskProcessor() {
		return processor;
	}

}
