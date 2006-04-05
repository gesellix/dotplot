/**
 * 
 */
package org.dotplot.eclipse;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.IGuiService;
import org.dotplot.core.IJobWithProgress;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.Structure;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.eclipse.actions.JobAction;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ConfigurationViews;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Tasks of this <code>Service</code> do nothing, the workingcontext is passed
 * untouched as resultcontext.
 * 
 * <hr>
 * <h2>Workingcontext</h2>
 * {@link org.dotplot.core.services.IContext}
 * <hr>
 * <h2>Resultcontext</h2>
 * {@link org.dotplot.core.services.IContext}
 * <hr>
 * <h2>Hotspots</h2>
 * <p>
 * <b>Hotspot:</b> Menu<br>
 * <b>Extention:</b> {@link org.dotplot.core.services.Structure}<br>
 * <b>Parameter:</b><br>
 * <li>name (required)</li>
 * <li>id (required)</li>
 * <li>menu (optional)</li>
 * <br>
 * <b>Info:</b><br>
 * </p>
 * <br>
 * <p>
 * <b>Hotspot:</b> Entry<br>
 * <b>Extention:</b> {@link org.dotplot.core.services.Structure}<br>
 * <b>Parameter:</b><br>
 * <li>name (required)</li>
 * <li>job (required)</li>
 * <li>menu (optional)</li>
 * <br>
 * <b>Info:</b><br>
 * </p>
 * <br>
 * <p>
 * <b>Hotspot:</b> View<br>
 * <b>Extention:</b>
 * {@link org.dotplot.ui.configuration.views.ConfigurationView}<br>
 * <b>Parameter:</b><br>
 * <br>
 * <b>Info:</b><br>
 * </p>
 * <hr>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class EclipseUIService extends DotplotService implements IGuiService {

	private boolean noOutput = false;

	/**
	 * Logger for debug informations.
	 */
	private static Logger logger = Logger.getLogger(EclipseUIService.class
			.getName());

	/**
	 * ID extention for the menu hotspot.
	 */
	private static final String ID_HOTSPOT_MENU = ".Menu";

	/**
	 * ID extention for the entry hotspot.
	 */
	private static final String ID_HOTSPOT_ENTRY = ".Entry";

	/**
	 * ID extention for the view hotspot.
	 */
	private static final String ID_HOTSPOT_VIEWS = ".View";

	/**
	 * 
	 * @author Christian Gerhardt <case42@gmx.net>
	 */
	private class MenuEntry {

		/**
		 * Id of the <code>MenuEntry</code>..
		 */
		public String id;

		/**
		 * Text to label the <code>MenuEntry</code>..
		 */
		public String text;

		/**
		 * The children of the <code>MenuEntry</code>..
		 */
		public Vector<MenuEntry> children;

		/**
		 * The action to be executed by the menu entry.
		 */
		public IAction action;

		/**
		 * The parent <code>MenuEntry</code>.
		 */
		public MenuEntry parent;

		/**
		 * The <code>Job</code> to be executed by the <code>MenuEntry</code>.
		 */
		public String jobID;

		/**
		 * Creates a new <code>MenuEntry</code>.
		 * 
		 * @param id -
		 *            the id of the <code>MenuEntry</code>..
		 * @param text -
		 *            the text of the <code>MenuEntry</code>..
		 */
		public MenuEntry(String id, String text) {
			this.id = id;
			this.text = text;
			this.children = new Vector<MenuEntry>();
			this.action = null;
		}
	}

	/**
	 * 
	 */
	private IMenuManager menuManager;

	/**
	 * 
	 */
	private Map<String, MenuEntry> menus;

	/**
	 * 
	 */
	private ConfigurationViews views;

	/**
	 * Counter for to generate unique ids.
	 */
	private static int id_number = 0;

	/**
	 * Creates a new <code>EclipseUIService</code>.
	 * 
	 * @param id -
	 *            the id of the service.
	 */
	public EclipseUIService(String id) {
		super(id);
		this.addHotSpot(new PluginHotSpot(id + ID_HOTSPOT_ENTRY,
				Structure.class));
		this
				.addHotSpot(new PluginHotSpot(id + ID_HOTSPOT_MENU,
						Structure.class));
		this.addHotSpot(new PluginHotSpot(id + ID_HOTSPOT_VIEWS,
				ConfigurationView.class));
		this.menus = new TreeMap<String, MenuEntry>();
		this.views = new ConfigurationViews();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IService#init()
	 */
	public void init() {
		super.init();

		if (this.frameworkContext instanceof DotplotContext) {
			((DotplotContext) this.frameworkContext).setGuiServiceID(this
					.getID());
		}

		if (menuManager != null) {
			for (MenuEntry entry : this.menus.values()) {
				if (entry.parent == null) {
					menuManager.remove(entry.id);
				}
			}
		}

		this.menus.clear();

		String menu, id, name, job;
		MenuEntry entry, parent;

		try {
			Collection<Extention> extentions = this.getHotSpot(
					this.getID() + ID_HOTSPOT_MENU).getActiveExtentions();
			for (Extention e : extentions) {
				name = e.getParameter("name");
				id = e.getParameter("id");
				menu = e.getParameter("menu");
				if (id != null && name != null) {
					if (menu == null) {
						entry = new MenuEntry(id, name);
						if (!this.menus.containsKey(id))
							this.menus.put(id, entry);
					}
					else {
						parent = this.menus.get(menu);
						if (parent != null) {
							entry = new MenuEntry(id, name);
							parent.children.add(entry);
							entry.parent = parent;
							if (!this.menus.containsKey(id))
								this.menus.put(id, entry);
						}
					}
				}
			}
		}
		catch (UnknownServiceHotSpotException e) {
			// sollte nicht vorkommen
		}
		try {
			Collection<Extention> extentions = this.getHotSpot(
					this.getID() + ID_HOTSPOT_ENTRY).getActiveExtentions();
			for (Extention e : extentions) {
				name = e.getParameter("name");
				job = e.getParameter("job");
				menu = e.getParameter("menu");
				if (name != null && job != null && menu != null) {
					parent = this.menus.get(menu);
					if (parent != null) {
						entry = new MenuEntry(genID(name), name);
						entry.jobID = job;
						entry.parent = parent;
						parent.children.add(entry);
					}
				}
			}
		}
		catch (UnknownServiceHotSpotException e) {
			// sollte nicht vorkommen
		}

		try {
			Collection<Extention> extentions = this.getHotSpot(
					this.getID() + ID_HOTSPOT_VIEWS).getActiveExtentions();
			for (Extention e : extentions) {
				id = e.getParameter("id");
				if (id != null) {
					ConfigurationView view = (ConfigurationView) e
							.getExtentionObject();
					view.setContext(this.frameworkContext);
					this.views.put(id, view);
				}
			}
		}
		catch (UnknownServiceHotSpotException e) {
			// sollte nicht vorkommen
		}

		if (menuManager != null) {
			this.fillMenuBar(this.menuManager);
			this.menuManager.markDirty();
			this.menuManager.updateAll(true);
		}
	}

	/**
	 * 
	 * @param menuBar
	 */
	public void fillMenuBar(IMenuManager menuBar) {
		this.menuManager = menuBar;
		for (MenuEntry entry : this.menus.values()) {
			if (entry.parent == null) {
				MenuManager menu = new MenuManager(entry.text, entry.id);
				try {
					// on first call, the menus from the plugin.xml aren't
					// created yet,
					// so this id is unknown at that time.
					menuBar
							.insertBefore(EclipseConstants.ID_MENU_DOTPLOT,
									menu);
				}
				catch (IllegalArgumentException e) {
					menuBar.add(menu);
				}
				this.fillMenuBar(menu, entry);
			}
		}
	}

	/**
	 * 
	 * @param menu
	 * @param menuEntry
	 */
	private void fillMenuBar(IMenuManager menu, MenuEntry menuEntry) {
		for (MenuEntry entry : menuEntry.children) {
			if (entry.jobID != null) {
				menu.add(new JobAction(entry.text, entry.jobID));
			}
			else {
				MenuManager subMenu = new MenuManager(entry.text, entry.id);
				menu.add(subMenu);
				this.fillMenuBar(subMenu, entry);
			}
		}
	}

	/**
	 * Generates an unique id.
	 * 
	 * @param text -
	 *            text on the end of the generated id
	 * @return the generated id
	 */
	private static String genID(String text) {
		id_number++;
		return "ID_" + id_number + "_" + text;
	}

	/**
	 * 
	 * @return
	 */
	public ConfigurationViews getConfigurationViews() {
		return this.views;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	@Override
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	@Override
	public boolean workingContextIsCompatible(Class contextClass) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	@Override
	public Class getResultContextClass() {
		return IContext.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	@Override
	public ITask createTask() {
		return new Task("UI Task", new ITaskResultMarshaler() {

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				return null;
			}
		}, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#showErrorMessage(java.lang.String)
	 */
	public void showErrorMessage(String message) {
		if (this.noOutput) return;
		MessageDialog.openError(this.getShell(),
				"Error", message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#showErrorMessage(java.lang.String,
	 *      java.lang.String)
	 */
	public void showErrorMessage(String message, String details) {
		if (this.noOutput) return;
		MessageDialog.openError(this.getShell(),
				message, details);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#showMessage(java.lang.String)
	 */
	public void showMessage(String message) {
		if (this.noOutput) return;
		MessageDialog.openInformation(this.getShell(),
		"Info", message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#appendToLog(java.lang.String)
	 */
	public void appendToLog(String message) {
		if (this.noOutput) return;
	}

	/**
	 * Returns the <code>Shell</code> of the active workbenchwindow.
	 * 
	 * @return The <code>Shell</code>.
	 */
	public Shell getShell() {
		if (this.noOutput) {
			return null;
		}
		else {
			try {
				return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getShell();
			}
			catch (Exception e) {
				logger.debug(e);
				return new Shell();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	@Override
	protected IContext createResultContext() {
		return this.getWorkingContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#setNoOutput(boolean)
	 */
	public void setNoOutput(boolean value) {
		this.noOutput = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IGuiService#executeJobWithProgress(org.dotplot.core.JobWithProgress)
	 */
	public boolean executeJobWithProgress(IJobWithProgress job) {
		try {
			new ProgressMonitorDialog(this.getShell()).run(true, true, job);
		}
		catch (InvocationTargetException e) {
			// handle exception
		}
		catch (InterruptedException e) {
			// handle cancelation
		}
		return job.getProcessResult();
	}

}
