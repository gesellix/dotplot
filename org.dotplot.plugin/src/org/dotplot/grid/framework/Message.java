package org.dotplot.grid.framework;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

class Message extends AbstractMessage {
    protected GridNode gridNode = null;

    private final static Logger logger = Logger.getLogger(Message.class
	    .getName());

    public Message() {
	this("message");
    }

    public Message(String messageID) {
	super(new Vector(), messageID);
    }

    @Override
    public boolean Do() {
	return false;
    }

    @Override
    public boolean handles(String msgId) {
	return false;
    }

    @Override
    public Message newCopy() {
	return new Message(id);
    }

    @Override
    public boolean readArgs(InputStream ins) {
	boolean success = true;

	try {
	    DataInputStream din = new DataInputStream(ins);
	    ObjectInputStream oin = null;

	    String token = din.readUTF();

	    // Read tokens until the "end-of-message" token is seen.
	    while (false == ENDTOKEN.equals(token)) {
		Object arg = token;

		// If an object signal is coming, read with an ObjectInputStream
		if (OBJECT_SIGNAL.equals(token)) {
		    // Next argument is a non-string Object
		    logger.debug("m: reading object");

		    if (oin == null) {
			oin = new ObjectInputStream(ins);
		    }

		    arg = oin.readObject();
		} else if (FILE_SIGNAL.equals(token)) {
		    // If a file signal is coming, read parts and write to
		    // temporary file

		    // Next argument is a non-string Object (File)
		    // save "filename" in message argument
		    arg = readFile(din);
		}

		addArg(arg);

		token = din.readUTF();
	    }
	} catch (Exception e) {
	    logger.error("Failed to read complete argument list", e);
	    success = false;
	}

	return success;
    }

    @Override
    public boolean writeArgs(OutputStream outs) {
	boolean success = true;

	DataOutputStream dout = new DataOutputStream(outs);
	ObjectOutputStream oout = null;

	// Write each argument in order
	Iterator argumentIter = argList.iterator();
	while (argumentIter.hasNext()) {
	    Object arg = argumentIter.next();

	    try {
		if (arg instanceof String) {
		    logger.debug("m: sending token \"" + arg + "\"");
		    dout.writeUTF((String) arg);
		    dout.flush();
		} else if (arg instanceof File) {
		    // Argument is a file handle, so send it as bytes
		    writeFile((File) arg, dout);
		} else {
		    // Argument wasn't a string or a file, so send it as an
		    // object
		    logger.debug("m: sending object " + arg);

		    // write signal to indicate the coming object
		    dout.writeUTF(OBJECT_SIGNAL);
		    dout.flush();

		    logger.debug("m: " + OBJECT_SIGNAL + " sent.");

		    // write object itself
		    if (oout == null) {
			oout = new ObjectOutputStream(new LoggingOutputStream(
				outs));
		    }
		    oout.reset();

		    oout.writeObject(arg);
		    oout.flush();

		    logger.debug("m: complete object sent.");
		}
	    } catch (IOException e) {
		// Something went wrong, indicate failure
		logger.error("m: Got exception writing arg " + arg, e);
		success = false;
	    }
	}

	try {
	    // Finish with the end-of-message token
	    dout.writeUTF(ENDTOKEN);
	    dout.flush();
	} catch (IOException e) {
	    logger.error("m: IOException writing EndToken", e);
	    success = false;
	}

	return success;
    }
}
