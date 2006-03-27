/*
 * Created on 06.05.2004
 */
package org.dotplot.tokenizer.filter;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dotplot.core.BaseType;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * General TokenFilter which is deprecated from BaseTokenfilter and which deals
 * with filterList's.
 * 
 * @author Christian Gerhardt, Jan Schillings , Sebastian Lorberg
 * @version 2.0
 */
public class GeneralTokenFilter extends BaseTokenFilter {
	private static Logger logger = Logger.getLogger(GeneralTokenFilter.class
			.getName());
	
	public static final String PARAM = "Tokens";

	private int[] filterList;

	public GeneralTokenFilter() {
		super();
		this.filterList = new int[0];
	}
	
	public GeneralTokenFilter(int[] filterList) {
		super();
		this.filterList = filterList;
	}

	/**
	 */
	public GeneralTokenFilter(ITokenStream tokenStream) {
		super(tokenStream);
		this.filterList = new int[0];
	}

	/**
	 * Filters the Token.
	 * 
	 * @param token
	 *            the token
	 * 
	 * @return token the current Token
	 */
	public Token filterToken(Token token) {
		int type = token.getType();
		for (int i = 0; i < this.filterList.length; i++) {
			if (this.filterList[i] == type) {
				logger.debug("token filtered: " + token.toString());
				return null;
			}
		}
		return token;
	}

	/**
	 * returns the filter list.
	 * 
	 * @return the filter list
	 */
	public int[] getFilterList() {
		return this.filterList;
	}

	public void applyParameter(Map<String, ?> params) {
		try {
			Collection<Integer> tokens = 
				(Collection<Integer>) params.get(PARAM);
			
			int[] filteredTokens = new int[tokens.size()];
			int i = 0;
			for (Integer g : tokens) {
				filteredTokens[i] = g.intValue();
				i++;
			}
			this.filterList = filteredTokens;
		}
		catch (Exception e) {
			// dann eben nicht.
		}
	}


	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
	 */
	public ISourceType getStreamType() {
		return BaseType.type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(ITokenFilter arg0) {
		if(arg0 instanceof LineFilter){
			return -1;
		}
		else if(arg0 instanceof SentenceFilter){
			return -1;
		}
		else {
			return 0;
		}
	}
}
