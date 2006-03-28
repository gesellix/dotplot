/*
 * Created on 27.05.2004
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import org.dotplot.core.BaseType;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;

/**
 * the view for the filter settings.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectFilterView extends ConfigurationView {

	private Composite root;

	private List<IFilterUI> uis;

	public SelectFilterView() {
		this(new DotplotContext(".","."));
	}
	
	/**
	 * Creates a new <code>SelectTokenTypesView</code>.
	 * 
	 * @param dotplotter
	 *            the IGUIDotplotter
	 */
	public SelectFilterView(DotplotContext context) {
		super(context);
		this.setName("Filter settings");
		this.uis = new ArrayList<IFilterUI>();
		// try {
		// FilterService service = (FilterService) this.context
		// .getServiceRegistry().get("org.dotplot.standard.Filter");
		// this.uis = service.getUIs();
		//
		// for (IFilterUI ui : uis) {
		// Observer observer = ui.getController((DotplotCreator) this
		// .getDotplotter(), this);
		// if (observer != null) {
		// this.addObserver(observer);
		// }
		// }
		// }
		// catch (UnknownIDException e) {
		// // dann eben nicht
		// }
	}

	public void draw(Composite parent) {

		this.deleteObservers();

		IConfigurationRegistry registry = this.getRegistry();
		ISourceType streamType = BaseType.type;
		IFilterConfiguration filterConfig;
		try {
			filterConfig = (IFilterConfiguration) registry
					.get(FilterService.ID_CONFIGURATION_FILTER);
			ITokenizerConfiguration tokenizerConfig = (ITokenizerConfiguration) registry
					.get(TokenizerService.ID_CONFIGURATION_TOKENIZER);
			TokenizerService tokenService = (TokenizerService)this.context.getServiceRegistry().get("org.dotplot.standard.Tokenizer");
			ITokenizer tokenizer = tokenService.getRegisteredTokenizer().get(tokenizerConfig.getTokenizerID());
			if(tokenizer != null){
				streamType = tokenizer.getStreamType();
			}
			else {
				streamType = BaseType.type;
			}
		}
		catch (UnknownIDException e1) {
			// dann eben nicht
			filterConfig = new DefaultFilterConfiguration();
		}

		parent.setLayout(new FillLayout());

		final ScrolledComposite sc1 = new ScrolledComposite(parent,
				SWT.V_SCROLL);
		final Composite c1 = new Composite(sc1, SWT.NONE);
		sc1.setContent(c1);

		Control control;
		GridData gd;
		GridLayout layout = new GridLayout(1, false);
		c1.setLayout(layout);
		Label l;
		int i = 0;

		try {
			FilterService service = (FilterService) this.context
					.getServiceRegistry().get("org.dotplot.standard.Filter");
			List<IFilterUI> uis = service.getUIs();

			this.uis.clear();
			this.uis.addAll(uis);
			
			for (IFilterUI ui : uis) {
				if (ui.getSourceType().getClass().isAssignableFrom(streamType.getClass())) {
					Observer observer = ui.getController(this);
					if (observer != null) {
						this.addObserver(observer);
					}

					if (i > 0) {
						l = new Label(c1, SWT.SEPARATOR | SWT.HORIZONTAL);
						gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
						l.setLayoutData(gd);
					}
					control = ui.draw(c1, this.changeListener);
					control
							.setLayoutData(new GridData(
									GridData.FILL_HORIZONTAL));
					i++;
				}
				else {
					filterConfig.getFilterList().remove(ui.getFilterID());
					this.uis.remove(ui);
				}
			}
		}
		catch (UnknownIDException e) {
			// dann eben nicht
		}

		this.root = parent;

		c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void refresh() {
		if (root != null && !root.isDisposed()) {
			Control[] children = root.getChildren();

			for (int i = 0; i < children.length; i++) {
				children[i].dispose();
			}

			draw(this.root);
			this.reset();
			this.root.layout();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ui.configuration.views.ConfigurationView#reset()
	 */
	public void reset() {
		IFilterConfiguration filterConfig = null;

		try {
			filterConfig = (IFilterConfiguration) this.getRegistry().get(
					FilterService.ID_CONFIGURATION_FILTER);

			for (IFilterUI ui : this.uis) {
				ui.reset(filterConfig);
			}

		}
		catch (UnknownIDException e) {
			// very bad
		}
	}
}
