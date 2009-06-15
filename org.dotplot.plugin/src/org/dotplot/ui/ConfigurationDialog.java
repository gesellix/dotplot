/*
 * Created on 17.06.2004
 */
package org.dotplot.ui;

import java.util.Iterator;

import org.dotplot.core.IConfigurationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

/**
 * Der Rahmen für die Konfiguration. Die <code>ConfigurationViews</code> die in
 * der <code>GlobalConfiguration</code> angegeben sind, werden mit einem
 * Rahmenwerk versehen. Für jeden <code>ConfigurationView</code> wird ein
 * eigener Tab erzeugt, der über einen Apply und einen Reset Knopf verfügt.
 * Durch den Apply-Knopf werden die Observer der <code>ConfigurationView</code>
 * angestoßen, und durch den Reset Knopf, die <code>reset()</code> Methode
 * gestartet.
 * 
 * @author Christian gerhardt <case42@gmx.net>
 */
public class ConfigurationDialog {

    /**
	 * 
	 */
    private IConfigurationRegistry registry;

    /**
     * Listener for the <code>cancelbutton</code>.
     */
    private SelectionListener cancelListener;

    /**
     * Listener for the <code>plottbutton</code>
     */
    private SelectionListener plotListener;

    /**
     * Der Container fuer die einzelnen <code>ConfigurationViews</code>
     * 
     * @see org.dotplot.ui.ConfigurationView
     * @see org.dotplot.ui.ConfigurationViews
     */
    private ConfigurationViews cvs;

    /**
     * Erzeugt ein <code>ConfigurationFramework</code>.
     */
    public ConfigurationDialog(IConfigurationRegistry registry,
	    ConfigurationViews views) {
	if (registry == null) {
	    throw new NullPointerException();
	}
	this.registry = registry;

	cancelListener = new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		((Button) e.widget).getShell().dispose();
	    }
	};

	plotListener = new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		((Button) e.widget).getShell().dispose();
	    }
	};

	cvs = views;

	for (ConfigurationView view : cvs.values()) {
	    view.setRegistry(this.registry);
	}

    }

    /**
     * Draws the <code>ConfigurationFramework</code>. This method generates a
     * <code>TabFolder</code> for the associated <code>ConfigurationViews</code>
     * , in which each <code>ConfigurationView</code> is displayed in a single
     * <code>Tab</code>. <code>
     * Shell s = new Shell();
     * ConfigurationFramework framework = new ConfigurationFramework();
     * framework.draw(s);
	 * </code>
     * 
     * @param parent
     *            the parent Composite of the
     *            <code>ConfigurationFramework</code>
     */
    public void draw(Composite parent) {
	Composite co = new Composite(parent, SWT.NONE);
	Layout layout = new GridLayout(2, false);
	co.setLayout(layout);

	Label l1 = new Label(co, SWT.LEFT);
	l1.setText("Configure your dotpot");

	Color green = co.getDisplay().getSystemColor(SWT.COLOR_GREEN);
	Color yellow = co.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
	Color darkGreen = co.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN);

	final CTabFolder tf = new CTabFolder(co, SWT.BORDER);
	tf.setSimple(false);
	tf.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true, 2, 2));

	tf.setSelectionBackground(new Color[] { yellow, green, darkGreen },
		new int[] { 50, 80 }, true);

	// add configuration views as tabs
	Iterator iter = cvs.values().iterator();
	while (iter.hasNext()) {
	    this.drawTab(tf, (ConfigurationView) iter.next());
	}

	tf.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		if (e.item.getData() instanceof ConfigurationView) {
		    ((ConfigurationView) e.item.getData()).refresh();
		}
	    }
	});

	Composite c2 = new Composite(co, SWT.NONE);
	RowLayout rl = new RowLayout();
	rl.justify = true;
	rl.pack = false;
	c2.setLayout(rl);
	c2.setLayoutData(new GridData(SWT.END, SWT.NONE, false, false, 2, 1));

	Button btnPlot = new Button(c2, SWT.PUSH);
	btnPlot.setText("Plot");
	Button btnCancel = new Button(c2, SWT.PUSH);
	btnCancel.setText(" Cancel ");

	btnPlot.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		cvs.notifyObservers();
	    }
	});

	// this has to be added _after_ the SelectionListener for the
	// notification of the Oberservers
	// to let them apply changes made by the user before starting the plot.
	btnPlot.addSelectionListener(plotListener);
	btnCancel.addSelectionListener(cancelListener);

	co.pack(true);
    }

    /**
     * Draws a <code>Tab</code> for a <code>ConfigurationView</code>.
     * 
     * @param folder
     *            the tabfolder in which the folder should be displayed.
     * @param view
     *            the associated ConfigurationView
     */
    protected void drawTab(CTabFolder folder, ConfigurationView view) {
	Composite c1, c2, c3;
	CTabItem ti;

	ti = new CTabItem(folder, SWT.NULL);
	ti.setText(view.getName());
	ti.setData(view);

	c1 = new Composite(folder, SWT.NONE);
	c1.setLayout(new GridLayout(1, false));
	ti.setControl(c1);

	c2 = new Composite(c1, SWT.NONE);
	view.draw(c2);

	c2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	c3 = new Composite(c1, SWT.NONE);
	RowLayout rl = new RowLayout();
	rl.justify = true;
	rl.pack = false;
	c3.setLayout(rl);
	c3.setLayoutData(new GridData(SWT.END, SWT.NONE, false, false));

	Button b = new Button(c3, SWT.PUSH);
	b.setText("Apply");
	b.addSelectionListener(view.getApplyListener());

	b = new Button(c3, SWT.PUSH);
	b.setText(" Reset ");
	b.addSelectionListener(view.getResetListener());
    }

    /**
     * Gets the listener for the Cancelbutton.
     * 
     * @return the Listener
     * 
     * @see #setCancelListener(org.eclipse.swt.events.SelectionListener)
     */
    public SelectionListener getCancelListener() {
	return cancelListener;
    }

    /**
     * Gets the listener for the Plottbutton.
     * 
     * @return the listener
     * 
     * @see #setPlotListener(org.eclipse.swt.events.SelectionListener)
     */
    public SelectionListener getPlotListener() {
	return plotListener;
    }

    public IConfigurationRegistry getRegistry() {
	return this.registry;
    }

    /**
     * Sets a new listener for the Cancelbutton.
     * 
     * @param listener
     *            the new listener
     * 
     * @see #getCancelListener()
     */
    public void setCancelListener(SelectionListener listener) {
	cancelListener = listener;
    }

    /**
     * Sets a new Listener for the Plotbutton.
     * 
     * @param listener
     *            the new listener
     * 
     * @see #getPlotListener()
     */
    public void setPlotListener(SelectionListener listener) {
	plotListener = listener;
    }
}
