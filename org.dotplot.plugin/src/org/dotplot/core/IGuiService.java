/**
 * 
 */
package org.dotplot.core;

/**
 * A gui-interface for displaying messages.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IGuiService {
		
	/**
	 * Shows an error message.
	 * @param message The message.
	 */
	public void showErrorMessage(String message);
	
	/**
	 * Shows an error message.
	 * @param message The message.
	 * @param details Details of the error.
	 */
	public void showErrorMessage(String message, String details);
	
	/**
	 * Shows a message.
	 * @param message The message.
	 */
	public void showMessage(String message);
	
	/**
	 * Appends a message to the log.
	 * @param message The message.
	 */
	public void appendToLog(String message);
}
