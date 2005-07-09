/*
 * Created on 12.05.2004
 */
package org.dotplot.tokenizer.tokenfilter;

import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;

/**
 * This Filter collects Token from a Line returns them as one Token.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 12.5.04
 */
public class LineFilter extends BaseTokenFilter
{
   /**
    * Buffer for the Token, which were scanned until now
    */
   private Token bufferToken;

   /**
    * If a empty line should be filtered as particular Token
    */
   private boolean returnEmptyLines;

   /**
    * Creates a <code>LineFilter</code>.
    */
   public LineFilter()
   {
      super();
      init();
   }

   /**
    * Creates a LineFilter <code>LineFilter</code> with a TokenStream.
    *
    * @param tokenStream - the TokenStream
    */
   public LineFilter(ITokenStream tokenStream)
   {
      super(tokenStream);
      init();
   }

   private void init()
   {
      this.returnEmptyLines = true;
      this.bufferToken = new Token("", Token.TYPE_LINE, 0);
   }

   /**
    * Filters Token.
    * <p />
    * All normal Token will be buffered until an EOLToken will be recognized.
    * After that, the buffered Token will be put together and will be seen as only one Token wich
    * represents a whole line.
    * All LineToken will be divided in it's amount through a blank space
    *
    * @param token - the Token which should be filtered
    *
    * @return -the Token, if it wasn't been filtered or NULL
    */
   public Token filterToken(Token token)
   {
      Token buffer;
      switch (token.getType())
      {

         case Token.TYPE_EOL:
            buffer = this.bufferToken;
            this.bufferToken = new Token("", Token.TYPE_LINE, token.getLine() + 1);
            if ((!this.returnEmptyLines) && buffer.getValue().equals(""))
            {
               buffer = null;
            }
            return buffer;

         case Token.TYPE_LINE:
            return token;
         case Token.TYPE_EOF:
         case Token.TYPE_EOS:
            if (this.bufferToken.getValue().equals(""))
            {
               buffer = token;
            }
            else
            {
               buffer = this.bufferToken;
               this.bufferToken = new Token("", Token.TYPE_LINE, 0);
            }
            return buffer;

         case Token.TYPE_IDENT:
         case Token.TYPE_NUMBER:
         case Token.TYPE_STRING:
         default :
            if (this.bufferToken.getValue().equals(""))
            {
               buffer = new Token(token.getValue(), Token.TYPE_LINE, token.getLine());
            }
            else
            {
               buffer
                     = new Token(this.bufferToken.getValue() + " " + token.getValue(),
                           Token.TYPE_LINE,
                           token.getLine());
            }
            this.bufferToken = buffer;
            return null;
      }
   }

   /**
    * Disables the returning of empty lines.
    * If two<code>EOLToken</code> follow after another, it's a sign that this is a empty line.
    * With this, a return of the LineToken will be closed down which are empty lines
    */
   public void dontReturnEmptyLines()
   {
      this.returnEmptyLines = false;
   }

   /**
    * Enbables the returning of empty lines.
    * If two<code>EOLToken</code> follow after another, it's a sign that this is a empty line.
    */
   public void doReturnEmptyLines()
   {
      this.returnEmptyLines = true;
   }

   /**
    * Proves if empty lines will be returned through the Filter.
    *
    * @return - The result
    */
   public boolean isReturningEmptyLines()
   {
      return this.returnEmptyLines;
   }
}
