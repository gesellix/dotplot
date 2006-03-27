/**
 * 
 */
package org.dotplot.core.services;

/**
 * Defaultimplementation of the interface <code>IProcessingErrorHandler</code>.
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class DefaultErrorHandler implements IErrorHandler {

	/**
	 * A buffer for errormessages.
	 */
	private StringBuffer errorMessages = new StringBuffer();
	
	/**
	 * A couter for errors.
	 */
	private Integer errorCounter = new Integer(0);
	
	/**
	 * Returns the buffered errormessages.
	 * @return The errormessages.
	 */
	public String getErrorMessages(){
		return errorMessages.toString();
	}
	
	/**
	 * Clears the errormessages buffer.
	 */
	public void clearErrorMessages(){
		this.errorMessages.setLength(0);
	}
	
	/**
	 * Adds an errormessage to the buffer.
	 * @param sourceType The type of the source.
	 * @param sourceID The id of the source.
	 * @param errorType The type of the error.
 	 * @param errorMessage The errormessage.
	 * @param grade The grade of the error.
	 */
	private void addErrorMessage(String sourceType, String sourceID, String errorType, String errorMessage, String grade){
		if(errorMessage == null) errorMessage = "no message";
		this.errorCounter++;
		this.errorMessages.append(String.format("[%d]%s(%s) at %s:%s -> %s\n", 
				new Object[]{
					this.errorCounter,
					errorType,
					grade,
					sourceType,
					sourceID,
					errorMessage
					}));
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#error(org.dotplot.core.services.ITaskPart, org.dotplot.core.services.ServiceException)
	 */
	public void error(ITaskPart t, Exception e) {
		this.addErrorMessage("TaskPart", t.getID(),"",e.getMessage(),"error");
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#fatal(org.dotplot.core.services.ITaskPart, org.dotplot.core.services.ServiceException)
	 */
	public void fatal(ITaskPart t, Exception e) {
		this.addErrorMessage("TaskPart", t.getID(),"",e.getMessage(),"fatal");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#warning(org.dotplot.core.services.ITaskPart, org.dotplot.core.services.ServiceException)
	 */
	public void warning(ITaskPart t, Exception e) {
		this.addErrorMessage("TaskPart", t.getID(),"",e.getMessage(),"warning");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#error(org.dotplot.core.services.IService, org.dotplot.core.services.ServiceException)
	 */
	public void error(IService<?,?> s, Exception e) {
		this.addErrorMessage("Service", s.getID(),"",e.getMessage(),"error");
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#fatal(org.dotplot.core.services.IService, org.dotplot.core.services.ServiceException)
	 */
	public void fatal(IService<?,?> s, Exception e) {
		this.addErrorMessage("Service", s.getID(),"",e.getMessage(),"fatal");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#warning(org.dotplot.core.services.IService, org.dotplot.core.services.ServiceException)
	 */
	public void warning(IService<?,?> s, Exception e) {
		this.addErrorMessage("Service", s.getID(),"",e.getMessage(),"warning");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#error(org.dotplot.core.services.IJob, org.dotplot.core.services.ServiceException)
	 */
	public void error(IJob j, Exception e) {
		this.addErrorMessage("Job", "no id","",e.getMessage(),"error");
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#fatal(org.dotplot.core.services.IJob, org.dotplot.core.services.ServiceException)
	 */
	public void fatal(IJob j, Exception e) {
		this.addErrorMessage("Job", "no id","",e.getMessage(),"fatal");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#warning(org.dotplot.core.services.IJob, org.dotplot.core.services.ServiceException)
	 */
	public void warning(IJob j, Exception e) {
		this.addErrorMessage("Job", "no id","",e.getMessage(),"warning");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#error(org.dotplot.core.services.ITaskProcessor, org.dotplot.core.services.ServiceException)
	 */
	public void error(ITaskProcessor t, Exception e) {
		this.addErrorMessage("TaskProcessor", "no id","",e.getMessage(),"error");		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#fatal(org.dotplot.core.services.ITaskProcessor, org.dotplot.core.services.ServiceException)
	 */
	public void fatal(ITaskProcessor t, Exception e) {
		this.addErrorMessage("TaskProcessor", "no id","",e.getMessage(),"fatal");				
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IErrorHandler#warning(org.dotplot.core.services.ITaskProcessor, org.dotplot.core.services.ServiceException)
	 */
	public void warning(ITaskProcessor t, Exception e) {
		this.addErrorMessage("TaskProcessor", "no id","",e.getMessage(),"warning");
	}
	
	/*  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.getErrorMessages();
	}
}
