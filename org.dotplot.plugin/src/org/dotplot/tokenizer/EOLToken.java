/*
 * Created on 10.05.2004
 */
package org.dotplot.tokenizer;

import java.io.File;

/**
 * End Of Line Token.
 * Token for the end of line.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class EOLToken extends Token
{
   /**
    * Creates an EOL-Token with a special behaviour in special line.
    *
    * @param file - the file in which the token occures
    * @param line - the line in which the token occures
    */
   public EOLToken(File file, int line)
   {
      super("\n", Token.TYPE_EOL, line);
      this.setFile(file);
   }

   /**
    * Creates an EOL-Token in a special line.
    *
    * @param line - the line in which the EOL-Token occures
    */
   public EOLToken(int line)
   {
      super("\n", Token.TYPE_EOL, line);
   }

   /**
    * returns the behaviour of the Token.
    */
   public String toString()
   {
      String s = new String();

      if (this.getFile() != null)
      {
         s = this.getFile().toString();
      }

      return s + " EOLToken";
   }
}
