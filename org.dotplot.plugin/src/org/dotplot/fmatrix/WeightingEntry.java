/*
 * Created on 01.07.2004
 */
package org.dotplot.fmatrix;

/**
 * Class to store the manual weightings wich will later be stored in the GlobalConfiguration.
 *
 * @author Thorsten Ruehl
 */
public class WeightingEntry
{
   private int tokenIndex;
   private double weight;

   /**
    * Constructs a WeightingEntry object.
    *
    * @param index     the index
    * @param weighting the corresponding weight
    */
   public WeightingEntry(int index, double weighting)
   {
      this.tokenIndex = index;
      this.weight = weighting;
   }

   /**
    * get the indexEntry of the weighted token.
    *
    * @return int
    */
   public int getTokenIndex()
   {
      return this.tokenIndex;
   }

   /**
    * get the manual weight of this token.
    *
    * @return double
    */
   public double getWeight()
   {
      return this.weight;
   }
}
