/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.InitializerService;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.plugins.PluginIntegrationService;
import org.dotplot.core.plugins.PluginLoadingService;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.system.PluginLoadingJob;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class PluginLoadingJobTest extends TestCase {

    private class TestService extends
	    AbstractService<PluginContext, PluginHotSpot> {

	private boolean init;

	/**
	 * @param id
	 */
	public TestService(String id) {
	    super(id);
	    this.addHotSpot(new PluginHotSpot("object1", Object.class));
	    this.addHotSpot(new PluginHotSpot("object2", Object.class));
	    this.init = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	@Override
	public ITask createTask() {
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#getResultContext()
	 */
	@Override
	public IContext getResultContext() {
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	@Override
	public Class getResultContextClass() {
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#init()
	 */
	@Override
	public void init() {
	    this.init = true;
	}

	public boolean isInitialized() {
	    return this.init;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.AbstractService#workingContextIsCompatible
	 * (java.lang.Class)
	 */
	@Override
	public boolean workingContextIsCompatible(Class contextClass) {
	    return false;
	}
    }

    PluginLoadingJob job;

    IPluginContext<Plugin> context;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.job = new PluginLoadingJob("org.dotplot.core.PluginLoader",
		"org.dotplot.core.PluginIntegrator",
		"org.dotplot.core.PluginInitializer");
	this.context = new PluginContext<Plugin>(".", "./testfiles/core/");
	this.context.getServiceRegistry().register(
		"org.dotplot.core.PluginLoader",
		new PluginLoadingService("org.dotplot.core.PluginLoader"));
	this.context.getServiceRegistry().register(
		"org.dotplot.core.PluginIntegrator",
		new PluginIntegrationService(
			"org.dotplot.core.PluginIntegrator"));
	this.context.getServiceRegistry().register(
		"org.dotplot.core.PluginInitializer",
		new InitializerService("org.dotplot.core.PluginInitializer"));
	this.context.getServiceRegistry().register(
		"org.dotplot.core.pluginloader",
		new TestService("org.dotplot.core.pluginloader"));
	this.context.getJobRegistry().register("PluginLoader", this.job);
	this.context.getPluginRegistry().register("org.dotplot.testplugin",
		new Plugin("org.dotplot.testplugin", "1.3"));
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingJob.process(IServiceManager,
     * IFrameworkContext)'
     */
    public void testProcess() {
	try {
	    assertFalse(((TestService) this.context.getServiceRegistry().get(
		    "org.dotplot.core.pluginloader")).isInitialized());
	    this.context.getJobRegistry().get("PluginLoader").process(
		    this.context);
	    assertEquals(2, this.context.getPluginRegistry().getAll().size());
	    assertTrue(this.context.getPluginRegistry().getAll().containsKey(
		    "org.dotplot.testplugin"));
	    assertTrue(this.context.getServiceRegistry().getAll().containsKey(
		    "org.dotplot.core.pluginloader2"));
	    assertTrue(((TestService) this.context.getServiceRegistry().get(
		    "org.dotplot.core.pluginloader")).isInitialized());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingJob.validatePreconditions(IServiceManager,
     * IFrameworkContext)'
     */
    public void testValidatePreconditions() {
	assertTrue(this.job.validatePreconditions(this.context
		.getServiceRegistry()));
    }

}
