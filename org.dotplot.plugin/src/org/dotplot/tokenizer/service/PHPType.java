package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing PHP - plotsources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PHPType extends SourceCodeFileType {

    /**
     * A static <code>PHPType</code>-object for default usage.
     */
    public static final PHPType type = new PHPType();

    /**
     * Creates a new <code>PHPType</code>.
     */
    public PHPType() {
	super("PHP");
    }

    /**
     * Creates a new <code>PHPType</code>.
     * 
     * @param name
     *            an identifier for the <code>PHPType</code>.
     */
    public PHPType(String name) {
	super(name);
    }
}
