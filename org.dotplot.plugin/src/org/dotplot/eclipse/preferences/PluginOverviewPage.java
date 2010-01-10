package org.dotplot.eclipse.preferences;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginContext;
import org.dotplot.eclipse.DotplotPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public final class PluginOverviewPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Description of the <code>PreferencePage</code>.
	 */
	private static final String PAGE_DESCRIPTION = "Overview of installed Plugins.";

	/**
	 * 
	 */
	private PluginOverviewGui gui;

	/**
	 * Creates a new <code>PluginOverviewPreferencePage</code>.
	 */
	public PluginOverviewPage() {
		super();
		this.init();
	}

	/**
	 * Creates a new <code>PluginOverviewPreferencePage</code>.
	 * 
	 * @param title
	 *            - the title
	 */
	public PluginOverviewPage(final String title) {
		super(title);
		this.init();
	}

	/**
	 * Creates a new <code>PluginOverviewPreferencePage</code>.
	 * 
	 * @param title
	 *            - the title
	 * @param image
	 *            - the image
	 */
	public PluginOverviewPage(final String title, final ImageDescriptor image) {
		super(title, image);
		this.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent) {
		PluginContext<Plugin> context = ContextFactory.getContext();
		this.gui = new PluginOverviewGui(parent, context.getPluginRegistry());
		this.gui.setDescription(PAGE_DESCRIPTION);
		return this.gui.getControl();
	}

	/**
	 * Initializes the <code>PluginOverviewPage</code>.
	 */
	private void init() {
		DotplotPlugin plugin = DotplotPlugin.getDefault();
		IPreferenceStore store = plugin.getPreferenceStore();
		this.setPreferenceStore(store);
		this.noDefaultAndApplyButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(final IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (this.gui != null) {
			this.gui.apply();
			this.updatePreferences();
			ContextFactory.getContext().inizializeServices();
		}
		return true;
	}

	/**
	 * Updates the preferences based of the current plugin settings in the
	 * <code>DotplotContext</code>.
	 */
	public void updatePreferences() {
		PluginContext<Plugin> context = ContextFactory.getContext();
		for (Plugin plugin : context.getPluginRegistry().getAll().values()) {
			this.getPreferenceStore().setValue(plugin.getID(),
					plugin.isActivated());
		}
	}
}
