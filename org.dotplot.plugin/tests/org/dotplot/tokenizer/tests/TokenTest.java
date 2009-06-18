/*
 * Created on 29.04.2004
 */
package org.dotplot.tokenizer.tests;

import java.io.File;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.tokenizer.Token;

/**
 * Test Class for a Token
 * 
 * @author case
 * @version 1.0
 */
public class TokenTest extends TestCase {

    private Token token;

    /**
     * Constructor for TokenTest.
     * 
     * @param arg0
     *            - the first argument
     */
    public TokenTest(String arg0) {
	super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.token = new Token("TestToken", 1, 2);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    public void testEquals() {
	Token token1 = new Token("bla", 1);
	Token token2 = new Token("bla", 1);

	assertEquals("pruefen ob die beiden tokens gleich sind", token1, token2);
    }

    public void testGetFile() {
	IPlotSource file = new DotplotFile("testfiles/tokenizer/test.txt");
	this.token.setSource(file);
	assertTrue("prüfen ob das was rein ging auch das ist was raus kommt",
		this.token.getSource() == file);
    }

    public void testGetLine() {
	assertEquals("prüfen ob der test stimmt", this.token.getLine(), 2);
    }

    public void testGetType() {
	assertEquals("prüfen ob der test stimmt", this.token.getType(), 1);
    }

    public void testGetValue() {
	assertEquals("prüfen ob der test stimmt", this.token.getValue(),
		"TestToken");
    }

    public void testSetFile() {
	File f1 = new File(".");
	File f2 = new File("./testfiles/tokenizer/test.txt");

	StringBuilder sb = new StringBuilder();
	sb.append("current execution path: ");
	sb.append(f1.getAbsolutePath());
	sb.append("\n");
	sb.append("path for searching file: ");
	sb.append(f2.getAbsolutePath());
	sb.append("\n");
	sb.append("is file: ");
	sb.append(f2.isFile());
	sb.append("\n");
	sb.append("does exist: ");
	sb.append(f2.exists());
	sb.append("\n");

	fail(sb.toString());

	IPlotSource file = new DotplotFile("./testfiles/tokenizer/test.txt");
	this.token.setSource(file);
	assertTrue("prüfen ob das was rein ging auch das ist was raus kommt",
		this.token.getSource() == file);
    }

    public void testSetLine() {
	this.token.setLine(4);
	assertTrue("prüfen ob das was rein ging auch das ist was raus kommt",
		this.token.getLine() == 4);
    }

    public void testSetType() {
	this.token.setType(3);
	assertTrue("prüfen ob das was rein ging auch das ist was raus kommt",
		this.token.getType() == 3);
    }

    /*
     * Test für String toString()
     */
    public void testToString() {
	assertEquals("prüfen ob der test stimmt", this.token.toString(),
		"Token(1) \"TestToken\" in line 2");
    }
}
