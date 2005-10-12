package org.dotplot.tokenizer.exceptions;

import org.dotplot.tokenizer.TokenizerException;

/**
 * Diese Ausnahme wird geworfen, wenn kein Scanner angegeben ist.
 *
 * @author Christian Gerhardt <case$@gmx.net>
 */
public class NoScannerException extends TokenizerException
{

   /**
    * Erzeugt eine neue <code>NoScannerException</code>.
    */
   public NoScannerException()
   {
      super("No scanner - object found");
   }

   /**
    * Erzeugt eine <code>NoScannerException</code> mit einer bestimmten Nachricht.
    *
    * @param msg - die Nachricht
    */
   public NoScannerException(String msg)
   {
      super(msg);
   }
}