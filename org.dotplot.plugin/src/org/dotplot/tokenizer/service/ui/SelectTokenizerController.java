/*
 * Created on 27.05.2004
 */
package org.dotplot.tokenizer.service.ui;

import java.util.Observable;

import org.apache.log4j.Logger;
import org.dotplot.core.BaseType;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.tokenizer.converter.ConverterService;
import org.dotplot.tokenizer.converter.DefaultConverterConfiguration;
import org.dotplot.tokenizer.converter.IConverterConfiguration;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.service.DefaultTokenizerConfiguration;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.UnknownIDException;

/**
 * Controller for the TokenScanner view.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenizerController extends ViewController {

    /**
     * Der Logger zur Ausgabe von Debug-Informationen
     */
    private static Logger logger = Logger
	    .getLogger(SelectTokenizerController.class.getName());

    /**
     * creates the view controller.
     * 
     * @param cv
     *            the corresponding configuration view
     */
    public SelectTokenizerController(ConfigurationView cv) {
	super(cv);
    }

    @Override
    public void update(Observable o, Object arg) {
	if (o instanceof SelectTokenizerView) {
	    ITokenizerConfiguration tokenizerConfig = null;
	    IConverterConfiguration converterConfig = null;
	    IFilterConfiguration filterConfig = null;

	    SelectTokenizerView stsv = (SelectTokenizerView) o;

	    DotplotContext context = ContextFactory.getContext();

	    IConfigurationRegistry registry = stsv.getRegistry();

	    try {
		tokenizerConfig = (ITokenizerConfiguration) registry
			.get(TokenizerService.TOKENIZER_CONFIGURATION_ID);
		converterConfig = (IConverterConfiguration) registry
			.get(ConverterService.CONVERTER_CONFIGURATION_ID);
		filterConfig = (IFilterConfiguration) registry
			.get(FilterService.FILTER_CONFIGURATION_ID);
	    } catch (UnknownIDException e1) {
		tokenizerConfig = new DefaultTokenizerConfiguration();
		converterConfig = new DefaultConverterConfiguration();
		filterConfig = new DefaultFilterConfiguration();
	    }

	    String selected = stsv.getSelectedTokenizer();

	    if (!selected.equals(tokenizerConfig.getTokenizerID())) {
		// filterConfig resetten
		filterConfig.clear();
	    }

	    ITokenizer tokenizer = null;
	    try {
		tokenizerConfig.setTokenizerID(selected);
		TokenizerService service = (TokenizerService) context
			.getServiceRegistry().get(
				"org.dotplot.standard.Tokenizer");

		logger.debug("selecting tokenizer: " + selected);

		tokenizer = service.getRegisteredTokenizer().get(selected);
	    } catch (UnknownIDException e) {
		/* dann eben nicht */
	    }

	    converterConfig.setConvertFiles(stsv.getConvertFilesState());

	    if (stsv.getConvertFilesState()) {
		if (tokenizer != null) {
		    converterConfig.setTargetType(tokenizer.getStreamType());
		} else {
		    converterConfig.setTargetType(TextType.type);
		}
		converterConfig.setConverter(PdfType.type,
			ConverterService.CONVERTER_PDF_TO_TEXT_ID);
	    } else {
		converterConfig.setTargetType(BaseType.type);
		converterConfig.setConverter(PdfType.type, null);
	    }
	    converterConfig.setConvertedFilesDirectory(stsv
		    .getConversionDirectory());
	    converterConfig.setKeepConvertedFiles(stsv
		    .getKeepConvertedFilesState());
	}
    }
}
