/*
 * Created on 03.05.2004
 */
package org.dotplot.tokenizer.service;

import java.io.IOException;
import java.io.InputStreamReader;

import org.dotplot.core.IPlotSource;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.NoPlotSourceException;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.TokenizerIOException;

/**
 * Basisscanner als Erweiterung der von JFlex glieferten Scanner.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 3.5.04
 */
public abstract class BaseScanner implements ITokenizer
{
	
	protected String name;
	
   /**
    * Ein Array mit tokenTypen.
    */
   protected static TokenType[] tokenTypes;

   /**
    * Die Datei die durchgescannt wird
    */
   private IPlotSource source;

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
    * @param source - die Datei
    *
    * @throws FileNotFoundException
    * @see #getFile()
    */
   public void setPlotSource(IPlotSource source) throws UnassignablePlotSourceException
   {
      if (source != null)
      {
    	  if(this.getStreamType().getClass().isAssignableFrom(source.getType().getClass())){    		  
	         this.source = source;
	         if(this.zzReader != null){
	        	 try {
	        		 this.zzReader.close();
	        	 }
	        	 catch (IOException e) {
	        		 /*hmm?*/
	        	 }
	        	 this.zzReader = null;
	         }
    	  }
    	  else{
    		  throw new UnassignablePlotSourceException(source.getURL().toString());
    	  }
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
    	  if(this.source == null) {
    		  throw new NoPlotSourceException();
    	  }
    	  else {
    		  this.zzReader = new InputStreamReader(source.getInputStream());
    		  this.yyreset(this.zzReader);
    	  }
      }

      Token token = null;

      try
      {
         token = this.yylex();
         if (token != null)
         {
            token.setSource(this.source);
         }
         else
         {
            token = new EOSToken();
            this.zzReader.close();
         }
      }
      catch (IOException e)
      {
         throw new TokenizerIOException(e.getMessage());
      }

      return token;
   }

   /**
    * Liefert die Datei die durchgescannt wird.
    *
    * @return -- the actuall file
    *
    * @see #setPlotSource(IPlotSource)
    */
   public IPlotSource gePlotSource()
   {
      return this.source;
   }

   /**
    * Gibt die Tokentypen zurueck, die dieser Scanner erkennt.
    *
    * @return ein Array von Tokentypen.
    */
   public abstract TokenType[] getTokenTypes();
   
   public void setName(String name){
	   this.name = name;
   }
   
   /*
    *  (non-Javadoc)
    * @see org.dotplot.tokenizer.service.ITokenizer#getName()
    */
   public String getName(){
	   return this.name;
   }
}
