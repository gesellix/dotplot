package org.dotplot.tokenizer.service;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;

/**
 * A tokenstream returns one token after the other, until there occure no new one.
 * If the stream ends, the tokenstream returns onlz EOSToken.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface ITokenStream
{
   /**
    * returns the next token from a tokenstream.
    *
    * @return - the Token
    *
    * @throws TokenizerException if the tokenizer has a problem...
    */
   public Token getNextToken() throws TokenizerException;
   
   /**
    * Returns the <code>SourceType</code> of this <code>TokenStream</code>.
    * @return - the <code>SourceType</code>
    */
   public ISourceType getStreamType();

}
