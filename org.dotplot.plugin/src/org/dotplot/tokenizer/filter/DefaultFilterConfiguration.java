/**
 * 
 */
package org.dotplot.tokenizer.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dotplot.core.IConfiguration;

/**
 * Defaultimplementation of the <code>IFilterConfiguration</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DefaultFilterConfiguration implements IFilterConfiguration {

    /**
     * The filterparameters.
     */
    private Map<String, Map<String, ?>> parameters;

    /**
     * A list of filters.
     */
    private List<String> filters;

    /**
     * Creates a new <code>DefaultFilterConfiguration</code>.
     */
    public DefaultFilterConfiguration() {
	super();
	this.parameters = new TreeMap<String, Map<String, ?>>();
	this.filters = new LinkedList<String>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.IFilterConfiguration#clear()
     */
    public void clear() {
	this.parameters.clear();
	this.filters.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IConfiguration#copy()
     */
    public IConfiguration copy() {
	DefaultFilterConfiguration clone = new DefaultFilterConfiguration();
	clone.filters.addAll(this.filters);
	clone.parameters.putAll(this.parameters);
	return clone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.IFilterConfiguration#getFilterList()
     */
    public List<String> getFilterList() {
	return this.filters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.IFilterConfiguration#getFilterParameter(java.lang
     * .String)
     */
    public Map<String, ?> getFilterParameter(String filterName) {
	if (filterName == null) {
	    throw new NullPointerException();
	} else {
	    return this.parameters.get(filterName);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IConfiguration#objectForm(java.lang.String)
     */
    public IConfiguration objectForm(String serivalizedForm)
	    throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IConfiguration#serializedForm()
     */
    public String serializedForm() throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.IFilterConfiguration#setFilterList(java.util.List)
     */
    public void setFilterList(List<String> list) {
	if (list == null) {
	    throw new NullPointerException();
	} else {
	    this.filters = list;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.IFilterConfiguration#setFilterParameter(java.lang
     * .String, java.util.Map)
     */
    public void setFilterParameter(String filterName, Map<String, ?> parameter) {
	if (filterName == null || parameter == null) {
	    throw new NullPointerException();
	} else {
	    this.parameters.put(filterName, parameter);
	}
    }

}
