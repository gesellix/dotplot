/*
 * Created on 27.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.fmatrix.TokenType;

/**
 * tests functionality of TokenType
 * 
 * @author Constantin von Zitzewitz
 * @version 0.2
 */
public class TokenTypeTest extends TestCase {

    private TokenType tokenType;

    @Override
    public void setUp() {
	this.tokenType = new TokenType("some token type");
    }

    public void testAddPosting() {
	int someIndex1 = 2;
	int someIndex2 = 3;
	int someIndex3 = 4;

	this.tokenType.addTypePosition(someIndex1);
	this.tokenType.addTypePosition(someIndex2);
	this.tokenType.addTypePosition(someIndex3);

	assertEquals("There should be 9 postings(match)", 9, this.tokenType
		.getNumberOfMatches());
    }

    public void testSetUp() {
	assertNotNull("TokenType object must not be null!", this.tokenType);
    }

    public void testSetWeight() {
	double someWeight = 0.8;
	this.tokenType.setWeight(someWeight);
	assertTrue("check if setWeight was successful!", this.tokenType
		.getWeight() == someWeight);
    }
}
