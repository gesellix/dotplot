/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer;

/**
 * Interface for a Tokenfilter.
 * <p />
 * A Tokenfilter filters several token out of a token stream and returns the next
 * unfiltered token.
 *
 * @author Christian Gerhardt, Jan Schillings, Sebastian Lorberg
 * @version 1.0
 */
public interface ITokenFilter extends ITokenStream
{
   /**
    * Returns the next filtered token out of the token stream.
    *
    * @param tokenStream -the tokenstream
    *
    * @return - the next token
    *
    * @throws TokenizerException if the tokenizer has a problem...
    */
   public Token getNextToken(ITokenStream tokenStream) throws TokenizerException;

   /**
    * Returns the next unfilterd token out of the tokenstream.
    *
    * @return - The next Token
    *
    * @throws TokenizerException if the tokenizer has a problem...
    */
   public Token getNextToken() throws TokenizerException;

   /**
    * Filteres a token.
    * <p />
    * If the token is not been filtered, the token returns otherwise it returns null.
    *
    * @param token the filter
    *
    * @return the filter token or null
    *
    * @throws TokenizerException if the tokenizer has a problem...
    */
   public Token filterToken(Token token) throws TokenizerException;
}
