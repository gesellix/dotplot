/**
 * 
 */
package org.dotplot.tokenizer.converter;

import org.dotplot.tokenizer.TokenizerException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class UnknownConverterException extends TokenizerException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6800347076398790101L;

    /**
     * @param arg0
     */
    public UnknownConverterException(String arg0) {
	super(arg0);

    }
}
