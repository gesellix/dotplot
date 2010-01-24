/**
 * 
 */
package org.dotplot.core.services;

/**
 * <code>Exception</code> thown to indicate insufficient ressources.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class InsufficientRessourcesException extends ServiceException {

	/**
	 * UID for serilization.
	 */
	private static final long serialVersionUID = -7425641904595159693L;

	/**
	 * Creates a new <code>InsufficientRessourcesException</code>.
	 */
	public InsufficientRessourcesException() {
		super();
	}

	/**
	 * Creates a new <code>InsufficientRessourcesException</code>.
	 * 
	 * @param message
	 *            An errormessage.
	 */
	public InsufficientRessourcesException(String message) {
		super(message);
	}

}
