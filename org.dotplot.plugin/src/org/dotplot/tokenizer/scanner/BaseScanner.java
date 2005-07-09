/*
 * Created on 03.05.2004
 */
package org.dotplot.tokenizer.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.dotplot.tokenizer.DotplotFile;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.exceptions.NoInputFileException;
import org.dotplot.tokenizer.exceptions.TokenizerIOException;

/**
 * Basisscanner als Erweiterung der von JFlex glieferten Scanner.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 3.5.04
 */
public abstract class BaseScanner implements IScanner
{
   /**
    * Ein Array mit tokenTypen.
    */
   private static TokenType[] tokenTypes;

   /**
    * Die Datei die durchgescannt wird
    */
   private File file;

   /**
    * the input device
    */
   private java.io.Reader zzReader;

   /**
    * Liefert das nächste Yytoken.
    * Ist hier nur zur kompatibilität mit den Klassen aufgeführt die
    * von Flex generiert werden.
    *
    * @return - das nächste Yytoken
    */
   protected abstract Token yylex() throws java.io.IOException;

   /**
    * Resettet einen Reader.
    * Ist hier nur zur kompatibilität mit den Klassen aufgeführt die
    * von Flex generiert werden.
    *
    * @param reader - der Reader
    */
   public abstract void yyreset(java.io.Reader reader);

   /**
    * Setzt die Datei die durchgescannt werden soll.
    *
    * @param file - die Datei
    *
    * @throws FileNotFoundException
    * @see #getFile()
    */
   public void setFile(File file) throws FileNotFoundException
   {
      if (file != null)
      {
         if (file instanceof DotplotFile)
         {
            this.zzReader = new FileReader(((DotplotFile) file).getFile());
         }
         else
         {
            this.zzReader = new FileReader(file);
         }
         this.yyreset(this.zzReader);
         this.file = file;
      }
   }

   /**
    * Holt das nächste Token aus der Datei.
    *
    * @return - das nächte Token
    *
    * @throws TokenizerException
    */
   public Token getNextToken() throws TokenizerException
   {
      if (this.zzReader == null)
      {
         throw new NoInputFileException();
      }

      Token token = null;

      try
      {
         token = this.yylex();
      }
      catch (IOException e)
      {
         throw new TokenizerIOException(e.getMessage());
      }

      if (token != null)
      {
         token.setFile(this.file);
      }
      else
      {
         token = new EOSToken();
      }

      return token;
   }

   /**
    * Liefert die Datei die durchgescannt wird.
    *
    * @return -- the actuall file
    *
    * @see #setFile(java.io.File)
    */
   public File getFile()
   {
      return this.file;
   }

   /**
    * Gibt die Tokentypen zurueck, die dieser Scanner erkennt.
    *
    * @return ein Array von Tokentypen.
    */
   public abstract TokenType[] getTokenTypes();
}
