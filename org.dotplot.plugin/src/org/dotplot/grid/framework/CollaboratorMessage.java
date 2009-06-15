package org.dotplot.grid.framework;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p/>
 * Class: CollaboratorMessage Example: 9-8 Description: A message used by
 * collaborators.
 * <p/>
 * Changed by Tobias Gesellchen
 */
class CollaboratorMessage extends Message {
    // protected GridNode gridNode = null;
    private Logger logger = Logger.getLogger(CollaboratorMessage.class
	    .getName());

    public CollaboratorMessage(GridNode c) {
	gridNode = c;
    }

    public CollaboratorMessage(String mid) {
	super(mid);
    }

    @Override
    public boolean Do() {
	boolean success = false;

	try {
	    String mtype = getMessageID();
	    Identity from = (Identity) getArg(0);
	    Object oarg = getArg(1);

	    if (mtype != null && mtype.equals(AbstractMessage.MSG_IDENTITY)) {
		gridNode.setIdentity((Identity) oarg);
	    }

	    gridNode.notify(mtype, oarg, from);

	    success = true;
	} catch (IOException e) {
	    logger.error("error notifying collaborator", e);
	    success = false;
	}
	return success;
    }

    // We want to handle all messages to the collaborator
    @Override
    public boolean handles(String msgId) {
	return true;
    }

    @Override
    public Message newCopy() {
	CollaboratorMessage copy;
	if (gridNode != null) {
	    // Make a new CollaboratorMessage with the same Collaborator
	    copy = new CollaboratorMessage(gridNode);
	    copy.setMessageId(getMessageID());
	} else {
	    copy = new CollaboratorMessage(getMessageID());
	}
	return copy;
    }
}
