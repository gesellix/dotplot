/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface IFilterUI {
	public void setFilterID(String filterID);
	public String getFilterID();
	public Control draw(Composite parent, SelectionListener changeListener);
	public void reset(IFilterConfiguration config);
	public ViewController getController(ConfigurationView view);
	public boolean isEnabled();
	public void setEnabled(boolean newState);
	public ISourceType getSourceType();
	public void setSourceType(ISourceType type);
}
