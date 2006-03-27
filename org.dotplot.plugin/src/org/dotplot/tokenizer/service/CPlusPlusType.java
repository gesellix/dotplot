/**
 * 
 */
package org.dotplot.tokenizer.service;


/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class CPlusPlusType extends CType {
	
	public static final CPlusPlusType type = new CPlusPlusType();
	
	/**
	 * 
	 */
	public CPlusPlusType() {
		super("C++");

	}

	/**
	 * @param name
	 */
	public CPlusPlusType(String name) {
		super(name);

	}

}
