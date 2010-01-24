/**
 * 
 */
package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing C - plotsources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class CType extends SourceCodeFileType {

	/**
	 * A static <code>CType</code>-object for default usage.
	 */
	public static final CType type = new CType();

	/**
	 * Creates a new <code>CType</code> object.
	 */
	public CType() {
		super("C");
	}

	/**
	 * Creates a new <code>CType</code> object.
	 * 
	 * @param name
	 *            an identifier for the CType.
	 */
	public CType(String name) {
		super(name);
	}

}
