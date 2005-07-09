package org.dotplot.grid.framework;

import org.apache.log4j.Logger;

/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p/>
 * Class: MediatorMessage
 * Example: 9-6
 * Description: Subclass of Message that handles messages for our collaboration
 * framework.
 * <p/>
 * Changed by Tobias Gesellchen
 */
class MediatorMessage extends Message
{
//   protected GridNode gridNode = null;
   private final Logger logger = Logger.getLogger(MediatorMessage.class.getName());

   public MediatorMessage(GridNode m)
   {
      if (false == (m instanceof Mediator))
      {
         throw new IllegalArgumentException("I need " + Mediator.class.getName());
      }

      gridNode = m;
   }

   public MediatorMessage(String mid)
   {
      super(mid);
   }

   public boolean Do()
   {
      boolean success = false;

      try
      {
         String mtype = getMessageID();

         if (mtype.compareTo(Message.MSG_TYPE_SEND) == 0)
         {
            logger.debug("mm: Got send message.");

            Identity from = (Identity) getArg(0);
            Identity to = (Identity) getArg(1);
            String tag = (String) getArg(2);
            Object oarg = getArg(3);

            gridNode.notify(tag, oarg, from);
            success = ((Mediator) gridNode).send(to, from, tag, oarg);
         }
         else if (mtype.compareTo(Message.MSG_TYPE_BROADCAST) == 0)
         {
            logger.debug("mm: Got broadcast message.");

            Identity from = (Identity) getArg(0);
            String tag = (String) getArg(1);
            Object oarg = getArg(2);

            gridNode.notify(tag, oarg, from);
            success = ((Mediator) gridNode).broadcast(from, tag, oarg);
         }
      }
      catch (Exception e)
      {
         logger.error("mm: Error parsing message.", e);
         success = false;
      }

      return success;
   }

   // We want to handle all messages.
   public boolean handles(String msgId)
   {
      return true;
   }

   public Message newCopy()
   {
      MediatorMessage copy;
      if (gridNode != null)
      {
         // Make a new MediatorMessage with the same Mediator
         copy = new MediatorMessage(gridNode);
         copy.setMessageId(getMessageID());
      }
      else
      {
         copy = new MediatorMessage(getMessageID());
      }
      return copy;
   }
}
