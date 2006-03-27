/**
 * 
 */
package org.dotplot.core;

import java.util.Enumeration;
import java.util.Iterator;

import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.fmatrix.TokenInformation;
import org.dotplot.fmatrix.TokenType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface ITypeTable {

	/**
	 * retrieve the TokenType-object assigned to a specific type index.
	 *
	 * @param typeIndex the index
	 *
	 * @return the corresponding TokenType
	 */
	public abstract TokenType getTokenType(int typeIndex);

	/**
	 * returns the number of token types stored in this object.
	 *
	 * @return int   - the number of types.
	 */
	public abstract int getNumberOfTypes();

	/**
	 * returns a new TypeTableNavigator object for a TypeTable.
	 *
	 * @return TypeTableNavigatorObject   - the TypeTableNavigator object
	 *
	 * @see      <code>TypeTableNavigator</code>
	 */
	public abstract ITypeTableNavigator createNavigator();

	/**
	 * returns an Iterator object, to iterate the typeTable.
	 *
	 * @return Iterator   - the iterator object.
	 */
	public abstract Iterator getTypeTableIterator();

	/**
	 * the function allows to specify a group of types via an regular expression wich should
	 * have a special weight thus a group of tokentypes could be highlighted
	 *
	 * @param regExp    the regular expression describing the wanted types
	 * @param weighting the weight for the new type
	 *
	 * @return the index of the new regular-expression-types
	 */
	public abstract int addRegularExpressionType(String regExp, double weighting);

	/**
	 * adds a TokenType object to the TypeTable.
	 *
	 * @param value the String value(of the token) to insert
	 *
	 * @return int      the index of the corresponding Type
	 */
	public abstract int addType(String value);

	public abstract void print();
	
	public Enumeration getTypeEnumeration();
	
	public TokenInformation getTokenInformation();
	
	public int getTypeIndex(String value);

}