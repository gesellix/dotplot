/**
 * 
 */
package org.dotplot.core.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.ISourceType;
import org.dotplot.core.ITypeBindingRegistry;
import org.dotplot.core.ITypeRegistry;
import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.IPluginRegistry;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.system.CoreSystem;
import org.dotplot.eclipse.EclipseUIService;
import org.dotplot.tokenizer.converter.ConverterService;
import org.dotplot.tokenizer.converter.IConverter;
import org.dotplot.tokenizer.converter.PDFtoTxtConverter;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.ITokenFilter;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.service.CPlusPlusScanner;
import org.dotplot.tokenizer.service.CPlusPlusType;
import org.dotplot.tokenizer.service.CScanner;
import org.dotplot.tokenizer.service.CType;
import org.dotplot.tokenizer.service.DefaultScanner;
import org.dotplot.tokenizer.service.HTMLType;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.JavaScanner;
import org.dotplot.tokenizer.service.JavaType;
import org.dotplot.tokenizer.service.PHPScanner;
import org.dotplot.tokenizer.service.PHPType;
import org.dotplot.tokenizer.service.TextScanner;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.tokenizer.service.XMLType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class ContextFactoryTest extends TestCase {

    private File workingDir = new File(".");

    private File pluginDir = new File("./plugins");

    /*
     * Test method for 'org.dotplot.core.ContextFactory.getContext()'
     */
    public void testGetContext() {
	ContextFactory.setShemaFile("./ressources/dotplotschema.xsd");
	DotplotContext context = ContextFactory.getContext();
	assertNotNull(context);
	assertSame(context, ContextFactory.getContext());
	assertSame(context, ContextFactory.getContext());

	try {
	    assertEquals(workingDir.getCanonicalPath(), context
		    .getWorkingDirectory());

	    assertEquals(pluginDir.getCanonicalPath(), context
		    .getPluginDirectory());
	} catch (IOException e1) {
	    fail("unexpected Exception");
	}
	IPluginRegistry<?> plugins = context.getPluginRegistry();
	assertNotNull(plugins);
	assertEquals(3, plugins.getAll().size());
	assertTrue(plugins.getAll().containsKey(CoreSystem.CORE_SYSTEM_ID));
	assertTrue(plugins.getAll().containsKey("org.dotplot.core.Standard"));
	assertTrue(plugins.getAll().containsKey("org.dotplot.examples"));

	IServiceRegistry services = context.getServiceRegistry();
	assertNotNull(services);
	assertEquals(9, services.getAll().size());
	assertTrue(services.getAll().containsKey(CoreSystem.SERVICE_LOADER_ID));
	assertTrue(services.getAll().containsKey(
		CoreSystem.SERVICE_INTEGRATOR_ID));
	assertTrue(services.getAll().containsKey(
		CoreSystem.SERVICE_INITIALIZER_ID));
	assertTrue(services.getAll().containsKey(
		"org.dotplot.standard.Tokenizer"));
	assertTrue(services.getAll().containsKey("org.dotplot.standard.Filter"));
	assertTrue(services.getAll().containsKey(
		"org.dotplot.standard.Converter"));
	assertTrue(services.getAll().containsKey(
		"org.dotplot.standard.Converter"));
	assertTrue(services.getAll()
		.containsKey("org.dotplot.standard.FMatrix"));
	assertTrue(services.getAll().containsKey("org.dotplot.standard.QImage"));
	assertTrue(services.getAll().containsKey(
		"org.dotplot.standard.EclipseUI"));

	assertNotNull(context.getGuiServiceID());
	assertEquals("org.dotplot.standard.EclipseUI", context
		.getGuiServiceID());
	try {
	    assertNotNull(context.getGuiService());
	    assertTrue(context.getGuiService() instanceof EclipseUIService);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

	IJobRegistry jobs = context.getJobRegistry();
	assertNotNull(jobs);
	assertEquals(6, jobs.getAll().size());
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_PLUGIN_LOADER_ID));
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_SHUTDOWN_ID));
	assertTrue(jobs.getAll().containsKey(CoreSystem.JOB_STARTUP_ID));
	assertTrue(jobs.getAll().containsKey("org.dotplot.jobs.PlotterJob"));
	assertTrue(jobs.getAll().containsKey("org.dotplot.jobs.ImagerJob"));
	assertTrue(jobs.getAll().containsKey("org.dotplot.jobs.TestJob"));

	try {
	    TokenizerService tokenizerService = (TokenizerService) services
		    .get("org.dotplot.standard.Tokenizer");
	    Map<String, ITokenizer> tokenizers = tokenizerService
		    .getRegisteredTokenizer();
	    assertEquals(6, tokenizers.size());
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.JavaTokenizer") instanceof JavaScanner);
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.PHPTokenizer") instanceof PHPScanner);
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.CTokenizer") instanceof CScanner);
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.CPPTokenizer") instanceof CPlusPlusScanner);
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.TextTokenizer") instanceof TextScanner);
	    assertTrue(tokenizers.get("org.dotplot.tokenizer.DefaultTokenizer") instanceof DefaultScanner);

	    FilterService filterService = (FilterService) services
		    .get("org.dotplot.standard.Filter");
	    Map<String, ITokenFilter> filters = filterService
		    .getRegisteredFilters();
	    assertEquals(5, filters.size());
	    assertTrue(filters.get("org.dotplot.filter.GeneralFilter") instanceof GeneralTokenFilter);
	    assertTrue(filters.get("org.dotplot.filter.KeyWordFilter") instanceof KeyWordFilter);
	    assertTrue(filters.get("org.dotplot.filter.LineFilter") instanceof LineFilter);
	    assertTrue(filters.get("org.dotplot.filter.SentenceFilter") instanceof SentenceFilter);
	    assertTrue(filters.get("org.dotplot.filter.4GrammFilter") instanceof org.dotplot.examples.FourGrammFilter.FourGrammFilter);

	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

	ITypeRegistry typeRegistry = context.getTypeRegistry();
	Map<String, ISourceType> types = typeRegistry.getAll();
	assertEquals(8, types.size());
	assertEquals(TextType.type, types.get(ConverterService.TYPE_TEXT_ID));
	assertEquals(PdfType.type, types.get(ConverterService.TYPE_PDF_ID));
	assertEquals(JavaType.type, types.get("org.dotplot.types.Text.Java"));
	assertEquals(CType.type, types.get("org.dotplot.types.Text.C"));
	assertEquals(XMLType.type, types.get("org.dotplot.types.Text.XML"));
	assertEquals(HTMLType.type, types.get("org.dotplot.types.Text.HTML"));
	assertEquals(CPlusPlusType.type, types
		.get("org.dotplot.types.Text.C++"));
	assertEquals(PHPType.type, types.get("org.dotplot.types.Text.PHP"));

	ITypeBindingRegistry bindingRegistry = context.getTypeBindingRegistry();
	Map<String, String> bindings = bindingRegistry.getAll();
	assertEquals(25, bindings.size());
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".txt"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".log"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".ini"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".conf"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".properties"));
	assertEquals("org.dotplot.types.Text.XML", bindings.get(".xml"));
	assertEquals("org.dotplot.types.Text.HTML", bindings.get(".htm"));
	assertEquals("org.dotplot.types.Text.HTML", bindings.get(".html"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".py"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".css"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".cs"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".hpp"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".csv"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".doc"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".rtf"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".tex"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".js"));
	assertEquals(ConverterService.TYPE_TEXT_ID, bindings.get(".cfg"));
	assertEquals("org.dotplot.types.Text.Java", bindings.get(".java"));
	assertEquals("org.dotplot.types.Text.C", bindings.get(".c"));
	assertEquals("org.dotplot.types.Text.C", bindings.get(".cc"));
	assertEquals("org.dotplot.types.Text.C", bindings.get(".h"));
	assertEquals("org.dotplot.types.Text.C++", bindings.get(".cpp"));
	assertEquals("org.dotplot.types.Text.PHP", bindings.get(".php"));
	assertEquals(ConverterService.TYPE_PDF_ID, bindings.get(".pdf"));

	try {
	    ConverterService converterService = (ConverterService) services
		    .get("org.dotplot.standard.Converter");
	    Map<String, IConverter> converters = converterService
		    .getRegisteredConverter();
	    assertNotNull(converters);
	    assertEquals(1, converters.size());
	    assertTrue(converters
		    .containsKey(ConverterService.CONVERTER_PDF_TO_TEXT_ID));
	    assertTrue(converters
		    .get(ConverterService.CONVERTER_PDF_TO_TEXT_ID) instanceof PDFtoTxtConverter);

	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

}
