/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.exceptions;

import org.dotplot.tokenizer.TokenizerException;

/**
 * Exception die beim einem IO Fehler geworfen wird.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class TokenizerIOException extends TokenizerException
{
   /**
    * Erzeugt eine Tokenizer-IO-Exception.
    *
    * @param message - Fehlernachricht
    */
   public TokenizerIOException(String message)
   {
      super(message);
   }

   /**
    * Erzeugt eine Tokenizer-IO-Exception.
    */
   public TokenizerIOException()
   {
      super("IO ERROR");
   }
}
