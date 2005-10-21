/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * This class is used as a datastorage for token types in the sense of the fMatrix.
 * It uses a Hashtable to implement datastructures(store TokenType objects)
 * and provides methods to access and modify data.
 * <p/>
 * The implemented Hashtable will not take duplicate entries.
 *
 * @author Constantin von Zitzewitz, Thorsten Ruehl
 * @version 0.4
 * @see <code>TokenType</code>
 */
public class TypeTable implements Serializable
{
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
    * Constructor receiving a reference on two TokenTable
    * objects, which will be filled by an instance of this class.
    * This constructor is used in a case where a token source is
    * to be plotted against another token source(if 2nd source is null,
    * the 1st source will)
    *
    * @param tokenTable the tokenTable to be used.
    */
   public TypeTable(TokenTable tokenTable)
   {
      this.tokenTable = tokenTable;
      typeTable = new Hashtable();
      typeTableReversed = new Hashtable();
      typeIndex = 0;
      numberOfMatches = 0;
   }

   /**
    * To register the tokenInformation
    */
   void registerTokenInformation(TokenInformation tokenInformation)
   {
      this.tokenInformation = tokenInformation;
   }

   /**
    * Retrieve the tokeninformation-object for this type-table
    *
    * @return tokeninformation-object
    */
   TokenInformation getTokenInformation()
   {
      return tokenInformation;
   }

   /**
    * looks for a value in the Hashtable and returns its typeIndex.
    *
    * @param value the String(value) to look for
    *
    * @return int the index of the match, or -1 for no match.
    */
   int getTypeIndex(String value)
   {
      Integer result = (Integer) typeTableReversed.get(value);
      if (result != null)
      {
         return result.intValue();
      }
      return -1;
   }

   /**
    * retrieve the TokenType-object assigned to a specific type index.
    *
    * @param typeIndex the index
    *
    * @return the corresponding TokenType
    */
   public TokenType getTokenType(int typeIndex)
   {
      return (TokenType) typeTable.get(new Integer(typeIndex));
   }

   /**
    * returns the number of token types stored in this object.
    *
    * @return int   - the number of types.
    */
   public int getNumberOfTypes()
   {
      return (typeIndex);
   }

   /**
    * returns the number of tokens stored in the tokenTable field.
    *
    * @return int   - the number of types.
    */
   int getNumberOfTokens()
   {
      return (tokenTable.getNumberOfTokens());
   }

   /**
    * returns a new TypeTableNavigator object for a TypeTable.
    *
    * @return TypeTableNavigatorObject   - the TypeTableNavigator object
    *
    * @see      <code>TypeTableNavigator</code>
    */
   public ITypeTableNavigator getNavigator()
   {
      return (ITypeTableNavigator) new TypeTableNavigator(this);
   }

   /**
    * returns the number of all matches for a fmatrix
    *
    * @return long - the number of matches
    */
   int getNumberOfMatches()
   {
      return numberOfMatches;
   }

   /**
    * returns an Iterator object, to iterate the typeTable.
    *
    * @return Iterator   - the iterator object.
    */
   public Iterator getTypeTableIterator()
   {
      return typeTable.entrySet().iterator();
   }

   /**
    * Enumerator to run trough the Types
    *
    * @return Enumeration
    */
   Enumeration getTypeEnumeration()
   {
      return typeTable.elements();
   }

   /**
    * the function allows to specify a group of types via an regular expression wich should
    * have a special weight thus a group of tokentypes could be highlighted
    *
    * @param regExp    the regular expression describing the wanted types
    * @param weighting the weight for the new type
    *
    * @return the index of the new regular-expression-types
    */
   int addRegularExpressionType(String regExp, double weighting)
   {
      Pattern p = Pattern.compile(regExp);

      TokenType tempTokenType = new TokenType(regExp);
      tempTokenType.setWeight(weighting);

      Enumeration typeTableIt = typeTable.elements();
      TokenType currentTokenType;

      while (typeTableIt.hasMoreElements())
      {
         currentTokenType = (TokenType) typeTableIt.nextElement();

         if (p.matcher(currentTokenType.getValue()).find())
         {
            // process matching types
            int numberOfMatches = currentTokenType.getNumberOfMatches();
            Match currentMatch;

            // copy the matches of the matching type in to the new regularExpressionType
            for (int i = 0; i < numberOfMatches; i++)
            {
               currentMatch = currentTokenType.getMatch(i);
               tempTokenType.addTypePosition(currentMatch.getX());
            }
         }
      }

      if (tempTokenType.getNumberOfMatches() > 0)
      {
         return addTokenType(tempTokenType);
      }

      return -1;
   }

   /**
    * adds a TokenType object to the TypeTable.
    *
    * @param value the String value(of the token) to insert
    *
    * @return int      the index of the corresponding Type
    */
   public int addType(String value)
   {
      TokenType tempTokenType;
      int currentTypeIndex = getTypeIndex(value);
      if (currentTypeIndex > -1)
      { // if type exists...
         // fetch tokenType object...
         tempTokenType = getTokenType(currentTypeIndex);
         // add the token position to the tokenType...
         numberOfMatches += tempTokenType.addTypePosition(tokenTable.addTypeIndex(currentTypeIndex));
         return currentTypeIndex;
      }
      else
      { // if type is new
         tempTokenType = new TokenType(value);

         // add the token position to the tokenType...
         numberOfMatches += tempTokenType.addTypePosition(tokenTable.addTypeIndex(typeIndex));

         return addTokenType(tempTokenType);
      }
   }

   private int addTokenType(TokenType tokenType)
   {
      Integer typeIndexInt = new Integer(typeIndex);

      // insert new tokenType into typeTable...
      typeTable.put(typeIndexInt, tokenType);
      // and into the reversed...
      typeTableReversed.put(tokenType.getValue(), typeIndexInt);

      typeIndex++;
      return typeIndex - 1;
   }

   /**
    * Function to set all weightings to the calculated weight
    */
   void setAllCalculatedWeight()
   {
      TokenType currentTokenType;
      Enumeration tokenTypes = typeTable.elements();
      while (tokenTypes.hasMoreElements())
      {
         currentTokenType = (TokenType) tokenTypes.nextElement();
         currentTokenType.setWeight(currentTokenType.getCalculatedWeight());
      }
   }
}
