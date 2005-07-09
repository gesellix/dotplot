/*
 * Created on 26.05.2004
 */
package org.dotplot;

import org.dotplot.fmatrix.TypeTable;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.IFileList;
import org.dotplot.tokenizer.TokenType;

/**
 * Interface zwischen einem Controller und dem DotplotCreator.
 * Jeder Controller soll nur mit diesem Interface kommunizieren um die
 * Einstellungen des Dotplotters zu veraendern. Anhand dieser Einstellungen
 * erzeugt dann der DotplotCreator einen Dotplot, der mit Hilfe des IGUIDotplotter
 * Interface abgerufen werden kann.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.IGUIDotplotter
 */
public interface IDotplotCreator extends IGUIDotplotter
{
   /**
    * Setzt die fuer den Dotplot zu erzeugende Dateiliste.
    *
    * @param fileList - Die Dateiliste
    */
   public void setFileList(IFileList fileList);

   /**
    * Uebergibt eine Liste an verwendeten Tokentypen an den DotplotCreator.
    *
    * @param tokenTypes - die Liste der Tokentypen;
    */
   public void setTokenTypes(TokenType[] tokenTypes);

   /**
    * Setzt eine neue Konfiguration des Tokenizers.
    *
    * @param configuration - die neue Konfiguration.
    */
   public void setTokenizerConfiguration(IConfiguration configuration);

   /**
    * Set a previously created TypeTable.
    * With a manually set TypeTable the DotplotCreator won't read and analyse
    * any other files given by #setFileList(IFileList)
    *
    * @param typeTable - the new TypeTable to use for the plots
    */
   public void setTypeTable(TypeTable typeTable);

   /**
    * Setzt das Dirtybit.
    */
   public void setDirty();
}
