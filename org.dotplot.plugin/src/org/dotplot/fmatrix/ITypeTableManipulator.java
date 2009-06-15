/*
 * Created on 19.05.2004
 */
package org.dotplot.fmatrix;

import java.util.Enumeration;

/**
 * Defines the requirements for an object that can manipulate the FMatrix.
 * 
 * @author smeo
 */
public interface ITypeTableManipulator {
    /**
     * To create a new type with regular expressions (perl-style).
     * 
     * @param regExp
     *            a new regular expression
     * @param weight
     *            a double as new setting for the regexp
     * 
     * @return an int representing the new index
     */
    public int addNewRegExpType(String regExp, double weight);

    /**
     * retrieve HashKey for later access to the type-hastable.
     * 
     * @param key
     *            a String representing the HashKey
     * 
     * @return int
     */
    public int getHashKeyByName(String key);

    /**
     * retrieve the tokentype by the typeName.
     * 
     * @param typeName
     *            a String representing the TokenType
     * 
     * @return TokenType
     */
    public TokenType getTokenTypeByName(String typeName);

    /**
     * To run through the types.
     * 
     * @return Enumeration
     */
    public Enumeration getTypeEnumeration();
}
