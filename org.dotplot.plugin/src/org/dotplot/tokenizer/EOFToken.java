/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer;

import org.dotplot.core.IPlotSource;

/**
 * End Of File Token. A special Token to sign the end of a file.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class EOFToken extends Token {
	/**
	 * Creates a EOF-Token for a special file.
	 * 
	 * @param source
	 *            - the file
	 */
	public EOFToken(IPlotSource source) {
		super("");
		this.setSource(source);
		this.setType(Token.TYPE_EOF);
	}

	/**
	 * returns it's behaviour.
	 */
	@Override
	public String toString() {
		return this.getSource().toString() + " EOFToken";
	}
}
