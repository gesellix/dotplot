/**
 * 
 */
package org.dotplot.examples.FourGrammFilter.tests;

import junit.framework.TestCase;

import org.dotplot.core.ISourceType;
import org.dotplot.examples.FourGrammFilter.FourGrammFilter;
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
public class FourGrammFilterTest extends TestCase {

    private FourGrammFilter filter;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.filter = new FourGrammFilter();
    }

    /*
     * Test method for
     * 'org.dotplot.examples.FourGrammFilter.FourGrammFilter.compareTo(ITokenFilter)'
     */
    public void testCompareTo() {
	assertEquals(1, this.filter.compareTo(null));
    }

    /*
     * Test method for
     * 'org.dotplot.examples.FourGrammFilter.FourGrammFilter.filterToken(Token)'
     */
    public void testFilterToken() {
	try {
	    assertNull(this.filter.filterToken(null));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

	Token filteredToken;

	Token token1 = new Token("One", Token.TYPE_STRING);
	Token token2 = new Token("Two", Token.TYPE_STRING);
	Token token3 = new Token("Three", Token.TYPE_STRING);
	Token token4 = new Token("4", Token.TYPE_NUMBER);

	Token point = new Token(".", Token.TYPE_STRING);
	Token eol = new EOLToken(0);
	Token eos = new EOSToken();

	assertNull(this.filter.filterToken(token1));
	assertNull(this.filter.filterToken(token2));
	assertNull(this.filter.filterToken(token3));

	filteredToken = this.filter.filterToken(token4);
	assertNotNull(filteredToken);
	assertEquals(new Token("One Two Three 4", Token.TYPE_STRING),
		filteredToken);

	assertNull(this.filter.filterToken(token3));
	assertNull(this.filter.filterToken(token1));
	assertNull(this.filter.filterToken(point));

	filteredToken = this.filter.filterToken(token4);
	assertNotNull(filteredToken);
	assertEquals(new Token("Three One . 4", Token.TYPE_STRING),
		filteredToken);

	assertNull(this.filter.filterToken(token3));
	assertNull(this.filter.filterToken(token1));
	assertNull(this.filter.filterToken(eol));
	assertNull(this.filter.filterToken(point));

	filteredToken = this.filter.filterToken(token4);
	assertNotNull(filteredToken);
	assertEquals(new Token("Three One . 4", Token.TYPE_STRING),
		filteredToken);

	assertNull(this.filter.filterToken(token3));
	assertNull(this.filter.filterToken(token1));
	filteredToken = this.filter.filterToken(eos);
	assertNotNull(filteredToken);
	assertEquals(new Token("Three One", Token.TYPE_STRING), filteredToken);

	assertSame(eos, this.filter.filterToken(eos));
    }

    /*
     * Test method for
     * 'org.dotplot.examples.FourGrammFilter.FourGrammFilter.getNextToken()'
     */
    public void testGetNextToken() {
	ITokenStream stream = new ITokenStream() {

	    private Token[] tokens = { new Token("One", Token.TYPE_STRING),
		    new Token("Two", Token.TYPE_STRING),
		    new Token("Three", Token.TYPE_STRING),
		    new Token("4", Token.TYPE_NUMBER), };

	    private int index = -1;

	    public Token getNextToken() throws TokenizerException {
		if (index < tokens.length - 1) {
		    index++;
		    return tokens[index];
		} else {
		    return new EOSToken();
		}
	    }

	    public ISourceType getStreamType() {
		return null;
	    }

	};

	FourGrammFilter filter = new FourGrammFilter(stream);
	assertEquals(new Token("One Two Three 4", Token.TYPE_STRING), filter
		.getNextToken());
	assertEquals(new EOSToken(), filter.getNextToken());
	assertEquals(new EOSToken(), filter.getNextToken());
	assertEquals(new EOSToken(), filter.getNextToken());
    }

    /*
     * Test method for
     * 'org.dotplot.examples.FourGrammFilter.FourGrammFilter.getNextToken(ITokenStream)'
     */
    public void testGetNextTokenITokenStream() {
	ITokenStream stream = new ITokenStream() {

	    private Token[] tokens = { new Token("One", Token.TYPE_STRING),
		    new Token("Two", Token.TYPE_STRING),
		    new Token("Three", Token.TYPE_STRING),
		    new Token("4", Token.TYPE_NUMBER), };

	    private int index = -1;

	    public Token getNextToken() throws TokenizerException {
		if (index < tokens.length - 1) {
		    index++;
		    return tokens[index];
		} else {
		    return new EOSToken();
		}
	    }

	    public ISourceType getStreamType() {
		return null;
	    }

	};

	assertEquals(new Token("One Two Three 4", Token.TYPE_STRING),
		this.filter.getNextToken(stream));
	assertEquals(new EOSToken(), this.filter.getNextToken(stream));
	assertEquals(new EOSToken(), this.filter.getNextToken(stream));
	assertEquals(new EOSToken(), this.filter.getNextToken(stream));

    }

    /*
     * Test method for
     * 'org.dotplot.examples.FourGrammFilter.FourGrammFilter.getStreamType()'
     */
    public void testGetStreamType() {
	assertEquals(TextType.type, this.filter.getStreamType());
    }

}
