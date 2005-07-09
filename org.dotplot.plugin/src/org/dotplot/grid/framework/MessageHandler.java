package org.dotplot.grid.framework;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p/>
 * Classes: MessageHandler, AgentConnection, AgentHandler
 * Example: 9-1
 * Description: A new version of message handler that handles each remote client
 * with a separate thread.
 * <p/>
 * Changed by Tobias Gesellchen
 */
class MessageHandler implements Runnable
{
   // A global MessageHandler, for applications where one central handler is used.
   public static MessageHandler current = null;

   private Hashtable agents = new Hashtable();
   private Hashtable handlers = new Hashtable();
   private Vector msgPrototypes = new Vector();

   private final Logger logger = Logger.getLogger(MessageHandler.class.getName());

   synchronized public int getNewAgentID()
   {
      return agents.size();
   }

   synchronized public Vector getAgentIds()
   {
      Vector ids = new Vector();
      Enumeration e = agents.keys();
      while (e.hasMoreElements())
      {
         ids.addElement(e.nextElement());
      }
      return ids;
   }

   synchronized protected int addAgent(Socket socket) throws IOException
   {
      final int id = getNewAgentID();
      final Integer key = new Integer(id);

      agents.put(new Identity(id), new AgentConnection(socket));

      AgentHandlerThread phThread = new AgentHandlerThread(new AgentHandler(id, this));
      handlers.put(key, phThread);

      startAgentHandler(id);

      return id;
   }

   synchronized private void startAgentHandler(int id)
   {
      AgentHandlerThread agentHandler = (AgentHandlerThread) handlers.get(new Integer(id));
      if (agentHandler != null)
      {
         logger.debug("AgentHandler(id=" + id + ").start()...");
         agentHandler.start();
      }
      else
      {
         logger.error("No AgentHandlerThread found for id " + id);
      }
   }

   synchronized public boolean removeAgent(int id)
   {
      boolean success = false;

      final Identity identityByInt = getByInt(id);
      if (identityByInt == null)
      {
         return false;
      }

      final Integer key = new Integer(identityByInt.getId());

      AgentHandlerThread hThread = (AgentHandlerThread) handlers.remove(key);
      if (hThread != null && agents.remove(identityByInt) != null)
      {
         hThread.halt();
         success = true;
      }

      return success;
   }

   synchronized protected AgentConnection getAgent(int id)
   {
      try
      {
         return (AgentConnection) agents.get(getByInt(id));
      }
      catch (NullPointerException e)
      {
         logger.warn("could not find Identity with id=" + id, e);
         return null;
      }
   }

   synchronized protected void updateAgentID(Identity newID)
   {
      final Identity oldID = getByInt(newID.getId());
      final Object value = agents.get(oldID);
      if (oldID == null || value == null)
      {
         logger.warn("could not find agent for id=" + newID.getId());
         return;
      }

      // delete old entry, put value with new ID in list
      agents.put(newID, agents.remove(oldID));
   }

   private synchronized Identity getByInt(int id)
   {
      Identity identity;
      Iterator idIter = getAgentIds().iterator();
      while (idIter.hasNext())
      {
         identity = (Identity) idIter.next();
         if (identity.getId() == id)
         {
            return identity;
         }
      }
      return null;
   }

   public void addMessageType(Message prototype)
   {
      synchronized (msgPrototypes)
      {
         msgPrototypes.addElement(prototype);
      }
   }

   public Message readMsg(int id) throws IOException
   {
      Message msg = null;

      AgentConnection conn = getAgent(id);
      if (conn != null)
      {
         try
         {
            logger.debug("mh: Trying to get lock on input stream of peer " + id);
            final InputStream in = conn.getInputStream();
            synchronized (in)  // get exclusive access to InputStream
            {
               logger.debug("mh: Got lock on input stream of peer " + id);

               DataInputStream din = new DataInputStream(in);
               String msgId = din.readUTF();

               logger.debug("mh: Got message id " + msgId);

               msg = buildMessage(msgId);
               if (msg != null)
               {
                  if (!msg.readArgs(in))
                  {
                     logger.error("mh: Error reading arguments");
                     removeAgent(id);
                     msg = null;
                  }
                  logger.debug("mh: Received complete message id=" + msg.id);
               }
            }
         }
         catch (SocketException s)
         {
            logger.error("mh: Lost connection to peer " + id);
            removeAgent(id);
            msg = null;
         }
         catch (EOFException eof)
         {
            logger.fatal("mh: EOF on peer " + id);
            removeAgent(id);
            msg = null;
         }
         catch (Exception e)
         {
            logger.error("mh: Error reading message", e);
            msg = null;
         }
      }

      return msg;
   }

   /**
    * Send a message to a specific agent.
    */
   public boolean sendMsg(Message msg, int receiverID) throws IOException
   {
      boolean success = false;

      AgentConnection conn = getAgent(receiverID);
      if (conn != null)
      {
         try
         {
            logger.debug("mh: Trying to get lock on output stream of peer " + receiverID);
            final OutputStream out = conn.getOutputStream();
            synchronized (out)  // get exclusive access to OutputStream
            {
               logger.debug("mh: Got lock on output stream of peer " + receiverID);

               DataOutputStream dout = new DataOutputStream(out);

               logger.debug("mh: Printing message id...");
               dout.writeUTF(msg.getMessageID());
               dout.flush();

               logger.debug("mh: Printing message args...");
               msg.writeArgs(out);

               out.flush();
               success = true;
            }
         }
         catch (SocketException s)
         {
            logger.error("mh: Lost connection to peer " + receiverID);
            removeAgent(receiverID);

            success = false;
         }
         catch (Exception e)
         {
            logger.error("mh: error sending message to peer " + receiverID, e);
            success = false;
         }
      }
      else
      {
         logger.error(
               "mh: No connection to peer " + receiverID + " found. Message '" + msg.getMessageID() + "' not sent!");
         success = false;
      }

      return success;
   }

   /**
    * Broadcast a message to all connected agents.
    */
   public boolean sendMsg(Message msg) throws IOException
   {
      Enumeration ids = agents.keys();
      boolean success = true;
      while (ids.hasMoreElements())
      {
         Identity id = (Identity) ids.nextElement();
         logger.debug("mh: Attempting send to peer " + id);
         if (!sendMsg(msg, id.getId()))
         {
            logger.error("mh: Sending failed. Peer: " + id);
            success = false;
         }
         else
         {
            logger.debug("mh: Sent message to peer " + id);
         }
      }

      return success;
   }

   /**
    * Default run() method does nothing...
    */
   public void run()
   {
   }

   protected Message buildMessage(String msgId)
   {
      Message msg = null;
      int numMTypes = msgPrototypes.size();
      for (int i = 0; i < numMTypes; i++)
      {
         Message m = null;
         synchronized (msgPrototypes)
         {
            m = (Message) msgPrototypes.elementAt(i);
         }

         logger.debug("mh: message prototype: " + m.getClass().getName());

         if (m.handles(msgId))
         {
            logger.debug("mh: message prototype used: " + m.getClass().getName());
            msg = m.newCopy();
            msg.setMessageId(msgId);
            break;
         }
      }

      return msg;
   }

   private class AgentHandler implements Runnable
   {
      int peerId;
      MessageHandler handler;
      boolean stop = false;

      public AgentHandler(int id, MessageHandler h)
      {
         peerId = id;
         handler = h;
      }

      public void run()
      {
         final Logger logger = Logger.getLogger(AgentHandler.class.getName());

         logger.debug("ah: Starting peer handler for peer " + peerId);
         while (!stop)
         {
            try
            {
               logger.debug("ah: waiting for messages...");
               Message m = handler.readMsg(peerId);
               logger.debug("ah: read message " + m);
               if (m != null)
               {
                  logger.debug("ah: Got a message from peer " + peerId);
                  m.Do();
               }
            }
            catch (IOException e)
            {
               logger.debug("ah: error reading message from peer " + peerId, e);
            }
         }
      }

      public void stop()
      {
         stop = true;
      }
   }

   private class AgentHandlerThread extends Thread
   {
      private AgentHandler target;

      public AgentHandlerThread(AgentHandler target)
      {
         super(target);
         this.target = target;
      }

      public void halt()
      {
         target.stop();
      }
   }

   private class AgentConnection
   {
      private Socket socket;

      public AgentConnection(Socket socket)
      {
         this.socket = socket;
      }

      synchronized InputStream getInputStream() throws IOException
      {
         return socket.getInputStream();
      }

      synchronized OutputStream getOutputStream() throws IOException
      {
         return socket.getOutputStream();
      }
   }
}
