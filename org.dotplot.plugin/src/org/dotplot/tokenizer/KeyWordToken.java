/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer;

/**
 * Token for marking of keywords.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class KeyWordToken extends Token {
	/**
	 * Creates a KeyWordToken.
	 * 
	 * @param value
	 *            - the value of the token
	 */
	public KeyWordToken(String value) {
		super(value);
	}

	/**
	 * Creates a KeywordToken.
	 * 
	 * @param value
	 *            - the value of the Token
	 * @param type
	 *            - the type of the Token
	 */
	public KeyWordToken(String value, int type) {
		super(value, type);
	}

	/**
	 * Creates a KeywordToken.
	 * 
	 * @param value
	 *            - the value of the Token
	 * @param type
	 *            - the type of the Token
	 * @param line
	 *            - the line of the Token
	 */
	public KeyWordToken(String value, int type, int line) {
		super(value, type, line);
	}
}
