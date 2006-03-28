/**
 * 
 */
package org.dotplot.examples.FourGrammFilter;

import java.util.Observable;

import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.ui.FilterViewController;
import org.dotplot.tokenizer.filter.ui.IFilterUI;
import org.dotplot.tokenizer.filter.ui.SelectFilterView;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FourGrammFilterUIController extends FilterViewController{

	/**
	 * Creates a new <code>FourGrammFilterUIController</code>.
	 * @param cv
	 * @param ui
	 */
	public FourGrammFilterUIController(ConfigurationView cv, IFilterUI ui) {
		super(cv, ui);
		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.ui.ViewController#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		FourGrammFilterUI ui = (FourGrammFilterUI) this.getFilterUI();
		
		if (o instanceof SelectFilterView) {
			SelectFilterView view = (SelectFilterView) o;
			IConfigurationRegistry registry = view.getRegistry();

			IFilterConfiguration config;
			try {
				config = (IFilterConfiguration) registry
						.get(FilterService.ID_CONFIGURATION_FILTER);
				config.getFilterList().remove(ui.getFilterID());
				
				if(ui.getUseFilter()){
					config.getFilterList().add(ui.getFilterID());
				}
			}
			catch (UnknownIDException e) {
				//dann eben nicht
			}
		}
	}
	
}
