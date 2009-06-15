/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.IPlugin;
import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.IPluginListContext;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.plugins.PluginLoadingService;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class PluginLoadingServiceTest extends TestCase {

    private class TestService extends
	    AbstractService<PluginContext, PluginHotSpot> {

	/**
	 * @param id
	 */
	public TestService(String id) {
	    super(id);
	    this.addHotSpot(new PluginHotSpot("object1", Object.class));
	    this.addHotSpot(new PluginHotSpot("object2", Object.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IService#createTask()
	 */
	@Override
	public ITask createTask() {
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IService#getResultContext()
	 */
	@Override
	public IContext getResultContext() {
	    return new NullContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IService#getResultContextClass()
	 */
	@Override
	public Class getResultContextClass() {
	    return NullContext.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IService#init()
	 */
	@Override
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IService#workingContextIsCompatible(java.lang
	 * .Class)
	 */
	@Override
	public boolean workingContextIsCompatible(Class contextClass) {
	    return false;
	}

    }

    private PluginLoadingService service;

    private IPluginContext<IPlugin> context;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.service = new PluginLoadingService(
		"org.dotplot.core.test.pluginloader");
	this.context = new PluginContext<IPlugin>(".", "./testfiles/core/");
	this.context.getServiceRegistry().register(
		"org.dotplot.core.pluginloader",
		new TestService("org.dotplot.core.pluginloader"));
	this.context.getPluginRegistry().register("org.dotplot.testplugin",
		new Plugin("org.dotplot.testplugin", "1.3"));
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingService.createTask()'
     */
    public void testCreateTask() {
	this.service.setFrameworkContext(this.context);
	ITask task = this.service.createTask();
	assertNotNull(task);
	assertEquals("Pluginloadingtask", task.getID());
	assertFalse(task.isPartAble());
	assertFalse(task.isPartless());
	assertEquals(1, task.countParts());
	assertFalse(task.isDone());
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingService.getResultContext()'
     */
    public void testGetResultContext() {
	try {
	    assertNotNull(this.service.getResultContext());
	    assertSame(NullContext.class, this.service.getResultContext()
		    .getClass());
	    this.service.run();
	    assertNotNull(this.service.getResultContext());
	    assertSame(NullContext.class, this.service.getResultContext()
		    .getClass());
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.service.setFrameworkContext(this.context);
	    this.service.run();
	    assertTrue(this.service.getResultContext() instanceof IPluginListContext);
	    IPluginListContext result = (IPluginListContext) this.service
		    .getResultContext();
	    assertEquals(1, result.getPluginList().size());
	    for (IPlugin p : result.getPluginList()) {
		assertEquals("testplugin", p.getName());
		assertNotNull(p.getExtentions().get(
			"org.dotplot.core.pluginloader").get("object2"));
		for (Extention e : p.getExtentions().get(
			"org.dotplot.core.pluginloader").get("object2")) {
		    assertNotNull(e.getParameter("test"));
		    assertEquals("true", e.getParameter("test"));
		}
	    }
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingService.getResultContextClass()'
     */
    public void testGetResultContextClass() {
	assertSame(IPluginListContext.class, this.service
		.getResultContextClass());
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingService.PluginLoadingService(String)'
     */
    public void testPluginLoadingService() {
	assertNotNull(this.service.getResultContext());
	assertSame(NullContext.class, this.service.getResultContext()
		.getClass());
	assertNull(this.service.createTask());
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginLoadingService.workingContextIsCompatible(Class)'
     */
    public void testWorkingContextIsCompatible() {
	try {
	    assertTrue(this.service.workingContextIsCompatible(null));
	    assertTrue(this.service.workingContextIsCompatible(IContext.class));
	    assertTrue(this.service
		    .workingContextIsCompatible(PluginContext.class));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

}
