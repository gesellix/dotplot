/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.plugins.ressources.DirectoryRessource;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PluginLoadingService extends PlugableService<IPluginContext<IPlugin>> {
	
	/**
	 * The service's resultcontext.
	 */
	private IContext resultContext;

	/**
	 * Creates a new <code>PluginLoadingService</code>.
	 * @param id The serviceid.
	 */
	public PluginLoadingService(String id) {
		super(id);
		this.resultContext = new NullContext();
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#workingContextIsCompatible(java.lang.Class)
	 */
	public boolean workingContextIsCompatible(Class contextClass) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#init()
	 */
	public void init() {}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#getResultContext()
	 */
	public IContext getResultContext() {
		if(this.getTaskProcessor().getTaskResult() != null){
			Object o = this.getTaskProcessor().getTaskResult();
			if(o instanceof Collection){
				final Collection<? extends IPlugin> list = (Collection<? extends IPlugin>)o;
				this.resultContext = new IPluginListContext(){

					public Collection<? extends IPlugin> getPluginList() {
						return list;
					}

					};				
			}
		}
		return this.resultContext;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#getResultContextClass()
	 */
	public Class getResultContextClass() {
		return IPluginListContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#createTask()
	 */
	public ITask createTask() {
		if(this.frameworkContext == null) return null;
		Task task = new Task(
				"Pluginloadingtask",
				new ITaskResultMarshaler(){

					public Object marshalResult(Map<String,? extends  Object> taskResult) {
						Object result = taskResult.get("Part 1");
						return ((Map)result).values();
					}},
				false);		
		task.addPart(new PluginLoadingTaskPart(
						"Part 1", 
						new DirectoryRessource(this.frameworkContext.getPluginDirectory()),
						this.frameworkContext));
		return task;
	}

}
