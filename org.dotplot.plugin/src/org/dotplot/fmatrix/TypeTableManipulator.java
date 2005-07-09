/*
 * Created on 19.05.2004
 */
package org.dotplot.fmatrix;

import java.util.Enumeration;

/**
 * The class holds several functions to maniplate the typeTable.
 *
 * @author Thorsten Ruehl
 * @version 0.1
 */
public class TypeTableManipulator implements ITypeTableManipulator
{
   private TypeTable typeTable;

   /**
    * Constructs a TypeTableManipulator object.
    *
    * @param typeTable a TypeTable object to be manipulated
    */
   public TypeTableManipulator(TypeTable typeTable)
   {
      this.typeTable = typeTable;
   }

   /**
    * To run through the types.
    *
    * @return Enumeration
    */
   public Enumeration getTypeEnumeration()
   {
      return typeTable.getTypeEnumeration();
   }

   /**
    * retrieve the tokentype by the typeName.
    *
    * @param typeName the name
    *
    * @return TokenType
    */
   public TokenType getTokenTypeByName(String typeName)
   {
      return typeTable.getTokenType(typeTable.getTypeIndex(typeName));
   }

   /**
    * retrieve HashKey for later acces to the type-hastable.
    *
    * @param typeName the name
    *
    * @return int
    */
   public int getHashKeyByName(String typeName)
   {
      return typeTable.getTypeIndex(typeName);
   }

   /**
    * To create a new type with regular expressions (perl-style).
    */
   public int addNewRegExpType(String regExp, double weight)
   {
      return typeTable.addRegularExpressionType(regExp, weight);
   }
}
