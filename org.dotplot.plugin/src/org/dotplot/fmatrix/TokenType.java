/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Vector;

/**
 * this entity is used to store, modify and access a tokentype
 * and all needed information of this type for the fmatrix.
 *
 * @author Constantin von Zitzewitz
 * @version 0.2
 */
public class TokenType implements Serializable
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = -7553622799263229124L;
   private Vector tokenPositions; // stores all token positions of this type.
   private String value; // stores the string corresponding to this type.
   private double weight; // weight for this type might be set by user.
   private int frequency; // stores the number of occurrencies for this type.

   /**
    * Default Constructor.
    *
    * @param value - the string value for the tokentype.
    */
   public TokenType(String value)
   {
      this.tokenPositions = new Vector();
      this.value = value;
   }

   /**
    * adds a position to this types occurrences(tokenPositions).
    *
    * @param tokenIndex - the index of the position of this type.
    *
    * @return int - the number of added matches
    */
   public int addTypePosition(int tokenIndex)
   {
      int currentNumberOfMatches = this.frequency * this.frequency;
      // checking for previous occurrences is not neccessary for this application.
      this.tokenPositions.add(new Integer(tokenIndex));
      this.frequency++; // increment the type's frequency.
      return (this.frequency * this.frequency) - currentNumberOfMatches;
   }

   /**
    * sets the weighting for a TokenType object "manually".
    *
    * @param weight - the weight to set to
    *
    * @see #getWeight
    */
   public void setWeight(double weight)
   {
      this.weight = weight;
   }

   /**
    * returns the (previously set)weighting for a TokenType object.
    *
    * @return double - the weight of this instance
    *
    * @see #setWeight
    */
   public double getWeight()
   {
      return weight;
   }

   /**
    * returns the string value for a TokenType object.
    *
    * @return String   - the value of this instance
    */
   public String getValue()
   {
      return this.value;
   }

   /**
    * returns the calculated weighting for a TokenType object
    * according to its frequency.
    *
    * @return double - the calculated weight of this instance
    */
   public double getCalculatedWeight()
   {
      // seems to be wrong, to multiply by "2" here...?
//      return (1.0 / frequency) * 2; // x2, because one match consists of 2 positions.
      return (1.0 / frequency);
   }

   /**
    * returns the number of token matches of this type.
    *
    * @return int - the number of matches
    */
   public int getNumberOfMatches()
   {
      return (frequency * frequency);
   }

   /**
    * returns the match at position "index".
    *
    * @param index - get the match at position index.
    *
    * @return Match - the Match.
    */
   public Match getMatch(int index)
   {
      return new Match(((Integer) (tokenPositions.get(index / frequency))).intValue(),
            ((Integer) tokenPositions.get(index % frequency)).intValue(),
            getWeight());
      //getCalculatedWeight());
   }

   /**
    * Returns how often the token occurs in the source.
    *
    * @return an int representing the frequency value
    */
   public int getFrequency()
   {
      return frequency;
   }

   /**
    * overridden toString() method.
    *
    * @return String   - instance as a string
    */
   public String toString()
   {
      String toReturn;

      toReturn = "Type value   : " + this.value + "\n";
      toReturn += "  set weight : " + this.weight + "\n";
      toReturn += " calc weight : " + getCalculatedWeight() + "\n";
      toReturn += " Matches     :\n";

      for (int i = 0; i < getNumberOfMatches(); i++)
      {
         toReturn += "                     "
               + "(x: " + this.tokenPositions.get(i / this.frequency) + ","
               + " y: " + this.tokenPositions.get(i % this.frequency) + ")\n";
      }
      return toReturn;
   }
}
