/**
 * 
 */
package org.dotplot.tokenizer.filter.ui;

import java.util.Map;
import java.util.Vector;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.tokenizer.filter.SentenceFilter;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class SentenceFilterUI extends AbstractFilterUI {

	public static final String INFO = "" +
			"Choose a token to mark the end of a sentence. " +
			"All tokens till the occurance of this token are concatinated " +
			"to be treated as a single token.";
	
	private ConfigurationView view;
	private Button sentenceCheckBox;
	private Combo punctuationCombo;
	
	/**
	 * Creates a new <code>SentenceFilterUI</code>.
	 * @param title
	 * @param info
	 */
	public SentenceFilterUI() {
		super("Sentence Filter", INFO);

	}

	public boolean isSentenceFilter(){
		if(this.sentenceCheckBox != null && !this.sentenceCheckBox.isDisposed()){
			return this.sentenceCheckBox.getSelection();
		}
		else {
			return false;
		}
	}
	
	public TokenType getSelectedPunktuationMark(){		
		if(this.punctuationCombo != null && ! this.punctuationCombo.isDisposed()){
			TokenType[] types = this.getTokenTypes();
			String mark = this.punctuationCombo.getText().trim(); 
			for(int i = 0; i < types.length; i++){
				if(mark.equals(types[i].getName())){
					return types[i];
				}
			}
		}
		return null;
	}
	
	public TokenType[] getTokenTypes(){
		if(this.view != null){
			try {
				DotplotContext context = ContextFactory.getContext();
				ITokenizerConfiguration config = (ITokenizerConfiguration)this.view.getRegistry().get(TokenizerService.TOKENIZER_CONFIGURATION_ID);
				TokenizerService service = (TokenizerService)context.getServiceRegistry().get("org.dotplot.standard.Tokenizer");
				ITokenizer tokenizer = service.getRegisteredTokenizer().get(config.getTokenizerID());
				return tokenizer.getTokenTypes();
			}
			catch (UnknownIDException e) {
				//hmm, pech
			}
			catch (NullPointerException e ){
				//auch pech
			}
		}
		return new TokenType[0];
	}
	
	protected String[] getPunktuationMarks() {
		Vector<String> v = new Vector<String>();
		
		TokenType[] tokenTypes = this.getTokenTypes();
		
		for (int i = 0; i < tokenTypes.length; i++) {
			if (tokenTypes[i].getKind() == TokenType.KIND_PUNCTUATION_MARK) {
				v.add( tokenTypes[i].getName() + "  ");
			}
		}

		return v.toArray(new String[0]);
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.AbstractFilterUI#refresh()
	 */
	@Override
	public void refresh() {
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.AbstractFilterUI#drawContent(org.eclipse.swt.widgets.Composite, org.eclipse.swt.events.SelectionListener)
	 */
	@Override
	public Control drawContent(Composite parent,
			SelectionListener changeListener) {
		Composite c = new Composite(parent, SWT.NONE);
		RowLayout layout = new RowLayout();
		c.setLayout(layout);
		
		final Button bSentences = new Button(c, SWT.CHECK);
		this.sentenceCheckBox = bSentences;

		final Combo cSelectMark = new Combo(c, SWT.READ_ONLY | SWT.DROP_DOWN);
		this.punctuationCombo = cSelectMark;
		cSelectMark.setToolTipText("choose an end of sentence token");

		SelectionListener filterListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				if (source.getSelection()) {
					cSelectMark.setEnabled(true);
				}
				else {
					cSelectMark.setEnabled(false);
					cSelectMark.clearSelection();
					cSelectMark.deselectAll();
				}
				
			}
		};
		
		bSentences.setText("filter sentences");
		String[] marks = this.getPunktuationMarks();
		bSentences
				.setToolTipText("activate the sentence filter");
		
		if (marks.length > 0) {
			bSentences.addSelectionListener(changeListener);
			bSentences.addSelectionListener(filterListener);
			cSelectMark.setItems(marks);
			cSelectMark.addSelectionListener(changeListener);
		}
		else {
			bSentences.setEnabled(false);
			cSelectMark.setEnabled(false);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#reset(org.dotplot.tokenizer.filter.IFilterConfiguration)
	 */
	public void reset(IFilterConfiguration config) {
		if(config.getFilterList().contains(this.getFilterID())){
			this.sentenceCheckBox.setSelection(true);
			this.punctuationCombo.setEnabled(true);
			
			Map<String, ? extends Object> parameter = config.getFilterParameter(this.getFilterID());
			Object value = parameter.get(SentenceFilter.PARAM_TOKEN_VALUE);
			if(value != null){
				String items[] = this.punctuationCombo.getItems();
				for(int i = 0 ; i< items.length; i++){
					if(value.equals(items[i].trim())){
						this.punctuationCombo.select(i);
						return;
					}
				}
			}
		}
		else {
			this.sentenceCheckBox.setSelection(false);			
			this.punctuationCombo.setEnabled(false);
		}
		this.punctuationCombo.clearSelection();
		this.punctuationCombo.deselectAll();

	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.filter.ui.IFilterUI#getController(org.dotplot.DotplotCreator, org.dotplot.ui.configuration.views.ConfigurationView)
	 */
	public ViewController getController(ConfigurationView view) {
		this.view = view;
		return new SentenceFilterController(view, this);
	}

}
