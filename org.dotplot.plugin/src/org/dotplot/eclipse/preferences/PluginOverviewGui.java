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
public class PluginOverviewGui {

	/**
	 * Logger for debug informations.
	 */
	private static Logger logger = Logger.getLogger(PluginOverviewGui.class
			.getName());

	
	private Composite control;
	private Label information;
	private Plugin currentPlugin;
	private Map<String, Label> labels;
	private Button activate;
	private Button deactivate;
	private Map<String, Boolean> pluginStates;
	private Tree tree;
	
	/**
	 * 
	 */
	public PluginOverviewGui(Composite parent, IPluginRegistry<Plugin> plugins) {
		super();
		this.pluginStates = new TreeMap<String, Boolean>();
		
		this.labels = new TreeMap<String, Label>();
		GridData gd;
		this.control = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		this.control.setLayout(layout);

		this.information = new Label(this.control, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.information.setLayoutData(gd);
		this.information.setText("Description");
		
		gd= new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.createTree(this.control, gd , plugins);
		this.createInfoArea(control, new GridData(GridData.FILL_HORIZONTAL));
		this.createButtons(control, new GridData(GridData.VERTICAL_ALIGN_END));
	}

	private void createButtons(Composite parent, Object layoutData){
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayoutData(layoutData);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.justify = true;
		layout.pack = false;
		c.setLayout(layout);
		
		this.activate= new Button(c, SWT.PUSH);
		this.activate.setText("Activate");
		this.activate.setEnabled(false);
		this.activate.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent e) {
				if(currentPlugin != null){
					pluginStates.put(currentPlugin.getID(), Boolean.TRUE);
					updatePluginInfo(currentPlugin);
				}
			}});
		
		this.deactivate= new Button(c, SWT.PUSH);
		this.deactivate.setText("Deactivate");
		this.deactivate.setEnabled(false);
		this.deactivate.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent e) {				
				if(currentPlugin != null){
					pluginStates.put(currentPlugin.getID(), Boolean.FALSE);
					updatePluginInfo(currentPlugin);
				}
			}});
	}
	
	public void apply(){
		if(this.tree != null){
			for(String id : this.pluginStates.keySet()){
				Plugin p = (Plugin) this.tree.getData(id);
				logger.debug(p.getID() + ":" + this.pluginStates.get(id).toString());
				p.setActivated(this.pluginStates.get(id));
			}
		}
	}
	
	public Control getControl() {
		return this.control;
	}

	public void setDescription(String description){
		if(description != null){
			this.information.setText(description);
		}
	}
	
	private void createInfoArea(Composite parent, Object layoutData){
		String[] texts = new String[]{"Name", "ID", "Provider","Status", "Info"};
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayoutData(layoutData);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 10;
		c.setLayout(layout);
		Label l;
		for(int i = 0; i < texts.length; i++){
			l = new Label(c, SWT.NONE);
			l.setLayoutData(new GridData());
			l.setText(texts[i]);
			
			l = new Label(c, SWT.WRAP);
			l.setLayoutData(new GridData(GridData.FILL_BOTH));
			l.setText("\n");
			this.labels.put(texts[i], l);
		}
		
//		this.info = new Text(parent, SWT.READ_ONLY | SWT.MULTI);
//		this.info.setLayoutData(layoutData);
	}
	
	private void createTree(Composite parent, Object layoutData,
			IPluginRegistry<Plugin> plugins) {
		this.tree = new Tree(parent, SWT.BORDER);
		this.tree.setLayoutData(layoutData);
		for (Plugin p : plugins.getAll().values()) {
			this.tree.setData(p.getID(), p);
			this.createPluginNodes(tree, p);
		}
		this.tree.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent e) {
				Widget item = e.item;
				if(item != null){
					Object data = item.getData();
					if(data != null){
						if(data != currentPlugin){
							updatePluginInfo((Plugin)data);
						}
					}
				}
			}});
	}

	private void updatePluginInfo(Plugin plugin){
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

		this.activate.setEnabled(! activeState);
		this.deactivate.setEnabled(activeState);
		
		this.currentPlugin = plugin;
	}
	
	private void createPluginNodes(Tree parent, Plugin plugin){
		this.pluginStates.put(plugin.getID(), new Boolean(plugin.isActivated()));
		TreeItem pluginItem = new TreeItem(parent, SWT.NONE);
		pluginItem.setData(plugin);
		pluginItem.setText(String.format("%s (%s)", new Object[]{plugin.getID(), plugin.getVersion()}));
		IServiceRegistry services = plugin.getServiceRegistry();
		
		TreeItem item;
		
		if(! services.getAll().isEmpty()){
			item = new TreeItem(pluginItem, SWT.NONE);
			item.setText("Services");
			item.setData(plugin);
			
			for(IService<DotplotContext, PluginHotSpot> s : services.getAll().values()){
				this.createServiceNodes(item, s, plugin);
			}
		}

		IJobRegistry jobs = plugin.getJobRegistry();
		
		if(! jobs.getAll().isEmpty()){
			item = new TreeItem(pluginItem, SWT.NONE);
			item.setText("Jobs");
			item.setData(plugin);
			
			for(IJob j : jobs.getAll().values()){
				this.createJobNodes(item, j, plugin);
			}
		}
	}
	
	private void createServiceNodes(TreeItem parent, IService<DotplotContext,PluginHotSpot> service, Plugin plugin){
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setData(plugin);
		item.setText(service.getID());
				
		Map<String, PluginHotSpot> spots = service.getHotSpots();
		if(! spots.isEmpty()){
			for(IServiceHotSpot s: spots.values())
			this.createHotSpotNodes(item, s, plugin);
		}
	}
	
	private void createHotSpotNodes(TreeItem parent, IServiceHotSpot spot, Plugin plugin){
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setData(plugin);
		item.setText(spot.getID());
		
		for(Extention e: spot.getAllExtentions()){
			this.createExtentionNodes(item, e, plugin);
		}
	}

	private void createExtentionNodes(TreeItem parent, Extention ex, Plugin plugin){
		TreeItem item, extentionItem = new TreeItem(parent, SWT.NONE);
		extentionItem.setData(plugin);
		extentionItem.setText(ex.getExtentionObject().getClass().getName());
				
		for(String name: ex.getParameters().keySet()){
			item = new TreeItem(extentionItem, SWT.NONE);
			item.setData(plugin);
			item.setText(String.format("%s = %s", new Object[]{name,
					ex.getParameter(name)
			}));
		}
	}
	
	
	private void createJobNodes(TreeItem parent, IJob job, Plugin plugin){
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setData(plugin);
		item.setText(job.getClass().getName());
	}
}
