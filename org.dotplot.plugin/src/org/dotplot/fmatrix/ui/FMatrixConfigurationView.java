/*
 * Created on 01.07.2004
 */
package org.dotplot.fmatrix.ui;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import org.dotplot.core.DotplotContext;
import org.dotplot.fmatrix.FMatrixService;
import org.dotplot.fmatrix.IFMatrixConfiguration;
import org.dotplot.fmatrix.ITypeTableManipulator;
import org.dotplot.fmatrix.TokenType;
import org.dotplot.fmatrix.TypeTableManipulator;
import org.dotplot.fmatrix.WeightingEntry;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.UnknownIDException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

/**
 * The view for the Dotplot Configuration menu.
 * 
 * @author Thorsten Ruehl
 */
public class FMatrixConfigurationView extends ConfigurationView {
	private static void showEmptyDialog(final Composite parent) {
		// Color white = new Color(parent.getDisplay(), 255, 255, 255);
		// Color lightRed = new Color(parent.getDisplay(), 100, 200, 200);

		GridData gd;

		final Point labelSize = new Point(150, 20);
		final Label noTypeTableLabel = new Label(parent, SWT.NONE);

		noTypeTableLabel.setAlignment(SWT.CENTER);
		noTypeTableLabel.setSize(labelSize);
		noTypeTableLabel.setLocation(new Point(10, 10));
		noTypeTableLabel.setText("No types loaded actually!");
		// noTypeTableLabel.setBackground(white);

		gd = new GridData();
		gd.horizontalAlignment = GridData.BEGINNING;
		gd.verticalAlignment = GridData.CENTER;
		noTypeTableLabel.setLayoutData(gd);

		noTypeTableLabel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Point parentSize = parent.getSize();
				Point location = new Point((parentSize.x - labelSize.x) / 2,
						(parentSize.y - labelSize.y) / 2);

				// center message on dialog
				noTypeTableLabel.setLocation(location);
			}
		});
	}

	private Composite parent;

	private TokenType displayedTokenType;

	private ITypeTableManipulator typeTableManipulator;

	private java.util.List<WeightingEntry> tokenWeights;

	private List tokenList;

	private Text weightValue;

	private Text frequencyValue;

	private Text regExpValue;

	// private Vector storedRegularExpressionVec;

	private Button regExpButton;

	public FMatrixConfigurationView() {
		this(new DotplotContext(".", "."));
	}

	/**
	 * create the configuration view.
	 * 
	 * @param dotplotter
	 *            the IGUIDotplotter
	 * 
	 * @see org.dotplot.ui.ConfigurationView#draw(org.eclipse.swt.widgets.Composite)
	 */
	public FMatrixConfigurationView(DotplotContext context) {
		super(context);
		this.setName("FMatrix settings");

		setApplyListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					double weight = Double.parseDouble(weightValue.getText());
					int index = typeTableManipulator
							.getHashKeyByName(displayedTokenType.getValue());

					tokenWeights.add(new WeightingEntry(index, weight));
					displayedTokenType.setWeight(weight);
				}
				catch (NumberFormatException ex) {
					System.err.println("invalid user entry");
				}
			}
		});
	}

	@Override
	public void draw(final Composite parent) {
		this.deleteObservers();
		this.addObserver(new FMatrixViewController(this));

		this.parent = parent;
		readConfig();

		if (context.getCurrentTypeTable().getNumberOfTypes() == 0) {
			showEmptyDialog(parent);
		}
		else {
			typeTableManipulator = new TypeTableManipulator(context
					.getCurrentTypeTable());

			initLayout(parent);
			initListeners();

			updateTokenList();
		}
	}

	private void initLayout(final Composite parent) {
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		parent.setLayout(new GridLayout(3, false));

		GridData gd;

		tokenList = new List(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.heightHint = 300;
		gd.widthHint = 150;
		gd.verticalSpan = 8;
		tokenList.setLayoutData(gd);

		Label weightLabel = new Label(parent, SWT.NULL);
		weightLabel.setText("Weight:");
		gd = new GridData();
		gd.horizontalIndent = 15;
		weightLabel.setLayoutData(gd);

		weightValue = new Text(parent, SWT.BORDER);
		weightValue.setText("");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		weightValue.setLayoutData(gd);

		Label frequencyLabel = new Label(parent, SWT.NULL);
		frequencyLabel.setText("Frequency:");
		gd = new GridData();
		gd.horizontalIndent = 15;
		frequencyLabel.setLayoutData(gd);

		frequencyValue = new Text(parent, SWT.BORDER);
		frequencyValue.setText("");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		frequencyValue.setLayoutData(gd);

		// create some space before the regEx controls
		Composite spacer = new Composite(parent, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 2;
		gd.heightHint = 15;
		spacer.setLayoutData(gd);

		Label regExpLabel = new Label(parent, SWT.NONE);
		regExpLabel.setText("Regular Expression");
		gd = new GridData();
		gd.horizontalIndent = 15;
		gd.horizontalSpan = 2;
		regExpLabel.setLayoutData(gd);

		regExpValue = new Text(parent, SWT.BORDER);
		regExpValue.setText("");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 15;
		gd.horizontalSpan = 2;
		regExpValue.setLayoutData(gd);

		regExpButton = new Button(parent, SWT.BUTTON1);
		regExpButton.setText("Add Type");
		gd = new GridData();
		gd.horizontalIndent = 20;
		regExpButton.setLayoutData(gd);

		Composite filler = new Composite(parent, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 10;
		filler.setLayoutData(gd);
	}

	private void initListeners() {
		regExpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String weight = weightValue.getText();
				if (weight != null) {
					weight = weight.trim();
					if (weight.equals("")) {
						MessageDialog.openInformation(parent.getShell(),
								"FMatrix", "Please enter a valid number");
						return;
					}
				}

				try {
					typeTableManipulator.addNewRegExpType(
							regExpValue.getText(), Double.parseDouble(weight));
				}
				catch (NumberFormatException e1) {
					MessageDialog.openInformation(parent.getShell(), "FMatrix",
							"Please enter a valid number");
				}
				// storedRegularExpressionVec.add(regExpValue.getText());
			}
		});

		tokenList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayedTokenType = typeTableManipulator
						.getTokenTypeByName(tokenList.getSelection()[0]);
				weightValue.setText("" + displayedTokenType.getWeight());
				frequencyValue.setText("" + displayedTokenType.getFrequency());
			}
		});
	}

	private void readConfig() {
		// this.tokenWeights = (Vector)
		// config.get(GlobalConfiguration.KEY_FMATRIX_REGULAR_EXPRESSIONS);
		IFMatrixConfiguration config;
		try {
			config = (IFMatrixConfiguration) this.registry
					.get(FMatrixService.ID_FMATRIX_CONFIGURATION);
			this.tokenWeights = config.getManualWeightedTokens();
		}
		catch (UnknownIDException e) {
			this.tokenWeights = new Vector<WeightingEntry>();
		}

	}

	private void updateTokenList() {
		Vector entries = new Vector();
		Enumeration typeEnum = typeTableManipulator.getTypeEnumeration();
		while (typeEnum.hasMoreElements()) {
			String element = ((TokenType) typeEnum.nextElement()).getValue();
			entries.add(element);
		}

		String[] strings = (String[]) entries.toArray(new String[] {});
		Arrays.sort(strings);

		tokenList.setItems(strings);
	}
}
