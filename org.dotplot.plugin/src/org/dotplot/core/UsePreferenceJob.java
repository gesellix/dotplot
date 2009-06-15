/**
 * 
 */
package org.dotplot.core;

import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.eclipse.DotplotPlugin;
import org.dotplot.util.DuplicateRegistrationException;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This <code>Job</code> collects information stored in the eclipse-preferences
 * and uses them on the dotplot - system.
 * <p>
 * Even this class depents on eclipse, it belongs to the dotplot core.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class UsePreferenceJob extends AbstractJob<DotplotContext> {

    /**
     * Logger for debug messages.
     */
    private static Logger logger = Logger.getLogger(UsePreferenceJob.class
	    .getName());

    /**
     * Creates a new <code>UsePreferenceJob</code>.
     */
    public UsePreferenceJob() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.IJob#process(C)
     */
    public boolean process(DotplotContext context) {
	try {
	    logger.debug("restoring preferences");

	    IPreferenceStore store = DotplotPlugin.getDefault()
		    .getPreferenceStore();
	    for (Plugin plugin : context.getPluginRegistry().getAll().values()) {
		plugin.setActivated(store.getBoolean(plugin.getID()));
	    }

	    IConfigurationRegistry registry = context
		    .getConfigurationRegistry();
	    Set<String> keys = new TreeSet<String>(registry.getAll().keySet());
	    for (String key : keys) {
		logger.debug("key: " + key);
		String serializedForm = store.getString(key);
		logger.debug("value: " + serializedForm);
		if (!serializedForm.equals("")) {
		    IConfiguration config2, config1 = registry.unregister(key);
		    try {
			try {
			    config2 = config1.objectForm(serializedForm);
			    registry.register(key, config2);
			} catch (UnsupportedOperationException e) {
			    registry.register(key, config1);
			}
		    } catch (DuplicateRegistrationException e1) {
			// sollte nicht passieren
		    }
		}
	    }

	    context.inizializeServices();

	    return true;
	} catch (Exception e) {
	    this.getErrorHandler().fatal(this, e);
	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core
     * .services.IServiceRegistry)
     */
    public boolean validatePreconditions(IServiceRegistry registry) {
	return true;
    }
}
