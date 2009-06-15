/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import org.dotplot.core.ISourceType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public abstract class AbstractFilterUI implements IFilterUI {

    private String filterID;

    private boolean enabled;

    private String title;

    private String info;

    private ISourceType streamType;

    /**
     * Creates a new <code>AbstractFilterUI</code>.
     */
    public AbstractFilterUI(String title, String info) {
	super();
	this.title = title;
	this.info = info;
	this.enabled = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.IFilterUI#draw(org.eclipse.swt.widgets
     * .Composite, org.eclipse.swt.events.SelectionListener)
     */
    public final Control draw(Composite parent, SelectionListener changeListener) {
	Composite c = new Composite(parent, SWT.NONE);

	GridLayout layout = new GridLayout(2, false);
	c.setLayout(layout);

	GridData gd;
	Label l;

	l = new Label(c, SWT.WRAP);
	l.setText(this.info);
	gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
	gd.widthHint = 100;
	l.setLayoutData(gd);

	Group group = new Group(c, SWT.NONE);
	group.setText(this.title);
	group.setLayout(new FillLayout());
	gd = new GridData(GridData.FILL_BOTH);
	gd.horizontalIndent = 15;
	group.setLayoutData(gd);

	this.drawContent(group, changeListener);

	return c;
    }

    public abstract Control drawContent(Composite parent,
	    SelectionListener changeListener);

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ui.IFilterUI#getFilterID()
     */
    public String getFilterID() {
	return this.filterID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ui.IFilterUI#getSourceType()
     */
    public ISourceType getSourceType() {
	return this.streamType;
    }

    public String getTitle() {
	return this.title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ui.IFilterUI#isEnabled()
     */
    public boolean isEnabled() {
	return this.enabled;
    }

    public abstract void refresh();

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ui.IFilterUI#setEnabled(boolean)
     */
    public void setEnabled(boolean newState) {
	this.enabled = newState;
	this.refresh();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.IFilterUI#setFilterID(java.lang.String)
     */
    public void setFilterID(String filterID) {
	this.filterID = filterID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ui.IFilterUI#setSourceType(org.dotplot.core
     * .ISourceType)
     */
    public void setSourceType(ISourceType type) {
	this.streamType = type;
    }

}
