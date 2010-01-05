/**
 * 
 */
package org.dotplot.core.plugins;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ServiceRegistry;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * Default implementation of the <code>IPluinContext</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PluginContext<P extends IPlugin> implements IPluginContext<P> {

    /**
     * <code>Logger</code> for loggin messages.
     */
    private static final Logger logger = Logger.getLogger(PluginContext.class
	    .getName());

    /**
     * <code>Registry</code> for registering <code>Services</code>.
     */
    private IServiceRegistry services;

    /**
     * The workingdirectory.
     */
    private String workingDirectory;

    /**
     * The plugindirectory.
     */
    private String pluginDirectory;

    /**
     * The schema of the dotplotplugin.xml.
     */
    private Schema schema;

    /**
     * <code>Registry</code> for registering <code>Plugin</code>s.
     */
    private PluginRegistry<P> pluginRegistry;

    /**
     * <code>Registry</code> for registering <code>Jobs</code>.
     */
    private JobRegistry jobRegistry;

    /**
     * Creates a new <code>PluginContext</code>.
     * 
     * @param workingDirectory
     *            The workingdirectory.
     * @throws NullPointerException
     *             - if <code>workingDirectory</code> is <code>null</code>
     * @throws IllegalArgumentException
     *             - if <code>workingDirectory</code> does not represent an
     *             existing directory
     */
    public PluginContext(String workingDirectory) {
	if (workingDirectory == null) {
	    throw new NullPointerException();
	}
	this.init(workingDirectory, workingDirectory);
    }

    /**
     * Creates a new <code>PluginContext</code>.
     * 
     * @param workingDirectory
     *            The workingdirectory.
     * @param pluginDirectory
     *            The plugindirectory.
     * @throws NullPointerException
     *             - if <code>workingDirectory</code> is <code>null</code>
     * @throws NullPointerException
     *             - if <code>pluginDirectory</code> is <code>null</code>
     * @throws IllegalArgumentException
     *             - if <code>workingDirectory</code> does not represent an
     *             existing directory
     * @throws IllegalArgumentException
     *             - if <code>pluginDirectory</code> does not represent an
     *             existing directory
     */
    public PluginContext(String workingDirectory, String pluginDirectory) {
	if (workingDirectory == null || pluginDirectory == null) {
	    throw new NullPointerException();
	}
	this.init(workingDirectory, pluginDirectory);
    }

    /**
     * Executes a <code>Job</code>.
     * 
     * @param job
     *            The <code>Job</code> to execute.
     * @throws NullPointerException
     *             if <code>job</code> is <code>null</code>
     */
    public boolean executeJob(IJob job) {
	if (job == null) {
	    throw new NullPointerException();
	}
	return job.process(this);
    }

    /**
     * Executes a <code>Job</code>.
     * 
     * @param jobID
     *            The if of the <code>Job</code> to be executed.
     * @throws UnknownIDException
     *             if the jobID is unknown to the <code>JobRegistry</code>.
     * @throws NullPointerException
     *             if <code>jobID</code> is <code>null</code>
     */
    public boolean executeJob(String jobID) throws UnknownIDException {
	IJob<?> job;
	if (jobID == null) {
	    throw new NullPointerException();
	} else {
	    job = this.getJobRegistry().get(jobID);
	    return this.executeJob(job);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.plugins.IPluginContext#getJobRegistry()
     */
    public IJobRegistry getJobRegistry() {
	return this.jobRegistry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.plugins.IPluginContext#getPluginDirectory()
     */
    public String getPluginDirectory() {
	return this.pluginDirectory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.plugins.IPluginContext#getPluginRegistry()
     */
    public IPluginRegistry<P> getPluginRegistry() {
	return this.pluginRegistry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.plugins.IPluginContext#getPluginSchema()
     */
    public Schema getPluginSchema() {
	return this.schema;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IFrameworkContext#getServiceManager()
     */
    public IServiceRegistry getServiceRegistry() {
	return this.services;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IFrameworkContext#getWorkingDirectory()
     */
    public String getWorkingDirectory() {
	return this.workingDirectory;
    }

    /**
     * Initializes the <code>PluginContext</code>.
     * 
     * @param workingDirectory
     *            The workingdirectory.
     * @param pluginDirectory
     *            The plugindirectory.
     */
    private void init(String workingDirectory, String pluginDirectory) {
	try {
	    File file;
	    file = new File(workingDirectory).getCanonicalFile();

	    if (file.exists() && file.isDirectory()) {
		this.workingDirectory = file.getAbsolutePath();
	    } else {
		logger.fatal("illegal workingdirectory: " + workingDirectory);
		throw new IllegalArgumentException(workingDirectory);
	    }

	    file = new File(pluginDirectory).getCanonicalFile();

	    if (file.exists() && file.isDirectory()) {
		this.pluginDirectory = file.getAbsolutePath();
	    } else {
		logger.fatal("illegal plugindirectory: " + pluginDirectory);
		throw new IllegalArgumentException(pluginDirectory);
	    }
	} catch (IOException e) {
	    throw new IllegalArgumentException();
	}

	this.services = new ServiceRegistry();
	this.jobRegistry = new JobRegistry();
	this.pluginRegistry = new PluginRegistry<P>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.plugins.IPluginContext#installPlugin(P)
     */
    public void installPlugin(P plugin) throws DuplicateRegistrationException {
	if (plugin == null) {
	    throw new NullPointerException();
	}

	logger.debug("Installplugin: " + plugin.getID());

	this.pluginRegistry.register(plugin.getID(), plugin);
	this.services.combine(plugin.getServiceRegistry());
	this.jobRegistry.combine(plugin.getJobRegistry());
    }

    /**
     * Sets the <code>File</code> containing the schema to load plugins.
     * 
     * @param shemaFile
     *            - the <code>File</code>.
     * @throws NullPointerException
     *             if shemaFile is <code>null</code>.
     */
    public void setShemaFile(File shemaFile) {
	if (shemaFile == null) {
	    throw new NullPointerException();
	}
	try {
	    logger.debug("PluginShema: " + shemaFile.getAbsolutePath());
	    SchemaFactory factory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    this.schema = factory.newSchema(shemaFile);
	} catch (Exception e) {
	    logger.fatal("wrong shemafile: " + shemaFile.getAbsolutePath());
	    this.schema = null;
	}
    }
}
