/*
 * Created on 06.05.2004
 */
package org.dotplot.tokenizer.tokenfilter;

import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;

/**
 * General TokenFilter which is deprecated from BaseTokenfilter and which deals with filterList's.
 *
 * @author Christian Gerhardt, Jan Schillings , Sebastian Lorberg
 * @version 1.0
 */
public class GeneralTokenFilter extends BaseTokenFilter
{
   private int[] filterList;

   /**
    * construct a new filter.
    *
    * @param filterList the filter list
    */
   public GeneralTokenFilter(int[] filterList)
   {
      super();
      this.filterList = filterList;
   }

   /**
    * construct a new filter.
    *
    * @param filterList  the filter list
    * @param tokenStream the tokenStream
    */
   public GeneralTokenFilter(int[] filterList, ITokenStream tokenStream)
   {
      super(tokenStream);
      this.filterList = filterList;
   }

   /**
    * Filters the Token.
    *
    * @param token the token
    *
    * @return token the current Token
    */
   public Token filterToken(Token token)
   {
      int type = token.getType();
      for (int i = 0; i < this.filterList.length; i++)
      {
         if (this.filterList[i] == type)
         {
            return null;
         }
      }
      return token;
   }

   /**
    * returns the filter list.
    *
    * @return the filter list
    */
   public int[] getFilterList()
   {
      return this.filterList;
   }
}
