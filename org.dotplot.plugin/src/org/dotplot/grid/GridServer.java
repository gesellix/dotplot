package org.dotplot.grid;

import org.apache.log4j.Logger;

import java.io.IOException;

import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.grid.framework.Identity;
import org.dotplot.grid.framework.MessageCollaborator;
import org.dotplot.grid.framework.MessageMediator;

/**
 * Server for the Grid.
 *
 * @author Tobias Gesellchen
 */
public class GridServer extends MessageMediator
{
   private static GridServer instance;

   private static final Logger logger = Logger.getLogger(GridServer.class.getName());

//   public static void main(String[] args)
//   {
//      new GridServer(args[0], Integer.parseInt(args[1]));
//   }

   /**
    * Constructs a GridServer.
    *
    * @param localAddress the address to listen to
    * @param localPort    the port to listen to
    */
   public GridServer(String localAddress, int localPort)
   {
      super(localAddress, localPort);

      instance = this;

      new Thread(this).start();
   }

   /**
    * returns the current GridServer.
    *
    * @return the server
    */
   public static GridServer getInstance()
   {
      return instance;
   }

   public synchronized boolean notify(String tag, Object data, Identity src) throws IOException
   {
      boolean b = super.notify(tag, data, src);

//            System.err.println("got it: " + tag + ", " + data);
      if (tag != null && tag.equals(GridClient.TAG_IMAGE))
      {
         GridPlotter.onGridImageReceived(src, data);
      }

      return b;
   }

   /**
    * tell the server to stop.
    */
//   public void stop(String localAddress)
   public void stop()
   {
      // stop it
      super.stop();

      // let a new client connect to the mediator to let it read the above request
      // to stop running (after having connected to our new client completely)
      try
      {
         new MessageCollaborator("SHUTDOWN", getSocket().getInetAddress().getHostAddress(),
               getSocket().getLocalPort());
      }
      catch (ConnectionException e)
      {
         logger.error("Error shutting down server. Connection could not be established.", e);
      }
   }
}
