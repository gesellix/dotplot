package org.dotplot.grid.framework;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p />
 * Class: Mediator Example: 9-4 Description: An interface for a mediator of a
 * remote collaboration.
 * <p />
 * Changes by Tobias Gesellchen
 */
public abstract class Mediator extends GridNode {
	private final static Logger logger = Logger.getLogger(Mediator.class
			.getName());

	/**
	 * sends a message to all clients by using the MessageHandler.
	 * 
	 * @param fromID
	 *            sender
	 * @param mtag
	 *            label for the message
	 * @param obj
	 *            data
	 * 
	 * @return success
	 * 
	 * @throws IOException
	 *             if an error occured
	 */
	public boolean broadcast(Identity fromID, String mtag, Object obj)
			throws IOException {
		logger.debug("mm: broadcasting message \"" + mtag + ", " + obj + "\"");

		Message msg = new Message(mtag);
		msg.addArg(fromID);
		msg.addArg(obj);

		return handler.sendMsg(msg);
	}

	/**
	 * returns all connected members, say "clients".
	 * 
	 * @return the members in a Vector
	 */
	public abstract Vector getMembers();

	/**
	 * signal for an incoming message.
	 * 
	 * @param tag
	 *            the message label
	 * @param data
	 *            data
	 * @param src
	 *            sender ID
	 * 
	 * @return successfully read
	 * 
	 * @throws IOException
	 *             if an error occured
	 */
	@Override
	public boolean notify(String tag, Object data, Identity src)
			throws IOException {
		logger.debug(getIdentity() + ": received \"" + tag + "\" object \""
				+ data + "\" from " + src.getName() + " (" + src.getId() + ")");

		if (tag.equals(AbstractMessage.MSG_IDENTITY_NAME)) {
			handler.updateAgentID((Identity) data);
		}

		return true;
	}

	/**
	 * sends a message to a single client by using the MessageHandler.
	 * 
	 * @param to
	 *            receiver
	 * @param fromID
	 *            sender
	 * @param mtag
	 *            label for the message
	 * @param obj
	 *            data
	 * 
	 * @return success
	 * 
	 * @throws IOException
	 *             if an error occured
	 */
	public boolean send(Identity to, Identity fromID, String mtag, Object obj)
			throws IOException {
		logger.debug("mm: sending message \"" + mtag + ", " + obj + "\" to "
				+ to);

		Message msg = new Message(mtag);
		msg.addArg(fromID);
		msg.addArg(obj);

		return handler.sendMsg(msg, to.getId());
	}
}
