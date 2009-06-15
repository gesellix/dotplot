/**
 * 
 */
package org.dotplot.tokenizer.service;

import org.dotplot.core.ISourceList;
import org.dotplot.tokenizer.converter.SourceListContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class TokenStreamContext extends SourceListContext {

    /**
	 * 
	 */
    private ITokenStream stream;

    /**
	 * 
	 */
    public TokenStreamContext(ITokenStream stream, ISourceList list) {
	super(list);
	if (stream == null) {
	    throw new NullPointerException();
	}
	this.stream = stream;
    }

    /**
     * Returns the assigned <code>TokenStream</code>.
     * 
     * @return - the <code>TokenStream</code>.
     */
    public ITokenStream getTokenStream() {
	return this.stream;
    }
}
