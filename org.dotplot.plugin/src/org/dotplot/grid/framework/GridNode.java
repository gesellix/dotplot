package org.dotplot.grid.framework;

import java.io.IOException;

/**
 * Common Node in our Grid. Provides an <code>Identity</code> and can be
 * notified about messages
 */
abstract class GridNode {
    protected MessageHandler handler = new MessageHandler();

    protected Identity id = null;

    public Identity getIdentity() {
	if (id == null || id.getName() == null) {
	    return null;
	} else {
	    return id;
	}
    }

    // Incoming messages/data
    public abstract boolean notify(String tag, Object data, Identity src)
	    throws IOException;

    public String setIdentity(Identity identity) {
	id = identity;
	return id.getName();
    }
}
