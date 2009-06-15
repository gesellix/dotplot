/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;

import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * This <code>TaskPart</code> integrates <code>Plugin</code>s into a
 * <code>PluginContext</code>.
 * <p>
 * It is basicly done by invoking the <code>installPlugin()</code> method of the
 * <code>PluginContext</code>.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see IPluginContext
 * @see IPluginContext#installPlugin(P)
 * @see PluginIntegrationService
 */
public class PluginIntegrationTaskPart extends AbstractTaskPart {

    /**
     * The <code>Plugin</code>s to integrate.
     */
    private Collection<? extends IPlugin> plugins;

    /**
     * The context to integrate the <code>Plugin</code>s in.
     */
    private IPluginContext context;

    /**
     * Creates a new <code>PluginIntegrationTaskPart</code>.
     * 
     * @param id
     *            The id of the <code>TaskPart</code>.
     * @param plugins
     *            The <code>Plugin</code>s to integrate.
     * @param context
     *            The context to integrate the plugins in.
     */
    public PluginIntegrationTaskPart(String id,
	    Collection<? extends IPlugin> plugins,
	    IPluginContext<? extends IPlugin> context) {
	super(id);
	if (plugins == null || context == null) {
	    throw new NullPointerException();
	}
	this.plugins = plugins;
	this.context = context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#errorOccured()
     */
    public boolean errorOccured() {
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#getResult()
     */
    public Object getResult() {
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
	for (IPlugin plugin : this.plugins) {
	    try {
		this.context.installPlugin(plugin);
	    } catch (DuplicateRegistrationException e) {
		this.getErrorhandler().warning(this, e);
	    }
	}

	for (IPlugin plugin : this.plugins) {
	    for (String service : plugin.getExtentions().keySet()) {
		for (String spot : plugin.getExtentions().get(service).keySet()) {
		    for (Extention e : plugin.getExtentions().get(service).get(
			    spot)) {
			try {
			    this.context.getServiceRegistry().get(service)
				    .addExtention(spot, e);
			} catch (UnknownServiceHotSpotException e1) {
			    this.getErrorhandler().warning(this, e1);
			} catch (UnknownIDException e1) {
			    this.getErrorhandler().warning(this, e1);
			}
		    }
		}
	    }
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.ITaskPart#setLocalRessources(java.util.Collection
     * )
     */
    public void setLocalRessources(Collection<? extends IRessource> ressouceList)
	    throws InsufficientRessourcesException {
	/* nothing to do */
    }
}
