/**
 * 
 */
package org.dotplot.tokenizer.service;


/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class PHPType extends SourceCodeFileType {

	public static final PHPType type = new PHPType();
	
	/**
	 * 
	 */
	public PHPType() {
		super("PHP");

	}

	/**
	 * @param name
	 */
	public PHPType(String name) {
		super(name);

	}

}
