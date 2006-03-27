package org.dotplot.tokenizer.service.tests;

import junit.framework.TestCase;

import org.dotplot.core.IConfiguration;
import org.dotplot.tokenizer.service.DefaultTokenizerConfiguration;
import org.dotplot.tokenizer.service.TokenizerService;

/**
 * Test Class for the DefaultConfiguration
 * 
 * @author case
 * @version 1.0
 */
public class DefaultTokenizerConfigurationTest extends TestCase {

	private DefaultTokenizerConfiguration defCon;

	/**
	 * Constructor for DefaultConfigurationTest.
	 * 
	 * @param arg0 -
	 *            the first argument
	 */
	public DefaultTokenizerConfigurationTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.defCon = new DefaultTokenizerConfiguration();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDefaultTokenizerConfiguration() {
		DefaultTokenizerConfiguration config = new DefaultTokenizerConfiguration();
		assertEquals(TokenizerService.DEFAULT_TOKENIZER_ID, config
				.getTokenizerID());
	}

	public void testCopy() {
		IConfiguration copy = this.defCon.copy();
		assertNotNull(copy);
		assertTrue(copy instanceof DefaultTokenizerConfiguration);
		DefaultTokenizerConfiguration def = (DefaultTokenizerConfiguration) copy;
		assertEquals(def.getTokenizerID(), this.defCon.getTokenizerID());
	}

	public void testSerializedForm() {
		assertEquals(TokenizerService.DEFAULT_TOKENIZER_ID, this.defCon
				.getTokenizerID());
		
		try {
			assertEquals(TokenizerService.DEFAULT_TOKENIZER_ID, this.defCon
					.serializedForm());					
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}		
	}

	public void testObjectForm() {
		try {
			this.defCon.objectForm(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			IConfiguration config = this.defCon.objectForm("test.tokenizer");
			assertNotNull(config);
			assertTrue(config instanceof DefaultTokenizerConfiguration);
			assertEquals("test.tokenizer", ((DefaultTokenizerConfiguration)config).getTokenizerID());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
}
