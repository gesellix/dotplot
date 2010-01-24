package org.dotplot.grid.framework;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p />
 * Class: Collaborator Example: 9-3 Description: Interface for a participant in
 * a remote collaboration.
 * <p />
 * Changed by Tobias Gesellchen 2005-03-13
 */
public abstract class Collaborator extends GridNode {
	private final Logger logger = Logger
			.getLogger(Collaborator.class.getName());

	protected int mediatorID;

	/**
	 * Send data to all clients in this grid.
	 * 
	 * @param tag
	 *            label for the message
	 * @param data
	 *            the data
	 * 
	 * @return success information
	 * 
	 * @throws IOException
	 *             when send had problems
	 */
	public boolean broadcast(String tag, Object data) throws IOException {
		boolean success = true;
		Message m = new Message(AbstractMessage.MSG_TYPE_BROADCAST);
		m.addArg(getIdentity());
		m.addArg(tag);

		// removed: Objects are recognized automatically by
		// Message.writeArgs(OutputStream outs)
		// m.addArg(Message.OBJECT_SIGNAL);
		m.addArg(data);

		logger.debug("mc: Sending broadcast message \"" + tag + "\"");
		success = handler.sendMsg(m);

		logger.debug("mc: success = " + success);
		return success;
	}

	/**
	 * Connect to a mediator - subclasses dictate properties needed.
	 * 
	 * @param p
	 *            a Properties object containing host and port of the mediator
	 * 
	 * @return true if connection successful
	 */
	public abstract boolean connect(Properties p);

	/**
	 * Disconnect from the mediator.
	 */
	public abstract void disconnect();

	/**
	 * Returns informations about the connection.
	 * 
	 * @return null, if no connection active.
	 */
	public abstract String getConnectionInfo();

	/**
	 * Returns the mediator identity.
	 * 
	 * @return an Identity object representing the mediator identity
	 */
	public Identity getMediatorIdentity() {
		return new Identity(mediatorID);
	}

	// Incoming messages/data
	@Override
	public boolean notify(String tag, Object data, Identity src)
			throws IOException {
		logger.debug(getIdentity() + ": received \"" + tag + "\" object \""
				+ data + "\" from " + src.getName() + " (" + src.getId() + ")");

		return true;
	}

	/**
	 * Outgoing messages/data to a single client in this grid.
	 * 
	 * @param tag
	 *            label for the message
	 * @param data
	 *            the data
	 * @param dst
	 *            the destination client
	 * 
	 * @return success information
	 * 
	 * @throws IOException
	 *             when send had problems
	 */
	public boolean send(String tag, Object data, Identity dst)
			throws IOException {
		boolean success = false;
		Message m = new Message(AbstractMessage.MSG_TYPE_SEND);
		m.addArg(getIdentity());
		m.addArg(dst);
		m.addArg(tag);

		// removed: Objects are recognized automatically by
		// Message.writeArgs(OutputStream outs)
		// m.addArg(Message.OBJECT_SIGNAL);
		m.addArg(data);
		success = handler.sendMsg(m, dst.getId());
		return success;
	}

	@Override
	public String setIdentity(Identity identity) {
		id = identity;

		logger.debug("MID: " + mediatorID);
		logger.debug("CID: " + id.getId());

		return id.getName();
	}
}
