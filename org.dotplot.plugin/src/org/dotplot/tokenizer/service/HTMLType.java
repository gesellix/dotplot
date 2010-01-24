package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing HTML - plotsources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class HTMLType extends TextType {

	/**
	 * A static <code>HTMLType</code>-object for default usage.
	 */
	public static final HTMLType type = new HTMLType();

	/**
	 * Creates a new <code>HTMLType</code>.
	 */
	public HTMLType() {
		super("HTML");
	}

	/**
	 * Creates a new <code>HTMLType</code>.
	 * 
	 * @param name
	 *            an identifier for the <code>HTMLType</code>.
	 */
	public HTMLType(String name) {
		super(name);
	}
}
