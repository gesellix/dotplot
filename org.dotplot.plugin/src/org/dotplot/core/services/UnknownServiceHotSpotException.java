/**
 * 
 */
package org.dotplot.core.services;

/**
 * Exception to indicate an unknown <code>ServiceHotSpot</code>.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class UnknownServiceHotSpotException extends ServiceException {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = 4347412982049966420L;

	/**
	 * Creates a new <code>UnknownServiceHotSpotException</code>.
	 * @param arg0
	 */
	public UnknownServiceHotSpotException(String arg0) {
		super(arg0);
	}

	/**
	 * Creates a new <code>UnknownServiceHotSpotException</code>.
	 * @param spot - the unknown <code>ServiceHotSpot</code>.
	 */
	public UnknownServiceHotSpotException(IServiceHotSpot spot) {
		super(spot.getID());		
	}

}
