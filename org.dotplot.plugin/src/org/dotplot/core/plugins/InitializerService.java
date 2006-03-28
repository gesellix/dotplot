/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;

/**
 * 
 * <hr>
 * 	<h2>Workingcontext</h2>
 * <hr>
 * 	<h2>Hotspots</h2>
 * <hr>
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class InitializerService extends PlugableService<IPluginContext> {

	public static final String ID_HOTSPOT_STARTUP = "org.dotplot.core.init.StartUp";
	public static final String ID_HOTSPOT_SHUTDOWN = "org.dotplot.core.init.ShutDown";
	
	private Collection<IJob> startUpjobs;
	private Collection<IJob> shutdownjobs;
	
	/**
	 * @param id
	 */
	public InitializerService(String id) {
		super(id);
		this.startUpjobs = new Vector<IJob>();
		this.shutdownjobs = new Vector<IJob>();
		this.addHotSpot(new PluginHotSpot(ID_HOTSPOT_STARTUP, IJob.class));
		this.addHotSpot(new PluginHotSpot(ID_HOTSPOT_SHUTDOWN, IJob.class));
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<IJob> getStartUpJobs(){		
		return this.startUpjobs;
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<IJob> getShutdownJobs(){		
		return this.shutdownjobs;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	public boolean workingContextIsCompatible(Class contextClass) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#init()
	 */
	public void init() {
		this.startUpjobs.clear();
		try {
			IPluginHotSpot spot = this.getHotSpot(ID_HOTSPOT_STARTUP);
			for(Object o : spot.getObjectsFromActivatedExtentions()){
				this.startUpjobs.add((IJob)o);
			}
		}
		catch (UnknownServiceHotSpotException e) {
			/*dann eben nicht*/
		}
		
		this.shutdownjobs.clear();
		try {
			IPluginHotSpot spot = this.getHotSpot(ID_HOTSPOT_SHUTDOWN);
			for(Object o : spot.getObjectsFromActivatedExtentions()){
				this.shutdownjobs.add((IJob)o);
			}
		}
		catch (UnknownServiceHotSpotException e) {
			/*dann eben nicht*/
		} 
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContext()
	 */
	public IContext getResultContext() {
		if(this.getTaskProcessor().getTaskResult() != null){
			return new NullContext();
		}
		else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	public Class getResultContextClass() {
		return NullContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	public ITask createTask() {
		ITask task = new Task("Initializer", new ITaskResultMarshaler(){

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				return new Object();
			}}, false);		
		task.addPart(new InitialiserTaskPart("part 1",  this.frameworkContext));
		return task;
	}	
}
