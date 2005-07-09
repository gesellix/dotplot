package org.dotplot.tokenizer;

/**
 * Base Exception of a Tokenizer.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class TokenizerException extends RuntimeException
{
   /**
    * construct a TokenizerException.
    *
    * @param message the exception details
    */
   public TokenizerException(String message)
   {
      super(message);
   }
}
