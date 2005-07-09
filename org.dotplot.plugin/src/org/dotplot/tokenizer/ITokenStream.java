package org.dotplot.tokenizer;

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
}
