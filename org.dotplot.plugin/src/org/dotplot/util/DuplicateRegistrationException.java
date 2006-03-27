/**
 * 
 */
package org.dotplot.util;

import org.dotplot.core.services.ServiceException;

/**
 * Instances of this class indicate a duplicate registration.
 * A duplicate registration occures when a registration id is allready
 * assigned. 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DuplicateRegistrationException extends ServiceException {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = 7294760750704346992L;

	/**
	 * Creates a new <code>DuplicateRegistrationException</code>.
	 * @param registrationID - The ID which is duplicate.
	 */
	public DuplicateRegistrationException(String registrationID) {
		super("" + registrationID); // das ("" +) ist dafür, das auch in fall von null ein string als message generiert wird 
	}
}
