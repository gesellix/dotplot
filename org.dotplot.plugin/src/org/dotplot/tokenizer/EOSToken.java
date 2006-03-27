/*
 * Created on 12.05.2004
 */
package org.dotplot.tokenizer;


/**
 * This Token signs the end of a token stream.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 11.5.04
 */
public class EOSToken extends Token
{
   /**
    * Creates an EOS token.
    */
   public EOSToken()
   {
      super("EOSToken", Token.TYPE_EOS);
   }
}
