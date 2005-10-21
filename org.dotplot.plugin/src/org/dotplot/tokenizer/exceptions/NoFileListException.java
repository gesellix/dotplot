package org.dotplot.tokenizer.exceptions;

import org.dotplot.tokenizer.TokenizerException;

/**
 * Diese Ausnahme wird geworfen, wenn keinne Dateiliste angegeben ist.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class NoFileListException extends TokenizerException
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = -566791228467168009L;

   /**
    * Erzeugt eine neue <code>NoFileListException</code>.
    */
   public NoFileListException()
   {
      super("Filelist is empty!");
   }
}
