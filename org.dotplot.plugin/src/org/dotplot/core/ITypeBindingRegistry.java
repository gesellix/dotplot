/**
 * 
 */
package org.dotplot.core;

import org.dotplot.util.IRegistry;

/**
 * A Registry to bind types to file endings.
 * <p>
 * Typical file endings are .txt .java .jar ...
 * </p>
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface ITypeBindingRegistry extends IRegistry<String> {
	
	/**
	 * Returns the bound <code>SourceType</code> of a file ending.
	 * @param ending The file ending.
	 * @return The bound <code>SourceType</code>.
	 */
	public ISourceType getTypeOf(String ending);
}
