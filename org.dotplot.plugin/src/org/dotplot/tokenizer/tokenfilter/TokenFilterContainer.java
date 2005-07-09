/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.tokenfilter;

import java.util.Enumeration;
import java.util.Vector;

import org.dotplot.tokenizer.ITokenFilter;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;

/**
 * A Container for all TokenFilter.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class TokenFilterContainer extends BaseTokenFilter
{
   private Vector theContainer;

   /**
    * Creates a<code>TokenFilterContainer</code>.
    * A Container for TokenFilter.
    * Every Token wich will be scanned are handled throught every Tokenfilter in this Container.
    */
   public TokenFilterContainer()
   {
      super();
      init();
   }

   /**
    * Creates <code>TokenFilterContainer</code>.
    * A Container for TokenFilter.
    * Every Token wich will be scanned are handled throught every Tokenfilter in this Container.
    *
    * @param tokenStream the token stream
    */
   public TokenFilterContainer(ITokenStream tokenStream)
   {
      super(tokenStream);
      init();
   }

   private void init()
   {
      this.theContainer = new Vector();
   }

   /**
    * adds a token filter.
    *
    * @param tokenFilter the new filter
    */
   public void addTokenFilter(ITokenFilter tokenFilter)
   {
      if (tokenFilter != null)
      {
         this.theContainer.add(tokenFilter);
      }
   }

   /**
    * The Filtering of the Token.
    *
    * @param token -- the Token, which schould be filterd
    *
    * @return Token token -- returns the filterd Token
    *
    * @throws TokenizerException
    */
   public Token filterToken(Token token) throws TokenizerException
   {
      for (Enumeration e = this.theContainer.elements(); e.hasMoreElements();)
      {
         token = ((ITokenFilter) e.nextElement()).filterToken(token);
         if (token == null)
         {
            break;
         }
      }
      return token;
   }

   /**
    * Returns the TokenFilter.
    *
    * @return - returns an array of ITokenFilters
    */
   public ITokenFilter[] getTokenFilters()
   {
      ITokenFilter[] filters = new ITokenFilter[this.theContainer.size()];
      int i = 0;
      for (Enumeration e = this.theContainer.elements(); e.hasMoreElements();)
      {
         filters[i] = (ITokenFilter) e.nextElement();
         i++;
      }
      return filters;
   }

   /**
    * Emptys the Container.
    */
   public void clear()
   {
      this.theContainer.clear();
   }
}
