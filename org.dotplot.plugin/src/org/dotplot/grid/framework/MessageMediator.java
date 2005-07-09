package org.dotplot.grid.framework;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p />
 * Class: MessageMediator
 * Example: 9-5
 * Description: A mediator that uses message-passing for its communication.
 * <p />
 * Changes by Tobias Gesellchen
 */
public class MessageMediator extends Mediator implements Runnable
{
   private Hashtable connections = new Hashtable();
   private ServerSocket socket = null;
   private String address = null;
   private int port;

   private boolean stop = false;
   private boolean running = false;

   private final static Logger logger = Logger.getLogger(MessageMediator.class.getName());

   /**
    * Constructs a MessageMediator.
    *
    * @param address listen on this address
    * @param port    listen on this port
    */
   public MessageMediator(String address, int port)
   {
      this.address = address;
      this.port = port;
      initHandler();
   }

   protected void initHandler()
   {
      // Add the mediator message "prototype" to the handler
      handler.addMessageType(new MediatorMessage(this));
   }

   /**
    * signal the wish to stop the mediator. it will not be stopped directly!
    */
   public void stop()
   {
      stop = true;
   }

   /**
    * returns the state of the mediator.
    *
    * @return true, if the mediator is ready
    */
   public boolean isRunning()
   {
      return running;
   }

   /**
    * returns the current Socket containing connection data.
    *
    * @return the Socket
    */
   public ServerSocket getSocket()
   {
      return socket;
   }

   public void run()
   {
      // Make the server socket
      try
      {
         socket = new ServerSocket();

         InetAddress localAddress = null;
         if (address != null)
         {
            localAddress = InetAddress.getByName(address);
         }

         socket.bind(new InetSocketAddress(localAddress, port));
      }
      catch (IOException e)
      {
         logger.error("Failed to bind to port " + port);
         return;
      }

      running = true;

      Identity mediatorID = new Identity(-1);
      mediatorID.setName("mediator");
      setIdentity(mediatorID);

      logger.debug("Mediator running on port " + port);

      // Listen for new clients...
      while (!stop)
      {
         try
         {
            // BLOCKS until connection comes in!
            Socket clientConn = socket.accept();

            logger.debug("mm: Got new connection...");

            int id = handler.addAgent(clientConn);

            Message idMsg = new Message(Message.MSG_IDENTITY);
            idMsg.addArg(getIdentity());
            idMsg.addArg(new Identity(id));
            handler.sendMsg(idMsg, id);

            connections.put(new Integer(id), clientConn);
         }
         catch (Exception e)
         {
            logger.error("mm: an error occurred while waiting for clients:", e);
         }
      }

      closeConnections();
      running = false;

      logger.debug("bye!");
   }

   private void closeConnections()
   {
      logger.debug("closing connections...");

      try
      {
         Thread.sleep(2000);
      }
      catch (InterruptedException e)
      {
         //e.printStackTrace();
         // catch silently
      }

      // close client connections
      Vector currentIDs = handler.getAgentIds();
      Iterator idIter = currentIDs.iterator();
      while (idIter.hasNext())
      {
         final int id = ((Identity) idIter.next()).getId();

         handler.removeAgent(id);

         try
         {
            ((Socket) connections.get(new Integer(id))).close();
         }
         catch (IOException e)
         {
            logger.error("error closing socket " + id, e);
         }
      }

      // close server socket
      try
      {
         socket.close();
      }
      catch (IOException e)
      {
         logger.error("error closing server socket", e);
      }
   }

   public Vector getMembers()
   {
      Vector members = new Vector();
      Vector ids = handler.getAgentIds();

      Enumeration idEnum = ids.elements();
      while (idEnum.hasMoreElements())
      {
         members.addElement(idEnum.nextElement());
      }

      return members;
   }
}
