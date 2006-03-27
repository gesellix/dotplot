/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Default implementation of the interface <code>IService</code>.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public abstract class AbstractService<F extends IFrameworkContext, S extends IServiceHotSpot> implements IService<F,S>{

	/**
	 * The serviceid.
	 */
	private String id;

	/**
	 * The <code>FrameworkContext</code>.
	 */
	protected F frameworkContext;
	
	/**
	 * The workingcontext.
	 */
	private IContext workingContext;
	
	/**
	 * The <code>ErrorHandler</code>
	 */
	private IErrorHandler errorHandler;
	
	/**
	 * <code>Map</code> for administering <code>ServiceHotSpots</code> 
	 */
	private Map<String, S> hotSpots;

	/**
	 * The <code>TaskProcessor</code>.
	 */
	private ITaskProcessor processor;

	/**
	 * Indicates if the <code>Service</code> is running.
	 */
	protected boolean isRunning;
	
	/**
	 * Indicates if the <code>Service</code> should be interupted.
	 */
	protected boolean isInterrupted;
	
	/**
	 * Creates a new <code>AbstractService</code>.
	 * @param id - The serviceid.
	 */
	public AbstractService(String id) {
		if(id == null) throw new NullPointerException();
		this.isRunning = false;
		id = id.trim();
		if(id.length() == 0) throw new IllegalArgumentException("wrong id");
		
		this.id = id;
		
		this.hotSpots = new TreeMap<String, S>();
		try {
			this.setWorkingContext(null);
		}
		catch (IllegalContextException e) {
			//could not be thrown at this point
		}
		this.setErrorHandler( new DefaultErrorHandler());
		this.setTaskProcessor(new TaskProcessor());
	}

	/**
	 * Adds a <code>ServiceHotSpot</code> to the <code>Service</code>.
	 * @param hotSpot - the <code>ServiceHotSpot</code> to add.
	 */
	protected void addHotSpot(S hotSpot){
		if(hotSpot == null) throw new NullPointerException();
		this.hotSpots.put(hotSpot.getID(), hotSpot);
	}
		
	/**
	 * Returns the <code>ErrorHandler</code>.
	 * @return The <code>ErrorHandler</code>.
	 */
	public IErrorHandler getErrorHandler(){
		return this.errorHandler;
	}
	
	/**
	 * Returns the workingcontext.
	 * @return - the workingcontext.
	 */
	public IContext getWorkingContext(){
		return this.workingContext;
	}
	
	/**
	 * Returns the <code>TaskProcessor</code>.
	 * @return The <code>TaskProcessor</code>.
	 */	
	public ITaskProcessor getTaskProcessor(){
		return this.processor;		
	}
		
	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#getID()
	 */
	public final String getID() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#setFrameworkContext(org.dotplot.services.FrameworkContext)
	 */
	public void setFrameworkContext(F context) {
		if(context == null) throw new NullPointerException();
		this.frameworkContext = context;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.services.IService#setWorkingContext(org.dotplot.services.IContext)
	 */
	public void setWorkingContext(IContext workingContext) throws IllegalContextException{
		if(workingContext == null){
			this.workingContext = new NullContext();
		}
		else if(this.workingContextIsCompatible(workingContext.getClass())){
			this.workingContext = workingContext;
		}
		else {
			throw new IllegalContextException(workingContext);
		}
	}
		
	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#setErrorHandler(org.dotplot.services.IProcessingErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {
		if(handler == null) throw new NullPointerException();
		this.errorHandler = handler;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#gethotSpots()
	 */
	public Map getHotSpots() {
		return this.hotSpots;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.core.services.IService#getHotSpot(java.lang.String)
	 */
	public S getHotSpot(String id)throws UnknownServiceHotSpotException{
		if(id == null) throw new NullPointerException();
		S spot = this.hotSpots.get(id);
		if(spot == null) throw new UnknownServiceHotSpotException(id);
		return spot;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#getServiceExtentions(java.lang.String)
	 */
	public Collection<?> getExtentions(String hotSpotID) throws UnknownServiceHotSpotException{
		IServiceHotSpot spot = this.getHotSpot(hotSpotID);		
		return spot.getAllExtentions();
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#addExtention(org.dotplot.services.IServiceHotSpot, java.lang.Object)
	 */
	public void addExtention(String hotSpotID, Extention extention) throws UnknownServiceHotSpotException {
		if(hotSpotID == null || extention == null) throw new NullPointerException();
		IServiceHotSpot spot = this.getHotSpot(hotSpotID);
		spot.addExtention(extention);
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.services.IService#addExtention(java.lang.String, org.dotplot.services.IServicePlugin, java.lang.Object)
	 */
	public void addExtention(String hotSpotID, IServiceExtentionActivator plugin, Object extentionObject) throws UnknownServiceHotSpotException {
		if(hotSpotID == null || plugin == null || extentionObject == null) throw new NullPointerException();
		Extention e = new Extention(plugin, extentionObject);
		this.addExtention(hotSpotID, e);
	}
		
	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.services.IService#setServiceProcessor(org.dotplot.services.IServiceProcessor)
	 */
	public void setTaskProcessor(ITaskProcessor processor){
		if(processor == null) throw new NullPointerException();
		this.processor = processor;
	}

	/*
	 *  (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		this.isRunning = true;
		this.isInterrupted = false;
		ITask task = this.createTask();
		if(task != null){
			if(! this.processor.process(task)){
				this.isRunning = false;
				throw new RuntimeException();
			}
		}
		this.isRunning = false;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.util.ExecutionUnit#stop()
	 */
	public synchronized void stop(){
		if(this.isRunning){
			this.isInterrupted = true;
			this.processor.stop();
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.util.ExecutionUnit#isRunning()
	 */
	public synchronized boolean isRunning(){
		 return this.isRunning;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IService#workingContextIsCompatible(java.lang.Class)
	 */
	public abstract boolean workingContextIsCompatible(Class contextClass);

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IService#init()
	 */
	public abstract void init();

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IService#getResultContext()
	 */
	public abstract IContext getResultContext();

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IService#getResultContextClass()
	 */
	public abstract Class getResultContextClass();

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IService#createTask()
	 */
	public abstract ITask createTask();
}
