/*
 * Created on 19.05.2004
 */
package org.dotplot.fmatrix;

import java.util.Enumeration;

import org.dotplot.core.ITypeTable;

/**
 * The class holds several functions to maniplate the typeTable.
 * 
 * @author Thorsten Ruehl
 * @version 0.1
 */
public class TypeTableManipulator implements ITypeTableManipulator {
    private ITypeTable typeTable;

    /**
     * Constructs a TypeTableManipulator object.
     * 
     * @param typeTable
     *            a TypeTable object to be manipulated
     */
    public TypeTableManipulator(ITypeTable typeTable) {
	this.typeTable = typeTable;
    }

    /**
     * To create a new type with regular expressions (perl-style).
     */
    public int addNewRegExpType(String regExp, double weight) {
	return typeTable.addRegularExpressionType(regExp, weight);
    }

    /**
     * retrieve HashKey for later acces to the type-hastable.
     * 
     * @param typeName
     *            the name
     * 
     * @return int
     */
    public int getHashKeyByName(String typeName) {
	return typeTable.getTypeIndex(typeName);
    }

    /**
     * retrieve the tokentype by the typeName.
     * 
     * @param typeName
     *            the name
     * 
     * @return TokenType
     */
    public TokenType getTokenTypeByName(String typeName) {
	return typeTable.getTokenType(typeTable.getTypeIndex(typeName));
    }

    /**
     * To run through the types.
     * 
     * @return Enumeration
     */
    public Enumeration getTypeEnumeration() {
	return typeTable.getTypeEnumeration();
    }
}
