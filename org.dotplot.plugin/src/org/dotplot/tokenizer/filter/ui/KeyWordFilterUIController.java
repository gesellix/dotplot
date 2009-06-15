/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
class KeyWordFilterUIController extends FilterViewController {

    /**
     * Creates a new <code>KeyWordFilterController</code>.
     * 
     * @param dotplotCreator
     * @param cv
     * @param ui
     */
    public KeyWordFilterUIController(ConfigurationView cv, KeyWordFilterUI ui) {
	super(cv, ui);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.ui.configuration.controller.ViewController#update(java.util
     * .Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
	KeyWordFilterUI ui = (KeyWordFilterUI) this.getFilterUI();

	if (o instanceof SelectFilterView) {
	    SelectFilterView view = (SelectFilterView) o;
	    IConfigurationRegistry registry = view.getRegistry();

	    IFilterConfiguration config;
	    try {
		config = (IFilterConfiguration) registry
			.get(FilterService.FILTER_CONFIGURATION_ID);
		config.getFilterList().remove(ui.getFilterID());

		Map<String, Object> parameter = new TreeMap<String, Object>();
		switch (ui.getKeywordFilterModus()) {
		case KeyWordFilter.LET_THROUGH_NO_KEYWORDS:
		    config.getFilterList().add(ui.getFilterID());
		    parameter.put(KeyWordFilter.PARAMETER_MODUS, new Integer(
			    KeyWordFilter.LET_THROUGH_NO_KEYWORDS));
		    break;

		case KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS:
		    config.getFilterList().add(ui.getFilterID());
		    parameter.put(KeyWordFilter.PARAMETER_MODUS, new Integer(
			    KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS));
		    break;
		}
		config.setFilterParameter(ui.getFilterID(), parameter);
	    } catch (UnknownIDException e) {
		// dann eben nicht
	    }
	}

    }

}
