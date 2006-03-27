/**
 * 
 */
package org.dotplot.core.services;

import java.util.Map;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface ITaskResultMarshaler {
	
	/**
	 * Marshals the result of a number of <code>TaskParts</code>.
	 * <p>
	 * 	<b><code>Map</code> useing:</b><br>
	 * 	key -> TaskPartID<br>
	 * 	value -> TaskPartResult<br>
	 * </p>
	 * @param taskResult - the result of the <code>TaskParts</code>.
	 * @returns - the marshaled result.
	 */
	public Object marshalResult(Map<String, ? extends Object> taskResult);
}
