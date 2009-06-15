/**
 * 
 */
package org.dotplot.core.services;

/**
 * Instances of this class represent contexts with no contend and should be used
 * rather then returning the nullpointer.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class NullContext implements IContext {

    /**
     * Default object of this class. This object should be used rather than
     * creating a new object.
     */
    public static final NullContext context = new NullContext();

    /**
     * Creates a new <code>NullContext</code>.
     */
    public NullContext() {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
	return o == null ? false : o.getClass() == NullContext.class;
    }
}
