/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import org.dotplot.tokenizer.Token;

/**
 * holds all line information per file.
 * 
 * @author Oguz Huryasar
 * @version 0.1
 */
public class LineInformation extends Hashtable implements Serializable {
    /**
     * is a single entry for the parents class hashtable (firstTokenIndex is the
     * key of the hashtable entry).
     * 
     * @author Oguz Huryasar
     * @version 0.1
     */
    private class LineInformationContainer implements Serializable {
	/**
	 * for being Serializable
	 */
	private static final long serialVersionUID = -4540120604945172975L;

	private int lineIndex;

	private int lastTokenIndex;

	private List tokensInLine;

	/**
	 * Default Constructor
	 * 
	 * @param lineIndex
	 *            - the lineIndex
	 * @param lastTokenIndex
	 *            - the tokenindex, the file ends with.
	 */
	public LineInformationContainer(int lineIndex, int lastTokenIndex,
		List tokensInLine) {
	    this.lineIndex = lineIndex;
	    this.lastTokenIndex = lastTokenIndex;
	    this.tokensInLine = tokensInLine;
	}

	/**
	 * returns the index of the last token in this row.
	 * 
	 * @return int - the linenumber
	 */
	public int getLastTokenIndex() {
	    return lastTokenIndex;
	}

	/**
	 * returns the linenumber.
	 * 
	 * @return int - the linenumber
	 */
	public int getLineIndex() {
	    return lineIndex;
	}

	public String getToken(int index) {
	    Token t = null;
	    for (int i = 0; i <= index && i < tokensInLine.size(); i++) {
		t = (Token) tokensInLine.get(i);
	    }
	    return (t == null) ? "" : t.getValue();
	}
    }

    /**
     * for being Serializable
     */
    private static final long serialVersionUID = 7913185742216121752L;

    /**
     * adds a linenumber, its first index and last index.
     * 
     * @param firstTokenIndex
     *            the first index
     * @param lastTokenIndex
     *            the last index
     * @param lineIndex
     *            the line number
     */
    public void addLineInformation(int firstTokenIndex, int lastTokenIndex,
	    int lineIndex, List tokensInLine) {
	put(new Integer(firstTokenIndex), new LineInformationContainer(
		lineIndex, lastTokenIndex, tokensInLine));
    }

    /**
     * returns the linenumber corresponding to a single token(index).
     * 
     * @param tokenIndex
     *            - the token index
     * 
     * @return int - the linenumber, the token is at. (-1 for not found)
     */
    public int getLineIndex(int tokenIndex) {
	while (!(containsKey(new Integer(tokenIndex)) || tokenIndex < 0)) {
	    tokenIndex--;
	}

	if (tokenIndex < 0) {
	    return -1;
	}

	return ((LineInformationContainer) get(new Integer(tokenIndex)))
		.getLineIndex();
    }

    /**
     * returns the linenumber corresponding to a single token(index).
     * 
     * @param tokenIndex
     *            - the token index
     * 
     * @return int - the linenumber, the token is at. (-1 for not found)
     */
    public String getToken(int tokenIndex) {
	int searchIx = tokenIndex;
	while (!(containsKey(new Integer(searchIx)) || searchIx < 0)) {
	    searchIx--;
	}

	if (searchIx < 0) {
	    return "";
	}

	return ((LineInformationContainer) get(new Integer(searchIx)))
		.getToken(tokenIndex - searchIx);
    }
}
