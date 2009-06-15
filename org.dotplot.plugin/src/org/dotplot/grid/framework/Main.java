package org.dotplot.grid.framework;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Test class for the collaboration framework.
 */
public class Main {
    final static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * the entry point.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
	int port = 80;

	if (args.length == 0 || args[0].equals("-mediator")) {
	    if (args.length > 1) {
		port = Integer.parseInt(args[1]);
	    }

	    startMediator(port);
	} else if (args.length == 3) {
	    port = Integer.parseInt(args[2]);

	    startCollaborator(args[0], args[1], port);
	} else {
	    showHelp();
	    return;
	}
    }

    private static void showHelp() {
	System.out.println("Usage:");
	System.out.println("java " + Main.class.getName()
		+ "[[-mediator [port]] | [<name> <host> <port>]]");
	System.out.println();
	System.out
		.println("To start the mediator/server, don't pass any arguments or use '-mediator'");
	System.out
		.println("To start the collaborator, give it's name and host/port of mediator");
    }

    private static void startCollaborator(String name, String host, int port) {
	Collaborator c = null;
	try {
	    c = new MessageCollaborator(name, host, port);
	} catch (ConnectionException e) {
	    logger.error("Error on trying to plot over grid", e);
	    return;
	}
	while (c.getIdentity() == null) {
	    ; // wait for ID
	}

	logger.debug("Identity of collaborator: " + c.getIdentity());

	try {
	    // ... here we go
	    testMessaging(c);
	} catch (IOException e) {
	}

	// while (c != null)
	// {
	// }
    }

    private static void startMediator(int port) {
	new Thread(new MessageMediator(null, port)).run();
    }

    private static void testMessaging(Collaborator c) throws IOException {
	logger.debug("\nbroadcast message 1...");
	c.broadcast("msg", "hello world");

	logger.debug("\nbroadcast message 2...");
	c.broadcast("mymsg", new Integer(5));

	logger.debug("\nsend      message 3...");
	c.send("mymsg2", new Integer(42), c.getIdentity());
    }
}
