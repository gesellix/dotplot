/*
 * Created on 13.06.2004
 */
package org.dotplot.tokenizer.filter.tests;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;

/**
 * Test Class for a SentenceFilter
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class SentenceFilterTest extends TestCase {

    private SentenceFilter sentenceFilter;

    /**
     * Constructor for SentenceFilterTest.
     * 
     * @param arg0
     *            - the first argument
     */
    public SentenceFilterTest(String arg0) {
	super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.sentenceFilter = new SentenceFilter();
    }

    public void testApplyParameters() {
	Map<String, Object> parameter = new TreeMap<String, Object>();
	assertNull(this.sentenceFilter.getEndOfSentenceToken());
	this.sentenceFilter.applyParameter(parameter);
	assertNull(this.sentenceFilter.getEndOfSentenceToken());

	parameter.put(SentenceFilter.PARAM_TOKEN_TYPE, new Integer(2));
	parameter.put(SentenceFilter.PARAM_TOKEN_VALUE, ";");

	this.sentenceFilter.applyParameter(parameter);
	Token token = this.sentenceFilter.getEndOfSentenceToken();
	assertNotNull(token);
	assertEquals(2, token.getType());
	assertEquals(";", token.getValue());

	parameter.put(SentenceFilter.PARAM_TOKEN_TYPE, new Integer(5));
	parameter.put(SentenceFilter.PARAM_TOKEN_VALUE, ",");

	this.sentenceFilter.applyParameter(parameter);
	token = this.sentenceFilter.getEndOfSentenceToken();
	assertNotNull(token);
	assertEquals(5, token.getType());
	assertEquals(",", token.getValue());

	parameter.clear();
	this.sentenceFilter.applyParameter(parameter);
	assertNull(this.sentenceFilter.getEndOfSentenceToken());
    }

    public void testCompareTo() {
	TokenFilterContainer container = new TokenFilterContainer();
	KeyWordFilter keyWord = new KeyWordFilter();
	GeneralTokenFilter general = new GeneralTokenFilter();
	LineFilter line = new LineFilter();
	SentenceFilter sentence = new SentenceFilter();

	assertEquals(1, sentence.compareTo(keyWord));
	assertEquals(1, sentence.compareTo(general));
	assertEquals(0, sentence.compareTo(line));
	assertEquals(0, sentence.compareTo(sentence));
	assertEquals(0, sentence.compareTo(container));
    }

    public void testFilterToken() {
	Token sentenceToken = new Token(".");
	Token token = new Token("bla", Token.TYPE_STRING);

	try {
	    assertSame("muß durch gehen", token, this.sentenceFilter
		    .filterToken(token));

	    this.sentenceFilter.setEndOfSentenceToken(sentenceToken);

	    assertNull("muß gefiltert werden 1", this.sentenceFilter
		    .filterToken(token));
	    assertNull("muß gefiltert werden 2", this.sentenceFilter
		    .filterToken(token));
	    assertEquals("prüfen ob das richtge Satztoken generiert wurde",
		    new Token("bla bla .", Token.TYPE_STRING),
		    this.sentenceFilter.filterToken(sentenceToken));

	    assertNull("muß gefiltert werden 3", this.sentenceFilter
		    .filterToken(token));
	    assertNull("muß gefiltert werden 4", this.sentenceFilter
		    .filterToken(token));
	    assertEquals("prüfen ob auch beim EOS das richtige raus kommt",
		    new Token("bla bla", Token.TYPE_STRING),
		    this.sentenceFilter.filterToken(new EOSToken()));
	} catch (TokenizerException e) {
	    fail("Exception wurde geworfen" + e.getMessage());
	}
    }

    public void testGetSetEndOfSentenceToken() {
	Token token = new Token("Bla");
	this.sentenceFilter.setEndOfSentenceToken(token);
	assertSame("Prüft ob das was rein ging auch wieder raus kommt", token,
		this.sentenceFilter.getEndOfSentenceToken());
    }

}
