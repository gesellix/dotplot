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
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;

/**
 * Test Class for a KeyWordFilter
 * 
 * @author hg12201
 * @version 1.0
 */
public final class KeyWordFilterTest extends TestCase {

	private KeyWordFilter kWF;

	private TestScanner ts;

	/**
	 * Constructor for KeyWordFilterTest.
	 * 
	 * @param arg0
	 *            - the first argument
	 */
	public KeyWordFilterTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {

		super.setUp();

		kWF = new KeyWordFilter();
		ts = new TestScanner();
		// scanner
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

		assertEquals(0, keyWord.compareTo(keyWord));
		assertEquals(0, keyWord.compareTo(general));
		assertEquals(-1, keyWord.compareTo(line));
		assertEquals(-1, keyWord.compareTo(sentence));
		assertEquals(0, keyWord.compareTo(container));
	}

	public void testFilterToken() throws TokenizerException {
		Token token = new Token("bla");
		KeyWordToken kwt = new KeyWordToken("blub");
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_ALL);
		assertSame("prüfen ob das was rein geht auch wieder raus kommt", token,
				this.kWF.filterToken(token));
		assertSame("prüfen ob das was rein geht auch wieder raus kommt", kwt,
				this.kWF.filterToken(kwt));
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
		assertSame("prüfen ob tokens nicht gefiltert werden", token, this.kWF
				.filterToken(token));
		assertNull("prüfen ob keywords gefiltert werdent", this.kWF
				.filterToken(kwt));
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS);
		assertNull("prüfen ob tokens gefiltert werden", this.kWF
				.filterToken(token));
		assertSame("prüfen ob keywords nicht gefiltert werden", kwt, this.kWF
				.filterToken(kwt));
	}

	/**
	 * Test for Token getNextToken()
	 */
	public void testGetNextToken() throws TokenizerException {
		this.kWF.setTokenStream(this.ts);
		assertEquals("prüfen ob das 1. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
		assertEquals("prüfen ob das 2. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
		assertEquals("prüfen ob das 3. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
		assertEquals("prüfen ob das 4. Token stimmt", new EOLToken(1), this.kWF
				.getNextToken());
		assertEquals("prüfen ob das 5. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
		assertEquals("prüfen ob das 6. Token stimmt", new KeyWordToken("bla",
				Token.TYPE_STRING, 2), this.kWF.getNextToken());
		assertEquals("prüfen ob das 7. Token stimmt", new KeyWordToken("bla",
				Token.TYPE_STRING, 2), this.kWF.getNextToken());
		assertEquals("prüfen ob das 8. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
		assertEquals("prüfen ob das 9. Token stimmt", new EOLToken(1), this.kWF
				.getNextToken());
		assertEquals("prüfen ob das 10. Token stimmt", new EOLToken(1),
				this.kWF.getNextToken());
		assertEquals("prüfen ob das 11. Token stimmt", new Token("bla",
				Token.TYPE_STRING, 1), this.kWF.getNextToken());
	}

	/**
	 * Test for Token getNextToken(ITokenStream)
	 */
	public void testGetNextTokenITokenStream() throws TokenizerException {
		Token token = null;
		int tokenanzahl = 0;
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
		while (!(token instanceof EOSToken)) {
			token = this.kWF.getNextToken(this.ts);
			tokenanzahl++;
			assertFalse("prüfen, das keine schluesselwoerter durchkommen ",
					token instanceof KeyWordToken);
			if (token == null) {
				break;
			}
		}
		// zehn = 9 token aus TestScanner + EOSToken
		assertEquals("prüfen ob auch zehn tokens durchgekommen sind",
				tokenanzahl, 10);
		this.ts.reset();
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS);
		token = this.kWF.getNextToken(this.ts);
		tokenanzahl = 1;
		do {
			assertFalse(
					"prüfen, das nur schluesselwoerter bzw spezial Tokens durchkommen",
					token.getType() == Token.TYPE_STRING
							&& token.getClass() == Token.class);
			token = this.kWF.getNextToken(this.ts);
			if (token == null) {
				break;
			}
			tokenanzahl++;
		} while (!(token instanceof EOSToken));
		assertEquals("prüfen ob auch sechs tokens durchgekommen sind", 6,
				tokenanzahl);
	}

	public void testGetSetTokenSream() {
		this.kWF.setTokenStream(this.ts);
		assertSame(
				"prüfen ob der richtige Tokenstrom auch zurückgegeben wird.",
				this.ts, this.kWF.getTokenStream());
	}

	/**
	 * tests the constructor
	 */
	public void testKeyWordFilter() {
		this.kWF = new KeyWordFilter();
		assertEquals("prüft ob der defaultmodus eingestellt ist",
				KeyWordFilter.LET_THROUGH_ALL, this.kWF.getModus());
		assertNull("prüft ob kein Tokenstrom eingestellt ist", this.kWF
				.getTokenStream());

		this.kWF = new KeyWordFilter(KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
		assertEquals("prüft ob der richtige Modus eingestellt ist",
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS, this.kWF.getModus());
		assertNull("prüft ob kein Tokenstrom eingestellt ist", this.kWF
				.getTokenStream());

		this.kWF = new KeyWordFilter(this.ts);
		assertEquals("prüft ob der defaultmodus eingestellt ist",
				KeyWordFilter.LET_THROUGH_ALL, this.kWF.getModus());
		assertSame("prüft ob der richtige Tokenstrom eingestellt ist", this.ts,
				this.kWF.getTokenStream());

		this.kWF = new KeyWordFilter(this.ts,
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
		assertEquals("prüft ob der richtige Modus eingestellt ist",
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS, this.kWF.getModus());
		assertSame("prüft ob der richtige Tokenstrom eingestellt ist", this.ts,
				this.kWF.getTokenStream());
	}

	public void testModus() {
		assertEquals("den anfangsmodus ueberpruefen",
				KeyWordFilter.LET_THROUGH_ALL, this.kWF.getModus());
		this.kWF.setModus(KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
		assertEquals("setmodus ueberpruefen",
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS, this.kWF.getModus());
		this.kWF.setModus(6);
		assertEquals("ueberpruefen ob falsche eingabe abgefangen wird",
				KeyWordFilter.LET_THROUGH_NO_KEYWORDS, this.kWF.getModus());
	}

}
