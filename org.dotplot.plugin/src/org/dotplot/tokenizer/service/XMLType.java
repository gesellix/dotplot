/**
 * 
 */
package org.dotplot.tokenizer.service;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class XMLType extends TextType {

	public static final XMLType type = new XMLType();
	
	/**
	 * Creates a new <code>XMLType</code>.
	 */
	public XMLType() {
		super("XML");

	}

	/**
	 * Creates a new <code>XMLType</code>.
	 * @param name
	 */
	public XMLType(String name) {
		super(name);

	}

}
