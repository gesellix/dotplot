/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.IPlugin;
import org.dotplot.core.plugins.MalformedVersionException;
import org.dotplot.core.plugins.PluginRegistry;
import org.dotplot.core.plugins.Version;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class PluginRegistryTest extends TestCase {

	private class TestPlugin implements IPlugin {

		private String id;
		private Version version;
		
		public TestPlugin(String id, String version){
			this.id = id;
			try {
				this.version = new Version(version);
			}
			catch (MalformedVersionException e) {
				/*sollte nicht vorkommen, also vorsicht!*/
			}
		}
		
		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getVersion()
		 */
		public Version getVersion() {
			return this.version;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.services.IServiceExtentiontActivator#getID()
		 */
		public String getID() {
			return this.id;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.services.IServiceExtentiontActivator#isActivated()
		 */
		public boolean isActivated() {
			return false;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#storeExtention(java.lang.String, org.dotplot.core.services.Extention)
		 */
		public void storeExtention(String serviceID,String hotSotID, Extention extention) {}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getExtentions()
		 */
		public Map<String, Map<String, Collection<Extention>>> getExtentions() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getName()
		 */
		public String getName() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getProvider()
		 */
		public String getProvider() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getInfo()
		 */
		public String getInfo() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#setActivated(boolean)
		 */
		public void setActivated(boolean status) {}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getJobRegistry()
		 */
		public IJobRegistry getJobRegistry() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.plugins.IPlugin#getServiceRegistry()
		 */
		public IServiceRegistry getServiceRegistry() {
			return null;
		}
		
	}

	
	private PluginRegistry<TestPlugin> registry;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.registry = new PluginRegistry<TestPlugin>();
	}

	/*
	 * Test method for 'org.dotplot.core.plugins.PluginRegistry.PluginRegistry()'
	 */
	public void testPluginRegistry() {
		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());
	}

	/*
	 * Test method for 'org.dotplot.core.plugins.PluginRegistry.registerPlugin(P)'
	 */
	public void testRegisterPlugin() {		
		TestPlugin plugin1 = new TestPlugin("test1","1");
		TestPlugin plugin2 = new TestPlugin("test2","1");
		TestPlugin plugin3 = new TestPlugin("test2","2");

		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());
		
		try {
			this.registry.register(null, null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
						
		try {
			assertNull(this.registry.register("test1",plugin1));
			assertNull(this.registry.register("test2",plugin2));
			assertEquals(2, this.registry.getAll().size());
			assertSame(plugin1, this.registry.get("test1"));
			assertSame(plugin2, this.registry.get("test2"));
			assertSame(plugin2, this.registry.register("test3",plugin3));
			assertSame(plugin3, this.registry.get("test2"));
			assertSame(plugin2, this.registry.register("test2",plugin2));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
		try {
			this.registry.register("test1",plugin1);
			fail("DuplicateRegistrationException must be thrown");
		}
		catch (DuplicateRegistrationException e) {
			assertEquals("test1", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}			

	}


	/*
	 * Test method for 'org.dotplot.core.plugins.PluginRegistry.unregisterPlugin(String)'
	 */
	public void testUnregisterPlugin() {
		TestPlugin plugin1 = new TestPlugin("test1","1");
		TestPlugin plugin2 = new TestPlugin("test2","1");
		
		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());
		
		try {
			this.registry.unregister(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
				
		try {
			assertNull(this.registry.unregister("test1"));
			assertNull(this.registry.register("test1",plugin1));
			assertNull(this.registry.register("test2",plugin2));
			assertEquals(2, this.registry.getAll().size());
			assertTrue(this.registry.getAll().containsKey("test1"));
			assertTrue(this.registry.getAll().containsKey("test2"));
			
			assertSame(plugin2, this.registry.unregister("test2"));
			assertEquals(1, this.registry.getAll().size());
			assertTrue(this.registry.getAll().containsKey("test1"));
			assertFalse(this.registry.getAll().containsKey("test2"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotContext.getPlugin(String)'
	 */
	public void testGet() {
		try {
			this.registry.get(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.registry.get("test");
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			assertEquals("test", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			TestPlugin plugin = new TestPlugin("test","1");
			this.registry.register("test",plugin);
			assertSame(plugin, this.registry.get("test"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}


	/*
	 * Test method for 'org.dotplot.core.plugins.PluginRegistry.getPlugins()'
	 */
	public void testGetPlugins(){
	}

}
