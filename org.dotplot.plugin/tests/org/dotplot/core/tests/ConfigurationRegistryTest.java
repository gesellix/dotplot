/**
 * 
 */
package org.dotplot.core.tests;

import junit.framework.TestCase;

import org.dotplot.core.ConfigurationRegistry;
import org.dotplot.core.IConfiguration;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;
import org.dotplot.tokenizer.service.DefaultTokenizerConfiguration;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class ConfigurationRegistryTest extends TestCase {

	private ConfigurationRegistry registry;

	private IConfiguration config;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.registry = new ConfigurationRegistry();
		this.config = new IConfiguration() {

			public IConfiguration copy() {
				return null;
			}

			public IConfiguration objectForm(String serivalizedForm)
					throws UnsupportedOperationException {
				return null;
			}

			public String serializedForm() throws UnsupportedOperationException {
				return null;
			}
		};
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.ConfigurationRegistry.combine(IRegistry<IConfiguration>)'
	 */
	public void testCombine() {
		try {
			this.registry.combine(null);
			fail("UnsupportedOperationException must be thrown");
		}
		catch (UnsupportedOperationException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.ConfigurationRegistry.ConfigurationRegistry()'
	 */
	public void testConfigurationRegistry() {
		assertNotNull(this.registry.getAll());
	}

	public void testCopy() {
		try {
			DefaultTokenizerConfiguration config1 = new DefaultTokenizerConfiguration();
			DefaultFilterConfiguration config2 = new DefaultFilterConfiguration();
			this.registry.register("test1", config1);
			this.registry.register("test2", config2);

			IConfigurationRegistry copy = this.registry.copy();
			assertNotNull(copy);
			assertTrue(copy instanceof ConfigurationRegistry);
			ConfigurationRegistry registry = (ConfigurationRegistry) copy;
			assertTrue(registry.getAll().keySet().containsAll(
					this.registry.getAll().keySet()));

			for (String key : registry.getAll().keySet()) {
				assertSame(registry.get(key).getClass(), this.registry.get(key)
						.getClass());
			}
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.ConfigurationRegistry.get(String)'
	 */
	public void testGet() {
		try {
			this.registry.get(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.get("test");
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("test", config);
			assertSame(config, this.registry.get("test"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.ConfigurationRegistry.register(String,
	 * IConfiguration)'
	 */
	public void testRegister() {
		try {
			this.registry.register(null, config);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("test", null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("test", config);
			assertNotNull(this.registry.getAll());
			assertEquals(1, this.registry.getAll().size());
			assertTrue(this.registry.getAll().containsKey("test"));
			assertSame(config, this.registry.getAll().get("test"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.ConfigurationRegistry.unregister(String)'
	 */
	public void testUnregister() {
		try {
			this.registry.unregister(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("test", config);
			assertTrue(this.registry.getAll().containsKey("test"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

		try {
			assertSame(config, this.registry.unregister("test"));
			assertFalse(this.registry.getAll().containsKey("test"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
