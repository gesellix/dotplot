package org.dotplot.tokenizer.service;

import java.io.FileNotFoundException;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.TokenType;

/**
 * Folgendes auswählen, um die Schablone für den erstellten Typenkommentar zu ändern:
 * Fenster&gt;Benutzervorgaben&gt;Java&gt;Codegenerierung&gt;Code und Kommentare.
 *
 * @author case
 */
public interface ITokenizer extends ITokenStream
{
   /**
    * sets the file.
    *
    * @param source the file
    *
    * @throws FileNotFoundException
    */
   public void setPlotSource(IPlotSource source) throws UnassignablePlotSourceException;
   
   /**
    * returns the token types.
    *
    * @return the TokenTypes as Array
    */
   public TokenType[] getTokenTypes();
   
   /**
    * Returns the name of the <code>Tokenizer</code>.
    * @return the <code>Tokenizer</code>'s name.
    */
   public String getName();
   
   /**
    * Sets the <code>Tokenizer</code>'s name.
    * @param name - the name
    */
   public void setName(String name);
}
