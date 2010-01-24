/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskPart;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;

/**
 * Instances of this class are <code>Services</code> to integrate
 * <code>Plugins</code> into the <code>PluginContext</code>.
 * <p>
 * This <code>Service</code> is second in the line of pluginloading. Its purpose
 * is to integrate the loaded plugins into the <code>PluginContext</code> by
 * using {@link org.dotplot.core.plugins.IPluginContext#installPlugin(P)}.
 * </p>
 * <hr>
 * <h2>Workingcontext</h2> {@link org.dotplot.core.plugins.IPluginListContext}
 * <hr>
 * <h2>Hotspots</h2> none
 * <hr>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.core.plugins.PluginIntegrationTaskPart
 * @see org.dotplot.core.plugins.PluginLoadingService
 * @see org.dotplot.core.plugins.InitializerService
 * @see org.dotplot.core.plugins.IPluginListContext
 */
public class PluginIntegrationService extends PlugableService<IPluginContext> {

	/**
	 * Creates a new <code>PluginIntegrationService</code>.
	 * 
	 * @param id
	 *            - The id of the <code>Service</code>.
	 */
	public PluginIntegrationService(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	@Override
	public ITask createTask() {
		ITaskResultMarshaler marshaler = new ITaskResultMarshaler() {

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				/* nothing to marshal without results */
				return null;
			}
		};

		if (this.frameworkContext instanceof NullContext) {
			this.getErrorHandler().fatal(this,
					new PluginException("framework is missing"));
			return null;
		}

		if (this.getWorkingContext() instanceof NullContext) {
			this.getErrorHandler().fatal(this,
					new PluginException("workingcontext is missing"));
			return null;
		}

		Collection<? extends IPlugin> plugins = ((IPluginListContext) this
				.getWorkingContext()).getPluginList();

		ITaskPart part = new PluginIntegrationTaskPart("Part 1", plugins,
				this.frameworkContext);

		Task task = new Task("Pluginintegrationtask", marshaler, false);
		task.addPart(part);
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#getResultContext()
	 */
	@Override
	public IContext getResultContext() {
		return new NullContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	@Override
	public Class getResultContextClass() {
		return NullContext.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#init()
	 */
	@Override
	public void init() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.AbstractService#workingContextIsCompatible(
	 * java.lang.Class)
	 */
	@Override
	public boolean workingContextIsCompatible(Class contextClass) {
		return IPluginListContext.class.isAssignableFrom(contextClass);
	}

}
