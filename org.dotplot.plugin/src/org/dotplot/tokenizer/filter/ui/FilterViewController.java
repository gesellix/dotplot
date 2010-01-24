/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public abstract class FilterViewController extends ViewController {

	private IFilterUI ui;

	/**
	 * Creates a new <code>FilterViewController</code>.
	 * 
	 * @param dotplotCreator
	 * @param cv
	 */
	public FilterViewController(ConfigurationView cv, IFilterUI ui) {
		super(cv);
		this.ui = ui;
	}

	public IFilterUI getFilterUI() {
		return this.ui;
	}

}
