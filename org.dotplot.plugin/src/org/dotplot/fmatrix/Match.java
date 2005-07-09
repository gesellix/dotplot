/*
 * Created on 31.05.2004
 */
package org.dotplot.fmatrix;

/**
 * represents a point of the fmatrix.  Matching tokentypes are stored as a Match object.
 *
 * @author Constantin von Zitzewitz
 * @version 0.1
 */
public class Match
{
   private int y;
   private int x;
   private double weight;

   /**
    * constructor taking the x- and y-coordinate for a match.
    *
    * @param y      - the y-Coordinate(-1 for not known)
    * @param x      - the x-Coordinate(-1 for not known)
    * @param weight - the weight of this type
    */
   public Match(int y, int x, double weight)
   {
      this.y = y;
      this.x = x;
      this.weight = weight;
   }

   /**
    * Getter method for y-coordinate.
    *
    * @return int	- the y-coordinate.
    */
   public int getY()
   {
      return this.y;
   }

   /**
    * Getter method for x-coordinate.
    *
    * @return int	- the x-coordinate.
    */
   public int getX()
   {
      return this.x;
   }

   /**
    * Getter method for the weight of this current match type.
    *
    * @return float - the requested weight
    */
   public double getWeight()
   {
      return this.weight;
   }

   /**
    * overridden toString() method for this class.
    *
    * @return String - the string representation for this match.
    */
   public String toString()
   {
      return ("weight(x = " + this.x + ", y = " + this.y + ") = " + this.weight);
   }
}
