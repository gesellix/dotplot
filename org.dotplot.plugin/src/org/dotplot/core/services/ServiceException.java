/**
 * 
 */
package org.dotplot.core.services;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class ServiceException extends Exception {

	/**
	 * UID for serilization.
	 */
	private static final long serialVersionUID = 3356729816445963567L;

	/**
	 * Creates a new <code>ServiceException</code>.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Creates a new <code>ServiceException</code>.
	 * 
	 * @param arg0
	 */
	public ServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * Creates a new <code>ServiceException</code>.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Creates a new <code>ServiceException</code>.
	 * 
	 * @param arg0
	 */
	public ServiceException(Throwable arg0) {
		super(arg0);
	}

}
