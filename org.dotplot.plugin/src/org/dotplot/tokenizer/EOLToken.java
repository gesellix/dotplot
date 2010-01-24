/*
 * Created on 10.05.2004
 */
package org.dotplot.tokenizer;

import org.dotplot.core.IPlotSource;

/**
 * End Of Line Token. Token for the end of line.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class EOLToken extends Token {
	/**
	 * Creates an EOL-Token in a special line.
	 * 
	 * @param line
	 *            - the line in which the EOL-Token occures
	 */
	public EOLToken(int line) {
		super("\n", Token.TYPE_EOL, line);
	}

	/**
	 * Creates an EOL-Token with a special behaviour in special line.
	 * 
	 * @param source
	 *            - the file in which the token occures
	 * @param line
	 *            - the line in which the token occures
	 */
	public EOLToken(IPlotSource source, int line) {
		super("\n", Token.TYPE_EOL, line);
		this.setSource(source);
	}

	/**
	 * returns the behaviour of the Token.
	 */
	@Override
	public String toString() {
		String s = new String();

		if (this.getSource() != null) {
			s = this.getSource().toString();
		}

		return s + " EOLToken";
	}
}
