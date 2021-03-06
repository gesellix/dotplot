package org.dotplot.image.ui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.dotplot.core.DotplotContext;
import org.dotplot.image.Colorbar;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.JAITools;
import org.dotplot.image.LUTs;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * With the ConfigQImageView the user can configure the dotplot settings. It
 * seperates the configuration in Image Settings and Export Settings
 */
public class ConfigQImageView extends ConfigurationView {
	private Composite root;

	private FileDialog dlgFileBrowser;

	private Combo cboExportFormat;

	private Combo cboLUT;

	private Button btnBrowse;

	private Button btnScaleOff;

	private Button btnScaleWindowSize;

	private Button chkExportDotplotToFile;

	private Button chkShowFileSeparators;

	private Button chkScaleUp;

	private Button chkUseLUT;

	private Button chkUseInfoMural;

	private Colorbar colBarLUT;

	private RGB background;

	private RGB foreground;

	private String exportFileName;

	private Text exportFileText;

	private int[][] currentLut; // selected LUT

	private String currentLutTitle;

	private int exportFormat;

	private int scaleMode;

	private boolean showFileSeparators;

	private boolean doExportToFile;

	private boolean doScaleUp;

	private boolean useLUT;

	private boolean useInfoMural;

	public ConfigQImageView() {
		this(new DotplotContext(".", "."));
	}

	/**
	 * Constructs a ConfigQImageView object.
	 * 
	 * @param dotplotter
	 *            a valid IGUIDotplotter object
	 */
	public ConfigQImageView(DotplotContext context) {
		super(context);
		this.setName("Image settings");
	}

	private Composite createGroup(Composite parent, String name, int columns) {
		Group g = new Group(parent, SWT.SHADOW_ETCHED_OUT);
		if (name != null) {
			g.setText(name);
		}
		g.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		g.setLayout(new GridLayout(columns, false));
		return g;
	}

	/**
	 * Scale up small images.
	 * 
	 * @return the currently configured value
	 */
	public boolean doScaleUp() {
		return this.doScaleUp;
	}

	@Override
	public void draw(Composite parent) {
		this.deleteObservers();
		this.addObserver(new ConfigQImageController(this));

		this.root = parent;

		this.dlgFileBrowser = new FileDialog(parent.getShell(), SWT.SAVE);
		this.dlgFileBrowser.setText("Choose a file to store your DotPlot.");
		// this.dlgFileBrowser.setFilterExtensions(JAITools.EXPORT_FORMATS);

		parent.setLayout(new GridLayout(1, false));
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		initLayoutImageGroup(this.createGroup(parent, "Image settings", 1));
		initLayoutExportGroup(this.createGroup(parent, "Export settings", 5));

		initListenersImageGroup();
		initListenersExportGroup();

		showCurrentConfig();
	}

	/**
	 * Get the user defined background.
	 * 
	 * @return the currently configured value
	 */
	public Color getBackground() {
		return new Color(background.red, background.green, background.blue);
	}

	/**
	 * Get the chosen LUT.
	 * 
	 * @return the currently configured value
	 */
	public String getChoosenLut() {
		return this.currentLutTitle;
	}

	/**
	 * Get current filename for exported dotplots.
	 * 
	 * @return the currently configured value
	 */
	public File getExportFileName() {
		return new File(this.exportFileName);
	}

	/**
	 * Get the user defined foreground.
	 * 
	 * @return the currently configured value
	 */
	public Color getForeground() {
		return new Color(foreground.red, foreground.green, foreground.blue);
	}

	/**
	 * Get the user defined scaling mode.
	 * 
	 * @return the currently configured value
	 */
	public int getScaleMode() {
		return this.scaleMode;
	}

	/**
	 * Get the user defined output-format.
	 * 
	 * @return the currently configured value
	 */
	public int getSelectedFormat() {
		return this.exportFormat;
	}

	private void initLayoutExportGroup(Composite parent) {
		GridData gd;

		// CheckBox (export to file, yes/no)
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 5;
		this.chkExportDotplotToFile = new Button(parent, SWT.CHECK);
		this.chkExportDotplotToFile.setText("export dotplot");
		this.chkExportDotplotToFile
				.setToolTipText("If checked, the generated DotPlot will be exported to a file");
		this.chkExportDotplotToFile.setLayoutData(gd);

		// Label (export format)
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
				| GridData.VERTICAL_ALIGN_CENTER);
		Label label = new Label(parent, SWT.NONE);
		label.setText("Format:");
		label.setLayoutData(gd);

		// ComboBox for choosing the Export format
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		this.cboExportFormat = new Combo(parent, SWT.DROP_DOWN);
		for (int i = 0; i < JAITools.EXPORT_FORMATS.length; i++) {
			this.cboExportFormat.add(JAITools.EXPORT_FORMATS[i]);
		}
		this.cboExportFormat.setToolTipText("Choose your export format");
		this.cboExportFormat.setText("Export format");
		this.cboExportFormat.setLayoutData(gd);

		// Label (filename)
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING
				| GridData.VERTICAL_ALIGN_CENTER);
		label = new Label(parent, SWT.NONE);
		label.setText("Filename:");
		label.setLayoutData(gd);

		// Filename text
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		gd.widthHint = 100;
		this.exportFileText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		this.exportFileText.setToolTipText("Filename for exported DotPlot");
		this.exportFileText.setLayoutData(gd);

		// Browser Button
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		this.btnBrowse = new Button(parent, SWT.PUSH);
		this.btnBrowse.setText("Browse...");
		this.btnBrowse.setLayoutData(gd);
	}

	private void initLayoutImageGroup(Composite _parent) {
		GridData gd;

		// Information Mural
		gd = new GridData();
		gd.horizontalSpan = 2;
		this.chkUseInfoMural = new Button(_parent, SWT.CHECK);
		this.chkUseInfoMural.setLayoutData(gd);
		this.chkUseInfoMural.setText("Use Information Mural");
		this.chkUseInfoMural.setToolTipText("Use alternate algorithm");
		this.chkUseInfoMural.setSelection(useInfoMural);

		// Scaling Configuration
		Composite parent = createGroup(_parent, "Scaling", 2);

		gd = new GridData();
		gd.horizontalSpan = 2;
		this.btnScaleOff = new Button(parent, SWT.RADIO);
		this.btnScaleOff.setLayoutData(gd);
		this.btnScaleOff.setText("No scaling");
		this.btnScaleOff.setToolTipText("Switches scaling off");

		gd = new GridData();
		this.btnScaleWindowSize = new Button(parent, SWT.RADIO);
		this.btnScaleWindowSize.setText("Fit to window size");
		this.btnScaleWindowSize.setSelection(true);
		this.btnScaleWindowSize
				.setToolTipText("Scales the image to the size of the viewer");
		this.btnScaleWindowSize.setLayoutData(gd);

		gd = new GridData(GridData.VERTICAL_ALIGN_END);
		this.chkScaleUp = new Button(parent, SWT.CHECK);
		this.chkScaleUp.setText("scale up small images");
		this.chkScaleUp.setSelection(doScaleUp);
		this.chkScaleUp
				.setToolTipText("If checked, small images will be scaled up, resulting in a 'fuzzy' image");
		this.chkScaleUp.setLayoutData(gd);

		// LUT Configuration
		parent = createGroup(_parent, "Lookup Table", 2);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 2;
		this.chkUseLUT = new Button(parent, SWT.CHECK);
		this.chkUseLUT.setText("use lookup table");
		this.chkUseLUT.setSelection(useLUT);
		this.chkUseLUT.setToolTipText("Use grayscaling (or colors) if checked");
		this.chkUseLUT.setLayoutData(gd);

		// Combo Box for choosing the color of the dotplot
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.cboLUT = new Combo(parent, SWT.DROP_DOWN);
		this.cboLUT.setItems(LUTs.availableLUTs);
		this.cboLUT.setText("Choose your desired colors");
		this.cboLUT.setLayoutData(gd);

		// Lut-Canvas for changing the Color of the dotPlot
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.colBarLUT = new Colorbar(parent, SWT.RADIO);
		this.colBarLUT.setLut(currentLut);
		this.colBarLUT.setToolTipText("Current Color for the DotPlot");
		this.colBarLUT.setLayoutData(gd);

		this.chkShowFileSeparators = new Button(_parent, SWT.CHECK);
		this.chkShowFileSeparators.setText("show file separators");
		this.chkShowFileSeparators
				.setToolTipText("If checked, the generated DotPlot shows borders of the plotted files");
		this.chkShowFileSeparators.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));
	}

	private void initListenersExportGroup() {
		this.chkExportDotplotToFile.addSelectionListener(this.changeListener);
		this.chkExportDotplotToFile
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						doExportToFile = ((Button) e.widget).getSelection();

						cboExportFormat.setEnabled(doExportToFile);
						exportFileText.setEnabled(doExportToFile);
						btnBrowse.setEnabled(doExportToFile);
					}
				});

		this.cboExportFormat.addSelectionListener(this.changeListener);
		this.cboExportFormat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget instanceof Combo) {
					exportFormat = ((Combo) e.widget).getSelectionIndex();
					updateExportFilename(exportFileName, exportFileName + "."
							+ cboExportFormat.getText());
				}
			}
		});

		this.exportFileText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				changeListener.widgetSelected(null);
				updateExportFilename(exportFileName, exportFileText.getText());
			}
		});

		this.btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);

				dlgFileBrowser.setFilterPath(exportFileName);
				String selectedFilename = dlgFileBrowser.open();
				if (selectedFilename != null) {
					exportFileName = selectedFilename;
					exportFileText.setText(exportFileName + "."
							+ cboExportFormat.getText());
				}
			}
		});
	}

	private void initListenersImageGroup() {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean scaleOff = btnScaleOff.getSelection();
				boolean scaleWindow = btnScaleWindowSize.getSelection();

				if (scaleOff) {
					scaleMode = 0;
					chkScaleUp.setEnabled(false);
				}
				else if (scaleWindow) {
					scaleMode = 1;
					chkScaleUp.setEnabled(true);
				}
			}
		};
		this.btnScaleOff.addSelectionListener(this.changeListener);
		this.btnScaleWindowSize.addSelectionListener(this.changeListener);
		this.btnScaleOff.addSelectionListener(selectionAdapter);
		this.btnScaleWindowSize.addSelectionListener(selectionAdapter);

		this.chkScaleUp.addSelectionListener(this.changeListener);
		this.chkScaleUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doScaleUp = chkScaleUp.getSelection();
			}
		});

		this.chkUseInfoMural.addSelectionListener(this.changeListener);
		this.chkUseInfoMural.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				useInfoMural = chkUseInfoMural.getSelection();
			}
		});

		this.chkUseLUT.addSelectionListener(this.changeListener);
		this.chkUseLUT.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				useLUT = chkUseLUT.getSelection();
				cboLUT.setEnabled(useLUT);
				colBarLUT.setEnabled(useLUT);

				if (!useLUT) {
					cboLUT.setText("inverted_gray");
					updateColbarLUT("inverted_gray");
				}
			}
		});

		this.cboLUT.addSelectionListener(this.changeListener);
		this.cboLUT.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Combo source = (Combo) e.getSource();
				System.out.println(source.getSelectionIndex());
				System.out.println(source.getText());
				currentLutTitle = LUTs.availableLUTs[source.getSelectionIndex()];
				System.out.println(currentLutTitle);
				updateColbarLUT(currentLutTitle);
			}
		});

		this.colBarLUT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				boolean leftPartWasKlicked = e.x < (colBarLUT.getSize().x / 2);

				ColorDialog colorDialog = new ColorDialog(colBarLUT.getShell());
				colorDialog.setText("Choose "
						+ ((leftPartWasKlicked) ? "background" : "foreground")
						+ " color");

				RGB color = colorDialog.open();
				if (color != null) {
					changeListener.widgetSelected(null); // signal change
					if (leftPartWasKlicked) {
						colBarLUT.setLUTBackground(color);
						background = color;
					}
					else {
						colBarLUT.setLUTForeground(color);
						foreground = color;
					}
				}
			}
		});

		this.chkShowFileSeparators.addSelectionListener(this.changeListener);
		this.chkShowFileSeparators.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showFileSeparators = ((Button) e.widget).getSelection();
			}
		});
	}

	/**
	 * Get current filename for exported dotplots.
	 * 
	 * @return the currently configured value
	 */
	public boolean isExportDotplot() {
		return this.doExportToFile;
	}

	@Override
	public void refresh() {
		// Control[] children = root.getChildren();
		this.showCurrentConfig();
		// this.root.layout();
	}

	@Override
	public void reset() {
		showCurrentConfig();
	}

	private void showCurrentConfig() {

		IQImageConfiguration config;
		try {
			config = (IQImageConfiguration) this.getRegistry().get(
					QImageService.QIMAGE_CONFIGURATION_ID);
		}
		catch (UnknownIDException e) {
			e.printStackTrace();
			config = new QImageConfiguration();
		}

		this.doExportToFile = config.isExportDotplotToFile();
		this.exportFormat = config.getExportFormat();
		this.exportFileName = config.getExportFilename();

		this.useInfoMural = config.useInformationMural();
		this.scaleMode = config.getScaleMode();
		this.doScaleUp = config.doScaleUp();
		this.showFileSeparators = config.showFileSeparators();

		this.useLUT = config.useLUT();
		this.currentLut = config.getLut();
		this.currentLutTitle = config.getLutTitle();
		Color colTmp = config.getLutBackground();
		this.background = new RGB(colTmp.getRed(), colTmp.getGreen(), colTmp
				.getBlue());
		colTmp = config.getLutForeground();
		this.foreground = new RGB(colTmp.getRed(), colTmp.getGreen(), colTmp
				.getBlue());

		this.chkUseInfoMural.setSelection(useInfoMural);

		// show file separators
		this.chkShowFileSeparators.setSelection(showFileSeparators);

		// scaling
		this.btnScaleOff.setSelection(false);
		this.btnScaleWindowSize.setSelection(false);
		this.chkScaleUp.setEnabled(false);

		switch (scaleMode) {
		case 0:
			this.btnScaleOff.setSelection(true);
			break;
		case 1:
			this.btnScaleWindowSize.setSelection(true);
			this.chkScaleUp.setEnabled(true);
			break;
		default:
			this.btnScaleOff.setSelection(true);
		}

		// scale up small images
		this.chkScaleUp.setSelection(doScaleUp);

		// lut
		this.chkUseLUT.setSelection(this.useLUT);
		this.colBarLUT.setLut(this.currentLut);
		this.colBarLUT.setLUTBackground(this.background);
		this.colBarLUT.setLUTForeground(this.foreground);
		this.cboLUT.setText(this.currentLutTitle);

		if (!useLUT) {
			this.cboLUT.setEnabled(useLUT);
			this.colBarLUT.setEnabled(useLUT);
			this.cboLUT.setText("inverted_gray");
			updateColbarLUT("inverted_gray");
		}

		this.chkExportDotplotToFile.setSelection(doExportToFile);

		// exportFormat
		this.cboExportFormat
				.setText(JAITools.EXPORT_FORMATS[this.exportFormat]);

		// exportFile
		String newFilename = this.exportFileName + "."
				+ this.cboExportFormat.getText();
		updateExportFilename("", newFilename);

		this.cboExportFormat.setEnabled(doExportToFile);
		this.exportFileText.setEnabled(doExportToFile);
		this.btnBrowse.setEnabled(doExportToFile);
	}

	/**
	 * Get the user defined setting to show or hide file separators.
	 * 
	 * @return the currently configured value
	 */
	public boolean showFileSeparators() {
		return this.showFileSeparators;
	}

	private void updateColbarLUT(String selectedLUT) {
		try {
			currentLut = (int[][]) LUTs.class.getMethod(selectedLUT,
					(Class[]) null).invoke(null, new Object[] {});
		}
		catch (Throwable exc) {
			exc.printStackTrace();
			System.err.flush();
			return;
		}

		colBarLUT.setLut(currentLut);
		background = colBarLUT.getLUTBackground();
		foreground = colBarLUT.getLUTForeground();
	}

	private void updateExportFilename(String oldFilename, String newFilename) {
		File filename = new File(newFilename);
		String newtext = oldFilename;
		try {
			newtext = filename.getCanonicalPath();
		}
		catch (IOException e1) {
			newtext = oldFilename;
		}

		exportFileText.setText(newtext);

		exportFileName = exportFileText.getText();
		if (exportFileName.indexOf('.') != -1) {
			exportFileName = exportFileName.substring(0, exportFileName
					.lastIndexOf('.'));
		}
	}

	/**
	 * Get the user defined setting to use the algorithm "Information Mural".
	 * 
	 * @return the currently configured value
	 */
	public boolean useInfoMural() {
		return useInfoMural;
	}

	/**
	 * Use grayscaling and/or colors?
	 * 
	 * @return the currently configured value
	 */
	public boolean useLUT() {
		return this.useLUT;
	}
}
