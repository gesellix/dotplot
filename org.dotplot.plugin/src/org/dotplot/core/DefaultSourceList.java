/**
 * 
 */
package org.dotplot.core;

import java.util.Collection;
import java.util.Vector;

/**
 * Defaultimplementation of the <code>ISourceList</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DefaultSourceList extends Vector<IPlotSource> implements
	ISourceList {

    /**
     * UID for serilisation.
     */
    private static final long serialVersionUID = -339504685531656249L;

    /**
     * Creates a new <code>DefaultSourceList</code>.
     */
    public DefaultSourceList() {
	super();
    }

    /**
     * Creates a new <code>DefaultSourceList</code>.
     * 
     * @param list
     *            A <code>Collection</code> of <code>PlotSources</code>.
     */
    public DefaultSourceList(Collection<IPlotSource> list) {
	super(list);
    }

    /**
     * 
     * Creates a new <code>DefaultSourceList</code>.
     * 
     * @param arg0
     */
    public DefaultSourceList(int arg0) {
	super(arg0);

    }

    /**
     * Creates a new <code>DefaultSourceList</code>.
     * 
     * @param arg0
     * @param arg1
     */
    public DefaultSourceList(int arg0, int arg1) {
	super(arg0, arg1);
    }

    /**
     * Derives the <code>SourceType</code> of the list.
     * 
     * @return The <code>SourceType</code>.
     */
    private ISourceType deriveSourceType() {
	if (this.size() == 0) {
	    return BaseType.type;
	}

	Vector<ISourceType> types = new Vector<ISourceType>();

	for (IPlotSource s : this) {
	    types.add(s.getType());
	}
	return BaseType.deriveCommonSourceType(types);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.ISourceList#getCombinedSourceType()
     */
    public ISourceType getCombinedSourceType() {
	return this.deriveSourceType();
    }

}
