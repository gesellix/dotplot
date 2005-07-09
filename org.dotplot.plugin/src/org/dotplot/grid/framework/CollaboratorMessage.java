package org.dotplot.grid.framework;

import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * Source code from "Java Distributed Computing", by Jim Farley.
 * <p/>
 * Class: CollaboratorMessage
 * Example: 9-8
 * Description: A message used by collaborators.
 * <p/>
 * Changed by Tobias Gesellchen
 */
class CollaboratorMessage extends Message
{
//   protected GridNode gridNode = null;
   private Logger logger = Logger.getLogger(CollaboratorMessage.class.getName());

   public CollaboratorMessage(GridNode c)
   {
      gridNode = c;
   }

   public CollaboratorMessage(String mid)
   {
      super(mid);
   }

   public boolean Do()
   {
      boolean success = false;

      try
      {
         String mtype = getMessageID();
         Identity from = (Identity) getArg(0);
         Object oarg = getArg(1);

         if (mtype != null && mtype.equals(Message.MSG_IDENTITY))
         {
            gridNode.setIdentity((Identity) oarg);
         }

         gridNode.notify(mtype, oarg, from);

         success = true;
      }
      catch (IOException e)
      {
         logger.error("error notifying collaborator", e);
         success = false;
      }
      return success;
   }

   // We want to handle all messages to the collaborator
   public boolean handles(String msgId)
   {
      return true;
   }

   public Message newCopy()
   {
      CollaboratorMessage copy;
      if (gridNode != null)
      {
         // Make a new CollaboratorMessage with the same Collaborator
         copy = new CollaboratorMessage(gridNode);
         copy.setMessageId(getMessageID());
      }
      else
      {
         copy = new CollaboratorMessage(getMessageID());
      }
      return copy;
   }
}
