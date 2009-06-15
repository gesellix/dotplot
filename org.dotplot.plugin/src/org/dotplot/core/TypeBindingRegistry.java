/**
 * 
 */
package org.dotplot.core;

import java.util.Map;
import java.util.TreeMap;

import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.IRegistry;
import org.dotplot.util.UnknownIDException;

/**
 * A defaultimplementation of the <code>ITypeBindingRegistry</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class TypeBindingRegistry implements ITypeBindingRegistry {

    /**
     * To store the bindings.
     */
    private Map<String, String> registry;

    /**
     * The <code>TypeRegistry</code>.
     */
    private ITypeRegistry typeRegistry;

    /**
     * Creates a new <code>TypeBindingRegistry</code>.
     * 
     * @param typeRegistry
     *            The <code>TypeRegistry</code> for the <code>SourceTypes</code>
     *            to be bound.
     * @throws NullPointerException
     *             if typeRegistry is <code>null</code>.
     */
    public TypeBindingRegistry(ITypeRegistry typeRegistry) {
	super();
	if (typeRegistry == null) {
	    throw new NullPointerException();
	}
	this.registry = new TreeMap<String, String>();
	this.typeRegistry = typeRegistry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.util.IRegistry#combine(org.dotplot.util.IRegistry)
     */
    public void combine(IRegistry<String> registry)
	    throws DuplicateRegistrationException {
	throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.util.IRegistry#get(java.lang.String)
     */
    public String get(String id) throws UnknownIDException {
	if (id == null) {
	    throw new NullPointerException();
	}
	String result = this.registry.get(id);
	if (result == null) {
	    throw new UnknownIDException(id);
	} else {
	    return result;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.util.IRegistry#getAll()
     */
    public Map<String, String> getAll() {
	return this.registry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.ITypeBindingRegistry#getTypeOf(java.lang.String)
     */
    public ISourceType getTypeOf(String ending) {
	if (ending == null) {
	    throw new NullPointerException();
	}
	if (this.registry.containsKey(ending)) {
	    try {
		return this.typeRegistry.get(this.registry.get(ending));
	    } catch (UnknownIDException e) {
		return null;
	    }
	} else {
	    return null;
	}
    }

    /**
     * Returns the assinged <code>TypeRegistry</code>.
     * 
     * @return The <code>TypeRegistry</code>.
     */
    public ITypeRegistry getTypeRegistry() {
	return this.typeRegistry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.util.IRegistry#register(java.lang.String, R)
     */
    public String register(String id, String type)
	    throws DuplicateRegistrationException {
	if (id == null || type == null) {
	    throw new NullPointerException();
	}
	if (this.registry.containsKey(id)) {
	    throw new DuplicateRegistrationException(id);
	}
	try {
	    this.typeRegistry.get(type);
	    this.registry.put(id, type);
	} catch (UnknownIDException e) {
	    throw new IllegalArgumentException(type);
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.util.IRegistry#unregister(java.lang.String)
     */
    public String unregister(String id) {
	if (id == null) {
	    throw new NullPointerException();
	} else {
	    return this.registry.remove(id);
	}
    }

}
