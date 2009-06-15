/*
 * Created on 27.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.core.ITypeTable;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.TokenTable;
import org.dotplot.fmatrix.TypeTable;

/**
 * test funcionality of TypeTable class
 * 
 * @author Constantin von Zitzewitz, Thorsten Ruehl
 * @version 0.3
 */
public class TypeTableTest extends TestCase {

    private ITypeTable typeTable;

    private TokenTable tokenTable;

    @Override
    public void setUp() {
	this.tokenTable = new TokenTable();
	this.typeTable = new TypeTable(this.tokenTable);
    }

    public void testAddRegularExpressionType() {

	this.typeTable.addType("aaa eee cat");
	this.typeTable.addType(" ffff ggg cat");
	this.typeTable.addType("ddd cat");
	this.typeTable.addType("look at this complete line");
	this.typeTable.addType("unique");

	assertEquals("(1)  Function returns -1 on no match", -1, this.typeTable
		.addRegularExpressionType("could not be found", 1.0));

	int typeIndex = typeTable.addRegularExpressionType("cat", 0.5);

	assertTrue("(2) New Type is added to Type Table", (typeIndex > 0));

	assertEquals(
		"(3) If the number of found postings is rigth - should be 9",
		9, this.typeTable.getTokenType(typeIndex).getNumberOfMatches());

	assertTrue("(4) If the weight-value is set correct - should be 0.5",
		(this.typeTable.getTokenType(typeIndex).getWeight() == 0.5));

    }

    public void testAddToken() {
	// Tokens...
	assertEquals("addType must return expected typeindex on success!", 0,
		this.typeTable.addType("new Token 1"));
	assertEquals("addType must return expected typeindex on success!", 0,
		this.typeTable.addType("new Token 1"));
	assertEquals("addType must return expected typeindex on success!", 1,
		this.typeTable.addType("new Token 2"));
    }

    public void testGetNavigator() {
	assertNotNull("navigator object must not be null!", this.typeTable
		.createNavigator());

	assertTrue("must return an ITypeTableNavigator", this.typeTable
		.createNavigator() instanceof ITypeTableNavigator);
    }

    public void testGetNumberOfTypes() {
	// add some(40 types)
	for (int i = 0; i < 20; i++) {
	    this.typeTable.addType("yToken_No_" + i);
	    this.typeTable.addType("xToken_No_" + i);
	}

	assertEquals("added 40 types - now expecting 40 types from typeTable",
		40, this.typeTable.getNumberOfTypes());
    }

    public void testGetTokenType() {
	assertNull("get token type on empty table must return null!",
		this.typeTable.getTokenType(0));
	// add some...
	this.typeTable.addType("new token");
	assertNotNull(
		"tokentype of previously inserted token must not be null!",
		this.typeTable.getTokenType(0));
    }

    public void testGetTypeTableIterator() {
	assertNotNull("iterator object must not be null!", this.typeTable
		.getTypeTableIterator());
    }

    public void testSetUp() {
	assertNotNull("TypeTable object must not be null!", this.typeTable);
	assertNotNull("TokenTable object must not be null!", this.tokenTable);
    }
}
