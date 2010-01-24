package org.dotplot.tokenizer.service;

import org.dotplot.core.IPlotSource;
import org.dotplot.tokenizer.TokenType;

/**
 * @author Christian Gerhardt case42@gmx.net
 */
public interface ITokenizer extends ITokenStream {
	/**
	 * Returns the name of the <code>Tokenizer</code>.
	 * 
	 * @return the <code>Tokenizer</code>'s name.
	 */
	public String getName();

	/**
	 * Returns the token types.
	 * 
	 * @return the TokenTypes as Array
	 */
	public TokenType[] getTokenTypes();

	/**
	 * Sets the <code>Tokenizer</code>'s name.
	 * 
	 * @param name
	 *            - the name
	 */
	public void setName(String name);

	/**
	 * Sets the file to be tokenized.
	 * 
	 * @param source
	 *            the file
	 * 
	 * @throws UnassignablePlotSourceException
	 *             if the <code>PlotSource</code> is unassignable to the
	 *             <code>Tokenizer<code/>.
	 */
	public void setPlotSource(IPlotSource source)
			throws UnassignablePlotSourceException;
}
