/**
 * 
 */
package org.dotplot.util;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class UnknownIDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3311949878578045769L;

	/**
	 * @param arg0
	 */
	public UnknownIDException(String jobID) {
		super("" + jobID);
	}
}
