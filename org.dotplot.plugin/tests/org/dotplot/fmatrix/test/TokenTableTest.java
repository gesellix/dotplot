/*
 * Created on 26.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.fmatrix.TokenTable;

/**
 * test functionality of TokenTable
 * 
 * @author Constantin von Zitzewitz
 * @version 0.2
 */
public final class TokenTableTest extends TestCase {

	private TokenTable tokenTable;

	@Override
	public void setUp() {
		this.tokenTable = new TokenTable();
	}

	public void testAdding() {
		int someTypeIndex = 5;
		int tokenIndex = 0;
		assertTrue("a successfully added index must return inserted index!",
				this.tokenTable.addTypeIndex(someTypeIndex) == tokenIndex);

		someTypeIndex = 17;
		tokenIndex++;
		assertTrue("a successfully added index must return inserted index!",
				this.tokenTable.addTypeIndex(someTypeIndex) == tokenIndex);
	}

	public void testGetNumberOfTokens() {
		int i;
		int someTypeIndex = 67;

		for (i = 0; i < 100; i++) {
			assertEquals("added index must be equal to simulated index", i,
					this.tokenTable.addTypeIndex(someTypeIndex));
			someTypeIndex++;
		}

		assertEquals("method must return same result, as calculated number",
				(i + 1), this.tokenTable.getNumberOfTokens());
	}

	public void testGetting() {

		// add some
		int someTypeIndex = 5;
		int tokenIndex = 0;
		this.tokenTable.addTypeIndex(someTypeIndex);
		assertTrue("previously added index must be returned successfully!",
				this.tokenTable.getTypeIndex(tokenIndex) == someTypeIndex);

		someTypeIndex = 99;
		tokenIndex++;
		this.tokenTable.addTypeIndex(someTypeIndex);

		assertTrue("previously added index must be returned successfully!",
				this.tokenTable.getTypeIndex(tokenIndex) == someTypeIndex);
	}

	public void testSetUp() {
		assertNotNull("TokenTable object must not be null!", this.tokenTable);
	}
}
