/**
 * 
 */
package org.dotplot.core.plugins;

/**
 * Exeption to indicate a malformed <code>Version</code>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class MalformedVersionException extends Exception {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = 5899924210465510896L;

	/**
	 * Creates a new <code>MalformedVersionException</code>.
	 */
	public MalformedVersionException() {
		super();
	}

	/**
	 * Creates a new <code>MalformedVersionException</code>.
	 * 
	 * @param msg
	 *            - an error message
	 */
	public MalformedVersionException(String msg) {
		super(msg);
	}

}
