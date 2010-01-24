/**
 * 
 */
package org.dotplot.tokenizer.service;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class SourceCodeFileType extends TextType {

	public static final SourceCodeFileType type = new SourceCodeFileType();

	/**
	 * 
	 */
	public SourceCodeFileType() {
		super("Sourcecode");

	}

	/**
	 * @param name
	 */
	public SourceCodeFileType(String name) {
		super(name);
	}

}
