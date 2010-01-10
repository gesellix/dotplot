/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.InitializerService;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class InitializerServiceTest extends TestCase {

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

	private InitializerService service;

	private IPluginContext context;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = new InitializerService("test");
		this.context = new PluginContext(".");
		this.context.getServiceRegistry().register("testservice",
				new TestService("testservice"));
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.InitializerService.createTask()'
	 */
	public void testCreateTask() {
		this.service.setFrameworkContext(this.context);
		ITask task = this.service.createTask();
		assertNotNull(task);
		assertFalse(task.isPartAble());
		assertFalse(task.isPartless());
		assertEquals(1, task.getParts().size());
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.InitializerService.getResultContext()'
	 */
	public void testGetResultContext() {
		try {
			assertNull(this.service.getResultContext());
			assertFalse(((TestService) this.context.getServiceRegistry().get(
					"testservice")).isInitialized());
			this.service.setFrameworkContext(this.context);
			this.service.run();
			assertNotNull(this.service.getResultContext());
			assertTrue(this.service.getResultContext() instanceof NullContext);
			assertTrue(((TestService) this.context.getServiceRegistry().get(
					"testservice")).isInitialized());

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.InitializerService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(NullContext.class, this.service.getResultContextClass());
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.InitializerService.workingContextIsCompatible(Class)'
	 */
	public void testWorkingContextIsCompatible() {
		assertTrue(service.workingContextIsCompatible(null));
		assertTrue(service.workingContextIsCompatible(NullContext.class));
		assertTrue(service.workingContextIsCompatible(PluginContext.class));
	}

}
