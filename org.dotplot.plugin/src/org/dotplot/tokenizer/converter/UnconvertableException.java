/**
 * 
 */
package org.dotplot.tokenizer.converter;

import org.dotplot.tokenizer.TokenizerException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class UnconvertableException extends TokenizerException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 36491415633776614L;

    /**
     * @param message
     */
    public UnconvertableException(String message) {
	super(message);

    }

}
