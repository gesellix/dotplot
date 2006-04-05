/**
 * 
 */
package org.dotplot.core;

import java.lang.reflect.InvocationTargetException;

import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IServiceRegistry;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public abstract class JobWithProgress extends AbstractJob<DotplotContext> implements IJobWithProgress {

	private DotplotContext context;
	
	private boolean result; 
	
	/**
	 * Creates a new <code>JobWithProgress</code>.
	 */
	public JobWithProgress() {
		super();
		this.result = false;
		this.context = null;
	}

	public void setContext(DotplotContext context){
		this.context = context;
	}

	public DotplotContext getContext(){
		return this.context;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		this.result = this.process(this.getContext(), monitor);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core.services.IServiceRegistry)
	 */
	public abstract boolean validatePreconditions(IServiceRegistry registry);

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#process(C)
	 */
	public boolean process(DotplotContext context){
		this. result = this.process(context, new IProgressMonitor(){

			public void beginTask(String name, int totalWork) {
			}

			public void done() {
			}

			public void internalWorked(double work) {
			}

			public boolean isCanceled() {
				return false;
			}

			public void setCanceled(boolean value) {
			}

			public void setTaskName(String name) {
			}

			public void subTask(String name) {
			}

			public void worked(int work) {
			}});
		
		return this.result;
	}
	
	protected abstract boolean process(DotplotContext context, IProgressMonitor monitor);

	public boolean getProcessResult(){
		return this.result;
	}
}
