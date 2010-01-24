/**
 * 
 */
package org.dotplot.core.services;

/**
 * <code>Exception</code> thrown if a <code>Context</code> is illegal.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class IllegalContextException extends ServiceException {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = -5479225327514246543L;

	/**
	 * Creates a new <code>IllegalContextException</code>.
	 * 
	 * @param context
	 *            The illegal <code>Context</code>, must not be
	 *            <code>null</code>.
	 */
	public IllegalContextException(IContext context) {
		super(context.getClass().getName());
	}
}
