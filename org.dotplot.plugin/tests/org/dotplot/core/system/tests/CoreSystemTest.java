/**
 * 
 */
package org.dotplot.core.system.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.InitializerService;
import org.dotplot.core.plugins.PluginIntegrationService;
import org.dotplot.core.plugins.PluginLoadingService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.system.CoreSystem;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class CoreSystemTest extends TestCase {

    private CoreSystem system;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.system = new CoreSystem();
    }

    /*
     * Test method for 'org.dotplot.core.CoreSystem.CoreSystem()'
     */
    public void testCoreSystem() {

	IServiceRegistry services = this.system.getServiceRegistry();
	assertNotNull(services);
	assertEquals(3, services.getAll().size());
	assertTrue(services.getAll().containsKey(CoreSystem.SERVICE_LOADER_ID));
	assertTrue(services.getAll().containsKey(
		CoreSystem.SERVICE_INTEGRATOR_ID));
	assertTrue(services.getAll().containsKey(
		CoreSystem.SERVICE_INITIALIZER_ID));

	try {
	    assertTrue(services.get(CoreSystem.SERVICE_INITIALIZER_ID) instanceof InitializerService);
	    assertTrue(services.get(CoreSystem.SERVICE_INTEGRATOR_ID) instanceof PluginIntegrationService);
	    assertTrue(services.get(CoreSystem.SERVICE_LOADER_ID) instanceof PluginLoadingService);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

	IJobRegistry jobs = this.system.getJobRegistry();
	assertNotNull(jobs);
	assertEquals(3, jobs.getAll().size());
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_PLUGIN_LOADER_ID));
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_SHUTDOWN_ID));
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_STARTUP_ID));
    }

    /*
     * Test method for 'org.dotplot.core.CoreSystem.isActivated()'
     */
    public void testIsActivated() {
	assertTrue(this.system.isActivated());
	this.system.setActivated(false);
	assertTrue(this.system.isActivated());
	this.system.setActivated(true);
	assertTrue(this.system.isActivated());
    }

}
