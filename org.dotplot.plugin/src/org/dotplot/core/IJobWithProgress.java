/**
 * 
 */
package org.dotplot.core;

import org.dotplot.core.services.IJob;
import org.eclipse.jface.operation.IRunnableWithProgress;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface IJobWithProgress extends IRunnableWithProgress, IJob<DotplotContext> {
	
	public void setContext(DotplotContext context);
	public boolean getProcessResult();
}
