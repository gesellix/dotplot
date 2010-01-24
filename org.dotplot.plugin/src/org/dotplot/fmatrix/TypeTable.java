/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.dotplot.core.ITypeTable;

/**
 * This class is used as a datastorage for token types in the sense of the
 * fMatrix. It uses a Hashtable to implement datastructures(store TokenType
 * objects) and provides methods to access and modify data.
 * <p/>
 * The implemented Hashtable will not take duplicate entries.
 * 
 * @author Constantin von Zitzewitz, Thorsten Ruehl
 * @version 0.4
 * @see <code>TokenType</code>
 */
public class TypeTable implements Serializable, ITypeTable {
	/**
	 * for being Serializable
	 */
	private static final long serialVersionUID = 5849280153515840844L;

	private Hashtable typeTable; // holds the TokenTypes

	private Hashtable typeTableReversed;

	private TokenTable tokenTable; // tokens

	private TokenInformation tokenInformation;

	private int typeIndex; // the type index

	private int numberOfMatches; // the number of all matches.

	/**
	 * Constructor receiving a reference on two TokenTable objects, which will
	 * be filled by an instance of this class. This constructor is used in a
	 * case where a token source is to be plotted against another token
	 * source(if 2nd source is null, the 1st source will)
	 * 
	 * @param tokenTable
	 *            the tokenTable to be used.
	 */
	public TypeTable(TokenTable tokenTable) {
		this.tokenTable = tokenTable;
		typeTable = new Hashtable();
		typeTableReversed = new Hashtable();
		typeIndex = 0;
		numberOfMatches = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.fmatrix.ITypeTable#addRegularExpressionType(java.lang.String,
	 * double)
	 */
	public int addRegularExpressionType(String regExp, double weighting) {
		Pattern p = Pattern.compile(regExp);

		TokenType tempTokenType = new TokenType(regExp);
		tempTokenType.setWeight(weighting);

		Enumeration typeTableIt = typeTable.elements();
		TokenType currentTokenType;

		while (typeTableIt.hasMoreElements()) {
			currentTokenType = (TokenType) typeTableIt.nextElement();

			if (p.matcher(currentTokenType.getValue()).find()) {
				// process matching types
				int numberOfMatches = currentTokenType.getNumberOfMatches();
				Match currentMatch;

				// copy the matches of the matching type in to the new
				// regularExpressionType
				for (int i = 0; i < numberOfMatches; i++) {
					currentMatch = currentTokenType.getMatch(i);
					tempTokenType.addTypePosition(currentMatch.getX());
				}
			}
		}

		if (tempTokenType.getNumberOfMatches() > 0) {
			return addTokenType(tempTokenType);
		}

		return -1;
	}

	private int addTokenType(TokenType tokenType) {
		Integer typeIndexInt = new Integer(typeIndex);

		// insert new tokenType into typeTable...
		typeTable.put(typeIndexInt, tokenType);
		// and into the reversed...
		typeTableReversed.put(tokenType.getValue(), typeIndexInt);

		typeIndex++;
		return typeIndex - 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#addType(java.lang.String)
	 */
	public int addType(String value) {
		TokenType tokenType;
		int typeIndex = getTypeIndex(value);
		if (typeIndex > -1) { // type exists
			// fetch tokenType object...
			tokenType = getTokenType(typeIndex);
			// add the token position to the tokenType...
			numberOfMatches += tokenType.addTypePosition(tokenTable
					.addTypeIndex(typeIndex));
		}
		else { // type is new
			tokenType = new TokenType(value);

			// add the token position to the tokenType...
			numberOfMatches += tokenType.addTypePosition(tokenTable
					.addTypeIndex(getNumberOfTypes()));

			typeIndex = addTokenType(tokenType);
		}
		return typeIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#createNavigator()
	 */
	public ITypeTableNavigator createNavigator() {
		return new TypeTableNavigator(this);
	}

	/**
	 * returns the number of all matches for a fmatrix
	 * 
	 * @return long - the number of matches
	 */
	int getNumberOfMatches() {
		return numberOfMatches;
	}

	/**
	 * returns the number of tokens stored in the tokenTable field.
	 * 
	 * @return int - the number of types.
	 */
	int getNumberOfTokens() {
		return (tokenTable.getNumberOfTokens());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#getNumberOfTypes()
	 */
	public int getNumberOfTypes() {
		return typeIndex;
	}

	/**
	 * Retrieve the tokeninformation-object for this type-table
	 * 
	 * @return tokeninformation-object
	 */
	public TokenInformation getTokenInformation() {
		return tokenInformation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#getTokenType(int)
	 */
	public TokenType getTokenType(int typeIndex) {
		return (TokenType) typeTable.get(new Integer(typeIndex));
	}

	/**
	 * Enumerator to run trough the Types
	 * 
	 * @return Enumeration
	 */
	public Enumeration getTypeEnumeration() {
		return typeTable.elements();
	}

	/**
	 * looks for a value in the Hashtable and returns its typeIndex.
	 * 
	 * @param value
	 *            the String(value) to look for
	 * 
	 * @return int the index of the match, or -1 for no match.
	 */
	public int getTypeIndex(String value) {
		Integer result = (Integer) typeTableReversed.get(value);
		if (result != null) {
			return result.intValue();
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#getTypeTableIterator()
	 */
	public Iterator getTypeTableIterator() {
		return typeTable.entrySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.ITypeTable#print()
	 */
	public void print() {
		Enumeration tokenTypes = typeTable.elements();
		while (tokenTypes.hasMoreElements()) {
			System.out.println(tokenTypes.nextElement());
		}
	}

	/**
	 * To register the tokenInformation
	 */
	void registerTokenInformation(TokenInformation tokenInformation) {
		this.tokenInformation = tokenInformation;
	}

	/**
	 * Function to set all weightings to the calculated weight
	 */
	void updateCalculatedWeights() {
		Enumeration tokenTypes = typeTable.elements();
		while (tokenTypes.hasMoreElements()) {
			((TokenType) tokenTypes.nextElement()).updateWeight();
		}
	}
}
