package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing Java - plotsources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class JavaType extends SourceCodeFileType {

    /**
     * A static <code>JavaType</code>-object for default usage.
     */
    public static final JavaType type = new JavaType();

    /**
     * Creates a new <code>JavaType</code>.
     */
    public JavaType() {
	super("Java");
    }

    /**
     * Creates a new <code>JavaType</code>.
     * 
     * @param name
     *            an identifier for the <code>JavaType</code>.
     */
    public JavaType(String name) {
	super(name);
    }
}
