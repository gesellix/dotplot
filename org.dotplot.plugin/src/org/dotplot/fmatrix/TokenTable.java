/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Vector;

/**
 * a TokenTable object holds all tokens(rather their index) of one
 * axis(dimension) of the dotplot. It uses a Vector to store all indices (and
 * references to further information, such as attributes).
 * <p/>
 * In this implementation a TokenTable object will be "fed" by a TypeTable
 * object, since all needed information (the type index) is provided by that
 * class.
 * 
 * @author Constantin von Zitzewitz
 * @version 0.4
 */
public class TokenTable implements Serializable {
    /**
     * for being Serializable
     */
    private static final long serialVersionUID = -3232840680096821917L;

    private Vector tokenTable;

    private int tokenIndex;

    /**
     * Default Constructor.
     */
    public TokenTable() {
	this.tokenTable = new Vector(); // initiate tokenTable
	this.tokenIndex = 0; // initiate tokenIndex
    }

    /**
     * adds a typeIndex to the table. The type indices will be inserted, as they
     * come in... Therefore this classes tokenIndex is the key to the inserted
     * typeIndex.
     * 
     * @return int - the key the inserted typeindex was inserted under.
     * 
     * @param typeIndex
     *            - the type index to insert
     */
    public int addTypeIndex(int typeIndex) {
	int tokenIndex = this.tokenIndex;
	this.tokenTable.add(new Integer(typeIndex));
	this.tokenIndex++;
	return tokenIndex;
    }

    /**
     * returns the number of tokens, the HashMap stores.
     * 
     * @return int the number of tokens
     */
    public int getNumberOfTokens() {
	return this.tokenIndex + 1; // since index starts with 0 add 1.
    }

    /**
     * returns the corresponding type index of a token index (the tokens
     * position on the y- / or x-Axis).
     * 
     * @param tokenIndex
     *            - the index of the token to get the type index for.
     * 
     * @return int - the corresponding type index or -1 for index not found.
     */
    public int getTypeIndex(int tokenIndex) {
	return ((Integer) (this.tokenTable.get(tokenIndex))).intValue();
    }
}
