/**
 * 
 */
package org.dotplot.tokenizer.filter.tests;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.core.ISourceType;
import org.dotplot.core.services.IRessource;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.FilterTaskPart;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.ITokenFilter;
import org.dotplot.tokenizer.filter.TokenFilterContainer;
import org.dotplot.tokenizer.service.ITokenStream;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FilterTaskPartTest extends TestCase {

	private FilterTaskPart part;
	private ITokenStream stream;
	private IFilterConfiguration config;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Map<String, ITokenFilter> filters = new TreeMap<String, ITokenFilter>();
		filters.put(FilterService.GENERAL_TOKEN_FILTER_ID, new GeneralTokenFilter());
		
		this.stream = new ITokenStream(){

			int i = 0;
			
			String[] tokens = new String[]{"to","be","or","not","to","be"};
			
			public Token getNextToken() throws TokenizerException {
				if(i < tokens.length){					
					i++;
					return new Token(tokens[i-1],i-1);
				}
				else {
					return new EOSToken();
				}
			}

			public ISourceType getStreamType() {
				return null;
			}};
			
		this.config = new DefaultFilterConfiguration();
		this.config.getFilterList().add(FilterService.GENERAL_TOKEN_FILTER_ID);
		
		Map<String,Collection<Integer>> params = new TreeMap<String, Collection<Integer>>();
		Collection<Integer> p = new Vector<Integer>();
		p.add(new Integer(2));
		p.add(new Integer(3));
		params.put(GeneralTokenFilter.PARAM, p);
		this.config.setFilterParameter(FilterService.GENERAL_TOKEN_FILTER_ID, params);
		
		this.part = new FilterTaskPart("testpart",filters,this.config);
		
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterTaskPart.FilterTaskPart(String)'
	 */
	public void testFilterTaskPart() {
		Map<String, ITokenFilter> filters = new TreeMap<String, ITokenFilter>();
		
		try {
			new FilterTaskPart(null,filters, this.config );
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FilterTaskPart("test", null, this.config );
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new FilterTaskPart("test", filters,null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		assertEquals("testpart", this.part.getID());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterTaskPart.getResult()'
	 */
	public void testGetResult() {		
		
		assertNull(this.part.getResult());
		this.part.run();
		Object result = this.part.getResult();
		assertNotNull(result);
		assertTrue(result instanceof ITokenFilter);
		assertTrue(result instanceof TokenFilterContainer);
		TokenFilterContainer filter = (TokenFilterContainer)result;
		filter.setTokenStream(this.stream);
		assertEquals("to", filter.getNextToken(this.stream).getValue());
		assertEquals("be", filter.getNextToken(this.stream).getValue());
		assertEquals("to", filter.getNextToken(this.stream).getValue());
		assertEquals("be", filter.getNextToken(this.stream).getValue());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterTaskPart.setLocalRessources(Collection<? extends IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			this.part.setLocalRessources(null);
			this.part.setLocalRessources(new Vector<IRessource>());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
