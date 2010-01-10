/**
 * 
 */
package org.dotplot.image.tests;

import junit.framework.TestCase;

import org.dotplot.core.ConfigurationRegistry;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfiguration;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.ISourceType;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageContext;
import org.dotplot.image.QImageService;
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
public final class QImageServiceTest extends TestCase {

	private QImageService service;

	private ITypeTableNavigator navigator;

	private ITokenStream stream;

	private IQImageConfiguration config;

	private FMatrixContext workingContext;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new QImageService("org.dotplot.test.QImage");
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
		FMatrixManager manager = new FMatrixManager(this.stream,
				new DefaultFMatrixConfiguration());
		manager.addTokens();
		this.navigator = manager.getTypeTableNavigator();
		this.config = new QImageConfiguration();
		this.workingContext = new FMatrixContext(this.navigator);
	}

	/*
	 * Test method for 'org.dotplot.image.QImageService.createTask()'
	 */
	public void testCreateTask() {
		DotplotContext context = new DotplotContext(".", "./plugins");
		try {
			context.getConfigurationRegistry().register(
					QImageService.QIMAGE_CONFIGURATION_ID, this.config);
			this.service.setFrameworkContext(context);
			this.service.setWorkingContext(this.workingContext);
			ITask task = this.service.createTask();
			assertNotNull(task);
			assertFalse(task.isPartAble());
			assertFalse(task.isPartless());
			assertEquals(1, task.countParts());
			assertEquals("Image Task", task.getID());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.image.QImageService.getResultContext()'
	 */
	public void testGetResultContext() {
		assertNotNull(this.service.getResultContext());
		assertEquals(NullContext.context, this.service.getResultContext());
		DotplotContext context = new DotplotContext(".", "./plugins");
		try {
			context.getConfigurationRegistry().register(
					QImageService.QIMAGE_CONFIGURATION_ID, this.config);
			this.service.setFrameworkContext(context);
			this.service.setWorkingContext(this.workingContext);
			this.service.run();
			IContext result = this.service.getResultContext();
			assertNotNull(result);
			assertTrue(result instanceof QImageContext);
			QImageContext imageContext = (QImageContext) result;
			assertNotNull(imageContext.getDotplot());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.image.QImageService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(QImageContext.class, this.service.getResultContextClass());
	}

	/*
	 * Test method for 'org.dotplot.image.QImageService.QImageService(String)'
	 */
	public void testQImageService() {
		assertEquals("org.dotplot.test.QImage", this.service.getID());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageService.registerDefaultConfiguration(IConfigurationRegistry)'
	 */
	public void testRegisterDefaultConfiguration() {
		IConfigurationRegistry registry = new ConfigurationRegistry();
		try {
			registry.get(QImageService.QIMAGE_CONFIGURATION_ID);
			fail("Exception must be thrown");
		}
		catch (UnknownIDException e) {
			// all clear
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.service.registerDefaultConfiguration(registry);
			IConfiguration config = registry
					.get(QImageService.QIMAGE_CONFIGURATION_ID);
			assertNotNull(config);
			assertTrue(config instanceof IQImageConfiguration);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageService.workingContextIsCompatible(Class)'
	 */
	public void testWorkingContextIsCompatible() {
		assertTrue(this.service
				.workingContextIsCompatible(FMatrixContext.class));
		assertFalse(this.service.workingContextIsCompatible(NullContext.class));
		assertFalse(this.service
				.workingContextIsCompatible(TokenStreamContext.class));
	}
}
