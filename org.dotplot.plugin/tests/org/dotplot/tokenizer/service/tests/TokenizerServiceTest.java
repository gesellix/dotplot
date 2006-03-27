/**
 * 
 */
package org.dotplot.tokenizer.service.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.converter.SourceListContext;
import org.dotplot.tokenizer.service.DefaultTokenizerConfiguration;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.PlotSourceListTokenizer;
import org.dotplot.tokenizer.service.TokenStreamContext;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.util.UnknownIDException;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class TokenizerServiceTest extends TestCase {

	private TokenizerService service;
	private DotplotContext context;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new TokenizerService("test");
		this.context = new DotplotContext(".");
		this.service.setFrameworkContext(this.context);
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.workingContextIsCompatible(Class)'
	 */
	public void testWorkingContextIsCompatible() {
		assertTrue(this.service.workingContextIsCompatible(SourceListContext.class));
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.init()'
	 */
	public void testInit() {
		assertNotNull(this.service.getRegisteredTokenizer());
		assertEquals(0, this.service.getRegisteredTokenizer().size());
		this.service.init();
		assertEquals(1, this.service.getRegisteredTokenizer().size());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.getResultContext()'
	 */
	public void testGetResultContext() {
		ITokenStream stream;
		IContext result;
		
		ISourceList list = new DefaultSourceList();
		SourceListContext context = new SourceListContext(list);
		
		assertEquals(NullContext.context, this.service.getResultContext());
		this.service.init();
		try {
			this.service.setWorkingContext(context);
			
			this.service.run();
			result = this.service.getResultContext();
			assertNotNull(result);
			assertTrue(result instanceof TokenStreamContext);
			stream = ((TokenStreamContext)result).getTokenStream();
			assertNotNull(stream);
			assertTrue(stream instanceof PlotSourceListTokenizer);
			
			assertEquals("EOSToken", stream.getNextToken().getValue());
			assertEquals("EOSToken", stream.getNextToken().getValue());
			
			IPlotSource source1 = new DotplotFile("./testfiles/tokenizer/test.txt");
			IPlotSource source2 = new DotplotFile("./testfiles/tokenizer/test.txt");
			
			list.add(source1);
			list.add(source2);
		
			this.service.run();
			
			result = this.service.getResultContext();
			assertNotNull(result);
			assertTrue(result instanceof TokenStreamContext);
			stream = ((TokenStreamContext)result).getTokenStream();
			assertNotNull(stream);
			assertTrue(stream instanceof PlotSourceListTokenizer);
			
			String[] text = {"to","be","or","not","to","be."};
			Token token;
			
			for(int i = 0; i < text.length; i++){
				token = stream.getNextToken();
				assertEquals(String.valueOf(i), text[i], token.getValue());
				assertSame(source1, token.getSource());
			}

			assertTrue(stream.getNextToken() instanceof EOFToken);			

			for(int i = 0; i < text.length; i++){
				token = stream.getNextToken();
				assertEquals(text[i], token.getValue());
				assertSame(source2, token.getSource());
			}
			
			assertTrue(stream.getNextToken() instanceof EOFToken);
			
			assertEquals("EOSToken", stream.getNextToken().getValue());			
			assertEquals("EOSToken", stream.getNextToken().getValue());			
			assertEquals("EOSToken", stream.getNextToken().getValue());			
			assertEquals("EOSToken", stream.getNextToken().getValue());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(TokenStreamContext.class, this.service.getResultContextClass());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.createTask()'
	 */
	public void testCreateTask() {
		try {
			
			this.service.init();
			this.service.setWorkingContext(new SourceListContext(new DefaultSourceList()));
			ITask task = this.service.createTask(); 
			assertNotNull(task);
			assertFalse(task.isDone());
			assertFalse(task.isPartAble());
			assertFalse(task.isPartless());
			assertEquals(1, task.getParts().size());
			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.TokenizerService(String)'
	 */
	public void testTokenizerService() {
		assertEquals("test", this.service.getID());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerService.getRegisteredTokenizer()'
	 */
	public void testGetRegisteredTokenizer() {
		assertNotNull(this.service.getRegisteredTokenizer());
		assertEquals(0, this.service.getRegisteredTokenizer().size());
		this.service.init();
		assertEquals(1, this.service.getRegisteredTokenizer().size());
		Map<String, ITokenizer> map = this.service.getRegisteredTokenizer();
		assertTrue(map.containsKey("org.dotplot.tokenizer.DefaultTokenizer"));		
	}

	public void testRegisterDefaultConfiguration(){
		try {
			this.context.getConfigurationRegistry().get(TokenizerService.TOKENIZER_CONFIGURATION_ID);
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		
		try {
			
			this.service.registerDefaultConfiguration(this.context.getConfigurationRegistry());
			assertNotNull(this.context.getConfigurationRegistry().get(TokenizerService.TOKENIZER_CONFIGURATION_ID));
			assertTrue(this.context.getConfigurationRegistry().get(TokenizerService.TOKENIZER_CONFIGURATION_ID) instanceof DefaultTokenizerConfiguration);

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}		
	}
	
}
