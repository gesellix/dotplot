/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class GeneralTokenFilterUIController extends FilterViewController {

	/**
	 * Creates a new <code>GeneralTokenFilterUIController</code>.
	 * @param dotplotCreator
	 * @param cv
	 * @param ui
	 */
	public GeneralTokenFilterUIController(ConfigurationView cv, IFilterUI ui) {
		super(cv, ui);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.ui.configuration.controller.ViewController#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		GeneralTokenFilterUI ui = (GeneralTokenFilterUI) this.getFilterUI();

		if (o instanceof SelectFilterView) {
			SelectFilterView view = (SelectFilterView) o;
			IConfigurationRegistry registry = view.getRegistry();

			IFilterConfiguration config;
			try {
				config = (IFilterConfiguration) registry
						.get(FilterService.FILTER_CONFIGURATION_ID);
				config.getFilterList().remove(ui.getFilterID());				
				Map<String, Object> parameter = new TreeMap<String, Object>();
				int[] types = ui.getCheckedTypes();
				Collection<Integer> tokenTypes = new Vector<Integer>(types.length);
				for(int i = 0; i < types.length; i++){
					tokenTypes.add(new Integer(types[i]));
				}
				if(types.length > 0){
					config.getFilterList().add(ui.getFilterID());
				}
				parameter.put(GeneralTokenFilter.PARAM, tokenTypes);
				config.setFilterParameter(ui.getFilterID(), parameter);
			}
			catch (UnknownIDException e) {
				//dann eben nicht
			}
		}
	}

}
