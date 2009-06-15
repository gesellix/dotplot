package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing XML - plotsources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class XMLType extends TextType {

    /**
     * A static <code>XMLType</code>-object for default usage.
     */
    public static final XMLType type = new XMLType();

    /**
     * Creates a new <code>XMLType</code>.
     */
    public XMLType() {
	super("XML");
    }

    /**
     * Creates a new <code>XMLType</code>.
     * 
     * @param name
     *            an identifier for the <code>XMLType</code>.
     */
    public XMLType(String name) {
	super(name);
    }
}
