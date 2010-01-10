/**
 * 
 */
package org.dotplot.eclipse.preferences;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.plugins.IJobRegistry;
import org.dotplot.core.plugins.IPluginRegistry;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceHotSpot;
import org.dotplot.core.services.IServiceRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class PluginOverviewGui {

	/**
	 * Logger for debug informations.
	 */
	private static Logger logger = Logger.getLogger(PluginOverviewGui.class
			.getName());

	/**
	 * 
	 */
	private Composite control;

	/**
	 * 
	 */
	private Label information;

	/**
	 * 
	 */
	private Plugin currentPlugin;

	/**
	 * 
	 */
	private Map<String, Label> labels;

	/**
	 * 
	 */
	private Button activate;

	/**
	 * 
	 */
	private Button deactivate;

	/**
	 * 
	 */
	private Map<String, Boolean> pluginStates;

	/**
	 * 
	 */
	private Tree tree;

	/**
	 * Storrage for <code>TreeItems</code> which represent <code>Plugins</code>
	 * in the <code>Tree</code>.
	 * <p>
	 * The Id of the <code>Plugin</code> is used as key.
	 * </p>
	 */
	private Map<String, TreeItem> pluginTreeItems;

	/**
	 * 
	 */
	public PluginOverviewGui(Composite parent, IPluginRegistry<Plugin> plugins) {
		super();
		this.pluginStates = new TreeMap<String, Boolean>();
		this.pluginTreeItems = new TreeMap<String, TreeItem>();

		this.labels = new TreeMap<String, Label>();
		GridData gd;
		this.control = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		this.control.setLayout(layout);

		this.information = new Label(this.control, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.information.setLayoutData(gd);
		this.information.setText("Description");

		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.createTree(this.control, gd, plugins);
		this.createInfoArea(control, new GridData(GridData.FILL_HORIZONTAL));
		this.createButtons(control, new GridData(GridData.VERTICAL_ALIGN_END));
	}

	/**
	 * 
	 */
	public void apply() {
		if (this.tree != null) {
			for (String id : this.pluginStates.keySet()) {
				Plugin p = (Plugin) this.tree.getData(id);
				logger.debug(p.getID() + ":"
						+ this.pluginStates.get(id).toString());
				p.setActivated(this.pluginStates.get(id));
			}
		}
	}

	/**
	 * 
	 * @param parent
	 * @param layoutData
	 */
	private void createButtons(Composite parent, Object layoutData) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayoutData(layoutData);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.justify = true;
		layout.pack = false;
		c.setLayout(layout);

		this.activate = new Button(c, SWT.PUSH);
		this.activate.setText("Activate");
		this.activate.setEnabled(false);
		this.activate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (currentPlugin != null) {
					pluginStates.put(currentPlugin.getID(), Boolean.TRUE);
					updatePluginInfo(currentPlugin);
				}
			}
		});

		this.deactivate = new Button(c, SWT.PUSH);
		this.deactivate.setText("Deactivate");
		this.deactivate.setEnabled(false);
		this.deactivate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (currentPlugin != null) {
					pluginStates.put(currentPlugin.getID(), Boolean.FALSE);
					updatePluginInfo(currentPlugin);
				}
			}
		});
	}

	/**
	 * 
	 * @param parent
	 * @param ex
	 * @param plugin
	 */
	private void createExtentionNodes(TreeItem parent, Extention ex,
			Plugin plugin) {
		TreeItem item, extentionItem = new TreeItem(parent, SWT.NONE);
		extentionItem.setData(plugin);
		extentionItem.setText(ex.getExtentionObject().getClass().getName());

		for (String name : ex.getParameters().keySet()) {
			item = new TreeItem(extentionItem, SWT.NONE);
			item.setData(plugin);
			item.setText(String.format("%s = %s", new Object[] { name,
					ex.getParameter(name) }));
		}
	}

	/**
	 * 
	 * @param parent
	 * @param spot
	 * @param service
	 * @param plugin
	 */
	private void createHotSpotNodes(TreeItem parent, IServiceHotSpot spot,
			IService<DotplotContext, PluginHotSpot> service, Plugin plugin) {
		TreeItem exItem, servItem = null, spotItem = null, item = new TreeItem(
				parent, SWT.NONE);
		item.setData(plugin);
		item.setText(spot.getID());
		Plugin activator;

		for (Extention e : spot.getAllExtentions()) {
			activator = (Plugin) e.getActivator();
			if (activator == plugin) {
				this.createExtentionNodes(item, e, activator);
			}
			else {
				exItem = this.pluginTreeItems.get(activator.getID()).getItem(0);

				for (int i = 0; i < exItem.getItemCount(); i++) {
					if (exItem.getItem(i).getText().equals(service.getID())) {
						servItem = exItem.getItem(i);
					}
				}
				if (servItem == null) {
					servItem = new TreeItem(exItem, SWT.NONE);
					servItem.setText(service.getID());
					servItem.setData(plugin);
				}
				else {
					for (int i = 0; i < servItem.getItemCount(); i++) {
						if (servItem.getItem(i).getText().equals(spot.getID())) {
							spotItem = servItem.getItem(i);
						}
					}
				}

				if (spotItem == null) {
					spotItem = new TreeItem(servItem, SWT.NONE);
					spotItem.setText(spot.getID());
					spotItem.setData(plugin);
				}

				this.createExtentionNodes(spotItem, e, activator);
			}
		}
	}

	/**
	 * 
	 * @param parent
	 * @param layoutData
	 */
	private void createInfoArea(final Composite parent, final Object layoutData) {
		String[] texts = new String[] { "Name", "ID", "Provider", "Status",
				"Info" };
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayoutData(layoutData);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 10;
		c.setLayout(layout);
		Label l;
		for (int i = 0; i < texts.length; i++) {
			l = new Label(c, SWT.NONE);
			l.setLayoutData(new GridData());
			l.setText(texts[i]);

			l = new Label(c, SWT.WRAP);
			l.setLayoutData(new GridData(GridData.FILL_BOTH));
			l.setText("\n");
			this.labels.put(texts[i], l);
		}

		// this.info = new Text(parent, SWT.READ_ONLY | SWT.MULTI);
		// this.info.setLayoutData(layoutData);
	}

	/**
	 * 
	 * @param parent
	 * @param job
	 * @param plugin
	 */
	private void createJobNodes(final TreeItem parent, final IJob<?> job,
			final Plugin plugin) {
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setData(plugin);
		item.setText(job.getClass().getName());
	}

	/**
	 * 
	 * @param parent
	 * @param plugin
	 * @param firstRun
	 */
	private void createPluginNodes(Tree parent, Plugin plugin, boolean firstRun) {
		this.pluginStates
				.put(plugin.getID(), new Boolean(plugin.isActivated()));
		TreeItem pluginItem;

		if (firstRun) {
			pluginItem = new TreeItem(parent, SWT.NONE);
			this.pluginTreeItems.put(plugin.getID(), pluginItem);

			pluginItem.setData(plugin);
			pluginItem.setText(String.format("%s (%s)", new Object[] {
					plugin.getID(), plugin.getVersion() }));

			TreeItem extentions = new TreeItem(pluginItem, SWT.NONE);
			extentions.setData(plugin);
			extentions.setText("Extentions");
		}
		else {
			pluginItem = this.pluginTreeItems.get(plugin.getID());
			IServiceRegistry services = plugin.getServiceRegistry();

			TreeItem item;

			if (!services.getAll().isEmpty()) {
				item = new TreeItem(pluginItem, SWT.NONE);
				item.setText("Services");
				item.setData(plugin);

				for (IService<DotplotContext, PluginHotSpot> s : services
						.getAll().values()) {
					this.createServiceNodes(item, s, plugin);
				}
			}

			IJobRegistry jobs = plugin.getJobRegistry();

			if (!jobs.getAll().isEmpty()) {
				item = new TreeItem(pluginItem, SWT.NONE);
				item.setText("Jobs");
				item.setData(plugin);

				for (IJob<?> j : jobs.getAll().values()) {
					this.createJobNodes(item, j, plugin);
				}
			}
		}

	}

	/**
	 * 
	 * @param parent
	 * @param service
	 * @param plugin
	 */
	private void createServiceNodes(TreeItem parent,
			IService<DotplotContext, PluginHotSpot> service, Plugin plugin) {
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setData(plugin);
		item.setText(service.getID());

		Map<String, PluginHotSpot> spots = service.getHotSpots();
		if (!spots.isEmpty()) {
			for (IServiceHotSpot s : spots.values()) {
				this.createHotSpotNodes(item, s, service, plugin);
			}
		}
	}

	/**
	 * 
	 * @param parent
	 * @param layoutData
	 * @param plugins
	 */
	private void createTree(Composite parent, Object layoutData,
			IPluginRegistry<Plugin> plugins) {
		TreeItem item;

		this.tree = new Tree(parent, SWT.BORDER);
		this.tree.setLayoutData(layoutData);
		for (Plugin p : plugins.getAll().values()) {
			this.tree.setData(p.getID(), p);
			this.createPluginNodes(tree, p, true);
		}

		for (Plugin p : plugins.getAll().values()) {
			this.createPluginNodes(tree, p, false);
		}

		for (TreeItem i : this.pluginTreeItems.values()) {
			item = i.getItem(0);
			if (item.getItemCount() == 0) {
				item.dispose();
			}
		}

		this.tree.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				Widget item = e.item;
				if (item != null) {
					Object data = item.getData();
					if (data != null) {
						if (data != currentPlugin) {
							updatePluginInfo((Plugin) data);
						}
					}
				}
			}
		});
	}

	/**
	 * 
	 * @return the control.
	 */
	public Control getControl() {
		return this.control;
	}

	/**
	 * 
	 * @param description
	 *            the description
	 */
	public void setDescription(final String description) {
		if (description != null) {
			this.information.setText(description);
		}
	}

	/**
	 * 
	 * @param plugin
	 */
	private void updatePluginInfo(Plugin plugin) {
		Label l;
		boolean activeState = this.pluginStates.get(plugin.getID());

		l = this.labels.get("ID");
		l.setText(plugin.getID());

		l = this.labels.get("Provider");
		l.setText(plugin.getProvider());

		l = this.labels.get("Name");
		l.setText(plugin.getName() + " (" + plugin.getVersion() + ")");

		l = this.labels.get("Info");
		l.setText(plugin.getInfo());

		l = this.labels.get("Status");
		l.setText(activeState ? "active" : "inactive");

		this.activate.setEnabled(!activeState);
		this.deactivate.setEnabled(activeState);

		this.currentPlugin = plugin;
	}
}
