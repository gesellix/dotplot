/**
 * 
 */
package org.dotplot.tokenizer.converter.tests;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IConfiguration;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.core.ITypeBindingRegistry;
import org.dotplot.core.ITypeRegistry;
import org.dotplot.core.services.DefaultErrorHandler;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.NullContext;
import org.dotplot.tokenizer.converter.ConverterService;
import org.dotplot.tokenizer.converter.DefaultConverterConfiguration;
import org.dotplot.tokenizer.converter.IConverter;
import org.dotplot.tokenizer.converter.PDFtoTxtConverter;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.converter.SourceListContext;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class ConverterServiceTest extends TestCase {

	private ConverterService service;

	private DotplotContext context;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new ConverterService("test");
		this.context = new DotplotContext(".");
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterService.ConverterService(String)'
	 */
	public void testConverterService() {
		assertEquals("test", this.service.getID());
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterService.createTask()'
	 */
	public void testCreateTask() {
		ITask task;
		ISourceList list = new DefaultSourceList();
		SourceListContext context = new SourceListContext(list);

		try {
			this.service.setFrameworkContext(this.context);
			this.service.init();

			DefaultConverterConfiguration config = (DefaultConverterConfiguration) this.context
					.getConfigurationRegistry().get(
							ConverterService.CONVERTER_CONFIGURATION_ID);
			config.getConverterRegistry().put(PdfType.type,
					ConverterService.CONVERTER_PDF_TO_TEXT_ID);
			config.setTargetType(TextType.type);
			config.setConvertedFilesDirectory(new File("./testfiles/converter")
					.getCanonicalFile());

			task = this.service.createTask();
			assertNull(task);

			this.service.setWorkingContext(context);
			task = this.service.createTask();
			assertNotNull(task);
			assertTrue(task.isPartless());
			assertEquals(0, task.countParts());

			list.add(new DotplotFile("./testfiles/converter/PO91-1999.pdf",
					PdfType.type));
			list.add(new DotplotFile(
					"./testfiles/converter/Zuordnung_Module_Schwerpunkte.pdf",
					PdfType.type));
			list.add(new DotplotFile("./testfiles/converter/pdfout.txt",
					TextType.type));

			this.service.setWorkingContext(context);

			task = this.service.createTask();
			if (task == null) {
				System.out.println(((DefaultErrorHandler) this.service
						.getErrorHandler()).getErrorMessages());
			}

			assertNotNull(task);
			assertFalse(task.isPartless());
			assertEquals(2, task.countParts());
			assertTrue(task.isPartAble());
			assertFalse(task.isDone());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterService.getResultContext()'
	 */
	public void testGetResultContext() {
		IContext result;
		SourceListContext resultContext;

		try {
			ISourceList list = new DefaultSourceList();
			SourceListContext context = new SourceListContext(list);
			File pdf1 = new File("./testfiles/converter/PO91-1999.pdf")
					.getCanonicalFile();

			this.service.setFrameworkContext(this.context);
			this.service.init();

			DefaultConverterConfiguration config = (DefaultConverterConfiguration) this.context
					.getConfigurationRegistry().get(
							ConverterService.CONVERTER_CONFIGURATION_ID);
			config.getConverterRegistry().put(PdfType.type,
					ConverterService.CONVERTER_PDF_TO_TEXT_ID);
			config.setTargetType(TextType.type);
			config.setConvertedFilesDirectory(new File("./testfiles/converter")
					.getCanonicalFile());

			list.add(new DotplotFile(pdf1, PdfType.type));

			this.service.setWorkingContext(context);

			result = this.service.getResultContext();
			assertNotNull(result);
			assertEquals(NullContext.context, result);

			this.service.run();

			result = this.service.getResultContext();
			assertNotNull(result);
			assertTrue(result instanceof SourceListContext);
			resultContext = (SourceListContext) result;

			List<IPlotSource> resultList = resultContext.getSourceList();

			assertEquals(1, resultList.size());

			DotplotFile file1 = (DotplotFile) resultList.get(0);

			File result1 = new File(pdf1.getCanonicalPath() + ".txt");

			assertSame(file1.getType().getClass(), TextType.class);

			assertEquals(result1, file1.getFile().getCanonicalFile());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

		try {
			this.setUp();
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

		try {
			ISourceList list = new DefaultSourceList();
			SourceListContext context = new SourceListContext(list);

			File pdf1 = new File("./testfiles/converter/PO91-1999.pdf");
			File pdf2 = new File(
					"./testfiles/converter/Zuordnung_Module_Schwerpunkte.pdf");
			File txt = new File("./testfiles/converter/pdfout.txt");

			this.service.setFrameworkContext(this.context);
			this.service.init();

			DefaultConverterConfiguration config = (DefaultConverterConfiguration) this.context
					.getConfigurationRegistry().get(
							ConverterService.CONVERTER_CONFIGURATION_ID);
			config.setConverter(PdfType.type,
					ConverterService.CONVERTER_PDF_TO_TEXT_ID);
			config.setTargetType(TextType.type);
			config.setConvertedFilesDirectory(new File("./testfiles/converter")
					.getCanonicalFile());

			list.add(new DotplotFile(pdf1, PdfType.type));
			list.add(new DotplotFile(txt, TextType.type));
			list.add(new DotplotFile(pdf2, PdfType.type));

			this.service.setWorkingContext(context);

			result = this.service.getResultContext();
			assertNotNull(result);
			assertEquals(NullContext.context, result);

			this.service.run();

			result = this.service.getResultContext();
			assertNotNull(result);
			assertTrue(result instanceof SourceListContext);
			resultContext = (SourceListContext) result;

			List<IPlotSource> resultList = resultContext.getSourceList();

			assertEquals(3, resultList.size());

			DotplotFile file1 = (DotplotFile) resultList.get(0);
			DotplotFile file2 = (DotplotFile) resultList.get(1);
			DotplotFile file3 = (DotplotFile) resultList.get(2);

			File result1 = new File(pdf1.getCanonicalPath() + ".txt");
			File result3 = new File(pdf2.getCanonicalPath() + ".txt");

			assertSame(file1.getType().getClass(), TextType.class);
			assertSame(file3.getType().getClass(), TextType.class);

			assertEquals(result1, file1.getFile().getCanonicalFile());
			assertEquals(txt.getCanonicalFile(), file2.getFile()
					.getCanonicalFile());
			assertEquals(result3, file3.getFile().getCanonicalFile());

		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterService.getResultContextClass()'
	 */
	public void testGetResultContextClass() {
		assertSame(SourceListContext.class, this.service
				.getResultContextClass());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.converter.ConverterService.init()'
	 */
	public void testInit() {
		try {
			this.service.setFrameworkContext(this.context);
			this.service.init();

			ITypeRegistry types = this.context.getTypeRegistry();
			ITypeBindingRegistry bindings = this.context
					.getTypeBindingRegistry();

			assertTrue(types.getAll().containsKey(ConverterService.TYPE_PDF_ID));
			assertTrue(types.getAll()
					.containsKey(ConverterService.TYPE_TEXT_ID));

			assertEquals(TextType.type, types
					.get(ConverterService.TYPE_TEXT_ID));
			assertEquals(PdfType.type, types.get(ConverterService.TYPE_PDF_ID));

			assertTrue(bindings.getAll().containsKey(".txt"));
			assertTrue(bindings.getAll().containsKey(".pdf"));

			assertEquals(ConverterService.TYPE_PDF_ID, bindings.get(".pdf"));
			assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".txt"));

			Map<String, IConverter> converters = this.service
					.getRegisteredConverter();

			assertNotNull(converters);
			assertTrue(converters
					.containsKey(ConverterService.CONVERTER_PDF_TO_TEXT_ID));
			assertTrue(converters
					.get(ConverterService.CONVERTER_PDF_TO_TEXT_ID) instanceof PDFtoTxtConverter);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testRegisterDefaultConfiguration() {
		try {
			this.context.getConfigurationRegistry().get(
					ConverterService.CONVERTER_CONFIGURATION_ID);
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.service.registerDefaultConfiguration(this.context
					.getConfigurationRegistry());
			IConfiguration config = this.context.getConfigurationRegistry()
					.get(ConverterService.CONVERTER_CONFIGURATION_ID);
			assertNotNull(config);
			assertTrue(config instanceof DefaultConverterConfiguration);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterService.workingContextIsCompatible(Class)'
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
		assertFalse(this.service.workingContextIsCompatible(IContext.class));
		assertFalse(this.service.workingContextIsCompatible(NullContext.class));
		assertTrue(this.service
				.workingContextIsCompatible(SourceListContext.class));
	}

}
