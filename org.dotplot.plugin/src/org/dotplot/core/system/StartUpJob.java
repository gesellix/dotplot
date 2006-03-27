/**
 * 
 */
package org.dotplot.core.system;

import org.dotplot.core.DotplotContext;
import org.dotplot.core.plugins.InitializerService;
import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.util.UnknownIDException;

/**
 * Instances of this class are <code>Job</code>s to be excuted in system start up.
 * <p>
 * The <code>InitializerService</code> is used to collect jobs which should be executed on 
 * start up and executes them. 
 * </p>
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.core.plugins.InitializerService
 * @see org.dotplot.core.plugins.InitializerService#getStartUpJobs()
 */
public class StartUpJob extends AbstractJob<DotplotContext> {

	/**
	 * Creates a new <code>StartUpJob</code>.
	 */
	public StartUpJob() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#process(C)
	 */
	public boolean process(DotplotContext context) {
		try {
			InitializerService service = (InitializerService) context.getServiceRegistry().get(CoreSystem.SERVICE_INITIALIZER_ID);
			for(IJob job : service.getStartUpJobs()){
				job.setErrorHandler(this.getErrorHandler());
				context.executeJob(job);
			}
			return true;
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this, e);
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core.services.IServiceRegistry)
	 */
	public boolean validatePreconditions(IServiceRegistry registry) {		
		try {
			registry.get(CoreSystem.SERVICE_INITIALIZER_ID);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
