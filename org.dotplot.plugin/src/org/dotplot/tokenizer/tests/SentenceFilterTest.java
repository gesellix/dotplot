/*
 * Created on 13.06.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.tokenfilter.SentenceFilter;

/**
 * Test Class for a SentenceFilter
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class SentenceFilterTest extends TestCase
{

   private SentenceFilter sentenceFilter;

   /**
    * Constructor for SentenceFilterTest.
    *
    * @param arg0 - the first argument
    */
   public SentenceFilterTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.sentenceFilter = new SentenceFilter();
   }

   public void testFilterToken()
   {
      Token sentenceToken = new Token(".");
      Token token = new Token("bla", Token.TYPE_STRING);

      try
      {
         assertSame("muß durch gehen", token, this.sentenceFilter.filterToken(token));

         this.sentenceFilter.setEndOfSentenceToken(sentenceToken);

         assertNull("muß gefiltert werden 1", this.sentenceFilter.filterToken(token));
         assertNull("muß gefiltert werden 2", this.sentenceFilter.filterToken(token));
         assertEquals("prüfen ob das richtge Satztoken generiert wurde",
               new Token("bla bla .", Token.TYPE_STRING),
               this.sentenceFilter.filterToken(sentenceToken));

         assertNull("muß gefiltert werden 3", this.sentenceFilter.filterToken(token));
         assertNull("muß gefiltert werden 4", this.sentenceFilter.filterToken(token));
         assertEquals("prüfen ob auch beim EOS das richtige raus kommt",
               new Token("bla bla", Token.TYPE_STRING),
               this.sentenceFilter.filterToken(new EOSToken()));
      }
      catch (TokenizerException e)
      {
         fail("Exception wurde geworfen" + e.getMessage());
      }
   }

   public void testGetSetEndOfSentenceToken()
   {
      Token token = new Token("Bla");
      this.sentenceFilter.setEndOfSentenceToken(token);
      assertSame("Prüft ob das was rein ging auch wieder raus kommt",
            token,
            this.sentenceFilter.getEndOfSentenceToken());
   }
}
