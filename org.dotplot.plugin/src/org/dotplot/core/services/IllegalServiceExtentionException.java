/**
 * 
 */
package org.dotplot.core.services;

/**
 * <code>Exception</code> thrown if an <code>Extention</code> is illegal.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class IllegalServiceExtentionException extends ServiceException {

	/**
	 * UID for serilization.
	 */
	private static final long serialVersionUID = -5479225327514246543L;

	/**
	 * Creates a new <code>IllegalServiceExtentionException</code>.
	 * @param hotSpot The <code>ServiceHotSot</code> which caused the <code>Exception</code>.
	 */
	public IllegalServiceExtentionException(IServiceHotSpot hotSpot) {
		super("Illegal extention for " + hotSpot.getID());
	}


}
