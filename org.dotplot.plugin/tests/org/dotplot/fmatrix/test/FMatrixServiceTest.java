/**
 * 
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.core.ConfigurationRegistry;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfiguration;
import org.dotplot.core.ISourceType;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.TaskProcessor;
import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.fmatrix.FMatrixService;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.Match;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.tokenizer.service.TokenStreamContext;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class FMatrixServiceTest extends TestCase {

	private FMatrixService service;

	private ITokenStream stream;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new FMatrixService("testservice");
		this.stream = new ITokenStream() {

			private String[] strings = { "to", "be", "or", "not", "to", "be" };

			private int i = 0;

			public Token getNextToken() throws TokenizerException {
				if (i < strings.length) {
					return new Token(this.strings[i++], 0, 1);
				}
				else {
					return new EOSToken();
				}
			}

			public ISourceType getStreamType() {
				return TextType.type;
			}
		};
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.FMatrixService.createTask()'
	 */
	public void testCreateTask() {
		try {
			DotplotContext frameworkcontext = new DotplotContext(".", ".");
			TokenStreamContext context = new TokenStreamContext(this.stream,
					new DefaultSourceList());
			this.service.setFrameworkContext(frameworkcontext);
			this.service.setWorkingContext(context);
			this.service.init();

			ITask task = this.service.createTask();
			assertNotNull(task);
			assertFalse(task.isPartless());
			assertEquals(1, task.countParts());
			assertFalse(task.isDone());
			assertFalse(task.isPartAble());
			assertEquals("FMatrix Task", task.getID());

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.fmatrix.FMatrixService.FMatrixService(String)'
	 */
	public void testFMatrixService() {
		assertEquals("testservice", this.service.getID());
	}

	/*
	 * Test method for 'org.dotplot.fmatrix.FMatrixService.getResultContext()'
	 */
	public void testGetResultContext() {
		try {
			DotplotContext frameworkcontext = new DotplotContext(".", ".");
			TokenStreamContext context = new TokenStreamContext(this.stream,
					new DefaultSourceList());
			this.service.setFrameworkContext(frameworkcontext);
			this.service.setWorkingContext(context);
			this.service.init();
			this.service.setTaskProcessor(new TaskProcessor());

			FMatrixContext resultContext;
			assertNotNull(this.service.getResultContext());
			assertTrue(this.service.getResultContext() instanceof NullContext);

			this.service.run();
			assertFalse(this.service.getResultContext() instanceof NullContext);
			resultContext = (FMatrixContext) this.service.getResultContext();
			assertNotNull(resultContext);

			ITypeTableNavigator navigator = resultContext
					.getTypeTableNavigator();
			assertNotNull(navigator);

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
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.fmatrix.FMatrixService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(FMatrixContext.class, this.service.getResultContextClass());
	}

	/*
	 * Test method for
	 * 'org.dotplot.fmatrix.FMatrixService.registerDefaultConfiguration(IConfigurationRegistry)'
	 */
	public void testRegisterDefaultConfiguration() {
		try {
			this.service.registerDefaultConfiguration(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		ConfigurationRegistry registry = new ConfigurationRegistry();
		try {
			registry.get(FMatrixService.ID_FMATRIX_CONFIGURATION);
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		this.service.registerDefaultConfiguration(registry);

		try {
			IConfiguration config = registry
					.get(FMatrixService.ID_FMATRIX_CONFIGURATION);
			assertNotNull(config);
			assertTrue(config instanceof DefaultFMatrixConfiguration);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.fmatrix.FMatrixService.workingContextIsCompatible(Class)'
	 */
	public void testWorkingContextIsCompatible() {
		try {
			this.service.workingContextIsCompatible(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			assertTrue(this.service
					.workingContextIsCompatible(TokenStreamContext.class));
			assertFalse(this.service
					.workingContextIsCompatible(NullContext.class));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
