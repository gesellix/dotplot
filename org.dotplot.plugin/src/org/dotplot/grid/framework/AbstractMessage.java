package org.dotplot.grid.framework;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

abstract class AbstractMessage {
    protected class LoggingOutputStream extends FilterOutputStream {
	private Logger logger = Logger.getLogger(LoggingOutputStream.class
		.getName());

	private int counter;

	public LoggingOutputStream(OutputStream out) {
	    super(out);
	    counter = 0;
	}

	@Override
	public void flush() throws IOException {
	    logger.debug("transmitted bytes: " + counter);
	    counter = 0;
	    super.flush();
	}

	private void update(int add) {
	    counter += add;
	    // if ((counter % 1000) == 0)
	    // {
	    // logger.debug("count: " + counter);
	    // }
	}

	@Override
	public void write(byte b[]) throws IOException {
	    super.write(b);
	    update(b.length);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
	    super.write(b, off, len);
	    update(len);
	}

	@Override
	public void write(int b) throws IOException {
	    super.write(b);
	    update(1);
	}
    }

    private final static Logger logger = Logger.getLogger(AbstractMessage.class
	    .getName());

    protected final static int BUF_SIZE = 1024;

    protected static File readFile(DataInputStream din) throws IOException {
	logger.debug("m: reading file");

	final String fileName = din.readUTF();

	// we should read until complete file is read - not more...
	final long fileSize = din.readLong();

	final File tempFile = File.createTempFile(fileName.substring(0,
		fileName.lastIndexOf('.'))
		+ "_", fileName.substring(fileName.lastIndexOf('.')));
	tempFile.deleteOnExit();
	FileOutputStream fout = new FileOutputStream(tempFile);

	long counter = 0;
	int bytes_read;

	byte[] buffer = new byte[BUF_SIZE];
	if (fileSize < BUF_SIZE) {
	    buffer = new byte[(int) fileSize];
	}

	while (true) {
	    bytes_read = din.read(buffer);
	    if (bytes_read == -1) {
		break;
	    }

	    fout.write(buffer, 0, bytes_read);
	    counter += bytes_read;

	    if (counter >= fileSize) {
		break;
	    }

	    if ((fileSize - counter) < BUF_SIZE) {
		buffer = new byte[(int) (fileSize - counter)];
	    }
	}

	fout.flush();
	fout.close();

	logger.debug("m: file written to " + tempFile.getAbsolutePath() + " ("
		+ counter + " bytes)");

	return tempFile;
    }

    protected static void writeFile(File file, DataOutputStream dout)
	    throws IOException {
	logger.debug("m: sending file " + file);

	final long fileSize = file.length();

	logger.debug("m: fileSize " + fileSize);

	// write signal to indicate the coming object
	dout.writeUTF(FILE_SIGNAL);
	dout.writeUTF(file.getName());
	dout.writeLong(fileSize);
	dout.flush();

	logger.debug("m: " + FILE_SIGNAL + " sent.");

	// write file itself
	FileInputStream fin = new FileInputStream(file);

	long counter = 0;
	int bytes_read;
	byte[] buffer = new byte[BUF_SIZE];
	while (true) {
	    bytes_read = fin.read(buffer);
	    if (bytes_read == -1) {
		break;
	    }

	    dout.write(buffer, 0, bytes_read);
	    counter += bytes_read;
	}

	dout.flush();
	fin.close();

	logger.debug("m: complete file sent. (" + counter + " bytes)");
    }

    protected String id;

    protected Vector argList;

    protected final static String ENDTOKEN = "END";

    public final static String OBJECT_SIGNAL = "#OBJ";

    public final static String FILE_SIGNAL = "#FILE";

    public final static String MSG_IDENTITY = "identity";

    public final static String MSG_IDENTITY_NAME = "id_name";

    public final static String MSG_TYPE_BROADCAST = "broadcast";

    public final static String MSG_TYPE_SEND = "send";

    protected AbstractMessage(Vector argList, String id) {
	this.argList = argList;
	this.id = id;
    }

    public void addArg(Object arg) {
	logger.debug("m: adding argument: " + arg);
	argList.addElement(arg);
    }

    public Vector argList() {
	return (Vector) argList.clone();
    }

    public abstract boolean Do();

    public Object getArg(int idx) {
	if (idx < argList.size()) {
	    return argList.elementAt(idx);
	} else {
	    return null;
	}
    }

    public String getMessageID() {
	return id;
    }

    public abstract boolean handles(String msgId);

    public abstract Message newCopy();

    public abstract boolean readArgs(InputStream ins);

    public void setMessageId(String mid) {
	id = mid;
    }

    public abstract boolean writeArgs(OutputStream outs);
}
