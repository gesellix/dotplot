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
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
class LineFilterUIController extends FilterViewController {

	/**
	 * Creates a new <code>LineFilterUIController</code>.
	 * 
	 * @param dotplotCreator
	 * @param cv
	 * @param ui
	 */
	public LineFilterUIController(ConfigurationView cv, LineFilterUI ui) {
		super(cv, ui);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.configuration.controller.ViewController#update(java.util.Observable,
	 *      java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		LineFilterUI ui = (LineFilterUI)this.getFilterUI();

		if (o instanceof SelectFilterView) {
			SelectFilterView view = (SelectFilterView) o;
			IConfigurationRegistry registry = view.getRegistry();

			try {
				IFilterConfiguration config = (IFilterConfiguration) registry
						.get(FilterService.FILTER_CONFIGURATION_ID);

				 ;
				Map<String, Object> parameter;

				if (ui.getLineFilter()) {
					if(! config.getFilterList().contains(ui.getFilterID())){
						config.getFilterList().add(ui.getFilterID());
					}
					parameter = new TreeMap<String, Object>();
					
					if (ui.getFilterLines()) {
						parameter.put(LineFilter.PARAMETER_EMPTYLINES,
								new Boolean(true));
					}
					else {
						parameter.put(LineFilter.PARAMETER_EMPTYLINES,
								new Boolean(false));
					}
					config.setFilterParameter(ui.getFilterID(),
							parameter);
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
