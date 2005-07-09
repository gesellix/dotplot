package org.dotplot.tokenizer.scanner;

import java.io.File;
import java.io.FileNotFoundException;

import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.TokenType;

/**
 * Folgendes auswählen, um die Schablone für den erstellten Typenkommentar zu ändern:
 * Fenster&gt;Benutzervorgaben&gt;Java&gt;Codegenerierung&gt;Code und Kommentare.
 *
 * @author case
 */
public interface IScanner extends ITokenStream
{
   /**
    * sets the file.
    *
    * @param file the file
    *
    * @throws FileNotFoundException
    */
   public void setFile(File file) throws FileNotFoundException;

   /**
    * returns the token types.
    *
    * @return the TokenTypes as Array
    */
   public TokenType[] getTokenTypes();
}
