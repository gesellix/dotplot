/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.filter;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * A Container for all TokenFilter.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class TokenFilterContainer extends BaseTokenFilter {

	/**
	 * Der Logger zur Ausgabe von Debug-Informationen
	 */
	private static Logger logger = Logger.getLogger(TokenFilterContainer.class
			.getName());

	private ISourceType streamType;

	private List<ITokenFilter> theContainer;

	/**
	 * Creates a<code>TokenFilterContainer</code>. A Container for TokenFilter.
	 * Every Token wich will be scanned are handled throught every Tokenfilter
	 * in this Container.
	 */
	public TokenFilterContainer() {
		super();
		init();
	}

	/**
	 * Creates <code>TokenFilterContainer</code>. A Container for TokenFilter.
	 * Every Token wich will be scanned are handled throught every Tokenfilter
	 * in this Container.
	 * 
	 * @param tokenStream
	 *            the token stream
	 */
	public TokenFilterContainer(ITokenStream tokenStream) {
		super(tokenStream);
		init();
	}

	/**
	 * adds a token filter.
	 * 
	 * @param tokenFilter
	 *            the new filter
	 */
	public void addTokenFilter(ITokenFilter tokenFilter) {
		if (tokenFilter != null) {
			logger.debug("adding filter :" + tokenFilter.getClass().getName());
			this.theContainer.add(tokenFilter);
			Collections.sort(this.theContainer);
			if (this.streamType == null) {
				this.streamType = tokenFilter.getStreamType();
			}
			else {

			}
		}
	}

	/**
	 * Emptys the Container.
	 */
	public void clear() {
		this.theContainer.clear();
		this.streamType = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(ITokenFilter arg0) {
		return 0;
	}

	/**
	 * The Filtering of the Token.
	 * 
	 * @param token
	 *            -- the Token, which schould be filterd
	 * 
	 * @return Token token -- returns the filterd Token
	 * 
	 * @throws TokenizerException
	 */
	@Override
	public Token filterToken(Token token) throws TokenizerException {
		for (ITokenFilter filter : this.theContainer) {
			token = filter.filterToken(token);
			if (token == null) {
				break;
			}
		}

		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
	 */
	public ISourceType getStreamType() {
		return this.streamType;
	}

	/**
	 * Returns the TokenFilter.
	 * 
	 * @return - returns an array of ITokenFilters
	 */
	public ITokenFilter[] getTokenFilters() {
		return this.theContainer.toArray(new ITokenFilter[0]);
	}

	private void init() {
		this.theContainer = new Vector<ITokenFilter>();
		this.streamType = null;
	}
}
