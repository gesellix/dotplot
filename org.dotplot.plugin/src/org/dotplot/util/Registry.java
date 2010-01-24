/**
 * 
 */
package org.dotplot.util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class Registry<C> implements IRegistry<C> {

	/**
	 * Storrage for registered objects.
	 */
	protected Map<String, C> registry;

	/**
	 * Creates a new <code>Registry</code>.
	 */
	public Registry() {
		super();
		this.registry = new TreeMap<String, C>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dotplot.services.IServiceManager#combine(org.dotplot.services.
	 * IServiceManager)
	 */
	public void combine(IRegistry<C> registry)
			throws DuplicateRegistrationException {
		if (registry == null) {
			throw new NullPointerException();
		}

		Iterator<String> iter = registry.getAll().keySet().iterator();
		String key;
		while (iter.hasNext()) {
			key = iter.next();
			if (this.registry.containsKey(key)) {
				throw new DuplicateRegistrationException(key);
			}
		}
		this.registry.putAll(registry.getAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServiceManager#getService(java.lang.String)
	 */
	public C get(String id) throws UnknownIDException {
		if (id == null) {
			throw new NullPointerException();
		}
		C object = this.registry.get(id);
		if (object == null) {
			throw new UnknownIDException(id);
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServiceManager#getServices()
	 */
	public Map<String, C> getAll() {
		return this.registry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IServiceManager#registerService(org.dotplot.services
	 * .IService)
	 */
	public C register(String id, C object)
			throws DuplicateRegistrationException {
		if (object == null || id == null) {
			throw new NullPointerException();
		}
		if (this.registry.containsKey(id)) {
			throw new DuplicateRegistrationException(id);
		}
		this.registry.put(id, object);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IServiceManager#unRegisterService(java.lang.String)
	 */
	public C unregister(String serviceID) {
		if (serviceID == null) {
			throw new NullPointerException();
		}
		return this.registry.remove(serviceID);
	}

}