/**
 * 
 */
package org.dotplot.examples.FourGrammFilter;

import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.ui.AbstractFilterUI;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class FourGrammFilterUI extends AbstractFilterUI {

    public static final String INFO = "A 4-Gramm filter combines "
	    + "four successive tokens to a single token.";

    private Button checkBox;

    /**
     * Creates a new <code>FourGrammFilterUI</code>.
     * 
     * @param title
     * @param info
     */
    public FourGrammFilterUI() {
	super("4-Gramms", INFO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.AbstractFilterUI#drawContent(org.eclipse
     * .swt.widgets.Composite, org.eclipse.swt.events.SelectionListener)
     */
    @Override
    public Control drawContent(Composite parent,
	    SelectionListener changeListener) {
	Composite c = new Composite(parent, SWT.NONE);
	c.setLayout(new RowLayout());

	final Button b4Gramms = new Button(c, SWT.CHECK);
	this.checkBox = b4Gramms;

	b4Gramms.setText("use 4-Gramm Filter");
	b4Gramms.addSelectionListener(changeListener);

	return c;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.IFilterUI#getController(org.dotplot.ui
     * .ConfigurationView)
     */
    public ViewController getController(ConfigurationView view) {
	return new FourGrammFilterUIController(view, this);
    }

    public boolean getUseFilter() {
	if (this.checkBox != null && !this.checkBox.isDisposed()) {
	    return this.checkBox.getSelection();
	} else {
	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ui.AbstractFilterUI#refresh()
     */
    @Override
    public void refresh() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.IFilterUI#reset(org.dotplot.tokenizer
     * .filter.IFilterConfiguration)
     */
    public void reset(IFilterConfiguration config) {
	this.checkBox.setSelection(false);

	if (config.getFilterList().contains(this.getFilterID())) {
	    this.checkBox.setSelection(true);
	}
    }

}
