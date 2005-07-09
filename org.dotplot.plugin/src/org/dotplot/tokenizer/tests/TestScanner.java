/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.tests;

import java.io.File;
import java.io.FileNotFoundException;

import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.KeyWordToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.scanner.IScanner;

/**
 * TestScanner Class
 *
 * @author Sebastian Lorberg
 * @version 1.0
 */
class TestScanner implements IScanner
{

   private int index = 0;

   private Token[] tokens = new Token[11];

   public TestScanner()
   {
      this.tokens[0] = new Token("bla", Token.TYPE_STRING, 1);
      this.tokens[1] = new Token("bla", Token.TYPE_STRING, 1);
      this.tokens[2] = new Token("bla", Token.TYPE_STRING, 1);
      this.tokens[3] = new EOLToken(1);
      this.tokens[4] = new Token("bla", Token.TYPE_STRING, 2);
      this.tokens[5] = new KeyWordToken("bla", Token.TYPE_STRING, 2);
      this.tokens[6] = new KeyWordToken("bla", Token.TYPE_STRING, 2);
      this.tokens[7] = new Token("bla", Token.TYPE_STRING, 2);
      this.tokens[8] = new EOLToken(1);
      this.tokens[9] = new EOLToken(1);
      this.tokens[10] = new Token("bla", Token.TYPE_STRING, 3);
   }

   /* (non-Javadoc)
    * @see org.dotplot.tokenizer.scanner.IScanner#setFile(java.io.File)
    */
   public void setFile(File file) throws FileNotFoundException
   {
   }

   /* (non-Javadoc)
    * @see org.dotplot.tokenizer.ITokenStream#getNextToken()
    */
   public Token getNextToken() throws TokenizerException
   {
      Token token;
      if (this.index < this.tokens.length)
      {
         return tokens[index++];
      }
      return new EOSToken();
   }

   public void reset()
   {
      this.index = 0;
   }

   /* (non-Javadoc)
    * @see org.dotplot.tokenizer.scanner.IScanner#getTokenTypes()
    */
   public TokenType[] getTokenTypes()
   {
      return null;
   }
}
