/**
 * 
 */
package org.dotplot.tokenizer.service;

import org.dotplot.tokenizer.TokenizerException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class UnassignablePlotSourceException extends TokenizerException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5683374026460026444L;

    /**
     * @param message
     */
    public UnassignablePlotSourceException(String message) {
	super(message);

    }

}
