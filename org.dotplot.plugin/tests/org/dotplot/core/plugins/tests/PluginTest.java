/**
 * 
 */
package org.dotplot.core.plugins.tests;

import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.services.Extention;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class PluginTest extends TestCase {
	
	private Plugin plugin;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.plugin = new Plugin("testid","testname","1" );
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.DotplotPlugin(String, String)'
	 */
	public void testDotplotPluginStringString() {
		try {
			new Plugin(null,"1");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Plugin("id",null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			Plugin plugin = new Plugin("id","1");
			assertEquals("id", plugin.getID());
			assertEquals("id", plugin.getName());
			assertEquals("Version 1", plugin.getVersion().toString());
			assertEquals("", plugin.getProvider());
			assertEquals("", plugin.getInfo());
			assertTrue(plugin.isActivated());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.DotplotPlugin(String, String, String)'
	 */
	public void testDotplotPluginStringStringString() {
		
		try {
			new Plugin(null,"name","1");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Plugin("id",null,"1");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Plugin("id","name",null);			
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			Plugin plugin = new Plugin("id","name","1");
			assertEquals("id", plugin.getID());
			assertEquals("name", plugin.getName());
			assertEquals("Version 1", plugin.getVersion().toString());
			assertEquals("", plugin.getProvider());
			assertEquals("", plugin.getInfo());
			assertTrue(plugin.isActivated());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
	
	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.DotplotPlugin(String, String, String, String, String)'
	 */
	public void testDotplotPluginStringStringStringStringString() {
		
		try {
			new Plugin(null,"name","1","provider","info");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Plugin("id",null,"1","provider","info");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Plugin("id","name",null,"provider","info");
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		Plugin plugin;
		
		try {
			plugin = new Plugin("id","name","1",null,"info");
			assertEquals("id", plugin.getID());
			assertEquals("name", plugin.getName());
			assertEquals("Version 1", plugin.getVersion().toString());
			assertEquals("", plugin.getProvider());
			assertEquals("info", plugin.getInfo());
			assertTrue(plugin.isActivated());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
		try {
			plugin = new Plugin("id","name","1","provider",null);
			assertEquals("id", plugin.getID());
			assertEquals("name", plugin.getName());
			assertEquals("Version 1", plugin.getVersion().toString());
			assertEquals("provider", plugin.getProvider());
			assertEquals("", plugin.getInfo());
			assertTrue(plugin.isActivated());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

		try {
			plugin = new Plugin("id","name","1","provider","info");
			assertEquals("id", plugin.getID());
			assertEquals("name", plugin.getName());
			assertEquals("Version 1", plugin.getVersion().toString());
			assertEquals("provider", plugin.getProvider());
			assertEquals("info", plugin.getInfo());
			assertTrue(plugin.isActivated());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.getName()'
	 */
	public void testGetName() {
		assertEquals("testname", this.plugin.getName());		
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.getID()'
	 */
	public void testGetID() {
		assertEquals("testid", this.plugin.getID());
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.isActivated()'
	 */
	public void testSetIsActivated() {
		this.plugin.setActivated(false);
		assertFalse(this.plugin.isActivated());
		this.plugin.setActivated(true);
		assertTrue(this.plugin.isActivated());
		this.plugin.setActivated(false);
		assertFalse(this.plugin.isActivated());
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotPlugin.getVersion()'
	 */
	public void testGetVersion() {
		assertEquals("Version 1", this.plugin.getVersion().toString());
	}
	
	public void testGetStoreExtention(){
		Extention extention = new Extention(this.plugin, "test"); 
		
		assertNotNull(this.plugin.getExtentions());
		assertEquals(0, this.plugin.getExtentions().size());
		
		try {
			this.plugin.storeExtention(null, "test", extention);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.plugin.storeExtention("test", null, extention);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.plugin.storeExtention("test", "test", null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.plugin.storeExtention("test", "test",extention);
			assertEquals(1, this.plugin.getExtentions().size());
			assertTrue(this.plugin.getExtentions().containsKey("test"));
			assertEquals(1, this.plugin.getExtentions().get("test").size());
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test"));
			assertEquals(1, this.plugin.getExtentions().get("test").get("test").size());
			
			this.plugin.storeExtention("test", "test",extention);
			assertEquals(1, this.plugin.getExtentions().size());
			assertTrue(this.plugin.getExtentions().containsKey("test"));
			assertEquals(1, this.plugin.getExtentions().get("test").size());
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test"));
			assertEquals(2, this.plugin.getExtentions().get("test").get("test").size());

			this.plugin.storeExtention("test","test2", extention);
			assertEquals(1, this.plugin.getExtentions().size());
			assertTrue(this.plugin.getExtentions().containsKey("test"));
			assertEquals(2, this.plugin.getExtentions().get("test").size());
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test"));
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test2"));
			assertEquals(2, this.plugin.getExtentions().get("test").get("test").size());
			assertEquals(1, this.plugin.getExtentions().get("test").get("test2").size());
			
			this.plugin.storeExtention("test2","test2", extention);
			assertEquals(2, this.plugin.getExtentions().size());
			assertTrue(this.plugin.getExtentions().containsKey("test"));
			assertTrue(this.plugin.getExtentions().containsKey("test2"));
			assertEquals(2, this.plugin.getExtentions().get("test").size());
			assertEquals(1, this.plugin.getExtentions().get("test2").size());
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test"));
			assertTrue(this.plugin.getExtentions().get("test").containsKey("test2"));
			assertEquals(2, this.plugin.getExtentions().get("test").get("test").size());
			assertEquals(1, this.plugin.getExtentions().get("test").get("test2").size());
			assertTrue(this.plugin.getExtentions().get("test2").containsKey("test2"));
			assertEquals(1, this.plugin.getExtentions().get("test2").get("test2").size());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

}
