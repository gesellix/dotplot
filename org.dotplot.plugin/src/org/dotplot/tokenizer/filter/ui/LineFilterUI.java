/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Map;

import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.LineFilter;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class LineFilterUI extends AbstractFilterUI {
	
	private Button lineCheckBox; 
	private Button filterLinesCheckBox ;
	
	private static final String INFO = "" +
			"Use this filter to merge the tokens of one line " +
			"of text to a single token.";
	
	/**
	 * Creates a new <code>LineFilterUI</code>.
	 */
	public LineFilterUI() {
		super("Linefilter", INFO);
	}
	
	public boolean getLineFilter(){
		if(this.lineCheckBox != null && ! this.lineCheckBox.isDisposed()){
			return this.lineCheckBox.getSelection();
		}
		else {
			return false;
		}
	}
	
	public boolean getFilterLines(){
		if(this.filterLinesCheckBox != null && ! this.filterLinesCheckBox.isDisposed()){
			return this.filterLinesCheckBox.getSelection();
		}
		else {
			return false;
		}		
	}
	
	public void refresh(){
		if(this.lineCheckBox != null && ! this.lineCheckBox.isDisposed()){
			this.lineCheckBox.setEnabled(this.isEnabled());
			if(this.isEnabled() && this.lineCheckBox.getSelection()){
				this.filterLinesCheckBox.setEnabled(true);
			}
			else {
				this.filterLinesCheckBox.setEnabled(false);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#draw(org.eclipse.swt.widgets.Composite, org.eclipse.swt.events.SelectionListener)
	 */
	public Control drawContent(Composite parent, SelectionListener changeListener) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new RowLayout());
		
		final Button bLines = new Button(c, SWT.CHECK);
		this.lineCheckBox = bLines;

		final Button bFilterLines = new Button(c, SWT.CHECK);
		this.filterLinesCheckBox = bFilterLines;
		
		bLines.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (bLines.getSelection()) {
					bFilterLines.setEnabled(true);
				}
				else {
					bFilterLines.setEnabled(false);
					bFilterLines.setSelection(false);
				}
			}
		});
		
		bLines.setText("use LineFilter");
		bLines.addSelectionListener(changeListener);
		
		bLines.setToolTipText("concatinates all tokens in a line");

		bFilterLines.setText("ignore empty lines");
		bFilterLines.addSelectionListener(changeListener);
		bFilterLines
				.setToolTipText("all empty lines will be ignored for the dotplot");
		
		this.refresh();
		
		return c;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#reset(org.dotplot.tokenizer.filter.IFilterConfiguration)
	 */
	public void reset(IFilterConfiguration config) {
		//standard einstellungen
		this.filterLinesCheckBox.setSelection(false);
		this.filterLinesCheckBox.setEnabled(false);
		this.lineCheckBox.setSelection(false);
		
		Map<String, ?> parameter;
		if(config.getFilterList().contains(this.getFilterID())){
			parameter = config.getFilterParameter(this.getFilterID());
			this.lineCheckBox.setSelection(true);
			this.filterLinesCheckBox.setEnabled(true);
			Object emptyLines = parameter.get(LineFilter.PARAMETER_EMPTYLINES);
			if(emptyLines instanceof Boolean){
				this.filterLinesCheckBox.setSelection(((Boolean)emptyLines).booleanValue());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#getController(org.dotplot.DotplotCreator, org.dotplot.ui.configuration.views.ConfigurationView)
	 */
	public ViewController getController(ConfigurationView view) {
		return new LineFilterUIController(view, this);
	}
}
