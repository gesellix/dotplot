/**
 * 
 */
package org.dotplot.fmatrix.test;

import java.util.Collection;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.ISourceList;
import org.dotplot.core.ISourceType;
import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.FMatrixTaskPart;
import org.dotplot.fmatrix.IFMatrixConfiguration;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.Match;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TextType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class FMatrixTaskPartTest extends TestCase {

    private FMatrixTaskPart part;

    private ITokenStream stream;

    private IFMatrixConfiguration config;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.config = new DefaultFMatrixConfiguration();
	this.stream = new ITokenStream() {

	    private String[] strings = { "to", "be", "or", "not", "to", "be" };

	    private int i = 0;

	    public Token getNextToken() throws TokenizerException {
		if (i < strings.length) {
		    return new Token(this.strings[i++], 0, 1);
		} else if (i == strings.length) {
		    i++;
		    return new EOLToken(1);
		} else if (i + 1 == strings.length) {
		    i++;
		    return new EOFToken(null);
		} else {
		    return new EOSToken();
		}
	    }

	    public ISourceType getStreamType() {
		return TextType.type;
	    }
	};

	this.part = new FMatrixTaskPart("Part 1", this.stream, this.config);
    }

    /*
     * Test method for
     * 'org.dotplot.fmatrix.FMatrixTaskPart.FMatrixTaskPart(String)'
     */
    public void testFMatrixTaskPart() {
	try {
	    new FMatrixTaskPart("test", null, this.config);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new FMatrixTaskPart(null, this.stream, this.config);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new FMatrixTaskPart("test", this.stream, null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new FMatrixTaskPart("test", this.stream, this.config);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
	assertEquals("Part 1", this.part.getID());
	assertSame(this.stream, this.part.getTokenStream());
    }

    /*
     * Test method for 'org.dotplot.fmatrix.FMatrixTaskPart.getResult()'
     */
    public void testGetResult() {
	assertNull(this.part.getResult());
	this.part.run();
	Object result = this.part.getResult();
	assertNotNull(result);
	assertTrue(result instanceof ITypeTableNavigator);
	ITypeTableNavigator navigator = (ITypeTableNavigator) result;
	assertEquals(10, navigator.getNumberOfAllMatches());
	Match match = navigator.getNextMatch();
	assertEquals(3, match.getX());
	assertEquals(3, match.getY());

	match = navigator.getNextMatch();
	assertEquals(2, match.getX());
	assertEquals(2, match.getY());

	match = navigator.getNextMatch();
	assertEquals(1, match.getX());
	assertEquals(1, match.getY());

	match = navigator.getNextMatch();
	assertEquals(5, match.getX());
	assertEquals(1, match.getY());

	match = navigator.getNextMatch();
	assertEquals(1, match.getX());
	assertEquals(5, match.getY());

	match = navigator.getNextMatch();
	assertEquals(5, match.getX());
	assertEquals(5, match.getY());

	match = navigator.getNextMatch();
	assertEquals(0, match.getX());
	assertEquals(0, match.getY());

	match = navigator.getNextMatch();
	assertEquals(4, match.getX());
	assertEquals(0, match.getY());

	match = navigator.getNextMatch();
	assertEquals(0, match.getX());
	assertEquals(4, match.getY());

	match = navigator.getNextMatch();
	assertEquals(4, match.getX());
	assertEquals(4, match.getY());

	assertNull(navigator.getNextMatch());
    }

    /*
     * Test method for
     * 'org.dotplot.fmatrix.FMatrixTaskPart.setLocalRessources(Collection<?
     * extends IRessource>)'
     */
    public void testSetLocalRessources() {
	try {
	    this.part.setLocalRessources(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    DotplotFile file = new DotplotFile("./testfiles/fmatrix/test.txt");
	    Collection<DotplotFile> coll = new Vector<DotplotFile>();
	    coll.add(file);
	    assertNotNull(this.part.getSourceList());
	    assertEquals(0, this.part.getSourceList().size());
	    this.part.setLocalRessources(coll);
	    ISourceList list = this.part.getSourceList();
	    assertEquals(1, list.size());
	    assertTrue(list.contains(file));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    public void testTokenStream() {
	assertEquals("to", this.stream.getNextToken().getValue());
	assertEquals("be", this.stream.getNextToken().getValue());
	assertEquals("or", this.stream.getNextToken().getValue());
	assertEquals("not", this.stream.getNextToken().getValue());
	assertEquals("to", this.stream.getNextToken().getValue());
	assertEquals("be", this.stream.getNextToken().getValue());
	assertEquals(new EOLToken(1), this.stream.getNextToken());
	assertEquals(new EOSToken(), this.stream.getNextToken());
    }

}
