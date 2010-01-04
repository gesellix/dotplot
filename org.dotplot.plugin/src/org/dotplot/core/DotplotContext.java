/**
 * 
 */
package org.dotplot.core;

import java.io.File;
import java.io.IOException;

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
 * For usage in the dotplot system the <code>DotplotContext</code> is created by
 * the <code>ContextFactory</code>. Use <code>ContextFactory.getContext()</code>
 * insted of creating a <code>DotplotContext</code> directly.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see ContextFactory
 */
public final class DotplotContext extends PluginContext<Plugin> {

    /**
     * Logger for debuginformation.
     */
    private static final Logger LOGGER = Logger.getLogger(DotplotContext.class
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
     * 
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
     * Indicates the no gui-responce should be used in case of an error.
     */
    private boolean noGui;

    /**
     * Creates a new <code>DotplotContext</code>.
     * 
     * @param workingDirectory
     *            - the working directroy of the system
     */
    public DotplotContext(String workingDirectory) {
	super(workingDirectory);
	LOGGER.debug("workingdirectory: " + workingDirectory);
	this.init();
    }

    /**
     * Creates a new <code>DotplotContext</code>.
     * 
     * @param workingDirectory
     *            - the working directroy of the system
     * @param pluginDirectory
     *            - the plugin dirextory of the system
     */
    public DotplotContext(String workingDirectory, String pluginDirectory) {
	super(workingDirectory, pluginDirectory);
	LOGGER.debug("workingdirectory: " + workingDirectory);
	LOGGER.debug("plugindirectory: " + pluginDirectory);
	this.init();
    }

    /**
     * Creates a <code>DotplotFile</code> out of a <code>File</code>.
     * <p>
     * The <code>DotplotFile</code> is created by using the
     * <code>DotplotContext</code>'s <code>TypeRegistry</code> and
     * <code>TypeBindingRegistry</code>. If no appropiate
     * <code>SourceType</code> could be found the <code>BaseType</code> will be
     * assigned.
     * </p>
     * 
     * @param file
     *            - the base file.
     * @return The new <code>DotplotFile</code>
     * @see BaseType
     */
    public DotplotFile createDotplotFile(File file) {
	ISourceType type;
	String typename;
	File theFile = null;

	try {
	    theFile = file.getCanonicalFile();
	} catch (IOException e1) {
	    throw new IllegalArgumentException();
	}

	if (theFile == null) {
	    throw new NullPointerException();
	}
	if (!theFile.isFile()) {
	    throw new IllegalArgumentException();
	}
	String name = theFile.getName();
	int index = name.lastIndexOf(".");
	if (index != -1) {
	    String ending = name.substring(index);

	    try {
		typename = this.getTypeBindingRegistry().get(ending);
		type = this.getTypeRegistry().get(typename);
	    } catch (UnknownIDException e) {
		type = new BaseType();
	    }
	} else {
	    type = new BaseType();
	}
	return new DotplotFile(theFile, type);
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public boolean executeJob(final IJob job) {
	if (job == null) {
	    throw new NullPointerException();
	}
	job.setTaskProcessor(this.getTaskProcessor());
	if (!job.process(this)) {
	    try {
		IGuiService gui = this.getGuiService();
		if (!this.isNoGui()) {
		    gui.showErrorMessage("Job Error", job.getErrorHandler()
			    .toString());
		}
		return false;
	    } catch (UnknownIDException e) {
		LOGGER.error("EWrror during executing a job.", e);
		// dann eben nicht
	    }
	}
	return true;
    }

    /**
     * Returns the <code>ConfigurationRegistry</code>.
     * 
     * @return The <code>ConfigurationRegistry</code>.
     */
    public IConfigurationRegistry getConfigurationRegistry() {
	return this.configurationRegistry;
    }

    /**
     * Returns the currentDotplot.
     * 
     * @return - the currentDotplot.
     */
    public IDotplot getCurrentDotplot() {
	return currentDotplot;
    }

    /**
     * Returns the current <code>TypeTable</code>.
     * 
     * @return The <code>TypeTable</code>.
     */
    public ITypeTable getCurrentTypeTable() {
	return this.typeTable;
    }

    /**
     * Returnes the assigned <code>GuiService</code>.
     * <p>
     * The <code>GuiService</code> must be registered to the
     * <code>ServiceRegistry</code> and it's service id must be known by the
     * <code>DotplotContext</code>.
     * </p>
     * 
     * @return the <code>GuiService</code>.
     * @throws UnknownIDException
     *             if the guiservice is not registered to the
     *             <code>ServiceRegistry</code>.
     * @see #setGuiServiceID(String)
     * @see #getGuiServiceID()
     */
    public IGuiService getGuiService() throws UnknownIDException {
	IService service = this.getServiceRegistry()
		.get(this.getGuiServiceID());
	if (service instanceof IGuiService) {
	    return (IGuiService) service;
	} else {
	    throw new UnknownIDException(this.getGuiServiceID());
	}
    }

    /**
     * Returns the guiServiceID.
     * 
     * @return - the guiServiceID.
     */
    public String getGuiServiceID() {
	return guiServiceID;
    }

    /**
     * Returns the sourceList.
     * 
     * @return - the sourceList.
     */
    public ISourceList getSourceList() {
	return sourceList;
    }

    /**
     * Returns the taskProcessor.
     * 
     * @return - the taskProcessor.
     */
    public ITaskProcessor getTaskProcessor() {
	return taskProcessor;
    }

    /**
     * Returns the <code>TypeBindingRegistry</code>.
     * 
     * @return The <code>TypeBindingRegistry</code>.
     */
    public ITypeBindingRegistry getTypeBindingRegistry() {
	return this.typeBindigRegistry;
    }

    /**
     * Returns the <code>TypeRegistry</code>.
     * 
     * @return The <code>TypeRegistry</code>.
     */
    public ITypeRegistry getTypeRegistry() {
	return this.typeRegistry;
    }

    /**
     * Inititializes the <code>DotplotContext</code>.
     */
    private void init() {
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
     * Initializes all <code>Services</code> registeres to the
     * <code>DotplotContext</code>.
     */
    public void inizializeServices() {
	for (IService<?, ?> service : this.getServiceRegistry().getAll()
		.values()) {
	    service.init();
	}
    }

    /**
     * Returns the noGui.
     * 
     * @return - the noGui.
     */
    public boolean isNoGui() {
	return this.noGui;
    }

    /**
     * Sets the currentDotplot.
     * 
     * @param currentDotplot
     *            The currentDotplot to set.
     */
    public void setCurrentDotplot(IDotplot dotplot) {
	this.currentDotplot = dotplot;
    }

    /**
     * Sets the current <code>TypeTable</code>.
     * 
     * @param table
     *            The new <code>TypeTable</code>.
     */
    public void setCurrentTypeTable(final ITypeTable table) {
	if (typeTable == null) {
	    this.typeTable = new TypeTable(new TokenTable());
	} else {
	    this.typeTable = table;
	}
    }

    /**
     * Sets the guiServiceID.
     * 
     * @param value
     *            The guiServiceID to set.
     */
    public void setGuiServiceID(final String value) {
	if (value == null) {
	    throw new IllegalArgumentException("value is null!");
	}
	this.guiServiceID = value;
    }

    /**
     * Sets the noGui.
     * 
     * @param value
     *            The noGui to set.
     */
    public void setNoGui(boolean value) {
	this.noGui = value;
    }

    /**
     * Sets the sourceList.
     * 
     * @param value
     *            The new sourceList.
     */
    public void setSourceList(final ISourceList value) {
	if (value == null) {
	    this.sourceList = new DefaultSourceList();
	} else {
	    this.sourceList = value;
	}
    }

    /**
     * Sets the taskProcessor.
     * <p>
     * If <code>taskProcessor</code> is <code>null</code> an new
     * <code>TaskProcessor</code> - object will be assigned.
     * </p>
     * 
     * @param taskProcessor
     *            The taskProcessor to set.
     * @see TaskProcessor
     */
    public void setTaskProcessor(final ITaskProcessor value) {
	if (value == null) {
	    this.taskProcessor = new TaskProcessor();
	} else {
	    this.taskProcessor = value;
	}
    }

}
