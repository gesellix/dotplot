/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.filter.tests;

import junit.framework.TestCase;

import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.KeyWordToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.ITokenFilter;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;

/**
 * Test Class for a TokenFilterContainer
 * 
 * @author hg12201
 * @version 1.0
 */
public final class TokenFilterContainerTest extends TestCase {

	private TokenFilterContainer tfc;

	private TestScanner ts;

	/**
	 * Constructor for TokenFilterContainerTest.
	 * 
	 * @param arg0
	 *            - the first argument
	 */
	public TokenFilterContainerTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.ts = new TestScanner();
		this.tfc = new TokenFilterContainer();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddTokenFilter() {
		ITokenFilter tf1 = new KeyWordFilter();
		ITokenFilter tf2 = new KeyWordFilter();
		ITokenFilter tf3 = new KeyWordFilter();
		ITokenFilter tf4 = new KeyWordFilter();

		this.tfc.addTokenFilter(tf1);
		assertEquals("prüft ob der 1. tokenfilter aufgenommen wurde", 1,
				this.tfc.getTokenFilters().length);

		this.tfc.addTokenFilter(tf2);
		assertEquals("prüft ob der 2. tokenfilter aufgenommen wurde", 2,
				this.tfc.getTokenFilters().length);

		this.tfc.addTokenFilter(tf3);
		assertEquals("prüft ob der 3. tokenfilter aufgenommen wurde", 3,
				this.tfc.getTokenFilters().length);

		this.tfc.addTokenFilter(tf4);
		ITokenFilter[] filters = this.tfc.getTokenFilters();
		assertEquals("prüft ob der 4. tokenfilter aufgenommen wurde", 4,
				filters.length);
		assertSame("prüft ob der 1. Filter richtig ist", tf1, filters[0]);
		assertSame("prüft ob der 2. Filter richtig ist", tf2, filters[1]);
		assertSame("prüft ob der 3. Filter richtig ist", tf3, filters[2]);
		assertSame("prüft ob der 4. Filter richtig ist", tf4, filters[3]);

	}

	public void testClear() {
		ITokenFilter tf1 = new KeyWordFilter();
		this.tfc.addTokenFilter(tf1);
		assertEquals("prüft ob der 1. tokenfilter aufgenommen wurde", 1,
				this.tfc.getTokenFilters().length);

		this.tfc.clear();
		assertEquals("prüft ob der Container geleert wurde", 0, this.tfc
				.getTokenFilters().length);
	}

	public void testCompareTo() {
		TokenFilterContainer container = new TokenFilterContainer();
		KeyWordFilter keyWord = new KeyWordFilter();
		GeneralTokenFilter general = new GeneralTokenFilter();
		LineFilter line = new LineFilter();
		SentenceFilter sentence = new SentenceFilter();

		assertEquals(0, container.compareTo(keyWord));
		assertEquals(0, container.compareTo(general));
		assertEquals(0, container.compareTo(line));
		assertEquals(0, container.compareTo(sentence));
		assertEquals(0, container.compareTo(container));
	}

	public void testFilterToken() throws TokenizerException {
		Token token = new Token("bla", Token.TYPE_STRING);
		KeyWordToken kwt = new KeyWordToken("blub", Token.TYPE_STRING);

		// tests mit einem filter
		this.tfc.addTokenFilter(new KeyWordFilter(
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS));
		assertSame("prüft ob normale Tokens zurückkommen", token, this.tfc
				.filterToken(token));
		assertNull("schlüsselwoörter müssen rausgefiltert werden", this.tfc
				.filterToken(kwt));

		// tests mit zwei filtern
		this.tfc.addTokenFilter(new KeyWordFilter(
				KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS));
		assertNull("tokens müssen rausgefiltert werden", this.tfc
				.filterToken(token));
		assertNull("schlüsselwoörter müssen rausgefiltert werden", this.tfc
				.filterToken(kwt));
	}

	/**
	 * Test for Token getNextToken()
	 */
	public void testGetNextToken() throws TokenizerException {
		this.tfc.setTokenStream(this.ts);
		assertEquals("prüfen ob das 1. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
		assertEquals("prüfen ob das 2. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
		assertEquals("prüfen ob das 3. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
		assertEquals("prüfen ob das 4. Token stimmt", new EOLToken(1), this.tfc
				.getNextToken());
		assertEquals("prüfen ob das 5. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
		assertEquals("prüfen ob das 6. Token stimmt", new KeyWordToken("bla",
				Token.TYPE_STRING, 2), this.tfc.getNextToken());
		assertEquals("prüfen ob das 7. Token stimmt", new KeyWordToken("bla",
				Token.TYPE_STRING, 2), this.tfc.getNextToken());
		assertEquals("prüfen ob das 8. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
		assertEquals("prüfen ob das 9. Token stimmt", new EOLToken(1), this.tfc
				.getNextToken());
		assertEquals("prüfen ob das 10. Token stimmt", new EOLToken(1),
				this.tfc.getNextToken());
		assertEquals("prüfen ob das 11. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken());
	}

	/**
	 * Test for Token getNextToken(ITokenStream)
	 */
	public void testGetNextTokenITokenStream() throws TokenizerException {
		assertEquals("prüft ob das 1. token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.tfc.getNextToken(this.ts));

		this.tfc.addTokenFilter(new KeyWordFilter(
				KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS));
		assertEquals("prüft ob das 2. token stimmt", new EOLToken(1), this.tfc
				.getNextToken(this.ts));

		this.tfc.addTokenFilter(new LineFilter());
		assertEquals("prüft ob das 3. token stimmt", new Token("bla bla",
				Token.TYPE_LINE), this.tfc.getNextToken(this.ts));
		assertEquals("prüft ob das 4. token stimmt", new Token("",
				Token.TYPE_LINE), this.tfc.getNextToken(this.ts));
		assertEquals("prüft ob das 5. token stimmt", new EOSToken(), this.tfc
				.getNextToken(this.ts));
	}

	public void testGetTokenFilters() {
		assertTrue("prüft ob ein ITOkenFilter array raus kommt", this.tfc
				.getTokenFilters() instanceof ITokenFilter[]);
	}

	public void testSetGetTokenStream() throws TokenizerException {
		this.tfc.setTokenStream(this.ts);
		assertSame("prüfen ob der Tokenstream richtig übernommen wurde",
				this.ts, this.tfc.getTokenStream());
	}

}
