package org.dotplot.tokenizer;

import org.dotplot.core.IPlotSource;

/**
 * Representation type of a Token.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class Token {
	/**
	 * Standard Token type for end of files.
	 */
	public static final int TYPE_EOF = -1;

	/**
	 * Standard token type for end of line.
	 */
	public static final int TYPE_EOL = -2;

	/**
	 * Standard token type for end of stream. This Token returns, when the end
	 * of the tokenstream is reached.
	 */
	public static final int TYPE_EOS = -3;

	/**
	 * Standard Tokentype for a text line.
	 */
	public static final int TYPE_LINE = -4;

	/**
	 * Standard Tokentype for an Identifier.
	 */
	public static final int TYPE_IDENT = -5;

	/**
	 * Standard Tokentype for numbers.
	 */
	public static final int TYPE_NUMBER = -6;

	/**
	 * Standard Tokentype for strings.
	 */
	public static final int TYPE_STRING = -7;

	/**
	 * Die Zeichenkette die durch das Token repräsentiert wird. The string which
	 * is represented through the token
	 */
	private String value;

	/**
	 * The source of the <code>Token</code>.
	 */
	private IPlotSource source;

	/**
	 * The line in which the Token occures.
	 */
	private int line;

	/**
	 * the type of the Token
	 * <p/>
	 * The Type depends on the used language and the definition and can't be
	 * predicted in any way.
	 */
	private int type;

	/**
	 * Creates a Token.
	 * 
	 * @param tokenValue
	 *            the string wich represents the Token
	 */
	public Token(final String tokenValue) {
		this(tokenValue, -1, -1);
	}

	/**
	 * Creates a Token.
	 * 
	 * @param tokenValue
	 *            The string which represents the Token
	 * @param typeValue
	 *            a type
	 */
	public Token(final String tokenValue, final int typeValue) {
		this(tokenValue, typeValue, -1);
	}

	/**
	 * Creates a Token.
	 * 
	 * @param tokenValue
	 *            - The string which represents the Token
	 * @param typeValue
	 *            a type
	 * @param lineValue
	 *            the line
	 */
	public Token(final String tokenValue, final int typeValue,
			final int lineValue) {
		this.value = tokenValue;
		this.line = lineValue;
		this.type = typeValue;
	}

	/**
	 * Proves if two objects are equal.
	 * <p />
	 * It returns only a positiv resulst, if the objects are Token. Two Token
	 * are equal, if the type and value are equal.
	 * 
	 * @param object
	 *            the object which should be examined
	 * 
	 * @return the result
	 */
	@Override
	public boolean equals(final Object object) {
		Token token;
		if (object instanceof Token) {
			token = (Token) object;
			return token.getValue().equals(this.getValue());
		}
		return false;
	}

	/**
	 * Proves if two Token are equal.
	 * 
	 * @param token
	 *            the Toke which should be examined
	 * 
	 * @return the result
	 */
	public boolean equals(final Token token) {
		return token.getValue().equals(this.getValue());
	}

	/**
	 * returns the line which contains the Token.
	 * 
	 * @return - The line
	 * 
	 * @see #setLine(int)
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * Returns the file which contains the Token.
	 * 
	 * @return - The file
	 * 
	 * @see #setSource(java.io.File)
	 */
	public final IPlotSource getSource() {
		return this.source;
	}

	/**
	 * returns the token type.
	 * 
	 * @return - the token type
	 * 
	 * @see #setType(int)
	 */
	public final int getType() {
		return this.type;
	}

	/**
	 * Returns the string which represents the Token.
	 * 
	 * @return - the string
	 */
	public final String getValue() {
		return this.value;
	}

	/**
	 * returns the line which contains the Token.
	 * 
	 * @param number
	 *            - the line number
	 * 
	 * @see #getLine()
	 */
	public final void setLine(final int number) {
		this.line = number;
	}

	/**
	 * Sets the <code>PlotSource</code>of the Token.
	 * 
	 * @param plotSource
	 *            - the <code>PlotSource</code>
	 * 
	 * @see #getSource()
	 */
	public final void setSource(final IPlotSource plotSource) {
		this.source = plotSource;
	}

	/**
	 * Sets the Tokentype.
	 * 
	 * @param tokenType
	 *            - the Tokentype
	 * 
	 * @see #getType()
	 */
	public final void setType(final int tokenType) {
		this.type = tokenType;
	}

	/**
	 * returns the string representation of the Token.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.source != null) {
			sb.append(source.toString() + ' ');
		}

		sb.append("Token");

		if (this.type != -1) {
			sb.append("(");
			sb.append(this.type);
			sb.append(")");
		}

		sb.append(" \"" + this.value + "\"");

		if (this.line > -1) {
			sb.append(" in line ");
			sb.append(this.line);
		}

		return sb.toString();
	}
}
