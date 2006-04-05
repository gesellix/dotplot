/**
 * 
 */
package org.dotplot.core;

import java.io.File;

import org.apache.log4j.Logger;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.ITaskProcessor;
import org.dotplot.core.services.TaskProcessor;
import org.dotplot.fmatrix.TokenTable;
import org.dotplot.fmatrix.TypeTable;
import org.dotplot.util.UnknownIDException;

/**
 * Instances of this class serves as frameworkcontext of the dotplot system.
 * <p>
 * For usage in the dotplot system the <code>DotplotContext</code> is created
 * by the <code>ContextFactory</code>. Use <code>ContextFactory.getContext()</code> insted of 
 * creating a <code>DotplotContext</code> directly. 
 * </p> 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see ContextFactory
 */
public class DotplotContext extends PluginContext<Plugin>{

	/**
	 * Logger for debuginformation.
	 */
	private static final Logger logger = Logger.getLogger(DotplotContext.class
			.getName());
	
	/**
	 * The configuration registry.
	 */
	private IConfigurationRegistry configurationRegistry;
	
	/**
	 * The type binfing registry.
	 */
	private ITypeBindingRegistry typeBindigRegistry;
	
	/**
	 * The type registry.
	 */
	private ITypeRegistry typeRegistry;
	
	/**
	 * The current <code>SourceList</code>.
	 */
	private ISourceList sourceList;
	
	/**
	 * The current <code>TypeTable</code>.
	 */
	private ITypeTable typeTable;
	
	/**
	 * The id of the dotplot systems <code>GuiService</code>.
	 * @see IGuiService
	 */
	private String guiServiceID;
	
	/**
	 * The current dotplot.
	 */
	private IDotplot currentDotplot;

	/**
	 * The <code>TaskProcessor</code> to process <code>Jobs</code>.
	 */
	private ITaskProcessor taskProcessor;

	/**
	 * Indicates tha no gui-responce should be used in case of an error.
	 */
	private boolean noGui;
	
	/**
	 * Creates a new <code>DotplotContext</code>.
	 * @param workingDirectory - the working directroy of the system
	 * @param pluginDirectory - the plugin dirextory of the system
	 */
	public DotplotContext(String workingDirectory, String pluginDirectory) {
		super(workingDirectory, pluginDirectory);
		logger.debug("workingdirectory: " + workingDirectory);
		logger.debug("plugindirectory: " + pluginDirectory);
		this.init();
	}

	/**
	 * Creates a new <code>DotplotContext</code>.
	 * @param workingDirectory - the working directroy of the system
	 */
	public DotplotContext(String workingDirectory) {
		super(workingDirectory);
		logger.debug("workingdirectory: " + workingDirectory);
		this.init();
	}
	
	/**
	 * Inititializes the <code>DotplotContext</code>. 
	 */
	private void init(){
		this.currentDotplot = null;
		this.noGui = false;
		this.taskProcessor = new TaskProcessor();
		this.configurationRegistry = new ConfigurationRegistry();
		this.typeRegistry = new TypeRegistry();
		this.typeBindigRegistry = new TypeBindingRegistry(this.typeRegistry);
		this.setSourceList(new DefaultSourceList());
		this.typeTable = new TypeTable(new TokenTable());
		this.guiServiceID = "";
		
	}
	
	/**
	 * Returns the <code>ConfigurationRegistry</code>.
	 * @return The <code>ConfigurationRegistry</code>.
	 */
	public IConfigurationRegistry getConfigurationRegistry(){
		return this.configurationRegistry;
	}
	
	/**
	 * Returns the <code>TypeRegistry</code>.
	 * @return The <code>TypeRegistry</code>.
	 */
	public ITypeRegistry getTypeRegistry(){
		return this.typeRegistry;
	}
	
	/**
	 * Returns the <code>TypeBindingRegistry</code>.
	 * @return The <code>TypeBindingRegistry</code>.
	 */
	public ITypeBindingRegistry getTypeBindingRegistry(){
		return this.typeBindigRegistry;
	}
	
	/**
	 * Creates a <code>DotplotFile</code> out of a <code>File</code>.
	 * <p>
	 * The <code>DotplotFile</code> is created by using the <code>DotplotContext</code>'s
	 * <code>TypeRegistry</code> and <code>TypeBindingRegistry</code>. If no appropiate
	 * <code>SourceType</code> could be found the <code>BaseType</code>
	 * will be assigned. 
	 * </p>
	 * @param file - the base file.
	 * @return The new <code>DotplotFile</code>
	 * @see BaseType
	 */
	public DotplotFile createDotplotFile(File file){
		ISourceType type;
		String typename;
		
		if(file == null) throw new NullPointerException();
		if(! file.isFile()) throw new IllegalArgumentException();
		String name = file.getName();
		int index = name.lastIndexOf(".");
		if(index != -1){
			String ending = name.substring(index); 			
			
			try {
				typename = this.getTypeBindingRegistry().get(ending);
				type = this.getTypeRegistry().get(typename);
			}
			catch (UnknownIDException e) {
				type = new BaseType();
			}			
		}
		else {
			type = new BaseType();
		}
		return new DotplotFile(file, type);
	}

	/**
	 * Initializes all <code>Services</code> registeres to the <code>DotplotContext</code>.
	 */
	public void inizializeServices(){
		for(IService<?,?> service : this.getServiceRegistry().getAll().values()){
			service.init();
		}
	}
	
	/**
	 * Returns the sourceList.
	 * @return - the sourceList.
	 */
	public ISourceList getSourceList() {
		return sourceList;
	}

	/**
	 * Sets the sourceList.
	 * @param sourceList The new sourceList.
	 */
	public void setSourceList(ISourceList sourceList) {
		if(sourceList == null){
			this.sourceList = new DefaultSourceList();
		}
		else {
			this.sourceList = sourceList;
		}
	}
	
	/**
	 * Sets the current <code>TypeTable</code>.
	 * @param table The new <code>TypeTable</code>.
	 */
	public void setCurrentTypeTable(ITypeTable table){
		if(typeTable == null){
			this.typeTable = new TypeTable(new TokenTable());
		}
		else {
			this.typeTable = table;
		}
	}
	
	/**
	 * Returns the current <code>TypeTable</code>.
	 * @return The <code>TypeTable</code>.
	 */
	public ITypeTable getCurrentTypeTable(){
		return this.typeTable;
	}
	
	/**
	 * Returnes the assigned <code>GuiService</code>.
	 * <p>
	 * The <code>GuiService</code> must be registered to the
	 * <code>ServiceRegistry</code> and it's service id must be
	 * known by the <code>DotplotContext</code>. 
	 * </p>
	 * @return the <code>GuiService</code>.
	 * @throws UnknownIDException if the guiservice is not registered to the <code>ServiceRegistry</code>.
	 * @see #setGuiServiceID(String)
	 * @see #getGuiServiceID()
	 */
	public IGuiService getGuiService() throws UnknownIDException{		
		IService service = this.getServiceRegistry().get(this.getGuiServiceID());
		if(service instanceof IGuiService){
			return (IGuiService)service;
		}
		else {
			throw new UnknownIDException(this.getGuiServiceID());
		}
	}

	/**
	 * Returns the guiServiceID.
	 * @return - the guiServiceID.
	 */
	public String getGuiServiceID() {
		return guiServiceID;
	}

	/**
	 * Sets the guiServiceID.
	 * @param guiServiceID The guiServiceID to set.
	 */
	public void setGuiServiceID(String guiServiceID) {
		if(guiServiceID == null) throw new NullPointerException();
		this.guiServiceID = guiServiceID;
	}
	
	/**
	 * Returns the currentDotplot.
	 * @return - the currentDotplot.
	 */
	public IDotplot getCurrentDotplot() {
		return currentDotplot;
	}

	/**
	 * Sets the currentDotplot.
	 * @param currentDotplot The currentDotplot to set.
	 */
	public void setCurrentDotplot(IDotplot dotplot) {
		this.currentDotplot = dotplot;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.dotplot.core.plugins.PluginContext#executeJob(org.dotplot.core.services.IJob)
	 */
	public boolean executeJob(IJob job){
		if(job == null) throw new NullPointerException();
		job.setTaskProcessor(this.getTaskProcessor());
		IGuiService gui = null;
		try {
			gui = this.getGuiService();
		}
		catch (UnknownIDException e1) {
			//null problemo
		}
		
		boolean result = false;
		
		if((job instanceof IJobWithProgress) && gui != null){
			IJobWithProgress jwp = (IJobWithProgress) job;
			jwp.setContext(this);
			result = gui.executeJobWithProgress(jwp);
		}
		else {
			result = job.process(this);
		}
		
		if(!result){
			if((! this.isNoGui()) && gui != null){
				gui.showErrorMessage("Job Error",job.getErrorHandler().toString());
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns the noGui.
	 * @return - the noGui.
	 */
	public boolean isNoGui() {
		return noGui;
	}

	/**
	 * Sets the noGui.
	 * @param noGui The noGui to set.
	 */
	public void setNoGui(boolean noGui) {
		this.noGui = noGui;
	}

	/**
	 * Returns the taskProcessor.
	 * @return - the taskProcessor.
	 */
	public ITaskProcessor getTaskProcessor() {
		return taskProcessor;
	}

	/**
	 * Sets the taskProcessor.
	 * <p>
	 * If <code>taskProcessor</code> is <code>null</code> an new
	 * <code>TaskProcessor</code> - object will be assigned.
	 * </p>
	 * @param taskProcessor The taskProcessor to set.
	 * @see TaskProcessor
	 */
	public void setTaskProcessor(ITaskProcessor taskProcessor) {
		if(taskProcessor == null){
			this.taskProcessor = new TaskProcessor();
		}
		else {
			this.taskProcessor = taskProcessor;
		}		
	}

}


