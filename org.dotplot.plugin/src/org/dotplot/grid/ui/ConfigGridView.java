package org.dotplot.grid.ui;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.mail.MessagingException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.dotplot.core.DotplotContext;
import org.dotplot.grid.GridClient;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.GridServer;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.image.QImageService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.util.MailUtil;
import org.dotplot.util.UnknownIDException;

/**
 * With the ConfigGridView the user can configure the grid settings. It
 * seperates the configuration in Server Settings and Client Settings
 */
public class ConfigGridView extends ConfigurationView {
	private Composite root;

	private Button chkEnableGrid;

	private Composite groupServer;

	private Composite groupClient;

	private Composite groupNotify;

	private Composite groupSMTP;

	private Composite groupEMail;

	private Label lblLocalPort;

	private Text txtLocalPort;

	private Button btnStartStopGridServer;

	private Label lblGridServerState;

	private Label lblClientName;

	private Text txtClientName;

	private Button btnUseServerSettings;

	private Label lblMediatorAddress;

	private Text txtMediatorAddress;

	private Label lblMediatorPort;

	private Text txtMediatorPort;

	private Button btnStartStopGridClient;

	private Label lblGridClientState;

	private Label lblAvailableAddresses;

	private Combo cboAvailableAddresses;

	private Button chkEnableNotify;

	private Label lblNotifySMTPHost;

	private Text txtNotifySMTPHost;

	private Label lblNotifySMTPUser;

	private Text txtNotifySMTPUser;

	private Label lblNotifySMTPPass;

	private Text txtNotifySMTPPass;

	private Label lblNotifyEmailSubject;

	private Text txtNotifyEmailSubject;

	private Label lblNotifyEmailFrom;

	private Text txtNotifyEmailFrom;

	private Label lblNotifyEmailTo;

	private Text txtNotifyEmailTo;

	private Button chkNotifyAttachImage;

	private Button btnNotifyTest;

	private String localAddress = "0.0.0.0";

	private int localPort = 88;

	private String mediatorAddress = "localhost";

	private int mediatorPort = 88;

	private String notifySMTPHost = "";

	private String notifySMTPUser = "";

	private String notifySMTPPass = "";

	private String notifyEmailSubject = "";

	private String notifyEmailFrom = "";

	private String notifyEmailTo = "";

	private GridServer gridServer;

	private GridClient gridClient;

	private final String recipientSeperator = ";";

	private final static Logger logger = Logger.getLogger(ConfigGridView.class
			.getName());

	public ConfigGridView() {
		this(new DotplotContext(".", "."));
	}

	/**
	 * Constructs a ConfigGridView object.
	 * 
	 * @param dotplotter
	 *            a valid IGUIDotplotter object
	 */
	public ConfigGridView(DotplotContext context) {
		super(context);
		setName("Grid settings");
	}

public void draw(Composite parent)
   {
	   this.deleteObservers();
	   this.addObserver(new ConfigGridController(this));
	   
      root = parent;

      GridLayout layout = new GridLayout(1, false);
      GridData gData = new GridData(GridData.FILL_BOTH);
      parent.setLayout(layout);
      parent.setLayoutData(gData);

      GridData gd = new GridData();
      chkEnableGrid = new Button(parent, SWT.CHECK);
      chkEnableGrid.setLayoutData(gd);
      chkEnableGrid.setText("Enable Grid");
      chkEnableGrid.setToolTipText("Enable and configure Grid");
      chkEnableGrid.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            changeListener.widgetSelected(null);

            if (gridClient != null && gridClient.getConnectionInfo() != null)
            {
               gridClient.disconnect();
            }

            if (gridServer != null && gridServer.isRunning())
            {
               onStartStopGridServer();
            }

            onEnableGrid(((Button) e.getSource()).getSelection());
         }
      });

      groupServer = createGroup(parent, "Server settings", 5);
      initLayoutServerGroup(groupServer);

      groupClient = createGroup(parent, "Client settings", 6);
      initLayoutClientGroup(groupClient);

      groupNotify = createGroup(parent, "Email notification", 2);
      initLayoutNotifyGroup(groupNotify);

      initListenersServerGroup();
      initListenersClientGroup();
      initListenersNotifyGroup();

      showCurrentConfig();
   }	private void initLayoutServerGroup(Composite _parent) {
		GridData gd;

		gd = new GridData();
		lblAvailableAddresses = new Label(_parent, SWT.NONE);
		lblAvailableAddresses.setText("Address");
		lblAvailableAddresses.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		cboAvailableAddresses = new Combo(_parent, SWT.NONE);
		cboAvailableAddresses.setItems((String[]) getAddresses().toArray(
				new String[0]));
		cboAvailableAddresses.clearSelection();

		// Port for the mediator socket
		gd = new GridData();
		gd.horizontalIndent = 10;
		lblLocalPort = new Label(_parent, SWT.NONE);
		lblLocalPort.setText("Port");
		lblLocalPort.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = 50;
		txtLocalPort = new Text(_parent, SWT.SINGLE | SWT.BORDER);
		txtLocalPort.setToolTipText("Port to which the mediator should listen");
		txtLocalPort.setLayoutData(gd);

		// Mediator start button
		gd = new GridData();
		gd.horizontalIndent = 15;
		btnStartStopGridServer = new Button(_parent, SWT.PUSH);
		btnStartStopGridServer.setLayoutData(gd);

		// Mediator status
		gd = new GridData();
		gd.horizontalSpan = 5;
		lblGridServerState = new Label(_parent, SWT.NONE);
		lblGridServerState.setLayoutData(gd);
	}

	private static Vector getAddresses() {
		InetAddress[] addresses = null;
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			addresses = InetAddress.getAllByName(hostName);
		}
		catch (UnknownHostException e) {
			// catch silently
			// e.printStackTrace();
		}

		Vector adr = new Vector();

		// add special addresses
		adr.add("0.0.0.0/0.0.0.0"); // "all"
		adr.add("localhost/127.0.0.1"); // "localhost"

		if (addresses != null && addresses.length > 0) {
			for (int i = 0; i < addresses.length; i++) {
				adr.add(addresses[i].toString());
			}
		}

		return adr;
	}

	private void initLayoutClientGroup(Composite _parent) {
		GridData gd;

		gd = new GridData();

		// Name for the client (optional)
		gd = new GridData();
		lblClientName = new Label(_parent, SWT.NONE);
		lblClientName.setText("Name");
		lblClientName.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = 100;
		gd.horizontalSpan = 5;
		txtClientName = new Text(_parent, SWT.SINGLE | SWT.BORDER);
		txtClientName.setToolTipText("Name of this client (optional)");
		txtClientName.setLayoutData(gd);

		// Use (local) server settings for client
		gd = new GridData();
		btnUseServerSettings = new Button(_parent, SWT.PUSH);
		btnUseServerSettings.setLayoutData(gd);
		btnUseServerSettings.setText(">");
		btnUseServerSettings
				.setToolTipText("Fill in \"Server settings\" from above");

		// IP for the mediator socket
		gd = new GridData();
		lblMediatorAddress = new Label(_parent, SWT.NONE);
		lblMediatorAddress.setText("Address");
		lblMediatorAddress.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = 100;
		txtMediatorAddress = new Text(_parent, SWT.SINGLE | SWT.BORDER);
		txtMediatorAddress.setToolTipText("Address of the mediator");
		txtMediatorAddress.setLayoutData(gd);

		// Port for the mediator socket
		gd = new GridData();
		gd.horizontalIndent = 10;
		lblMediatorPort = new Label(_parent, SWT.NONE);
		lblMediatorPort.setText("Port");
		lblMediatorPort.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = 50;
		txtMediatorPort = new Text(_parent, SWT.SINGLE | SWT.BORDER);
		txtMediatorPort.setToolTipText("Connection port");
		txtMediatorPort.setLayoutData(gd);

		// Mediator start button
		gd = new GridData();
		gd.horizontalIndent = 15;
		btnStartStopGridClient = new Button(_parent, SWT.PUSH);
		btnStartStopGridClient.setLayoutData(gd);
		btnStartStopGridClient.setText("Connect");

		// Mediator status
		gd = new GridData();
		gd.horizontalSpan = 6;
		lblGridClientState = new Label(_parent, SWT.NONE);
		lblGridClientState.setLayoutData(gd);
	}

	private void initLayoutNotifyGroup(Composite _parent) {
		final int widthHint = 90;

		GridData gd;

		gd = new GridData();
		// gd.horizontalSpan = 2;
		chkEnableNotify = new Button(_parent, SWT.CHECK);
		chkEnableNotify.setLayoutData(gd);
		chkEnableNotify.setText("Enable email notification");
		chkEnableNotify
				.setToolTipText("Enable email notification on completion of plots");

		gd = new GridData();
		chkNotifyAttachImage = new Button(_parent, SWT.CHECK);
		chkNotifyAttachImage.setLayoutData(gd);
		chkNotifyAttachImage.setText("Attach image(s)");
		chkNotifyAttachImage
				.setToolTipText("Attach image(s). They won't be combined!");

		groupSMTP = createGroup(_parent, "SMTP server", 2);
		groupEMail = createGroup(_parent, "Mail settings", 2);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifySMTPHost = new Label(groupSMTP, SWT.NONE);
		lblNotifySMTPHost.setText("host");
		lblNotifySMTPHost.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifySMTPHost = new Text(groupSMTP, SWT.SINGLE | SWT.BORDER);
		txtNotifySMTPHost
				.setToolTipText("SMTP host used to send the notification mail");
		txtNotifySMTPHost.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifySMTPUser = new Label(groupSMTP, SWT.NONE);
		lblNotifySMTPUser.setText("user");
		lblNotifySMTPUser.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifySMTPUser = new Text(groupSMTP, SWT.SINGLE | SWT.BORDER);
		txtNotifySMTPUser
				.setToolTipText("Login (user) to connect to the SMTP host");
		txtNotifySMTPUser.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifySMTPPass = new Label(groupSMTP, SWT.NONE);
		lblNotifySMTPPass.setText("password");
		lblNotifySMTPPass.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifySMTPPass = new Text(groupSMTP, SWT.SINGLE | SWT.BORDER);
		txtNotifySMTPPass.setEchoChar('*');
		txtNotifySMTPPass
				.setToolTipText("Login (password) to connect to the SMTP host");
		txtNotifySMTPPass.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifyEmailFrom = new Label(groupEMail, SWT.NONE);
		lblNotifyEmailFrom.setText("From");
		lblNotifyEmailFrom.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifyEmailFrom = new Text(groupEMail, SWT.SINGLE | SWT.BORDER);
		txtNotifyEmailFrom.setToolTipText("From");
		txtNotifyEmailFrom.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifyEmailTo = new Label(groupEMail, SWT.NONE);
		lblNotifyEmailTo.setText("To");
		lblNotifyEmailTo.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifyEmailTo = new Text(groupEMail, SWT.SINGLE | SWT.BORDER);
		txtNotifyEmailTo.setToolTipText("Use \"" + recipientSeperator
				+ "\" to seperate recipients");
		txtNotifyEmailTo.setLayoutData(gd);

		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		lblNotifyEmailSubject = new Label(groupEMail, SWT.NONE);
		lblNotifyEmailSubject.setText("Subject");
		lblNotifyEmailSubject.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalIndent = 5;
		gd.widthHint = widthHint;
		txtNotifyEmailSubject = new Text(groupEMail, SWT.SINGLE | SWT.BORDER);
		txtNotifyEmailSubject.setToolTipText("Subject");
		txtNotifyEmailSubject.setLayoutData(gd);

		gd = new GridData();
		gd.horizontalSpan = 2;
		btnNotifyTest = new Button(_parent, SWT.NONE);
		btnNotifyTest.setLayoutData(gd);
		btnNotifyTest.setText("Test notification");
		btnNotifyTest.setToolTipText("Test email notification settings");
	}

	private void initListenersServerGroup() {
		btnStartStopGridServer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
				onStartStopGridServer();
				btnUseServerSettings.forceFocus();
			}
		});

		txtLocalPort.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtLocalPort.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}

				updateLocalPort();
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtLocalPort.getText();
				txtLocalPort.selectAll();
			}
		});

		cboAvailableAddresses.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
				onServerAddressChanged();
			}
		});
	}

	private void initListenersClientGroup() {
		btnUseServerSettings.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);

				mediatorAddress = localAddress.trim();
				if (mediatorAddress.equals("0.0.0.0")) {
					mediatorAddress = "localhost";
				}

				txtMediatorAddress.setText(mediatorAddress);
				txtMediatorPort.setText("" + localPort);

				updateMediatorPort();
				btnStartStopGridClient.forceFocus();
			}
		});

		btnStartStopGridClient.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
				onStartStopGridClient();
			}
		});

		txtMediatorAddress.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtMediatorAddress.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}

				mediatorAddress = text.trim();
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtMediatorAddress.getText();
				txtMediatorAddress.selectAll();
			}
		});

		txtMediatorPort.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtMediatorPort.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}

				updateMediatorPort();
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtMediatorPort.getText();
				txtMediatorPort.selectAll();
			}
		});
	}

	private void initListenersNotifyGroup() {
		chkEnableNotify.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
				onEnableDisableNotify(chkEnableNotify.getSelection());
			}
		});

		chkNotifyAttachImage.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
			}
		});

		btnNotifyTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeListener.widgetSelected(null);
				try {
					MailUtil.postMailBySMTP(txtNotifySMTPHost.getText(),
							txtNotifySMTPUser.getText(), txtNotifySMTPPass
									.getText(), txtNotifyEmailFrom.getText(),
							getNotifyEmailTo(),
							txtNotifyEmailSubject.getText(),
							"Mail notification test");

					MessageDialog
							.openInformation(Display.getCurrent()
									.getActiveShell(), "Test passed",
									"Mail successfully sent. Please check your mailbox.");
				}
				catch (MessagingException msgExc) {
					MessageDialog.openError(Display.getCurrent()
							.getActiveShell(), "Test failed!", msgExc
							.getMessage());
					logger.warn("Mail notification test failed", msgExc);
				}
			}
		});

		txtNotifySMTPHost.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifySMTPHost.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifySMTPHost = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifySMTPHost.getText();
				txtNotifySMTPHost.selectAll();
			}
		});

		txtNotifySMTPUser.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifySMTPUser.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifySMTPUser = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifySMTPUser.getText();
				txtNotifySMTPUser.selectAll();
			}
		});

		txtNotifySMTPPass.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifySMTPPass.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifySMTPPass = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifySMTPPass.getText();
				txtNotifySMTPPass.selectAll();
			}
		});

		txtNotifyEmailFrom.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifyEmailFrom.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifyEmailFrom = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifyEmailFrom.getText();
				txtNotifyEmailFrom.selectAll();
			}
		});

		txtNotifyEmailTo.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifyEmailTo.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifyEmailTo = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifyEmailTo.getText();
				txtNotifyEmailTo.selectAll();
			}
		});

		txtNotifyEmailSubject.addFocusListener(new FocusAdapter() {
			private Object oldValue;

			public void focusLost(FocusEvent e) {
				final String text = txtNotifyEmailSubject.getText();
				if (oldValue != null && text != null && !oldValue.equals(text)) {
					changeListener.widgetSelected(null);
				}
				notifyEmailSubject = text;
			}

			public void focusGained(FocusEvent e) {
				oldValue = txtNotifyEmailSubject.getText();
				txtNotifyEmailSubject.selectAll();
			}
		});
	}

	private void onEnableDisableNotify(final boolean flag) {
		chkNotifyAttachImage.setEnabled(flag);
		setEnabledRecursive(groupSMTP, flag);
		setEnabledRecursive(groupEMail, flag);
		btnNotifyTest.setEnabled(flag);
	}

	private void updateLocalPort() {
		String s = txtLocalPort.getText();

		if (s == null || s.trim().equals("")) {
			txtLocalPort.setText("88");
		}

		try {
			localPort = Integer.parseInt(s.trim());
		}
		catch (NumberFormatException e) {
			localPort = 88;
			txtLocalPort.setText("88");
		}
	}

	private void updateMediatorPort() {
		String s = txtMediatorPort.getText();

		if (s == null || s.trim().equals("")) {
			txtMediatorPort.setText("88");
		}

		try {
			mediatorPort = Integer.parseInt(s.trim());
		}
		catch (NumberFormatException e) {
			mediatorPort = 88;
			txtMediatorPort.setText("88");
		}
	}

	private void updateGridServerUI() {
		if (gridServer != null && gridServer.isRunning()) {
			btnStartStopGridServer.setText("Stop mediator");
			btnStartStopGridServer
					.setToolTipText("Stops the mediator and disconnects all clients");

			lblGridServerState.setText("Mediator bound to  "
					+ gridServer.getSocket().getInetAddress() + ":"
					+ gridServer.getSocket().getLocalPort());
			lblGridServerState
					.setToolTipText("Shows the current address and port, the mediator is bound to."
							+ "\nAn IP of \"0.0.0.0\" lets the mediator listen to any IP.");
		}
		else {
			btnStartStopGridServer.setText("Start mediator");
			btnStartStopGridServer
					.setToolTipText("Starts the mediator to allow clients\nmake connections to this computer");

			lblGridServerState.setText("Mediator not running");
			lblGridServerState.setToolTipText("Push the start button");
		}

		lblGridServerState.pack(true);
	}

	private void updateGridClientUI() {
		if (gridClient != null && gridClient.getConnectionInfo() != null) {
			btnStartStopGridClient.setText("Stop collaborator");
			btnStartStopGridClient.setToolTipText("Stops this collaborator");

			lblGridClientState.setText("Collaborator bound to  "
					+ gridClient.getConnectionInfo());
			lblGridClientState
					.setToolTipText("Shows the current connection info.");

			txtClientName.setText(gridClient.getIdentity().getName());
		}
		else {
			btnStartStopGridClient.setText("Start collaborator");
			btnStartStopGridClient
					.setToolTipText("Connects this computer as collaborator to the mediator at the given address");

			lblGridClientState.setText("Collaborator not running");
			lblGridClientState.setToolTipText("Push the start button");

			txtClientName.setText("");
		}

		lblGridClientState.pack(true);
	}

	private void onEnableGrid(boolean flag) {
		setEnabledRecursive(groupServer, flag);
		setEnabledRecursive(groupClient, flag);
		setEnabledRecursive(groupNotify, flag);

		if (!isEnableNotification()) {
			onEnableDisableNotify(false);
		}

		if (flag) {
			cboAvailableAddresses.forceFocus();
		}
	}

	private void onServerAddressChanged() {
		String[] listenToAddress = cboAvailableAddresses.getText().split("/");
		localAddress = listenToAddress[listenToAddress.length - 1];
	}

	private void onStartStopGridServer() {
				
		GridConfiguration config;		
		try {
			config = (GridConfiguration)this.getRegistry().get(QImageService.ID_CONFIGURATION_GRID);
		}
		catch (UnknownIDException e) {
			config = new GridConfiguration();
		}

		// let the user wait for the mediator (and its clients) to start/stop
		btnStartStopGridServer.setEnabled(false);

		// TODO disconnect locally connected collaborator

		if (gridServer != null && gridServer.isRunning()) {
			// gridServer.stop(localAddress);
			gridServer.stop();
			config.setGridServer(null);
		}
		else {
			updateLocalPort();

			gridServer = new GridServer(localAddress, localPort);
			config.setGridServer(gridServer);
		}

		try {
			// wait for mediator to close all connections (when stopping)
			// or to establish binding to ServerSocket (when starting)
			Thread.sleep(2000);
		}
		catch (InterruptedException e1) {
			// e1.printStackTrace();
			// catch silently
		}

		updateGridServerUI();

		// allow clicking the button again
		btnStartStopGridServer.setEnabled(true);
	}

	private void onStartStopGridClient() {
		GridConfiguration config;		
		try {
			config = (GridConfiguration)this.getRegistry().get(QImageService.ID_CONFIGURATION_GRID);
		}
		catch (UnknownIDException e) {
			config = new GridConfiguration();
		}

		// let the user wait for the collaborator to start/stop
		btnStartStopGridClient.setEnabled(false);

		if (gridClient != null && gridClient.getConnectionInfo() != null) {
			gridClient.disconnect();
			config.setGridClient(null);
		}
		else {
			updateMediatorPort();

			logger.debug("connecting to " + getMediatorAddress() + ":"
					+ getMediatorPort());
			try {
				String clientName = txtClientName.getText();
				if (clientName == null || clientName.trim().equals("")) {
					clientName = "*" + getMediatorAddress() + "*";
				}

				gridClient = new GridClient(clientName, getMediatorAddress(),
						getMediatorPort());
			}
			catch (ConnectionException e) {
				updateGridClientUI();

				// allow clicking the button again
				btnStartStopGridClient.setEnabled(true);
			}

			// connection request successful?
			if (gridClient.getConnectionInfo() != null) {
				logger.debug("collaborator-id: " + gridClient.getIdentity());
				config.setGridClient(gridClient);
				txtClientName.setText(gridClient.getIdentity().getName());
			}
			else {
				logger.debug("connection not established");
			}
		}

		// try
		// {
		// // wait for collaborator to close connection (when stopping)
		// // or to establish connection to mediator (when starting)
		// Thread.sleep(2000);
		// }
		// catch (InterruptedException e1)
		// {
		// //e1.printStackTrace();
		// // catch silently
		// }

		updateGridClientUI();

		// allow clicking the button again
		btnStartStopGridClient.setEnabled(true);
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

	public void refresh() {
		// Control[] children = root.getChildren();
		draw(root);
		// this.root.layout();
	}

	private void showCurrentConfig() {
		GridConfiguration config;		
		try {
			config = (GridConfiguration)this.getRegistry().get(QImageService.ID_CONFIGURATION_GRID);
		}
		catch (UnknownIDException e) {
			config = new GridConfiguration();
		}

		localPort = config.getServerPort();
		mediatorAddress = config.getMediatorAddress();
		mediatorPort = config.getMediatorPort();

		cboAvailableAddresses.setText(mediatorAddress);
		txtLocalPort.setText("" + localPort);
		updateLocalPort();

		updateGridServerUI();

		chkEnableGrid.setSelection(config.isGridEnabled());
		onEnableGrid(config.isGridEnabled());

		txtMediatorAddress.setText(mediatorAddress);
		txtMediatorPort.setText("" + mediatorPort);

		updateGridClientUI();

		chkEnableNotify.setSelection(config.isEnableNotification());

		if (isEnableNotification()) {
			chkNotifyAttachImage.setSelection(config.isNotifyAttachImage());

			notifySMTPHost = config.getNotifySMTPHost();
			notifySMTPUser = config.getNotifySMTPUser();
			notifySMTPPass = config.getNotifySMTPPass();
			notifyEmailFrom = config.getNotifyEmailFrom();
			notifyEmailTo = glueSplittedStrings(config.getNotifyEmailTo(),
					recipientSeperator);
			notifyEmailSubject = config.getNotifyEmailSubject();

			txtNotifySMTPHost.setText(notifySMTPHost);
			txtNotifySMTPUser.setText(notifySMTPUser);
			txtNotifySMTPPass.setText(notifySMTPPass);
			txtNotifyEmailFrom.setText(notifyEmailFrom);
			txtNotifyEmailTo.setText(notifyEmailTo);
			txtNotifyEmailSubject.setText(notifyEmailSubject);
		}
	}

	private String glueSplittedStrings(String[] list, String glue) {
		if (list == null || glue == null) {
			return null;
		}

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			res.append(glue + list[i]);
		}

		return res.substring(glue.length());
	}

	public void reset() {
		showCurrentConfig();
	}

	/**
	 * did the user enable the grid?
	 * 
	 * @return the current state
	 */
	public boolean isEnableGrid() {
		return chkEnableGrid.getSelection();
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the current setting
	 */
	public int getLocalPort() {
		return localPort;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the current setting
	 */
	public String getMediatorAddress() {
		return mediatorAddress;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the current setting
	 */
	public int getMediatorPort() {
		return mediatorPort;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the current setting
	 */
	public GridClient getGridClient() {
		return gridClient;
	}

	/**
	 * returns the current setting.
	 * 
	 * @return the current setting
	 */
	public GridServer getGridServer() {
		return gridServer;
	}

	public boolean isEnableNotification() {
		return chkEnableNotify.getSelection();
	}

	public boolean isNotifyAttachImage() {
		return chkNotifyAttachImage.getSelection();
	}

	public String getNotifyEmailFrom() {
		return notifyEmailFrom;
	}

	public String getNotifyEmailSubject() {
		return notifyEmailSubject;
	}

	public String[] getNotifyEmailTo() {
		return notifyEmailTo.split('\\' + recipientSeperator);
	}

	public String getNotifySMTPHost() {
		return notifySMTPHost;
	}

	public String getNotifySMTPPass() {
		return notifySMTPPass;
	}

	public String getNotifySMTPUser() {
		return notifySMTPUser;
	}
}
