/**
 * 
 */
package org.dotplot.tokenizer.service;

/**
 * A <code>SourceType</code> for representing C++ - plotsources.
 * <p>
 * This class extends <code>CType</code>, becourse the C specifications are
 * included in the C++ specifications.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class CPlusPlusType extends CType {

	/**
	 * A static <code>CPlusPlusType</code>-object for default usage.
	 */
	public static final CPlusPlusType type = new CPlusPlusType();

	/**
	 * Creates a new <code>CPlusPlusType</code>.
	 */
	public CPlusPlusType() {
		super("C++");
	}

	/**
	 * Creates a new <code>CPlusPlusType</code>.
	 * 
	 * @param name
	 *            an identifier for the <code>CPlusPlusType</code>.
	 */
	public CPlusPlusType(String name) {
		super(name);
	}
}
