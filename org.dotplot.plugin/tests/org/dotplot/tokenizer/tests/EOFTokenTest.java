/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.Token;

/**
 * TestClass for an EOFToken
 * 
 * @author case
 * @version 1.0
 */
public class EOFTokenTest extends TestCase {
    private EOFToken eoft;

    private String file = "./testfiles/tokenizer/test.txt";

    private IPlotSource source;

    /**
     * Constructor for EOFTokenTest.
     * 
     * @param arg0
     *            -- the first argument
     */
    public EOFTokenTest(String arg0) {
	super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.source = new DotplotFile(this.file);
	this.eoft = new EOFToken(this.source);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    public void testEOFToken() {
	assertEquals("prüfen ob das richtige File zurückgegeben wird",
		this.eoft.getSource(), this.source);
	assertEquals("prüfen ob der Tokentyp stimmt", this.eoft.getType(),
		Token.TYPE_EOF);
    }
}
