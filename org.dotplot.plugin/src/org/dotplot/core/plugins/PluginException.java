/**
 * 
 */
package org.dotplot.core.plugins;

import org.dotplot.core.services.ServiceException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class PluginException extends ServiceException {

	/**
	 * UID for serialisation.
	 */
	private static final long serialVersionUID = -112476321729843371L;

	/**
	 * Creates a new <code>PluginException</code>.
	 */
	public PluginException() {
		super();
	}

	/**
	 * Creates a new <code>PluginException</code>.
	 * @param arg0
	 */
	public PluginException(String arg0) {
		super(arg0);
	}

	/**
	 * Creates a new <code>PluginException</code>.
	 * @param arg0
	 * @param arg1
	 */
	public PluginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Creates a new <code>PluginException</code>.
	 * @param arg0
	 */
	public PluginException(Throwable arg0) {
		super(arg0);
	}

}
