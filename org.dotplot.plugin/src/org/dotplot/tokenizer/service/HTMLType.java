/**
 * 
 */
package org.dotplot.tokenizer.service;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class HTMLType extends TextType {

	public static final HTMLType type = new HTMLType();
	
	/**
	 * Creates a new <code>HTMLType</code>.
	 */
	public HTMLType() {
		super("HTML");

	}

	/**
	 * Creates a new <code>HTMLType</code>.
	 * @param name
	 */
	public HTMLType(String name) {
		super(name);

	}

}
