/**
 * 
 */
package org.dotplot.core.services;

import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IRessource {
	
	/**
	 * Returns an <code>InputStream</code> to read from the <code>Ressource</code>.
	 * @return The <code>InputStream</code>.
	 */
	public InputStream getInputStream();
	
	/**
	 * The an <code>URL</code> to locate the <code>Ressource</code>.
	 * @return The <code>URL</code>.
	 */
	public URL getURL();
}
