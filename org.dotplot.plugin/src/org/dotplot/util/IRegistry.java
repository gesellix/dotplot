/**
 * 
 */
package org.dotplot.util;

import java.util.Map;

/**
 * Interface for registrys.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IRegistry<R> {

	/**
	 * 
	 * @param registry
	 * @throws DuplicateRegistrationException
	 */
	public void combine(IRegistry<R> registry)
			throws DuplicateRegistrationException;

	/**
	 * Returns an registered object from the registry.
	 * 
	 * @param id
	 *            - The id of the object.
	 * @return - The object registered by the id.
	 * @throws UnknownIDException
	 *             if the id is unknown to the registry.
	 */
	public R get(String id) throws UnknownIDException;

	/**
	 * Returns a <code>Map</code> of all registry entrys.
	 * 
	 * @return The <code>Map</code>.
	 */
	public Map<String, R> getAll();

	/**
	 * Registers an object to the registry.
	 * 
	 * @param id
	 *            - the id to identify the object.
	 * @param object
	 *            - the object to be registered.
	 * @return
	 * @throws DuplicateRegistrationException
	 *             if an object is allready registed by the id.
	 */
	public R register(String id, R object)
			throws DuplicateRegistrationException;

	/**
	 * Unregisters an object form the registry.
	 * 
	 * @param id
	 *            - the id of the object.
	 * @return - the registred object, or <code>null</code> if no object is
	 *         registred by the id
	 */
	public R unregister(String id);
}
