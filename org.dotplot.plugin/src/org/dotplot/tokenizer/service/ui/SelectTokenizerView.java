/*
 * Created on 27.05.2004
 */
package org.dotplot.tokenizer.service.ui;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dotplot.core.BaseType;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.converter.ConverterService;
import org.dotplot.tokenizer.converter.DefaultConverterConfiguration;
import org.dotplot.tokenizer.converter.IConverterConfiguration;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.DefaultTokenizerConfiguration;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

// import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * the configuration view for the scanner settings.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenizerView extends ConfigurationView {

	/**
	 * Der Logger zur Ausgabe von Debug-Informationen
	 */
	private static Logger logger = Logger.getLogger(SelectTokenizerView.class
			.getName());

	private String selectedTokenizer;

	private Combo tokenizerSelecter;

	private boolean convertFiles;

	private boolean keepConvertedFiles;

	private String conversionDirectory;

	private Button convertFilesButton;

	private Button keepConvertedFilesButton;

	private Button browseButton;

	private Text conversionDirectoryText;

	private DirectoryDialog fileBrowser;

	public SelectTokenizerView() {
		this(new DotplotContext(".", "."));
	}

	/**
	 * creates the view.
	 * 
	 * @param dotplotter
	 *            the IGUIDotplotter
	 */
	public SelectTokenizerView(DotplotContext context) {
		super(context);
		this.setName("Tokenizer settings");
		this.selectedTokenizer = TokenizerService.DEFAULT_TOKENIZER_ID;
		this.conversionDirectory = new String(".");
		this.keepConvertedFiles = false;
		this.convertFiles = false;
	}

	private void createConverterSettings(Composite parent) {
		GridData gd;
		final SelectionListener sl = this.changeListener;

		parent.setLayout(new GridLayout(3, false));

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 3;
		this.convertFilesButton = new Button(parent, SWT.CHECK);
		this.convertFilesButton.setText("convert pdf - files");
		this.convertFilesButton
				.setToolTipText("pdf-files will be converted into a readable format of the dotplotter");
		this.convertFilesButton.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_END
				| GridData.VERTICAL_ALIGN_CENTER);
		Label l = new Label(parent, SWT.NONE);
		l.setText("Directory:");
		l.setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.conversionDirectoryText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		this.conversionDirectoryText
				.setToolTipText("Directory to store converted files.");
		this.conversionDirectoryText.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		this.browseButton = new Button(parent, SWT.PUSH);
		this.browseButton.setText("Browse");
		this.browseButton.setLayoutData(gd);

		// filling Label
		l = new Label(parent, SWT.NONE);

		gd = new GridData();
		gd.horizontalSpan = 2;
		this.keepConvertedFilesButton = new Button(parent, SWT.CHECK);
		this.keepConvertedFilesButton.setText("keep converted files");
		this.keepConvertedFilesButton
				.setToolTipText("converted files will not be deleted after generating the dotplot");

		final Button kcfb = this.keepConvertedFilesButton;
		final Text cdt = this.conversionDirectoryText;
		final Button browse = this.browseButton;
		final DirectoryDialog browser = this.fileBrowser;
		final SelectTokenizerView stsv = this;

		// listeners
		this.convertFilesButton.addSelectionListener(this.changeListener);
		this.convertFilesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean set = ((Button) e.widget).getSelection();
				stsv.convertFiles = set;
				browse.setEnabled(set);
				kcfb.setEnabled(set);
				cdt.setEnabled(set);
				updateTokenizerSelecter();
				tokenizerSelecter.select(0);
			}
		});

		this.keepConvertedFilesButton.addSelectionListener(this.changeListener);
		this.keepConvertedFilesButton
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						stsv.keepConvertedFiles = kcfb.getSelection();
					}
				});

		this.conversionDirectoryText.addSelectionListener(this.changeListener);
		this.conversionDirectoryText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				sl.widgetSelected(null);
			}
		});

		this.browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setFilterPath(cdt.getText());
				browser.open();
				cdt.setText(browser.getFilterPath());
			}
		});
	}

	private Composite createGroup(Composite parent, String name) {
		Group g = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		g.setText(name);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		g.setLayoutData(gd);
		g.setLayout(new GridLayout(2, false));
		return g;
	}

	private void createSelectScannerComboBox(Composite parent) {
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER
				| GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalAlignment = 3;
		Label l = new Label(parent, SWT.NONE);
		l.setText("selected Scanner:");
		l.setLayoutData(gd);

		Combo selectScannerComboBox = new Combo(parent, SWT.BORDER
				| SWT.READ_ONLY | SWT.FLAT | SWT.DROP_DOWN);
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER
				| GridData.HORIZONTAL_ALIGN_BEGINNING);
		selectScannerComboBox.setLayoutData(gd);

		selectScannerComboBox.addSelectionListener(this.changeListener);
		selectScannerComboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.getSource();
				String key = combo.getText();
				String id = (String) combo.getData(key);
				selectedTokenizer = id;
				logger.debug("selected tokenizer = " + selectedTokenizer);
			}
		});
		this.tokenizerSelecter = selectScannerComboBox;
	}

	private ISourceType deriveSourceType() {
		ISourceType current;
		List<ISourceType> types = new Vector<ISourceType>();
		for (IPlotSource st : context.getSourceList()) {
			current = st.getType();
			if (current instanceof PdfType) {
				if (this.convertFilesButton.getSelection()) {
					types.add(TextType.type);
				}
			}
			else {
				types.add(st.getType());
			}
		}

		ISourceType type = BaseType.deriveCommonSourceType(types);
		return type == null ? BaseType.type : type;
	}

	/**
	 * @see org.dotplot.ui.ConfigurationView#draw(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void draw(Composite parent) {
		this.deleteObservers();
		this.addObserver(new SelectTokenizerController(this));

		this.fileBrowser = new DirectoryDialog(parent.getShell(), SWT.OPEN);
		this.fileBrowser
				.setMessage("Choose a directory to store your converted files.");
		parent.setLayout(new GridLayout());
		this.createSelectScannerComboBox(this.createGroup(parent,
				"Scannerselection"));
		this.createConverterSettings(this.createGroup(parent,
				"Conversionsettings"));
		this.reset();
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the conversion directory
	 */
	public File getConversionDirectory() {
		return new File(this.conversionDirectory);
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the state
	 */
	public boolean getConvertFilesState() {
		return this.convertFiles;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return keep converted files?
	 */
	public boolean getKeepConvertedFilesState() {
		return this.keepConvertedFiles;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the selected scanner
	 * 
	 * @see #setSelectedTokenizer(String)
	 */
	public String getSelectedTokenizer() {
		return this.selectedTokenizer;
	}

	@Override
	public void refresh() {
		IConverterConfiguration converterConfig = null;

		try {
			converterConfig = (IConverterConfiguration) this.getRegistry().get(
					ConverterService.CONVERTER_CONFIGURATION_ID);
		}
		catch (UnknownIDException e) {
			converterConfig = new DefaultConverterConfiguration();
		}

		String tokenizer;
		ITokenizerConfiguration tokenizerConfig;

		try {
			tokenizerConfig = (ITokenizerConfiguration) this.getRegistry().get(
					TokenizerService.TOKENIZER_CONFIGURATION_ID);
		}
		catch (UnknownIDException e) {
			tokenizerConfig = new DefaultTokenizerConfiguration();
		}

		if (tokenizerConfig.getTokenizerID() == null) {
			tokenizer = "none";
		}
		else {
			tokenizer = tokenizerConfig.getTokenizerID();
		}

		// converter settings
		boolean set = converterConfig.getConvertFiles();
		this.convertFilesButton.setSelection(set);
		this.conversionDirectoryText.setEnabled(set);
		this.keepConvertedFilesButton.setEnabled(set);
		this.browseButton.setEnabled(set);

		this.conversionDirectoryText.setText(converterConfig
				.getConvertedFilesDirectory().getAbsolutePath());
		this.keepConvertedFilesButton.setSelection(converterConfig
				.keepConvertedFiles());

		// Tokenizer settings
		// nach dem converter refresh, damit auf converter einstellungen
		// reagiert werden kann.
		this.updateTokenizerSelecter();

		String[] items = this.tokenizerSelecter.getItems();
		for (int i = 0; i < items.length; i++) {
			if (tokenizer.equals(this.tokenizerSelecter.getData(items[i]))) {
				this.tokenizerSelecter.select(i);
				break;
			}
			else if (tokenizer.equals(items[i])) {
				this.tokenizerSelecter.select(i);
				break;
			}
		}
	}

	@Override
	public void reset() {

		this.refresh();

		String name = this.tokenizerSelecter.getText();
		String id = (String) this.tokenizerSelecter.getData(name);

		this.selectedTokenizer = id;

		// reset converter
		this.keepConvertedFiles = this.keepConvertedFilesButton.getSelection();
		this.convertFiles = this.convertFilesButton.getSelection();
		this.conversionDirectory = this.conversionDirectoryText.getText();
	}

	/**
	 * sets the scanner to be used.
	 * 
	 * @param tokenizer
	 *            the scanner
	 * 
	 * @see #getSelectedTokenizer()
	 */
	public void setSelectedTokenizer(String tokenizer) {
		this.selectedTokenizer = tokenizer;
	}

	private void updateTokenizerSelecter() {
		logger.debug("updateTokenizerSelecter");

		this.tokenizerSelecter.removeAll();
		Map<String, ITokenizer> tokenizers = null;
		try {
			TokenizerService service = (TokenizerService) context
					.getServiceRegistry().get("org.dotplot.standard.Tokenizer");
			tokenizers = service.getRegisteredTokenizer();
		}
		catch (UnknownIDException e1) {
			tokenizers = new TreeMap<String, ITokenizer>();
		}

		ITokenizer tokenizer;
		Object data;
		String name;
		int n = 0;
		ISourceType sourceType = this.deriveSourceType();
		logger.debug("SourceType: " + sourceType.getName());
		Class<?> sourceClass = sourceType.getClass();
		for (String id : tokenizers.keySet()) {
			tokenizer = tokenizers.get(id);
			Class<?> tokenizerType = tokenizer.getStreamType().getClass();
			if (tokenizerType.isAssignableFrom(sourceClass)) {

				this.tokenizerSelecter.add(tokenizer.getName());
				name = tokenizer.getName();

				data = this.tokenizerSelecter.getData(name);
				while (data != null) {
					n++;
					name = String.format("%s (%d)", new Object[] {
							tokenizer.getName(), new Integer(n) });
					data = this.tokenizerSelecter.getData(name);
				}
				this.tokenizerSelecter.setData(name, id);
			}
		}
	}
}
