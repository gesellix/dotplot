/*
 * Created on 26.05.2004
 */
package org.dotplot;

import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.image.IDotplot;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.Tokenizer;
import org.dotplot.tokenizer.TokenizerException;

/**
 * Interface zwischen der GUI und dem DotplotCreator.
 * Ueber dieses Interface sollten die Gui mit einem DotplotCreator kommunizieren um an
 * den generierten Dotplot zu kommen. Die Art und Weise wie der Dotplot generiert
 * wird, die dazu noetigen Dateien, sollten ueber das Interface IDotplotCreator
 * eingestellt werden.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.IDotplotCreator
 */
public interface IGUIDotplotter
{
   /**
    * Erzeugt einen Dotplot und gibt diesen zurueck.
    *
    * @return - der Dotplot
    *
    * @throws TokenizerException - falls es probleme mit dem Tokenizer gibt
    */
   public IDotplot getDotplot() throws TokenizerException;

   /**
    * Gibt eine Liste der Tokentypen zurueck die aktuell erkannt werden.
    * Falls kein Scanner gewaehlt wurde sollte die Liste die zurueck gegeben
    * wird leer sein.
    *
    * @return - die Tokentypen
    */
   public TokenType[] getTokenTypes();

   /**
    * Gibt die aktuelle Tokenizer Konfiguration zurueck.
    *
    * @return - die Konfiguration
    */
   public IConfiguration getTokenizerConfiguration();

   /**
    * Gibt den aktuellen Tokenizer zurueck.
    *
    * @return - der Tokenizer
    */
   public Tokenizer getTokenizer();

   /**
    * Delivers the current FMatrixManager-Object.
    *
    * @return the current FMatrixObject
    */
   public FMatrixManager getFMatrixController();
}
