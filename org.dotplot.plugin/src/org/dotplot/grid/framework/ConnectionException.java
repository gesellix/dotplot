package org.dotplot.grid.framework;

/**
 * Represents an exception that can be thrown to signal connection errors.
 */
public class ConnectionException extends Exception
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = -2273439752462613679L;

   /**
    * Constructs a ConnectionException object.
    *
    * @param message a String explaining the error
    */
   public ConnectionException(String message)
   {
      super(message);
   }
}
