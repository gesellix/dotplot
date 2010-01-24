/*
 * Created on 01.06.2004
 */
package org.dotplot.fmatrix;

import java.awt.Dimension;

import org.dotplot.core.ITypeTable;

/**
 * interface for TypeTableNavigator.
 * 
 * @author Constantin von Zitzewitz
 * @version 0.1
 * @see <code>TypeTableNavigator</code>
 */
public interface ITypeTableNavigator {
	/**
	 * sets the current tokentype to the specified token type and so enables
	 * user to access all needed data.
	 * <p />
	 * best Usage: Match tempMatch while ((tempMatch = navigator.getNextMatch())
	 * != null ) { }
	 * 
	 * @return Match the next match
	 * 
	 * @see <code>hasMoreMatches()</code>
	 */
	public Match getNextMatch();

	/**
	 * returns the number of all matches for a fmatrix (the number of all
	 * matches within the matrix).
	 * 
	 * @return long - the number of matches
	 */
	public long getNumberOfAllMatches();

	/**
	 * returns the dimension of the TypeTable.
	 * 
	 * @return Dimension the dimension
	 */
	public Dimension getSize();

	/**
	 * Get the tokenInformationObject, to retrieve stored information about the
	 * sourcefiles and its tokens.
	 * 
	 * @return tokeninformation-object
	 */
	public TokenInformation getTokenInformation();

	/**
	 * Returns the current type table.
	 * 
	 * @return a TypeTable object representing the type table
	 * 
	 * @see #setTypeTable
	 */
	public ITypeTable getTypeTable();

	/**
	 * Resets the navigator to let it start from the beginning.
	 */
	public void reset();

	/**
	 * set the region of interest. Define the area of the matrix from wich
	 * getNextMatch should deliver the matches
	 * 
	 * @param x
	 *            the starting point on the X dimension
	 * @param y
	 *            the starting point on the Y dimension
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * 
	 * @return true if the commited coordinates are in range
	 */
	public boolean setRegionOfInterest(int x, int y, int width, int height);

	/**
	 * sets the threshhold for the frequency. (when set, only tokentypes below
	 * this frequency will be considered.)
	 * 
	 * @param frequency
	 *            - the frequency threshhold. (set to 0 for not considering the
	 *            threshhold.)
	 */
	public void setThreshold(int frequency);

	/**
	 * Specifies the new type table.
	 * 
	 * @param typeTable
	 *            a TypeTable object specifying the new type table
	 * 
	 * @see #getTypeTable
	 */
	public void setTypeTable(TypeTable typeTable);
}
