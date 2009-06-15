/**
 * 
 */
package org.dotplot.tokenizer.service;

import org.dotplot.core.BaseType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class TextType extends BaseType {

    public static final TextType type = new TextType();

    /**
     * @param name
     */
    public TextType() {
	super("Text");
    }

    /**
     * @param name
     */
    public TextType(String name) {
	super(name);
    }
}
