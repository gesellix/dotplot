/**
 * 
 */
package org.dotplot.image;

import org.dotplot.core.IDotplot;
import org.dotplot.core.services.IContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class QImageContext implements IContext {

	private IDotplot dotplot;

	/**
	 * Creates a new <code>QImageContext</code>.
	 */
	public QImageContext(IDotplot dotplot) {
		super();
		if (dotplot == null) {
			throw new NullPointerException();
		}
		this.dotplot = dotplot;
	}

	public IDotplot getDotplot() {
		return this.dotplot;
	}

}
