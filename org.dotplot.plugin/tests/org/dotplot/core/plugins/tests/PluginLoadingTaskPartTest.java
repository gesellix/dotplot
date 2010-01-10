/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.plugins.BatchJob;
import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.IPlugin;
import org.dotplot.core.plugins.IPluginContext;
import org.dotplot.core.plugins.MalformedVersionException;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.plugins.PluginLoadingService;
import org.dotplot.core.plugins.PluginLoadingTaskPart;
import org.dotplot.core.plugins.Version;
import org.dotplot.core.plugins.ressources.DirectoryRessource;
import org.dotplot.core.plugins.ressources.FileRessource;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.NullContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class PluginLoadingTaskPartTest extends TestCase {

	private class TestService extends
			AbstractService<PluginContext, PluginHotSpot> {

		/**
		 * @param id
		 *            -
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

	private PluginLoadingTaskPart part;

	private DirectoryRessource dir;

	private IPluginContext<IPlugin> context;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.dir = new DirectoryRessource("./testfiles/core/");
		this.context = new PluginContext<IPlugin>(".");
		this.part = new PluginLoadingTaskPart("pluginloader", this.dir,
				this.context);
		this.context.getServiceRegistry().register(
				"org.dotplot.core.pluginloader",
				new TestService("org.dotplot.core.pluginloader"));
		this.context.getJobRegistry().register("org.dotplot.test.testjob4",
				new BatchJob());
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.PluginLoadingTaskPart.PluginLoadingTaskPart(String,
	 * DirectoryRessource, DotplotContext)'
	 */
	public void testPluginLoadingTaskPart() {
		try {
			new PluginLoadingTaskPart(null, this.dir, this.context);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginLoadingTaskPart("pluginloader", null, this.context);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginLoadingTaskPart("pluginloader", this.dir, null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			PluginLoadingTaskPart part = new PluginLoadingTaskPart(
					"pluginloader", this.dir, this.context);
			assertEquals("pluginloader", part.getID());
			assertTrue(this.part.getRessources().contains(this.dir));
			assertEquals(1, this.part.getRessources().size());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.PluginLoadingTaskPart.run()'
	 */
	public void testRun() {
		try {
			assertNull(this.part.getResult());

			Vector<IRessource> v = new Vector<IRessource>();
			v.add(this.dir);
			this.part.setLocalRessources(v);
			this.part.run();

			assertNotNull(this.part.getResult());
			assertTrue(this.part.getResult() instanceof Map);
			assertEquals(0, ((Map) this.part.getResult()).size());

			this.context.getPluginRegistry().register("org.dotplot.testplugin",
					new IPlugin() {

						private Version v;

						public Map<String, Map<String, Collection<Extention>>> getExtentions() {
							return null;
						}

						public String getID() {
							return "org.dotplot.testplugin";
						}

						public String getInfo() {
							return null;
						}

						public IJobRegistry getJobRegistry() {
							return null;
						}

						public String getName() {
							return null;
						}

						public String getProvider() {
							return null;
						}

						public IServiceRegistry getServiceRegistry() {
							return null;
						}

						public Version getVersion() {
							if (v == null) {
								try {
									v = new Version("1.0");
								}
								catch (MalformedVersionException e) {
									e.printStackTrace();
								}
							}
							return v;
						}

						public boolean isActivated() {
							return true;
						}

						public void setActivated(boolean status) {
						}

						public void storeExtention(String serviceID,
								String hotSpotID, Extention extention) {
						}
					});

			assertEquals(1, this.context.getPluginRegistry().getAll().size());

			this.part.run();

			assertNotNull(this.part.getResult());
			assertTrue(this.part.getResult() instanceof Map);
			assertEquals(0, ((Map) this.part.getResult()).size());

			this.context.getPluginRegistry().register("org.dotplot.testplugin",
					new IPlugin() {

						private Version v;

						public Map<String, Map<String, Collection<Extention>>> getExtentions() {
							return null;
						}

						public String getID() {
							return "org.dotplot.testplugin";
						}

						public String getInfo() {
							return null;
						}

						public IJobRegistry getJobRegistry() {
							return null;
						}

						public String getName() {
							return null;
						}

						public String getProvider() {
							return null;
						}

						public IServiceRegistry getServiceRegistry() {
							return null;
						}

						public Version getVersion() {
							if (v == null) {
								try {
									v = new Version("1.3");
								}
								catch (MalformedVersionException e) {
									e.printStackTrace();
								}
							}
							return v;
						}

						public boolean isActivated() {
							return true;
						}

						public void setActivated(boolean status) {
						}

						public void storeExtention(String serviceID,
								String hotSpotID, Extention extention) {
						}
					});

			assertEquals(1, this.context.getPluginRegistry().getAll().size());

			this.part.run();

			assertNotNull(this.part.getResult());
			assertTrue(this.part.getResult() instanceof Map);
			assertEquals(1, ((Map) this.part.getResult()).size());

			Plugin plugin = (Plugin) ((Map) this.part.getResult())
					.get("org.dotplot.core.test");
			assertEquals("org.dotplot.core.test", plugin.getID());
			assertEquals("testplugin", plugin.getName());
			assertEquals("Version 1.0", plugin.getVersion().toString());
			assertEquals("Christian Gerhardt", plugin.getProvider());
			assertEquals("Einführender Test für die plugin.xml", plugin
					.getInfo());

			assertEquals(1, plugin.getServiceRegistry().getAll().size());
			IService service = plugin.getServiceRegistry().get(
					"org.dotplot.core.pluginloader2");
			assertNotNull(service);
			assertTrue(service instanceof PluginLoadingService);

			assertNotNull(plugin.getExtentions());
			assertEquals(1, plugin.getExtentions().size());
			assertTrue(plugin.getExtentions().containsKey(
					"org.dotplot.core.pluginloader"));
			assertEquals(2, plugin.getExtentions().get(
					"org.dotplot.core.pluginloader").size());
			assertTrue(plugin.getExtentions().get(
					"org.dotplot.core.pluginloader").containsKey("object1"));
			assertTrue(plugin.getExtentions().get(
					"org.dotplot.core.pluginloader").containsKey("object2"));
			assertEquals(2, plugin.getExtentions().get(
					"org.dotplot.core.pluginloader").get("object1").size());
			assertEquals(1, plugin.getExtentions().get(
					"org.dotplot.core.pluginloader").get("object2").size());

			Iterator<Extention> iter;
			iter = plugin.getExtentions().get("org.dotplot.core.pluginloader")
					.get("object1").iterator();
			assertTrue(iter.next().getExtentionObject() instanceof String);
			assertTrue(iter.next().getExtentionObject() instanceof String);
			assertFalse(iter.hasNext());

			iter = plugin.getExtentions().get("org.dotplot.core.pluginloader")
					.get("object2").iterator();
			assertTrue(iter.next().getExtentionObject() instanceof String);
			assertFalse(iter.hasNext());

			assertNotNull(plugin.getJobRegistry().getAll());
			assertEquals(1, plugin.getJobRegistry().getAll().size());
			assertTrue(plugin.getJobRegistry().getAll().containsKey(
					"org.dotplot.test.testbatchjob"));

			assertTrue(plugin.getJobRegistry().getAll().get(
					"org.dotplot.test.testbatchjob") instanceof BatchJob);

			BatchJob job;
			job = (BatchJob) plugin.getJobRegistry().getAll().get(
					"org.dotplot.test.testbatchjob");
			assertEquals(1, job.getServiceBatch().size());
			assertEquals("org.dotplot.core.pluginloader2", job
					.getServiceBatch().get(0));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.PluginLoadingTaskPart.setLocalRessources(Collection<IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			this.part.setLocalRessources(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		Vector<IRessource> v = new Vector<IRessource>();
		FileRessource file = new FileRessource("testfiles/ressources/test.txt");

		try {
			this.part.setLocalRessources(v);
			fail("InsufficientRessourcesException must be thrown");
		}
		catch (InsufficientRessourcesException e) {
			assertEquals("Plugindirectory needed.", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		v.add(file);

		try {
			this.part.setLocalRessources(v);
			fail("InsufficientRessourcesException must be thrown");
		}
		catch (InsufficientRessourcesException e) {
			assertEquals("Plugindirectory needed.", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			v.remove(file);
			v.add(this.dir);
			this.part.setLocalRessources(v);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
}
