/*
 * Created on 06.05.2004
 */
package org.dotplot.tokenizer.filter.tests;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * Test Class for a GenralTokenFilter
 * 
 * @author hg12201
 * @version 1.0
 */
public class GeneralTokenFilterTest extends TestCase {

	GeneralTokenFilter gtf = null;

	ITokenStream ts = null;

	class SimpleTestTokenStream implements ITokenStream {

		int index = 0;

		public Token getNextToken() throws TokenizerException {
			this.index++;
			switch (this.index) {
			case 1:
				return new Token("if", 0);
			case 2:
				return new Token("for", 1);
			case 3:
				return new Token("while", 2);
			case 4:
				return new Token("do", 3);
			case 5:
				return new Token("class", 4);
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
		 */
		public ISourceType getStreamType() {
			return null;
		}
	}

	/**
	 * Constructor for GeneralTokenFilterTest.
	 * 
	 * @param arg0 -
	 *            the first argument
	 */
	public GeneralTokenFilterTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.ts = new SimpleTestTokenStream();
		this.gtf = new GeneralTokenFilter(this.ts);
	}

	/**
	 * Test for Token getNextToken(ITokenStream)
	 */
	public void testGetNextTokenITokenStream() {
		Token token;
		
		Collection<Integer> list = new Vector<Integer>();
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		Map<String, Collection<Integer>> map = new TreeMap<String, Collection<Integer>>();
		map.put(GeneralTokenFilter.PARAM, list);
		
		this.gtf.applyParameter(map);
		
		try {

			token = this.gtf.getNextToken(this.ts);
			assertNotNull(token);
			assertEquals("if", token.getValue());
			
			if (token != null) {
				assertTrue("dieses Token sollte ein Token des Typs 0 sein",
						token.getType() == 0);
			}
			token = this.gtf.getNextToken(this.ts);
			assertTrue("pruefen ob auch ein token rausgekommen ist",
					token instanceof Token);
			if (token != null) {
				assertEquals("dieses Token sollte ein Token des Typs 4 sein",4,
						token.getType());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test for Token getNextToken()
	 */
	public void testGetNextToken() {
		Token token;
		try {
			for (int i = 0; i < 5; i++) {
				token = this.gtf.getNextToken();
				assertTrue("pruefen ob auch ein Token rausgekommen ist",
						token instanceof Token);
				if (token != null) {
					assertTrue("dieses Token sollte vom typ " + i + " sein",
							token.getType() == i);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetTokenStream() {
		this.gtf.setTokenStream(null);
		assertTrue("pruefen ob der tokenstream null ist", this.gtf
				.getTokenStream() == null);
		this.gtf.setTokenStream(this.ts);
		assertTrue("pruefen ob der tokenstream uebergeben wurde", this.gtf
				.getTokenStream() == this.ts);
	}

	public void testGetTokenStream() {
		this.gtf.setTokenStream(this.ts);
		assertTrue("pruefen ob der tokenstream uebergeben wurde", this.gtf
				.getTokenStream() == this.ts);
	}

	public void testFilterToken() {
		Collection<Integer> list = new Vector<Integer>();
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		Map<String, Collection<Integer>> map = new TreeMap<String, Collection<Integer>>();
		map.put(GeneralTokenFilter.PARAM, list);
		
		this.gtf.applyParameter(map);

		
		Token t1 = new Token("if", 0);
		Token t2 = new Token("if", 1);
		assertTrue("pruefen ob das token druchkommt",
				this.gtf.filterToken(t1) == t1);
		assertTrue("pruefen ob das token gefiltert wird", this.gtf
				.filterToken(t2) == null);
	}

	public void testApplyParameters(){
		Token t = new Token("test", 0);
		assertSame(t, this.gtf.filterToken(t));
		
		Collection<Integer> list = new Vector<Integer>();
		list.add(new Integer(0));
		Map<String, Collection<Integer>> map = new TreeMap<String, Collection<Integer>>();
		map.put(GeneralTokenFilter.PARAM, list);
		
		this.gtf.applyParameter(map);
		
		assertNull(this.gtf.filterToken(t));
	}
	
	public void testCompareTo(){
		TokenFilterContainer container = new TokenFilterContainer();
		KeyWordFilter keyWord = new KeyWordFilter();
		GeneralTokenFilter general = new GeneralTokenFilter();
		LineFilter line = new LineFilter();
		SentenceFilter sentence = new SentenceFilter();
		
		assertEquals(0, general.compareTo(keyWord));
		assertEquals(0, general.compareTo(general));
		assertEquals(-1, general.compareTo(line));
		assertEquals(-1, general.compareTo(sentence));
		assertEquals(0, general.compareTo(container));
	}
}
