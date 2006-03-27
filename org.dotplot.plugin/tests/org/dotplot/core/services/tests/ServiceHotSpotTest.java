/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;

import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IServiceExtentionActivator;
import org.dotplot.core.services.ServiceHotSpot;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class ServiceHotSpotTest extends TestCase {

	/*
	 * Test method for 'org.dotplot.services.ServiceHotSpot.ServiceHotSpot(String, Class)'
	 */
	public void testServiceHotSpot() {
		
		try{
			new ServiceHotSpot(null, String.class);
			fail("Exception must be thrown");
		}
		catch(NullPointerException e) {
			
		}
		catch(Exception e){
			fail("wrong exception");			
		}
		
		try{
			new ServiceHotSpot("test", null);
			fail("Exception must be thrown");
		}
		catch(NullPointerException e) {
			/*all clear*/
		}
		catch(Exception e){
			fail("wrong exception");			
		}

		try{
			new ServiceHotSpot(null, null);
			fail("Exception must be thrown");
		}
		catch(NullPointerException e) {
			/*all clear*/
		}
		catch(Exception e){
			fail("wrong exception");
		}

		try{
			ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", String.class);
			assertEquals("org.dotplot.services.testspot", spot.getID());
			assertSame(String.class, spot.getExtentionClass());
		}
		catch(Exception e){
			fail("no exception");
		}

	}

	/*
	 * Test method for 'org.dotplot.services.ServiceHotSpot.isValidExtention(Class)'
	 */
	public void testIsValidExtentionClass() {
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", Number.class);
		try{
			spot.isValidExtention((Class)null);
			fail("exception must be thrown");
		}
		catch(NullPointerException e){
			/*all clear*/
		}
		catch(Exception e){
			fail("wrong exception");
		}
		
		
		try{
			assertTrue(spot.isValidExtention(Number.class));
			assertTrue("test inheritance",spot.isValidExtention(Byte.class));			
			assertFalse(spot.isValidExtention(Object.class));
		}
		catch(Exception e){
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.ServiceHotSpot.isValidExtention(Extention)'
	 */
	public void testIsValidExtentionExtention() {
		IServiceExtentionActivator plugin = new IServiceExtentionActivator(){

			public String getID() {
				return "org.dotplot.services.testes.testplugin";
			}

			public boolean isActivated() {
				return true;
			}};

		Throwable t = new Throwable();
		Exception x = new Exception();
		
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", Throwable.class);
		try{
			spot.isValidExtention((Extention)null);
			fail("exception must be thrown");
		}
		catch(NullPointerException e){
			/*all clear*/
		}
		catch(Exception e){
			fail("wrong exception");
		}
		
		
		try{
			assertTrue(spot.isValidExtention(new Extention(plugin,t)));
			assertTrue("test inheritance",spot.isValidExtention(new Extention(plugin,x)));			
			assertFalse(spot.isValidExtention(new Extention(plugin, new Object())));
		}
		catch(Exception e){
			fail("no exception");
		}
	}
	
	public void testAddGetAllExtentions(){
		IServiceExtentionActivator plugin = new IServiceExtentionActivator(){

			public String getID() {
				return "org.dotplot.services.testes.testplugin";
			}

			public boolean isActivated() {
				return true;
			}};
		
		String s1 = "test1";
		String s2 = "test2";
		String s3 = "test3";
		
		Extention e1 = new Extention(plugin, s1);
		Extention e2 = new Extention(plugin, s2);
		Extention e3 = new Extention(plugin, s3);
		
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", String.class);
		
		try {
			spot.addExtention(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("no exception");
		}
		
		try {
			spot.addExtention(new Extention(plugin, new Exception()));
			fail("exception must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("no exception");
		}
		
		Collection c = spot.getAllExtentions();
		assertNotNull(c);
		assertTrue(c.isEmpty());
		
		spot.addExtention(e1);
		spot.addExtention(e2);
		
		assertFalse(c.isEmpty());
		assertEquals(2, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));

		spot.addExtention(e3);
		
		assertFalse(c.isEmpty());
		assertEquals(3, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));
		assertTrue(c.contains(e3));

		spot.addExtention(e3);
		
		assertFalse(c.isEmpty());
		assertEquals(4, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));
		assertTrue(c.contains(e3));
	}

	public void testRemoveExtentions(){
		IServiceExtentionActivator plugin = new IServiceExtentionActivator(){

			public String getID() {
				return "org.dotplot.services.testes.testplugin";
			}

			public boolean isActivated() {
				return true;
			}};

		
		String s1 = "test1";
		String s2 = "test2";
		String s3 = "test3";
		
		Extention e1 = new Extention(plugin, s1);
		Extention e2 = new Extention(plugin, s2);
		Extention e3 = new Extention(plugin, s3);
		
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", String.class);
		
		try {
			spot.removeExtention(null);
			/*no failure here*/			
		}
		catch (Exception e) {
			fail("no exception");
		}
		
		try {
			spot.removeExtention(new Extention(plugin, new Exception()));
		}
		catch (Exception e) {
			/*no failure here*/
		}
		
		Collection c = spot.getAllExtentions();
		assertNotNull(c);
		assertTrue(c.isEmpty());
		
		spot.addExtention(e1);
		spot.addExtention(e2);
		
		assertFalse(c.isEmpty());
		assertEquals(2, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));

		spot.addExtention(e3);
		
		assertFalse(c.isEmpty());
		assertEquals(3, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));
		assertTrue(c.contains(e3));

		spot.addExtention(e3);
		
		assertFalse(c.isEmpty());
		assertEquals(4, c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));
		assertTrue(c.contains(e3));
		
		spot.removeExtention(e1);

		assertFalse(c.isEmpty());
		assertEquals(3, c.size());
		assertFalse(c.contains(e1));
		assertTrue(c.contains(e2));
		assertTrue(c.contains(e3));

		spot.removeExtention(e2);

		assertFalse(c.isEmpty());
		assertEquals(2, c.size());
		assertFalse(c.contains(e1));
		assertFalse(c.contains(e2));
		assertTrue(c.contains(e3));

	}
	
	public void testGetActivatedExtentions(){
		IServiceExtentionActivator plugin1 = new IServiceExtentionActivator(){

			public String getID() {
				return "org.dotplot.services.testes.testplugin";
			}

			public boolean isActivated() {
				return true;
			}};

		IServiceExtentionActivator plugin2 = new IServiceExtentionActivator(){

				public String getID() {
					return "org.dotplot.services.testes.testplugin";
				}

				public boolean isActivated() {
					return false;
				}};

		String s1 = "test1";
		String s2 = "test2";
		String s3 = "test3";
		
		Extention e1 = new Extention(plugin1, s1);
		Extention e2 = new Extention(plugin1, s2);
		Extention e3 = new Extention(plugin2, s3);
		
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", String.class);
		
		spot.addExtention(e1);
		spot.addExtention(e2);
		spot.addExtention(e3);
		spot.addExtention(e3);
		
		Collection c = spot.getActiveExtentions();
		assertNotNull(c);
		assertFalse(c.isEmpty());
		assertEquals(2,c.size());
		assertTrue(c.contains(e1));
		assertTrue(c.contains(e2));
		assertFalse(c.contains(e3));
		
	}
	
	public void testgetObjectsFromActivatedExtentions(){
		IServiceExtentionActivator plugin1 = new IServiceExtentionActivator(){

			public String getID() {
				return "org.dotplot.services.testes.testplugin";
			}

			public boolean isActivated() {
				return true;
			}};

		IServiceExtentionActivator plugin2 = new IServiceExtentionActivator(){

				public String getID() {
					return "org.dotplot.services.testes.testplugin";
				}

				public boolean isActivated() {
					return false;
				}};

		String s1 = "test1";
		String s2 = "test2";
		String s3 = "test3";
		
		Extention e1 = new Extention(plugin1, s1);
		Extention e2 = new Extention(plugin1, s2);
		Extention e3 = new Extention(plugin2, s3);
		
		ServiceHotSpot spot = new ServiceHotSpot("org.dotplot.services.testspot", String.class);
		
		spot.addExtention(e1);
		spot.addExtention(e2);
		spot.addExtention(e3);
		spot.addExtention(e3);
		
		Collection c = spot.getObjectsFromActivatedExtentions();
		assertNotNull(c);
		assertFalse(c.isEmpty());
		assertEquals(2,c.size());
		assertTrue(c.contains(s1));
		assertTrue(c.contains(s2));
		assertFalse(c.contains(s3));
	}
}
