/**
 * 
 */
package org.dotplot.tokenizer.service;


/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class CType extends SourceCodeFileType {

	public static final CType type = new CType();
	
	/**
	 * 
	 */
	public CType() {
		super("C");

	}

	/**
	 * @param name
	 */
	public CType(String name) {
		super(name);

	}

}
