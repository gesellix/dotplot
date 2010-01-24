/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IServiceExtentionActivator;
import org.dotplot.core.services.IServiceRegistry;

/**
 * Interface as supertype for plugins.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IPlugin extends IServiceExtentionActivator {

	/**
	 * Returns all <code>Extention</code>s of the <code>Plugin</code>.
	 * <p>
	 * The <code>Extention</code>s are organized as a <code>Map</code> of
	 * <code>Map</code>s of <code>Collection</code>s. The key of the first
	 * <code>Map</code> is the serviceid and the key of the second
	 * <code>Map</code> is the hotspotid.
	 * </p>
	 * 
	 * @return - the <code>Extention</code>s
	 */
	public Map<String, Map<String, Collection<Extention>>> getExtentions();

	/**
	 * Returns information about the <code>Plugin</code>.
	 * 
	 * @return The information.
	 */
	public String getInfo();

	/**
	 * Returns the <code>JobRegistry</code> of the <code>Plugin</code>. The
	 * <code>JobRegistry</code> contains all <code>Job</code>s added by the
	 * plugin.
	 * 
	 * @return The <code>JobRegistry</code>
	 */
	public IJobRegistry getJobRegistry();

	/**
	 * Returns the name of the <code>Plugin</code>.
	 * 
	 * @return The name.
	 */
	public String getName();

	/**
	 * Returns the provider of the <code>Plugin</code>.
	 * 
	 * @return The provider.
	 */
	public String getProvider();

	/**
	 * Returns the <code>ServiceRegistry</code> of the <code>Plugin</code>.
	 * 
	 * @return
	 */
	public IServiceRegistry getServiceRegistry();

	/**
	 * Returns the version of the <code>Plugin</code>.
	 * 
	 * @return The version.
	 */
	public Version getVersion();

	/**
	 * Activates the <code>Plugin</code>.
	 * 
	 * @param status
	 *            <code>true</code> to activate> and <code>false</code> to
	 *            deactivate
	 */
	public void setActivated(boolean status);

	/**
	 * Stores an <code>Extention</code> in the Plugin.
	 * <p>
	 * An <code>Extention</code> must be assigned to a <code>Service</code> and
	 * a <code>HotSpot</code>.
	 * </p>
	 * 
	 * @param serviceID
	 *            - the id of the <code>Service</code>
	 * @param hotSpotID
	 *            - the id of the <code>HotSpot</code>
	 * @param extention
	 *            - the <code>Extention</code> to store
	 */
	public void storeExtention(String serviceID, String hotSpotID,
			Extention extention);
}
