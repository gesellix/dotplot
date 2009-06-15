/*
 * Created on 13.06.2004
 */
package org.dotplot.tokenizer.filter;

import java.util.Map;

import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * Definition of a Base Token Filter.
 * 
 * @author Christian gerhardt <case42@gmx.net>
 * @version 1.0
 */
public abstract class BaseTokenFilter implements ITokenFilter {

    /**
     * the current handled TokenStream.
     */
    private ITokenStream tokenStream;

    /**
     * construct a new token filter.
     */
    public BaseTokenFilter() {
	this(null);
    }

    /**
     * constructs a new token filter with the given tokenStream.
     * 
     * @param tokenStream
     *            the tokenStream to be read.
     */
    public BaseTokenFilter(ITokenStream tokenStream) {
	this.tokenStream = tokenStream;
    }

    public void applyParameter(Map<String, ? extends Object> parameter) {
    };

    /**
     * Filters the Token.
     * 
     * @see org.dotplot.tokenizer.filter.ITokenFilter#filterToken(org.dotplot.tokenizer.Token)
     */
    public abstract Token filterToken(Token token) throws TokenizerException;

    /**
     * Returns the next filterd<code>Token</code> of a TokenStream.
     */
    public Token getNextToken() throws TokenizerException {
	if (this.tokenStream == null) {
	    throw new TokenizerException("no tokenstream defined!");
	}
	return this.tokenStream.getNextToken();
    }

    /**
     * Returns the next filtered <code>Token</code> of a TokenStream.
     * 
     * @param tokenStream
     *            - the TokenStream of the<code>Token</code> which should be
     *            filtered
     * 
     * @return the filtered token <code>Token</code>
     * 
     * @throws TokenizerException
     */
    public Token getNextToken(ITokenStream tokenStream)
	    throws TokenizerException {
	Token token1, token2;

	do {
	    token1 = tokenStream.getNextToken();

	    if (token1 == null) {
		return null;
	    }

	    token2 = this.filterToken(token1);
	} while (token2 == null);

	return token2;
    }

    /**
     * Collects the current TokenStream.
     * 
     * @return the TokenStream
     * 
     * @see #setTokenStream(org.dotplot.tokenizer.service.ITokenStream)
     */
    public ITokenStream getTokenStream() {
	return tokenStream;
    }

    /**
     * Sets the TokenStream which should be used.
     * 
     * @param stream
     *            - the TokenStream
     * 
     * @see #getTokenStream()
     */
    public void setTokenStream(ITokenStream stream) {
	tokenStream = stream;
    }
}
