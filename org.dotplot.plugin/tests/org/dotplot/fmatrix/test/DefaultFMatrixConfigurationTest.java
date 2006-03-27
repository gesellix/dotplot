/**
 * 
 */
package org.dotplot.fmatrix.test;

import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.IFMatrixConfiguration;
import org.dotplot.fmatrix.WeightingEntry;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class DefaultFMatrixConfigurationTest extends TestCase {

	private DefaultFMatrixConfiguration config;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.config = new DefaultFMatrixConfiguration();
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.DefaultFMatrixConfiguration.DefaultFMatrixConfiguration()'
	 */
	public void testDefaultFMatrixConfiguration() {
		DefaultFMatrixConfiguration config = new DefaultFMatrixConfiguration();
		try {
			assertNotNull(config.getManualWeightedTokens());
			assertEquals(0, config.getManualWeightedTokens().size());
			assertNotNull(config.getRegularExpressions());
			assertEquals(0, config.getRegularExpressions().size());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.DefaultFMatrixConfiguration.getRegularExpressions()'
	 */
	public void testGetRegularExpressions() {
		assertNotNull(this.config.getRegularExpressions());
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.DefaultFMatrixConfiguration.getManualWeightedTokens()'
	 */
	public void testGetManualWeightedTokens() {
		assertNotNull(this.config.getManualWeightedTokens());
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.DefaultFMatrixConfiguration.copy()'
	 */
	public void testCopy() {
		WeightingEntry entry1 = new WeightingEntry(0, 0);
		WeightingEntry entry2 = new WeightingEntry(0, 0);
		WeightingEntry entry3 = new WeightingEntry(0, 0);
		
		this.config.getManualWeightedTokens().add(entry1);
		this.config.getManualWeightedTokens().add(entry2);
		this.config.getManualWeightedTokens().add(entry3);
		assertEquals(3, this.config.getManualWeightedTokens().size());
		
		String reg1 = "1";
		String reg2 = "2";
		String reg3 = "3";
		
		this.config.getRegularExpressions().add(reg1);
		this.config.getRegularExpressions().add(reg2);
		this.config.getRegularExpressions().add(reg3);
		assertEquals(3, this.config.getRegularExpressions().size());
		
		try {
			IFMatrixConfiguration copy = (IFMatrixConfiguration)this.config.copy();
			assertNotSame(this.config.getManualWeightedTokens(),copy.getManualWeightedTokens());
			assertNotSame(this.config.getRegularExpressions(), copy.getRegularExpressions());
			
			assertEquals(this.config.getManualWeightedTokens().size(), copy.getManualWeightedTokens().size());
			assertEquals(this.config.getRegularExpressions().size(), copy.getRegularExpressions().size());
			
			assertTrue(copy.getManualWeightedTokens().contains(entry1));
			assertTrue(copy.getManualWeightedTokens().contains(entry2));
			assertTrue(copy.getManualWeightedTokens().contains(entry3));
			
			assertTrue(copy.getRegularExpressions().contains(reg1));
			assertTrue(copy.getRegularExpressions().contains(reg2));
			assertTrue(copy.getRegularExpressions().contains(reg3));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
		
	}
	
	public void testSerializedForm(){
		try {
			this.config.serializedForm();
			fail("UnsupportedOperationException must be thrown");
		}
		catch (UnsupportedOperationException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
	}
	
	public void testObjectForm(){
		try {
			this.config.objectForm("");
			fail("UnsupportedOperationException must be thrown");
		}
		catch (UnsupportedOperationException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
	}
}
