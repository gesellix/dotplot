/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer;

import java.io.File;

/**
 * End Of File Token.
 * A special Token to sign the end of a file.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class EOFToken extends Token
{
   /**
    * Creates a EOF-Token for a special file.
    *
    * @param file - the file
    */
   public EOFToken(File file)
   {
      super("");
      this.setFile(file);
      this.setType(Token.TYPE_EOF);
   }

   /**
    * returns it's behaviour.
    */
   public String toString()
   {
      return this.getFile().toString() + " EOFToken";
   }
}
