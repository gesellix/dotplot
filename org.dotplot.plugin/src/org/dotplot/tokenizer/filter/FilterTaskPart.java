/**
 * 
 */
package org.dotplot.tokenizer.filter;

import java.util.Collection;
import java.util.Map;

import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class FilterTaskPart extends AbstractTaskPart {

    private Map<String, ITokenFilter> filters;

    private IFilterConfiguration config;

    private TokenFilterContainer container;

    private boolean errorOccured;

    /**
     * @param id
     */
    public FilterTaskPart(String id, Map<String, ITokenFilter> filters,
	    IFilterConfiguration config) {
	super(id);
	if (filters == null || config == null) {
	    throw new NullPointerException();
	}
	this.errorOccured = false;
	this.filters = filters;
	this.config = config;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#errorOccured()
     */
    public boolean errorOccured() {
	return this.errorOccured;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#getResult()
     */
    public Object getResult() {
	return this.container;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
	ITokenFilter filter;

	this.container = new TokenFilterContainer();
	for (String filterID : this.config.getFilterList()) {
	    filter = this.filters.get(filterID);
	    if (filter == null) {
		this.getErrorhandler().fatal(this,
			new UnknownIDException(filterID));
		this.container = null;
		this.errorOccured = true;
		return;
	    }
	    filter.applyParameter(this.config.getFilterParameter(filterID));
	    container.addTokenFilter(filter);
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
    }

}
