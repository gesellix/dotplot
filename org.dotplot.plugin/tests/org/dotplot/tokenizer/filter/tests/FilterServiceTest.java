/**
 * 
 */
package org.dotplot.tokenizer.filter.tests;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.ISourceType;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.ITokenFilter;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TokenStreamContext;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FilterServiceTest extends TestCase {

	private FilterService service;
	private DotplotContext context;
	private ITokenStream stream;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new FilterService("test");
		this.context = new DotplotContext(".");
		this.service.setFrameworkContext(this.context);
		
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
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.workingContextIsCompatible(Class)'
	 */
	public void testWorkingContextIsCompatible() {
		try {
			this.service.workingContextIsCompatible(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			assertTrue(this.service.workingContextIsCompatible(TokenStreamContext.class));	
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.getResultContext()'
	 */
	public void testGetResultContext() {
		TokenStreamContext context = new TokenStreamContext(this.stream, new DefaultSourceList());
		IFilterConfiguration config;

		try {
			this.service.init();
			this.service.setWorkingContext(context);
			
			config = (IFilterConfiguration)this.context.getConfigurationRegistry().get(FilterService.ID_CONFIGURATION_FILTER);
			
			config.getFilterList().add(FilterService.ID_FILTER_GENERAL_TOKEN_FILTER);
			
			Map<String,Collection<Integer>> params = new TreeMap<String, Collection<Integer>>();
			Collection<Integer> p = new Vector<Integer>();
			p.add(new Integer(2));
			p.add(new Integer(3));
			params.put(GeneralTokenFilter.PARAM, p);
			config.setFilterParameter(FilterService.ID_FILTER_GENERAL_TOKEN_FILTER, params);
			
			assertEquals(NullContext.context, this.service.getResultContext());
			this.service.run();
			assertFalse(NullContext.context.equals(this.service.getResultContext()));
			TokenStreamContext resultContext = (TokenStreamContext)this.service.getResultContext();
			ITokenStream result = resultContext.getTokenStream();
			assertNotNull(result);
			
			assertEquals("to", result.getNextToken().getValue());
			assertEquals("be", result.getNextToken().getValue());
			assertEquals("to", result.getNextToken().getValue());
			assertEquals("be", result.getNextToken().getValue());
			assertEquals("EOSToken", result.getNextToken().getValue());
			assertEquals("EOSToken", result.getNextToken().getValue());

			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}		
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(TokenStreamContext.class, this.service.getResultContextClass());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.createTask()'
	 */
	public void testCreateTask() {
		try {
			
			TokenStreamContext context = new TokenStreamContext(new ITokenStream(){
	
				public Token getNextToken() throws TokenizerException {
					return null;
				}

				public ISourceType getStreamType() {
					return null;
				}}, new DefaultSourceList());
			
			this.service.init();
			this.service.setWorkingContext(context);
			ITask task = this.service.createTask();
			
			assertNotNull(task);
			assertFalse(task.isDone());
			assertFalse(task.isPartless());
			assertFalse(task.isPartAble());
			assertEquals(1, task.countParts());

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.registerDefaultConfiguration(IConfigurationRegistry)'
	 */
	public void testRegisterDefaultConfiguration() {
		this.service.registerDefaultConfiguration(this.context.getConfigurationRegistry());
		assertTrue(this.context.getConfigurationRegistry().getAll().containsKey(FilterService.ID_CONFIGURATION_FILTER));
		assertNotNull(this.context.getConfigurationRegistry().getAll().get(FilterService.ID_CONFIGURATION_FILTER));
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.FilterService.FilterService(String)'
	 */
	public void testFilterService() {
		assertEquals("test", this.service.getID());
	}
	
	public void testGetRegisteredFilters(){
		Map<String, ITokenFilter> filters;
		filters = this.service.getRegisteredFilters();
		assertNotNull(filters);
		assertEquals(0, filters.size());
		this.service.init();
		filters = this.service.getRegisteredFilters();
		assertNotNull(filters);
		assertEquals(1, filters.size());
	}

}
