/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Collection;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.plugins.BatchJob;
import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.IPlugin;
import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.IPluginRegistry;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.plugins.PluginIntegrationTaskPart;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class PluginIntegrationTaskPartTest extends TestCase {

	private class TestService extends
			AbstractService<PluginContext, PluginHotSpot> {

		/**
		 * @param id
		 */
		public TestService(String id) {
			super(id);
			this.addHotSpot(new PluginHotSpot("spot1", Object.class));
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

	private PluginIntegrationTaskPart part;

	private IPluginContext<Plugin> context;

	private IPlugin plugin;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.context = new PluginContext<Plugin>(".", "./testfiles/core/");
		this.context.getServiceRegistry().register("testservice1",
				new TestService("testservice1"));
		this.plugin = new Plugin("testplugin", "1.0");

		this.plugin.getJobRegistry().register("testjob", new BatchJob());
		this.plugin.getServiceRegistry().register("testservice2",
				new TestService("testservice2"));
		this.plugin.getServiceRegistry().register("testservice3",
				new TestService("testservice3"));
		this.plugin.storeExtention("testservice1", "spot1", new Extention(
				this.plugin, new Object()));
		this.plugin.storeExtention("testservice2", "spot1", new Extention(
				this.plugin, new Object()));

		Collection<IPlugin> plugins = new Vector<IPlugin>();
		plugins.add(this.plugin);

		this.part = new PluginIntegrationTaskPart("testpart", plugins,
				this.context);
	}

	public void testgetResult() {
		assertNull(this.part.getResult());
		this.part.run();
		assertNull(this.part.getResult());
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.PluginIntegrationTaskPart.PluginIntegrationTaskPart(String,
	 * IPlugin)'
	 */
	public void testPluginIntegrationTaskPart() {
		assertEquals("testpart", this.part.getID());
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.PluginIntegrationTaskPart.run()'
	 */
	public void testRun() {
		IService service;

		try {
			IPluginRegistry<Plugin> plugins = this.context.getPluginRegistry();
			IServiceRegistry services = this.context.getServiceRegistry();
			IJobRegistry jobs = this.context.getJobRegistry();

			assertTrue(plugins.getAll().isEmpty());
			assertFalse(services.getAll().isEmpty());
			assertEquals(1, services.getAll().size());

			assertTrue(services.getAll().containsKey("testservice1"));
			assertFalse(services.getAll().containsKey("testservice2"));
			assertFalse(services.getAll().containsKey("^testservice3"));

			service = services.get("testservice1");
			assertEquals(0, service.getExtentions("spot1").size());

			assertTrue(jobs.getAll().isEmpty());

			this.part.run();

			assertFalse(plugins.getAll().isEmpty());
			assertFalse(services.getAll().isEmpty());
			assertEquals(3, services.getAll().size());
			assertFalse(jobs.getAll().isEmpty());

			assertTrue(plugins.getAll().containsKey("testplugin"));
			assertTrue(services.getAll().containsKey("testservice1"));
			assertTrue(services.getAll().containsKey("testservice2"));
			assertTrue(services.getAll().containsKey("testservice3"));
			assertTrue(jobs.getAll().containsKey("testjob"));

			service = services.get("testservice1");
			assertEquals(1, service.getExtentions("spot1").size());

			service = services.get("testservice2");
			assertEquals(1, service.getExtentions("spot1").size());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.PluginIntegrationTaskPart.setLocalRessources(Collection<IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			Collection<IRessource> c = new Vector<IRessource>();
			this.part.setLocalRessources(c);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
