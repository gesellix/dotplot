package org.dotplot.grid.framework;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p />
 * Class: MessageCollaborator Example: 9-7 Description: A colaborator that
 * communicates using message-passing.
 * <p />
 * Changes by Tobias Gesellchen
 */
public class MessageCollaborator extends Collaborator {
    private final Logger logger = Logger.getLogger(MessageCollaborator.class
	    .getName());

    private String name;

    private Socket mConn;

    /**
     * Constructs a new MessageCollaborator.
     * 
     * @param name
     *            a name for this Client
     * @param host
     *            the Mediator address
     * @param port
     *            the Mediator port
     * 
     * @throws ConnectionException
     *             if an error occured
     */
    public MessageCollaborator(String name, String host, int port)
	    throws ConnectionException {
	initHandler();

	this.name = name;

	Properties p = new Properties();
	p.put("host", host);
	p.put("port", String.valueOf(port));

	if (!connect(p)) {
	    disconnect();
	    logger.error("error connecting to mediator");
	    throw new ConnectionException("error connecting to mediator");
	    // System.exit(1);
	} else {
	    logger.debug("connected to mediator, waiting for id...");
	}
    }

    @Override
    public boolean connect(Properties p) {
	boolean success = false;

	String host = p.getProperty("host");
	String itmp = p.getProperty("port");
	if (host != null && itmp != null) {
	    try {
		int port = 0;
		try {
		    port = Integer.parseInt(itmp);
		} catch (NumberFormatException e) {
		    logger.error("Given port is no number: " + itmp);
		    return false;
		}

		// Make a socket connection to the mediator.
		mConn = new Socket(host, port);
		mediatorID = handler.addAgent(mConn);

		logger.debug("Got socket to Mediator, mediatorID = "
			+ mediatorID);

		// The mediator should send us an identity in a message...
		id = null;

		success = true;
	    } catch (UnknownHostException e) {
		logger.error("Unknown host: " + host, e);
		success = false;
	    } catch (IOException e) {
		logger.error("Error connecting to mediator or reading message",
			e);
		success = false;
	    }
	} else {
	    logger.debug("no host/port given");
	    success = false;
	}

	return success;
    }

    @Override
    public void disconnect() {
	if (mConn != null) {
	    handler.removeAgent(mediatorID);
	    try {
		mConn.close();
	    } catch (IOException e) {
		// e.printStackTrace();
		// catch silently
	    } finally {
		mConn = null;
	    }
	}
    }

    @Override
    public String getConnectionInfo() {
	if (mConn == null || !(mConn.isConnected() && mConn.isBound())) {
	    return null;
	} else {
	    return mConn.getInetAddress().toString() + ":" + mConn.getPort();
	}
    }

    protected void initHandler() {
	handler.addMessageType(new CollaboratorMessage(this));
    }

    @Override
    public String setIdentity(Identity identity) {
	id = identity;
	id.setName(name);

	logger.debug("Got identity from mediator, id = " + id.getId());
	logger.debug("Set id name to " + id.getName());

	logger.debug("MID: " + mediatorID);
	logger.debug("CID: " + id.getId());

	try {
	    send(AbstractMessage.MSG_IDENTITY_NAME, id, getMediatorIdentity());
	    logger.debug("updated id: " + id);
	} catch (IOException e) {
	    logger.error("could not send name for id " + id, e);
	}

	return id.getName();
    }
}
