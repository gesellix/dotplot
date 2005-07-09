/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.exceptions;

import org.dotplot.tokenizer.TokenizerException;

/**
 * Diese Ausnahme wird geworfen, wenn etwas mit der Configuration nicht in ordnung ist.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class BadConfigurationException extends TokenizerException
{
   /**
    * Erzeigt eine BadConfigurationException.
    */
   public BadConfigurationException()
   {
      super("Die Konfiguration ist fehlerhaft!");
   }
}
