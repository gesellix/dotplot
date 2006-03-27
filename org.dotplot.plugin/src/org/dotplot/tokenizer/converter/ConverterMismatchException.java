/**
 * 
 */
package org.dotplot.tokenizer.converter;

import org.dotplot.tokenizer.TokenizerException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class ConverterMismatchException extends TokenizerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686652013708303306L;

	/**
	 * @param message
	 */
	public ConverterMismatchException(String message) {
		super(message);

	}

}
