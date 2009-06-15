/*
 * Created on 12.05.2004
 */
package org.dotplot.tokenizer.filter.tests;

import junit.framework.TestCase;

import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;

/**
 * Test Class for a LineFilter
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class LineFilterTest extends TestCase {

    private TestScanner ts;

    private LineFilter lf;

    /**
     * Constructor for LineFilterTest.
     * 
     * @param arg0
     *            - the first argument
     */
    public LineFilterTest(String arg0) {
	super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.ts = new TestScanner();
	this.lf = new LineFilter(this.ts);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    public void testCompareTo() {
	TokenFilterContainer container = new TokenFilterContainer();
	KeyWordFilter keyWord = new KeyWordFilter();
	GeneralTokenFilter general = new GeneralTokenFilter();
	LineFilter line = new LineFilter();
	SentenceFilter sentence = new SentenceFilter();

	assertEquals(1, line.compareTo(keyWord));
	assertEquals(1, line.compareTo(general));
	assertEquals(0, line.compareTo(line));
	assertEquals(0, line.compareTo(sentence));
	assertEquals(0, line.compareTo(container));

    }

    public void testDontReturnEmptyLines() throws TokenizerException {
	this.lf.dontReturnEmptyLines();
	assertEquals("prüft ob das 1. Token die erste Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prüft ob das 2. Token die zweite Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prüft ob das 3. Token die zweite Zeile repräsentiert",
		this.lf.getNextToken(this.ts),
		new Token("bla", Token.TYPE_LINE));
	assertEquals("prueft ob ein Token des Typs EOS zuruekgegeben wurde",
		this.lf.getNextToken(this.ts).getType(), Token.TYPE_EOS);
    }

    public void testDoReturnEmptyLines() throws TokenizerException {
	this.lf.doReturnEmptyLines();
	assertEquals("prüft ob das 1. Token die erste Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prüft ob das 2. Token die zweite Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prueft ob ein Token des Typs LINE zuruekgegeben wurde",
		this.lf.getNextToken(this.ts).getType(), Token.TYPE_LINE);
    }

    public void testFilterToken() {
	Token token1 = new Token("test", Token.TYPE_STRING, 1);
	Token token2 = new Token("test", Token.TYPE_STRING, 1);
	assertNull("prüft ob das erste Token gefiltert wird", this.lf
		.filterToken(token1));
	assertNull("prüft ob das zweite Token gefiltert wird", this.lf
		.filterToken(token2));
	assertEquals("prüft ob nun ein LineToken rauskommt", this.lf
		.filterToken(new EOLToken(1)), new Token("test test",
		Token.TYPE_LINE, 1));
    }

    /*
     * Test für Token getNextToken()
     */
    public void testGetNextToken() throws TokenizerException {
	assertEquals("prüft ob das 1. Token stimmt", this.lf.getNextToken(),
		new Token("bla", Token.TYPE_STRING));
	assertEquals("prüft ob das 2. Token stimmt", this.lf.getNextToken(),
		new Token("bla", Token.TYPE_STRING));
	assertEquals("prüft ob das 3. Token stimmt", this.lf.getNextToken(),
		new Token("bla", Token.TYPE_STRING));
	assertEquals("prüft ob das 4. Token stimmt", this.lf.getNextToken(),
		new EOLToken(1));
    }

    /*
     * Test für Token getNextToken(ITokenStream)
     */
    public void testGetNextTokenITokenStream() throws TokenizerException {
	assertEquals("prüft ob das 1. Token die erste Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prüft ob das 2. Token die zweite Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("bla bla bla bla",
			Token.TYPE_LINE));
	assertEquals("prüft ob das 3. Token die dritte Zeile repräsentiert",
		this.lf.getNextToken(this.ts), new Token("", Token.TYPE_LINE));
	assertEquals("prüft ob das 4. Token den rest repräsentiert", this.lf
		.getNextToken(this.ts), new Token("bla", Token.TYPE_LINE));
	assertEquals("prüft ob das 5. Token den schluss repräsentiert", this.lf
		.getNextToken(this.ts), new EOSToken());
    }

}
