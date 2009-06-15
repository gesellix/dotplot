/**
 * 
 */
package org.dotplot.core.tests;

import junit.framework.TestCase;

import org.dotplot.core.TypeBindingRegistry;
import org.dotplot.core.TypeRegistry;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class TypeBindingRegistryTest extends TestCase {

    private TypeBindingRegistry registry;

    private TypeRegistry typeRegistry;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.typeRegistry = new TypeRegistry();
	this.registry = new TypeBindingRegistry(this.typeRegistry);
    }

    /*
     * Test method for
     * 'org.dotplot.core.TypeBindingRegistry.combine(IRegistry<String>)'
     */
    public void testCombine() {
	try {
	    this.registry.combine(null);
	    fail("UnsupportedOperationException must be thrown");
	} catch (UnsupportedOperationException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}
    }

    /*
     * Test method for 'org.dotplot.core.TypeBindingRegistry.get(String)'
     */
    public void testGet() {
	try {
	    this.registry.get(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.registry.get("test");
	    fail("UnknownIDException must be thrown");
	} catch (UnknownIDException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.typeRegistry.register("type", TextType.type);
	    this.registry.register("test", "type");
	    assertEquals("type", this.registry.get("test"));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for 'org.dotplot.core.TypeBindingRegistry.getAll()'
     */
    public void testGetAll() {
	assertNotNull(this.registry.getAll());
	assertTrue(this.registry.getAll().isEmpty());
    }

    public void testGetTypeOf() {
	try {
	    this.registry.getTypeOf(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    assertNull(this.registry.getTypeOf("test"));
	    this.typeRegistry.register("type", TextType.type);
	    this.registry.register("test", "type");
	    assertSame(TextType.type, this.registry.getTypeOf("test"));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for 'org.dotplot.core.TypeBindingRegistry.getTypeRegistry()'
     */
    public void testGetTypeRegistry() {
	assertSame(this.typeRegistry, this.registry.getTypeRegistry());
    }

    /*
     * Test method for 'org.dotplot.core.TypeBindingRegistry.register(String,
     * String)'
     */
    public void testRegister() {
	try {
	    this.registry.register(null, "test");
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.registry.register("test", null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.registry.register("test", "type");
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.typeRegistry.register("type", TextType.type);
	    this.registry.register("test", "type");
	    assertNotNull(this.registry.getAll());
	    assertEquals(1, this.registry.getAll().size());
	    assertTrue(this.registry.getAll().containsKey("test"));
	    assertEquals("type", this.registry.getAll().get("test"));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

	try {
	    this.registry.register("test", "type");
	    fail("DuplicateRegistrationException must be thrown");
	} catch (DuplicateRegistrationException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}
    }

    /*
     * Test method for
     * 'org.dotplot.core.TypeBindingRegistry.TypeBindingRegistry(ITypeRegistry)'
     */
    public void testTypeBindingRegistry() {
	try {
	    new TypeBindingRegistry(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new TypeBindingRegistry(this.typeRegistry);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for 'org.dotplot.core.TypeBindingRegistry.unregister(String)'
     */
    public void testUnregister() {
	try {
	    this.registry.unregister(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
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
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

}
