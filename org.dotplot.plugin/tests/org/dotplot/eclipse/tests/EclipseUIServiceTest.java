/**
 * 
 */
package org.dotplot.eclipse.tests;

import junit.framework.TestCase;

import org.dotplot.core.ConfigurationRegistry;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.eclipse.EclipseUIService;
import org.dotplot.fmatrix.FMatrixContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class EclipseUIServiceTest extends TestCase {

    private EclipseUIService service;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.service = new EclipseUIService("test");
    }

    /*
     * Test method for 'org.dotplot.eclipse.EclipseUIService.createTask()'
     */
    public void testCreateTask() {
	ITask task = this.service.createTask();
	assertNotNull(task);
	assertTrue(task.isPartless());
	assertFalse(task.isPartAble());
	assertEquals(0, task.countParts());
    }

    /*
     * Test method for
     * 'org.dotplot.eclipse.EclipseUIService.EclipseUIService(String)'
     */
    public void testEclipseUIService() {
	assertEquals("test", this.service.getID());
    }

    /*
     * Test method for 'org.dotplot.eclipse.EclipseUIService.getResultContext()'
     */
    public void testGetResultContext() {
	assertEquals(NullContext.context, this.service.getResultContext());
	assertSame(NullContext.context, this.service.getResultContext());
	NullContext n = new NullContext();
	assertNotSame(n, this.service.getResultContext());
	try {
	    this.service.setWorkingContext(n);
	    assertSame(n, this.service.getWorkingContext());
	    assertEquals(n, this.service.getResultContext());
	    this.service.run();
	    assertSame(n, this.service.getResultContext());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for
     * 'org.dotplot.eclipse.EclipseUIService.getResultContextClass()'
     */
    public void testGetResultContextClass() {
	assertSame(IContext.class, this.service.getResultContextClass());
    }

    /*
     * Test method for
     * 'org.dotplot.eclipse.EclipseUIService.registerDefaultConfiguration(IConfigurationRegistry)'
     */
    public void testRegisterDefaultConfiguration() {
	ConfigurationRegistry registry = new ConfigurationRegistry();
	assertEquals(0, registry.getAll().size());
	this.service.registerDefaultConfiguration(registry);
	assertEquals(0, registry.getAll().size());
    }

    /*
     * Test method for
     * 'org.dotplot.eclipse.EclipseUIService.workingContextIsCompatible(Class)'
     */
    public void testWorkingContextIsCompatible() {
	assertTrue(this.service.workingContextIsCompatible(null));
	assertTrue(this.service.workingContextIsCompatible(NullContext.class));
	assertTrue(this.service
		.workingContextIsCompatible(FMatrixContext.class));
    }

}
