/*
 * Created on 21.06.2004
 */
package org.dotplot.fmatrix;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Map;

/**
 * an object of this class allows to navigate through tokentypes
 * and all neccessary information.
 *
 * @author Constantin von Zitzewitz, Thorsten Ruehl
 * @version 0.2
 * @see <code>TypeTable</code>
 */
public class TypeTableNavigator implements ITypeTableNavigator
{
   private TypeTable typeTable; // reference on a TypeTable object.
   private int threshold; // if threshold for frequency is set manually.
   private boolean considerThreshold; // flag wether to use consider threshold.
   private Iterator typeTableIterator; // Iterator for TypeTable.
   private TokenType currentTokenType; // holds the current tokenType.
   private int currentIndex; // holds the index of the current match (of the number of matches of current tokenType).

   // Region of Interest
   private boolean useRegionOfInterest;
   private Rectangle roi;

   /**
    * Default Constructor.
    *
    * @param typeTable - a reference on a TypeTable object.
    */
   public TypeTableNavigator(TypeTable typeTable)
   {
      setTypeTable(typeTable);
   }

   /**
    * finds the next tokenType in the typeTable with matches.
    */
   private TokenType getNextNonEmptyTokenType()
   {
      if (typeTableIterator == null)
      {
         typeTableIterator = typeTable.getTypeTableIterator();
      }

      while (typeTableIterator.hasNext())
      {
         currentTokenType = (TokenType) (((Map.Entry) typeTableIterator.next()).getValue());
         if (currentTokenType.getNumberOfMatches() > 0)
         {
            return currentTokenType;
         }
      }

      // if no further tokenTypes are found...
      return null;
   }

   /**
    * set the region of interest. Define the area of the matrix from wich getNextMatch should
    * deliver the matches
    *
    * @param x      the starting point on the X dimension
    * @param y      the starting point on the Y dimension
    * @param width  the endpoint on the X dimension
    * @param height the endpoint on the Y dimension
    *
    * @return true if the commited coordinates are in range
    */
   public synchronized boolean setRegionOfInterest(int x, int y, int width, int height)
   {
      roi = new Rectangle(x, y, width, height);

      int numberOfTokens = typeTable.getNumberOfTokens();
      Rectangle complete = new Rectangle(0, 0, numberOfTokens, numberOfTokens);

      if (!complete.contains(roi))
      {
         useRegionOfInterest = false;
         return false;
      }

      useRegionOfInterest = true;
      return true;
   }

   /**
    * returns the next Match of the fmatrix.
    *
    * @return Match - the next match
    *
    * @see <code>hasMoreMatches()</code>
    */
   public synchronized Match getNextMatch()
   {
      if (currentIndex == 0 || currentIndex == currentTokenType.getNumberOfMatches())
      {
         // fetch next TokenType with matches...
         currentTokenType = getNextNonEmptyTokenType();

         // reset index
         currentIndex = 0;
      }

      if (currentTokenType == null)
      {
         return null;
      }

      if (useRegionOfInterest)
      {
         return getMatchInROI();
      }
      else
      {
         Match toReturn = currentTokenType.getMatch(currentIndex);
         currentIndex++;
         return toReturn;
      }
   }

   /**
    * checks if the currentIndex points to a match within the set region of interest.
    * If it is not, the function searches for the next match that's in this region.
    *
    * @return if the match is in, or a new match within the region of interest could be found, function returns true
    */
   private synchronized Match getMatchInROI()
   {
      int lastMatch = currentTokenType.getNumberOfMatches();

      Match match = null;
      do
      {
         if (currentIndex >= lastMatch)
         {
            currentTokenType = getNextNonEmptyTokenType();
            if (currentTokenType == null)
            {
               return null;
            }

            lastMatch = currentTokenType.getNumberOfMatches();
            currentIndex = 0;
            continue;
         }
         else
         {
            match = currentTokenType.getMatch(currentIndex);
            currentIndex++;

            if (roi.contains(match.getX(), match.getY()))
            {
               break;
            }
         }
      }
      while (!roi.contains(match.getX(), match.getY()));

      return match;
   }

   /**
    * sets the threshold for the frequency.
    * (when set, only tokentypes below this frequency will be
    * considered.)
    *
    * @param frequency - the frequency threshold.
    *                  (set to 0 for not considering the threshold.)
    */
   public void setThreshold(int frequency)
   {
      if (frequency == 0)
      {
         threshold = 0;
         considerThreshold = false;
      }
      else
      {
         threshold = frequency;
         considerThreshold = true;
      }
   }

   /**
    * returns the number of all matches for a fmatrix
    * (the number of all matches within the matrix).
    *
    * @return long - the number of matches
    */
   public long getNumberOfAllMatches()
   {
      return typeTable.getNumberOfMatches();
   }

   /**
    * returns the dimension of the TypeTable.
    *
    * @return Dimension   - the dimension
    */
   public Dimension getSize()
   {
      int numberOfTokens = typeTable.getNumberOfTokens();
      return new Dimension(numberOfTokens, numberOfTokens);
   }

   /**
    * Get the tokenInformationObject, to retrieve stored information about the sourcefiles
    * and its tokens.
    *
    * @return tokeninformation-object
    */
   public TokenInformation getTokenInformation()
   {
      return typeTable.getTokenInformation();
   }

   /**
    * Resets the navigator to let it start from the beginning.
    */
   public void reset()
   {
      currentIndex = 0;
      currentTokenType = null;
//      this.considerThreshold = false;
//      this.threshold = 0;
      roi = null;
      useRegionOfInterest = false;
      typeTableIterator = null;
   }

   /**
    * @see org.dotplot.fmatrix.ITypeTableNavigator#getTypeTable()
    */
   public TypeTable getTypeTable()
   {
      return typeTable;
   }

   public void setTypeTable(TypeTable typeTable)
   {
      this.typeTable = typeTable;
      reset();
   }
}
