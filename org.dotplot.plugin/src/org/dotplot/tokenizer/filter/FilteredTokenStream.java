/**
 * 
 */
package org.dotplot.tokenizer.filter;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FilteredTokenStream implements ITokenStream {

	private ITokenStream stream;
	private ITokenFilter filter;
	
	/**
	 * 
	 */
	public FilteredTokenStream(ITokenStream stream, ITokenFilter filter) {
		if(stream == null || filter == null){
			throw new NullPointerException();
		}
		else {
			this.stream = stream;
			this.filter = filter;			
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.ITokenStream#getNextToken()
	 */
	public Token getNextToken() throws TokenizerException {
		return filter.getNextToken(this.stream);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
	 */
	public ISourceType getStreamType() {
		return this.filter.getStreamType();
	}

}
