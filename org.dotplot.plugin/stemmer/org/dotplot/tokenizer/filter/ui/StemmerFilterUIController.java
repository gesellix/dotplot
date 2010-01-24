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
import org.dotplot.tokenizer.filter.StemmerFilter;
import org.dotplot.tokenizer.filter.stemmingFilter.StemmerLanguage;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * @author n
 * 
 */
public class StemmerFilterUIController extends FilterViewController {

	/**
	 * @param cv
	 * @param ui
	 */
	public StemmerFilterUIController(ConfigurationView cv, IFilterUI ui) {
		super(cv, ui);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.ViewController#update(java.util.Observable,
	 * java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		StemmerFilterUI ui = (StemmerFilterUI) this.getFilterUI();

		if (o instanceof SelectFilterView) {
			SelectFilterView view = (SelectFilterView) o;
			IConfigurationRegistry registry = view.getRegistry();

			try {
				IFilterConfiguration config = (IFilterConfiguration) registry
						.get(FilterService.FILTER_CONFIGURATION_ID);

				;
				Map<String, Object> parameter;

				if (ui.isStemmerFilter()) {
					if (!config.getFilterList().contains(ui.getFilterID())) {
						config.getFilterList().add(ui.getFilterID());
						parameter = new TreeMap<String, Object>();

						String stemmerType = ui.getSelectedStemmer();
						StemmerLanguage stemmerLanguage = ui
								.getSelectedLanguage();
						if (stemmerType != null) {
							parameter.put(StemmerFilter.STEMMER_TYPE,
									stemmerType);
						}
						if (stemmerLanguage != null) {
							parameter.put(StemmerFilter.STEMMER_LANGUAGE,
									stemmerLanguage);
						}
						config.setFilterParameter(ui.getFilterID(), parameter);
					}
				}
				else {
					config.getFilterList().remove(ui.getFilterID());
				}
			}
			catch (UnknownIDException e) {
				// very bad
			}

		}
	}

}
