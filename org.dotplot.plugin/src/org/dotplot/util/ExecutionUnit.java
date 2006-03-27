/**
 * 
 */
package org.dotplot.util;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface ExecutionUnit extends Runnable {
	public void stop();
	public boolean isRunning();
}
