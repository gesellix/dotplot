/*
 * Created on 13.06.2004
 */
package org.dotplot.tokenizer.tokenfilter;

import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;

/**
 * Merging of Token to one Line.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class SentenceFilter extends BaseTokenFilter
{
   /**
    * Buffer for the current scanned Token
    */
   private Token bufferToken;

   /**
    * The Token which indicates the end of a sentence
    */
   private Token endOfSentenceToken;

   /**
    * Creates a <code>SentenceFilter</code>.
    */
   public SentenceFilter()
   {
      super();
      init();
   }

   /**
    * Creates a <code>SentenceFilter</code> with a TokenStream.
    *
    * @param tokenStream - the TokenStream
    */
   public SentenceFilter(ITokenStream tokenStream)
   {
      super(tokenStream);
      init();
   }

   private void init()
   {
      this.endOfSentenceToken = null;
      this.bufferToken = new Token("", Token.TYPE_STRING, 0);
   }

   /**
    * Filters a TokenStream.
    * <p />
    * All Token will be filtered out until a Sentence Token occures.
    * Then all filtered token will be merged together and will be handled as one Token.
    * If no Sentence Token occures, there won't be filtered any Token.
    *
    * @see org.dotplot.tokenizer.ITokenFilter#filterToken(org.dotplot.tokenizer.Token)
    */
   public Token filterToken(Token token) throws TokenizerException
   {
      Token buffer = null;
      if (this.endOfSentenceToken == null || token == null)
      {
         return token;
      }

      if (token.equals(this.endOfSentenceToken))
      {
         this.addToBufferToken(token);
         buffer = this.bufferToken;
         this.bufferToken = new Token("", Token.TYPE_STRING, 0);
         return buffer;
      }
      else
      {
         switch (token.getType())
         {
            case Token.TYPE_EOF:
            case Token.TYPE_EOL:
               break;

            case Token.TYPE_EOS:
               if (this.bufferToken.equals(new Token("", Token.TYPE_STRING, 0)))
               {
                  return token;
               }
               else
               {
                  buffer = this.bufferToken;
                  this.bufferToken = new Token("", Token.TYPE_STRING, 0);
                  return buffer;
               }

            default :
               this.addToBufferToken(token);

         }
      }
      return null;
   }

   /**
    * Returns the <code>Token</code> which symbolized the end of sentence.
    *
    * @return - the <code>Token</code>
    *
    * @see #setEndOfSentenceToken(org.dotplot.tokenizer.Token)
    */
   public Token getEndOfSentenceToken()
   {
      return endOfSentenceToken;
   }

   /**
    * Sets the <code>Token</code> which symbolizes the end of sentence Token.
    *
    * @param token - the <code>Token</code>
    *
    * @see #getEndOfSentenceToken()
    */
   public void setEndOfSentenceToken(Token token)
   {
      endOfSentenceToken = token;
   }

   /**
    * Fügt ein <code>Token</code> an das Buffertoken an.
    *
    * @param token - das <code>Token</code> das angefügt werden soll
    *
    * @see SentenceFilter#bufferToken
    */
   private void addToBufferToken(Token token)
   {
      if (this.bufferToken.equals(new Token("", Token.TYPE_STRING, 0)))
      {
         this.bufferToken = token;
      }
      else
      {
         this.bufferToken
               = new Token(this.bufferToken.getValue() + " " + token.getValue(), Token.TYPE_STRING, token.getLine());
      }
   }
}
