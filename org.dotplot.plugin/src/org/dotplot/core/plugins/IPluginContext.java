/**
 * 
 */
package org.dotplot.core.plugins;

import javax.xml.validation.Schema;

import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.util.DuplicateRegistrationException;

/**
 * Instances of this class are <code>FrameworkContext</code> for the
 * plugin-framework.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IPluginContext<P extends IPlugin> extends IFrameworkContext {

    /**
     * Returns the <code>JobRegistry</code>.
     * 
     * @return The registry.
     */
    public IJobRegistry getJobRegistry();

    /**
     * Returns the path of the pluing directory. Plugins are stored in this
     * directory.
     * 
     * @return - the path of the directory.
     */
    public String getPluginDirectory();

    /**
     * Returns the <code>PluginRegistry</code>.
     * 
     * @return The registry.
     */
    public IPluginRegistry<P> getPluginRegistry();

    /**
     * Returns the <code>Schema</code> to define the dotplotplugin.xml
     * 
     * @return The <code>Schema</code>.
     */
    public Schema getPluginSchema();

    /**
     * Installs a <code>Plugin</code> into the <code>PluginContext</code>.
     * <p>
     * A <code>Plugin</code> in installed in adding its jobs and services to the
     * registrys of the <code>PluginContext</code> and registering it in the
     * <code>PluginRegistry</code>.
     * </p>
     * 
     * @param plugin
     *            - the <code>Plugin</code> to be installed
     * @throws DuplicateRegistrationException
     *             if a <code>Plugin</code> with the specified id is allready
     *             installed.
     */
    public void installPlugin(P plugin) throws DuplicateRegistrationException;
}
