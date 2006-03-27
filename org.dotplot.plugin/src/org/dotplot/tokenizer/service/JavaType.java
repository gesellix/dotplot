/**
 * 
 */
package org.dotplot.tokenizer.service;


/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class JavaType extends SourceCodeFileType {

	public static final JavaType type = new JavaType();
	
	/**
	 * 
	 */
	public JavaType() {
		super("Java");

	}

	/**
	 * @param name
	 */
	public JavaType(String name) {
		super(name);

	}

}
