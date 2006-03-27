/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.filter;

import java.util.Map;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.KeyWordToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.SourceCodeFileType;

/**
 * A filter for keywords.
 * Depending of the configuration, all Token or only keywords pass the filter.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class KeyWordFilter implements ITokenFilter
{
	
	public static final String PARAMETER_MODUS = "modus";
	
   /**
    * All Token are able to pass the filter.
    */
   public final static int LET_THROUGH_ALL = 1;

   /**
    * Only keyword are able to pass the filter.
    */
   public final static int LET_THROUGH_ONLY_KEYWORDS = 2;

   /**
    * No keywords are able to pass the filter.
    */
   public final static int LET_THROUGH_NO_KEYWORDS = 3;

   /**
    * The mode in which the filter is set
    */
   private int modus;

   /**
    * The TokenStream which should be filtered
    */
   private ITokenStream tokenStream;

   /**
    * Creates a KeyWordFilter.
    * It starts in the default mode - All Token are able to pass the filter.
    */
   public KeyWordFilter()
   {
      this.modus = KeyWordFilter.LET_THROUGH_ALL;
   }

   /**
    * Creates a KeyWordFilter in a particular mode.
    *
    * @param modus - the mode in which the KeyWordFilter runs
    */
   public KeyWordFilter(int modus)
   {
      this.modus = KeyWordFilter.LET_THROUGH_ALL;
      this.setModus(modus);
   }

   /**
    * Creates a KeyWordFilter for a TokenStream.
    *
    * @param tokenStream - the TokenStream
    */
   public KeyWordFilter(ITokenStream tokenStream)
   {
      this.modus = KeyWordFilter.LET_THROUGH_ALL;
      this.tokenStream = tokenStream;
   }

   /**
    * Creates a KeyWordFilter for a particular TokenStream in particular mode.
    *
    * @param tokenStream - the TokenStream
    * @param modus       - the mode in which the TokenFilter runs
    */
   public KeyWordFilter(ITokenStream tokenStream, int modus)
   {
      this.modus = KeyWordFilter.LET_THROUGH_ALL;
      this.setModus(modus);
      this.tokenStream = tokenStream;
   }

   /**
    * Returns the next filtered <code>Token</code> of a TokenStream.
    *
    * @param tokenStream - the TokenStream
    *
    * @return token - The next filtered Token
    *
    * @throws TokenizerException
    */
   public Token getNextToken(ITokenStream tokenStream) throws TokenizerException
   {
      Token token = null;
      while (token == null)
      {
         token = tokenStream.getNextToken();
         if (token instanceof EOSToken)
         {
            break;
         }
         token = this.filterToken(token);
      }
      return token;
   }

   /**
    * Returns the next unfiltered<code>Token</code>from the TokenStream.
    *
    * @return - the next unfiltered<code>Token</code>
    *
    * @throws TokenizerException
    */
   public Token getNextToken() throws TokenizerException
   {
      return this.tokenStream.getNextToken();
   }

   /**
    * Filters the Token.
    * <p />
    * Depending of the configuration, the keywords wil be allowed to pass or will be filtered
    * The Token which will pass at any time are: <code>EOS, EOF, EOL, Line</code>.
    *
    * @param token - the<code>Token</code> which should be filtered
    *
    * @return - the <code>Token</code>
    *
    * @throws TokenizerException
    */
   public Token filterToken(Token token) throws TokenizerException
   {
      switch (this.modus)
      {
         case LET_THROUGH_ALL:
            break;

         case LET_THROUGH_NO_KEYWORDS:
            if (token instanceof KeyWordToken)
            {
               return null;
            }
            break;

         case LET_THROUGH_ONLY_KEYWORDS:
            if (token instanceof EOLToken)
            {
               break;
            }
            else if (token instanceof EOSToken)
            {
               break;
            }
            else if (token instanceof EOFToken)
            {
               break;
            }
            else if (token instanceof KeyWordToken)
            {
               break;
            }
            //line tokens müssen auch durchgelassen werden.
            else if (token.getType() == Token.TYPE_LINE)
            {
               break;
            }
            else
            {
               return null;
            }

      }
      return token;
   }

   /**
    * Returns the current TokenStream.
    *
    * @return - the TokenStream
    *
    * @see #setTokenStream(org.dotplot.tokenizer.service.ITokenStream)
    */
   public ITokenStream getTokenStream()
   {
      return tokenStream;
   }

   /**
    * Sets the current TokenStream.
    *
    * @param stream - the TokenStream
    *
    * @see #getTokenStream()
    */
   public void setTokenStream(ITokenStream stream)
   {
      tokenStream = stream;
   }

   /**
    * Returns the current mode of the Filter.
    *
    * @return - the current mode
    *
    * @see #setModus(int)
    */
   public int getModus()
   {
      return modus;
   }

   /**
    * Sets the current mode.
    *
    * @param modus - the mode, which should be configured
    *
    * @see #getModus()
    */
   public void setModus(int modus)
   {
      if (modus > 0 && modus < 4)
      {
         this.modus = modus;
      }
   }

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.ITokenFilter#applyParameter(java.lang.Object[])
	 */
	public void applyParameter(Map<String, ? extends Object> parameter) {
		try{
			this.setModus(((Integer)parameter.get(PARAMETER_MODUS)).intValue());
		}
		catch(Exception e){
			/*dann eben nicht*/
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
	 */
	public ISourceType getStreamType() {
		return SourceCodeFileType.type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(ITokenFilter arg0) {
		if(arg0 instanceof LineFilter){
			return -1;
		}
		else if(arg0 instanceof SentenceFilter){
			return -1;
		}
		else {
			return 0;
		}
	}
}
