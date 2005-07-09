/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.exceptions;

import org.dotplot.tokenizer.TokenizerException;

/**
 * Ausnahme zur Behandlung einer fehlenden Eingabedatei.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class NoInputFileException extends TokenizerException
{
   /**
    * Erzeugt eine NoInputFileException.
    */
   public NoInputFileException()
   {
      super("Inputfile is missing!");
   }
}
