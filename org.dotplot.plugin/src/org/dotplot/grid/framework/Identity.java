package org.dotplot.grid.framework;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p/>
 * Class: Identity Example: 9-2 Description: Representation of an agent
 * identity.
 * <p/>
 * Changes by Tobias Gesellchen
 */
public class Identity implements Serializable {
	/**
	 * for being Serializable
	 */
	private static final long serialVersionUID = -1736874104450838025L;

	private Hashtable props = new Hashtable();

	/**
	 * Creates a new Identity.
	 * 
	 * @param id
	 *            a new ID
	 */
	public Identity(int id) {
		props.put("id", new Integer(id));
	}

	@Override
	public boolean equals(Object o) {
		boolean same = false;
		if (o != null && o.getClass() == this.getClass()) {
			Identity oi = (Identity) o;
			if (oi == this
					|| (oi.getId() == this.getId() && ((oi.getName() == null && this
							.getName() == null) || (oi.getName() != null
							&& this.getName() != null && oi.getName()
							.compareTo(this.getName()) == 0)))) {
				same = true;
			}
		}
		return same;
	}

	/**
	 * returns the ID.
	 * 
	 * @return an int representing the id
	 */
	public int getId() {
		return ((Integer) props.get("id")).intValue();
	}

	/**
	 * returns the name, if one was set.
	 * 
	 * @return a String representing the name, or null
	 * 
	 * @see #setName
	 */
	public String getName() {
		return (String) props.get("name");
	}

	/**
	 * returns the property for the key, or null, if none was found.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the value, or null
	 * 
	 * @see #setProperty
	 */
	public Object getProperty(Object key) {
		return props.get(key);
	}

	/**
	 * sets a name for this Identity.
	 * 
	 * @param n
	 *            a String specifying the name
	 * 
	 * @see #getName
	 */
	public void setName(String n) {
		props.put("name", n);
	}

	/**
	 * sets a value for the key.
	 * 
	 * @param key
	 *            the key
	 * @param val
	 *            the value
	 * 
	 * @see #getProperty
	 */
	public void setProperty(Object key, Object val) {
		props.put(key, val);
	}

	@Override
	public String toString() {
		return "Identity id=" + getId() + ", name=" + getName();
	}
}
