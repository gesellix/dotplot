/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Map;

import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.KeyWordFilter;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class KeyWordFilterUI extends AbstractFilterUI {

	private Scale keyWordFilterSettings;

	private static final String INFO = ""
			+ "Use this filter to plot none or only keywords "
			+ "of programming languages.";

	/**
	 * Creates a new <code>KeyWordFilterUI</code>.
	 */
	public KeyWordFilterUI() {
		super("Keywordfilter", INFO);
	}

	public int getKeywordFilterModus() {
		if (this.keyWordFilterSettings != null
				&& !this.keyWordFilterSettings.isDisposed()) {
			switch (this.keyWordFilterSettings.getSelection()) {
			case 0:
				return KeyWordFilter.LET_THROUGH_NO_KEYWORDS;

			case 1:
				return KeyWordFilter.LET_THROUGH_ALL;

			case 2:
				return KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS;

			default:
				return KeyWordFilter.LET_THROUGH_ALL;
			}
		}
		else {
			return KeyWordFilter.LET_THROUGH_ALL;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#draw(org.eclipse.swt.widgets.Composite)
	 */
	public Control drawContent(Composite parent,
			SelectionListener changeListener) {
		GridData gd;

		Composite ng = new Composite(parent, SWT.NONE);
		ng.setLayout(new GridLayout(3, true));

		gd = new GridData(SWT.LEAD, SWT.NONE, true, false, 1, 1);
		Label l1 = new Label(ng, SWT.NONE);
		l1.setText("none");
		l1.setToolTipText("no keyword will pass");
		l1.setLayoutData(gd);

		gd = new GridData(SWT.CENTER, SWT.NONE, true, false, 1, 1);
		Label l2 = new Label(ng, SWT.NONE);
		l2.setText("off");
		l2.setToolTipText("don't use the keywordfilter");
		l2.setLayoutData(gd);

		gd = new GridData(SWT.TRAIL, SWT.NONE, true, false, 1, 1);
		Label l3 = new Label(ng, SWT.NONE);
		l3.setText("only");
		l3.setToolTipText("only keywords will pass");
		l3.setLayoutData(gd);

		gd = new GridData(SWT.FILL, SWT.NONE, true, false, 3, 1);
		final Scale keyWordFilterSettings = new Scale(ng, SWT.HORIZONTAL);
		this.keyWordFilterSettings = keyWordFilterSettings;
		keyWordFilterSettings.setIncrement(1);
		keyWordFilterSettings.setPageIncrement(1);
		keyWordFilterSettings.setMinimum(0);
		keyWordFilterSettings.setMaximum(2);
		keyWordFilterSettings.setSelection(1);
		keyWordFilterSettings.setLayoutData(gd);
		keyWordFilterSettings.addSelectionListener(changeListener);
		keyWordFilterSettings.setEnabled(this.isEnabled());

		return ng;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#reset(org.dotplot.core.IConfigurationRegistry)
	 */
	public void reset(IFilterConfiguration config) {
		Map<String, ?> parameter;

		// standard einstellung
		this.keyWordFilterSettings.setSelection(1);

		if (config.getFilterList().contains(this.getFilterID())) {
			parameter = config.getFilterParameter(this.getFilterID());
			Object modus = parameter.get(KeyWordFilter.PARAMETER_MODUS);
			if (modus instanceof Integer) {
				switch (((Integer) modus).intValue()) {
				case KeyWordFilter.LET_THROUGH_ALL:
					this.keyWordFilterSettings.setSelection(1);
					break;

				case KeyWordFilter.LET_THROUGH_NO_KEYWORDS:
					this.keyWordFilterSettings.setSelection(0);
					break;

				case KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS:
					this.keyWordFilterSettings.setSelection(2);
					break;

				default:
					this.keyWordFilterSettings.setSelection(1);
				}
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#getController()
	 */
	public ViewController getController(ConfigurationView view) {
		return new KeyWordFilterUIController(view, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.filter.ui.AbstractFilterUI#refresh()
	 */
	public void refresh() {
		if (this.keyWordFilterSettings != null
				&& !this.keyWordFilterSettings.isDisposed()) {
			this.keyWordFilterSettings.setEnabled(this.isEnabled());
		}
	}

}
