/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.filter.GeneralTokenFilter;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class GeneralTokenFilterUI extends AbstractFilterUI {

    /**
     * a checkbox for the handling of token types.
     * 
     * @author Christian Gerhardt <case42@gmx.net>
     * @version 1.0, 27.6.04
     */
    private class TokenTypesCheckBox extends Composite {
	private int tokenType;

	private Button button;

	/**
	 * create the checkbox.
	 * 
	 * @param parent
	 *            parent composite
	 * @param tokenType
	 *            token type to be used
	 * @param name
	 *            the name
	 */
	public TokenTypesCheckBox(Composite parent, int tokenType, String name) {
	    super(parent, SWT.NONE);
	    this.tokenType = tokenType;
	    this.setLayout(new FillLayout());
	    this.button = new Button(this, SWT.CHECK);
	    this.button.setText(name);
	    this.button.setToolTipText("filter this token");
	}

	public void addSelectionListener(SelectionListener s) {
	    this.button.addSelectionListener(s);
	}

	public boolean getSelection() {
	    return this.button.getSelection();
	}

	public String getTokenName() {
	    return this.button.getText();
	}

	public int getType() {
	    return this.tokenType;
	}

	public void setSelection(boolean set) {
	    this.button.setSelection(set);
	}
    }

    private ConfigurationView view;

    private TokenType tokentypes[];

    private TokenTypesCheckBox checkBoxes[];

    public static final String INFO = ""
	    + "Use this filter to select tokentypes to be filtered.";

    /**
     * Creates a new <code>GeneralTokenFilterUI</code>.
     * 
     * @param title
     * @param info
     */
    public GeneralTokenFilterUI() {
	super("Tokentypes", INFO);

    }

    private int countCheckedTypes() {
	int count = 0;
	for (int i = 0; i < this.checkBoxes.length; i++) {
	    if (this.checkBoxes[i].getSelection()) {
		count++;
	    }
	}
	return count;
    }

    /**
     * @param parent
     *            - the parent Composite of this group
     * @param name
     *            - the name of this group
     * @param tokenKind
     *            - the tokenkind which is represented by this group
     */
    private void createGroup(Composite parent, String name, int tokenKind,
	    SelectionListener changeListener) {
	Group g = null;
	boolean first = true;

	for (int i = 0; i < this.tokentypes.length; i++) {
	    if (this.tokentypes[i].getKind() == tokenKind) {
		if (first) {
		    g = new Group(parent, SWT.NONE);
		    g.setText(name);

		    GridData gd = new GridData(SWT.FILL);
		    gd.widthHint = 300;
		    g.setLayoutData(gd);

		    RowLayout layout = new RowLayout();
		    layout.pack = false;
		    g.setLayout(layout);
		    first = false;
		}

		TokenTypesCheckBox t = new TokenTypesCheckBox(g,
			this.tokentypes[i].getType(), this.tokentypes[i]
				.getName());

		t.addSelectionListener(changeListener);
		this.checkBoxes[i] = t;
	    }
	}
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
	this.tokentypes = this.getTokenTypes();

	Composite c = new Composite(parent, SWT.NONE);
	c.setLayout(new FillLayout());

	this.drawTypeList(c, changeListener);

	return c;
    }

    protected void drawTypeList(Composite parent,
	    SelectionListener changeListener) {
	parent.setLayout(new GridLayout(1, true));

	this.checkBoxes = new TokenTypesCheckBox[this.tokentypes.length];

	this.createGroup(parent, "keywords", TokenType.KIND_KEYWORD,
		changeListener);
	this.createGroup(parent, "operators", TokenType.KIND_OPERATOR,
		changeListener);
	this.createGroup(parent, "punctuation marks",
		TokenType.KIND_PUNCTUATION_MARK, changeListener);
	this.createGroup(parent, "other tokens", TokenType.KIND_OTHER,
		changeListener);
    }

    /**
     * returns the selected types.
     * 
     * @return the selection
     */
    public int[] getCheckedTypes() {
	int[] checkedTypes = new int[this.countCheckedTypes()];
	int j = 0;
	for (int i = 0; i < this.checkBoxes.length; i++) {
	    if (this.checkBoxes[i].getSelection()) {
		checkedTypes[j] = this.checkBoxes[i].getType();
		j++;
	    }
	}
	return checkedTypes;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.dotplot.tokenizer.filter.ui.IFilterUI#getController(org.dotplot.
     * DotplotCreator, org.dotplot.ui.configuration.views.ConfigurationView)
     */
    public ViewController getController(ConfigurationView view) {
	this.view = view;
	return new GeneralTokenFilterUIController(view, this);
    }

    public TokenType[] getTokenTypes() {
	if (this.view != null) {
	    try {
		DotplotContext context = ContextFactory.getContext();
		ITokenizerConfiguration config = (ITokenizerConfiguration) this.view
			.getRegistry().get(
				TokenizerService.TOKENIZER_CONFIGURATION_ID);
		TokenizerService service = (TokenizerService) context
			.getServiceRegistry().get(
				"org.dotplot.standard.Tokenizer");
		ITokenizer tokenizer = service.getRegisteredTokenizer().get(
			config.getTokenizerID());
		return tokenizer.getTokenTypes();
	    } catch (UnknownIDException e) {
		// hmm, pech
	    } catch (NullPointerException e) {
		// auch pech
	    }
	}
	return new TokenType[0];
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
	Map<String, ?> parameter;

	// standard einstellung
	for (int i = 0; i < this.checkBoxes.length; i++) {
	    this.checkBoxes[i].setSelection(false);
	}
	if (config.getFilterList().contains(this.getFilterID())) {
	    parameter = config.getFilterParameter(this.getFilterID());
	    Object param = parameter.get(GeneralTokenFilter.PARAM);
	    if (param != null) {
		Collection<Integer> tokens = (Collection<Integer>) param;
		for (int i = 0; i < this.checkBoxes.length; i++) {
		    if (tokens.contains(this.checkBoxes[i].getType())) {
			this.checkBoxes[i].setSelection(true);
		    }
		}
	    }
	}
    }
}
