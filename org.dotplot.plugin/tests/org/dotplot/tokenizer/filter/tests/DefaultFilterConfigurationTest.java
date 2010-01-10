/**
 * 
 */
package org.dotplot.tokenizer.filter.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.dotplot.core.IConfiguration;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public final class DefaultFilterConfigurationTest extends TestCase {

	/**
	 * 
	 */
	private DefaultFilterConfiguration config;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.config = new DefaultFilterConfiguration();
	}

	public void testCopy() {
		IConfiguration copy = this.config.copy();
		assertNotNull(copy);
		assertTrue(copy instanceof DefaultFilterConfiguration);
		DefaultFilterConfiguration config = (DefaultFilterConfiguration) copy;
		assertTrue(config.getFilterList().containsAll(
				this.config.getFilterList()));
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.DefaultFilterConfiguration.DefaultFilterConfiguration()'
	 */
	public void testDefaultFilterConfiguration() {
		List<String> filter = this.config.getFilterList();
		assertNotNull(filter);
		assertEquals(0, filter.size());
	}

	public void testGetFilterParameter() {
		try {
			this.config.getFilterParameter(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		assertNull(this.config.getFilterParameter("test"));
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.DefaultFilterConfiguration.setFilterList(List<String>)'
	 */
	public void testGetSetFilterList() {
		try {
			this.config.setFilterList(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		List<String> list = new ArrayList<String>();

		try {
			this.config.setFilterList(list);
			assertSame(list, this.config.getFilterList());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testObjectForm() {
		try {
			this.config.objectForm("");
			fail("UnsupportedOperationException must be thrown");
		}
		catch (UnsupportedOperationException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.objectForm(null);
			fail("UnsupportedOperationException must be thrown");
		}
		catch (UnsupportedOperationException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

	}

	public void testSerializedForm() {
		try {
			this.config.serializedForm();
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
	 * 'org.dotplot.tokenizer.DefaultFilterConfiguration.setFilterParameter(String,
	 * Map<String, ? extends Object>)'
	 */
	public void testSetFilterParameter() {
		Map<String, ?> m1 = new TreeMap<String, Object>();
		Map<String, ?> m2 = new TreeMap<String, Object>();

		try {
			this.config.setFilterParameter("test", null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setFilterParameter(null, m1);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setFilterParameter("test", m1);
			assertSame(m1, this.config.getFilterParameter("test"));
			this.config.setFilterParameter("test", m2);
			assertSame(m2, this.config.getFilterParameter("test"));

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
