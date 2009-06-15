/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Collection;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.plugins.IPlugin;
import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.IPluginListContext;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginIntegrationService;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class PluginIntegrationServiceTest extends TestCase {

    private PluginIntegrationService service;

    private IPluginContext context;

    IPluginListContext workingContext = new IPluginListContext() {

	private Collection<Plugin> plugins;

	public Collection<? extends IPlugin> getPluginList() {
	    if (this.plugins == null) {
		this.plugins = new Vector<Plugin>();
		this.plugins.add(new Plugin("testplugin", "1.0"));
	    }
	    return plugins;
	}
    };

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.service = new PluginIntegrationService(
		"org.dotplot.core.pluginintegration");
	this.context = new PluginContext(".", "./testfiles/core/");
	this.service.setFrameworkContext(this.context);
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginIntegrationService.createTask()'
     */
    public void testCreateTask() {
	try {

	    this.service.setFrameworkContext(this.context);
	    this.service.setWorkingContext(this.workingContext);

	    ITask task = this.service.createTask();
	    assertNotNull(task);
	    assertEquals("Pluginintegrationtask", task.getID());
	    assertFalse(task.isPartAble());
	    assertFalse(task.isPartless());
	    assertEquals(1, task.countParts());
	    assertFalse(task.isDone());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginIntegrationService.getResultContext()'
     */
    public void testGetResultContext() {
	assertNotNull(this.service.getResultContext());
	this.service.run();
	assertNotNull(this.service.getResultContext());
	assertSame(NullContext.class, this.service.getResultContext()
		.getClass());
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginIntegrationService.getResultContextClass()'
     */
    public void testGetResultContextClass() {
	assertSame(NullContext.class, this.service.getResultContextClass());
    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginIntegrationService.PluginIntegrationService(String)'
     */
    public void testPluginIntegrationService() {
	assertEquals("org.dotplot.core.pluginintegration", this.service.getID());
    }

    public void testRun() {
	try {
	    assertFalse(this.context.getPluginRegistry().getAll().containsKey(
		    "testplugin"));
	    this.service.setFrameworkContext(this.context);
	    this.service.setWorkingContext(this.workingContext);
	    this.service.run();
	    assertTrue(this.context.getPluginRegistry().getAll().containsKey(
		    "testplugin"));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for
     * 'org.dotplot.core.plugins.PluginIntegrationService.workingContextIsCompatible(Class)'
     */
    public void testWorkingContextIsCompatible() {
	assertTrue(this.service
		.workingContextIsCompatible(IPluginListContext.class));
    }

}
