package org.dotplot.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.dotplot.core.TypeBindingRegistry;
import org.dotplot.core.TypeRegistry;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class TypeBindingRegistryTest {

	/**
	 * 
	 */
	private TypeBindingRegistry registry;

	/**
	 * 
	 */
	private TypeRegistry typeRegistry;

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.typeRegistry = new TypeRegistry();
		this.registry = new TypeBindingRegistry(this.typeRegistry);
	}

	/**
	 * Test method for
	 * 'org.dotplot.core.TypeBindingRegistry.combine(IRegistry<String>)'.
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 * 
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testCombineNULL() throws Exception {
		this.registry.combine(null);
	}

	/**
	 * Test method for 'org.dotplot.core.TypeBindingRegistry.get(String)'.
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test
	public void testGet() throws Exception {
		this.typeRegistry.register("type", TextType.type);
		this.registry.register("test", "type");
		assertEquals("type", this.registry.get("test"));
	}

	/**
	 * Test method for 'org.dotplot.core.TypeBindingRegistry.getAll()'.
	 */
	@Test
	public void testGetAll() {
		assertNotNull(this.registry.getAll());
		assertTrue(this.registry.getAll().isEmpty());
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = NullPointerException.class)
	public void testGetNULL() throws Exception {
		this.registry.get(null);
	}

	/**
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test
	public void testGetTypeOf() throws Exception {
		assertNull(this.registry.getTypeOf("test"));
		this.typeRegistry.register("type", TextType.type);
		this.registry.register("test", "type");
		assertSame(TextType.type, this.registry.getTypeOf("test"));
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testGetTypeOfNULL() {
		this.registry.getTypeOf(null);
	}

	/**
	 * Test method for 'org.dotplot.core.TypeBindingRegistry.getTypeRegistry()'.
	 */
	@Test
	public void testGetTypeRegistry() {
		assertSame(this.typeRegistry, this.registry.getTypeRegistry());
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = UnknownIDException.class)
	public void testGetUNKNOWNid() throws Exception {
		this.registry.get("test");
	}

	/**
	 * Test method for 'org.dotplot.core.TypeBindingRegistry.register(String,
	 * String)'.
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 * 
	 */
	@Test
	public void testRegister() throws Exception {
		this.typeRegistry.register("type", TextType.type);
		this.registry.register("test", "type");
		assertNotNull(this.registry.getAll());
		assertEquals(1, this.registry.getAll().size());
		assertTrue(this.registry.getAll().containsKey("test"));
		assertEquals("type", this.registry.getAll().get("test"));
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = DuplicateRegistrationException.class)
	public void testRegisterDublicateRegistration() throws Exception {
		this.typeRegistry.register("type", TextType.type);
		this.registry.register("test", "type");
		this.registry.register("test", "type");
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = NullPointerException.class)
	public void testRegisterKEYnull() throws Exception {
		this.registry.register("test", null);
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = NullPointerException.class)
	public void testRegisterVALUEnull() throws Exception {
		this.registry.register(null, "test");
	}

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterWRONGargument() throws Exception {
		this.registry.register("test", "type");
	}

	/**
	 * Test method for
	 * 'org.dotplot.core.TypeBindingRegistry.TypeBindingRegistry(ITypeRegistry)'
	 * .
	 */
	@Test
	public void testTypeBindingRegistry() {
		new TypeBindingRegistry(this.typeRegistry);
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testTypeBindingRegistryNULL() {
		new TypeBindingRegistry(null);
	}

	/**
	 * Test method for
	 * 'org.dotplot.core.TypeBindingRegistry.unregister(String)'.
	 * 
	 * @throws Exception
	 *             in case of an Exception
	 */
	@Test
	public void testUnregister() throws Exception {
		assertNull(this.registry.unregister("test"));
		this.typeRegistry.register("type", TextType.type);
		this.registry.register("test", "type");
		assertNotNull(this.registry.getAll());
		assertEquals(1, this.registry.getAll().size());
		assertTrue(this.registry.getAll().containsKey("test"));
		assertEquals("type", this.registry.unregister("test"));
		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());
		assertFalse(this.registry.getAll().containsKey("test"));
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testUnregisterNULL() {
		this.registry.unregister(null);
	}
}
