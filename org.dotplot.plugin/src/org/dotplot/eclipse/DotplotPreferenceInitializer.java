package org.dotplot.eclipse;

import org.apache.log4j.Logger;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.plugins.Plugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * The <code>PreferenceInitializer</code> of the dotplot system.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotPreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * Logger for debugging.
     */
    private static Logger logger = Logger
	    .getLogger(DotplotPreferenceInitializer.class.getName());

    /**
     * Creates a new <code>DotplotPreferenceInitializer</code>.
     */
    public DotplotPreferenceInitializer() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
	logger.debug("initializing preferences");

	// every plugin is activated by default
	IEclipsePreferences node = new DefaultScope()
		.getNode(EclipseConstants.ID_PLUGIN_DOTPLOT);
	DotplotContext context = ContextFactory.getContext();
	for (Plugin plugin : context.getPluginRegistry().getAll().values()) {
	    node.putBoolean(plugin.getID(), true);
	}
    }
}
