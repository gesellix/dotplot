/**
 * 
 */
package org.dotplot.core.system;

import org.apache.log4j.Logger;
import org.dotplot.core.plugins.BatchJob;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITaskProcessor;

/**
 * Job for loading plugins.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.core.plugin.PluginLoadingService
 * @see org.dotplot.core.plugin.PluginIntegrationService
 * @see org.dotplot.core.plugin.InitializerService
 */
public final class PluginLoadingJob implements IJob {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger
			.getLogger(PluginLoadingJob.class.getName());

	/**
	 * The Job.
	 */
	private BatchJob job;

	/**
	 * Creates a new <code>PluginLoadingJob</code>.
	 * 
	 * @param loaderID
	 *            - id of the <code>PluginLoadingService</code>
	 * @param integratorID
	 *            - id of the <code>PluginIntegrationService</code>
	 * @param inizializerID
	 *            - id of the <code>InitializerService</code>
	 */
	public PluginLoadingJob(String loaderID, String integratorID,
			String inizializerID) {
		super();
		LOGGER.debug("Constructor:" + loaderID + ", " + integratorID + ", "
				+ inizializerID);
		this.job = new BatchJob();
		this.job.addService(loaderID);
		this.job.addService(integratorID);
		this.job.addService(inizializerID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IJob#getErrorHandler()
	 */
	public IErrorHandler getErrorHandler() {
		return this.job.getErrorHandler();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dotplot.core.services.IJob#process(org.dotplot.core.services.
	 * IServiceManager, org.dotplot.core.services.IFrameworkContext)
	 */
	public boolean process(IFrameworkContext context) {
		LOGGER.debug("process");
		return this.job.process(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.IJob#setErrorHandler(org.dotplot.core.services
	 * .IErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {
		this.job.setErrorHandler(handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.IJob#setTaskProcessor(org.dotplot.core.services
	 * .ITaskProcessor)
	 */
	public void setTaskProcessor(ITaskProcessor processor) {
		this.job.setTaskProcessor(processor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core
	 * .services.IServiceManager, org.dotplot.core.services.IFrameworkContext)
	 */
	public boolean validatePreconditions(IServiceRegistry manager) {
		LOGGER.debug("validate");
		return this.job.validatePreconditions(manager);
	}
}
