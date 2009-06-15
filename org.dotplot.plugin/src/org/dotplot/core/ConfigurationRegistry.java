/**
 * 
 */
package org.dotplot.core;

import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.IRegistry;
import org.dotplot.util.Registry;
import org.dotplot.util.UnknownIDException;

/**
 * Instances of this class serves as registries for <code>IConfiguration</code>
 * objects.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class ConfigurationRegistry extends Registry<IConfiguration> implements
	IConfigurationRegistry {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.IRegistry#combine(org.dotplot.core.services
     * .IRegistry)
     */
    @Override
    public void combine(IRegistry<IConfiguration> registry)
	    throws DuplicateRegistrationException {
	throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IConfigurationRegistry#copy()
     */
    public IConfigurationRegistry copy() {
	ConfigurationRegistry clone = new ConfigurationRegistry();
	for (String id : this.getAll().keySet()) {
	    try {
		clone.register(id, this.get(id).copy());
	    } catch (DuplicateRegistrationException e) {
		// sollte nicht vorkommen
	    } catch (UnknownIDException e) {
		// sollte nicht vorkommen
	    }
	}
	return clone;
    }

}
