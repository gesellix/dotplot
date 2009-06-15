/*
 * Created on 26.05.2004
 */
package org.dotplot.ui;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * A container for <code>ConfigurationView</code> objects in form of a
 * <code>TreeMap</code>.
 * 
 * @author Christian Gerhardt <case423@gmx.net
 * @see org.dotplot.ui.ConfigurationView
 */
public class ConfigurationViews extends TreeMap<String, ConfigurationView> {
    /**
     * for being Serializable
     */
    private static final long serialVersionUID = -5681058414337679722L;

    /**
     * Notifies all attached <code>Observers</code> of the
     * <code>ConfigurationViews</code> in the container.
     */
    public void notifyObservers() {
	Iterator iter = this.values().iterator();
	while (iter.hasNext()) {
	    ((ConfigurationView) iter.next()).notifyObservers();
	}
    }
}
