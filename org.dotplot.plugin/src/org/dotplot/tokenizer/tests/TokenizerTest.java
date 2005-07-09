/*
 * Created on 29.04.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.DefaultConfiguration;
import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Tokenizer;
import org.dotplot.tokenizer.TokenizerException;

/**
 * Test Class for a Tokenizer
 *
 * @author case
 * @version 1.0
 */
public class TokenizerTest extends TestCase
{

   private Tokenizer tokenizer;
   private DefaultFileList fileList;

   /**
    * Constructor for TokenizerTest.
    *
    * @param arg0 - the first argument
    */
   public TokenizerTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.fileList = new DefaultFileList();
      this.fileList.add(new File("txt/bild/1.txt"));
      this.tokenizer = new Tokenizer();
      this.tokenizer.getConfiguration().setFileList(this.fileList);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testGetConfiguration()
   {
      assertTrue("prüfen ob ein Objekt vom Typ IConfiguration zurückgegeben wird",
            this.tokenizer.getConfiguration() instanceof IConfiguration);
   }

   public void testSetConfiguration()
   {
      DefaultConfiguration defCon = new DefaultConfiguration();
      this.tokenizer.setConfiguration(defCon);
      assertTrue("prüfen ob das objekt das rein geht gleich dem ist das raus kommt.",
            this.tokenizer.getConfiguration() == defCon);
   }

   public void testGetTokenStream()
   {
      ITokenStream tokenStream = null;
      try
      {
         tokenStream = this.tokenizer.getTokenStream();
      }
      catch (TokenizerException e)
      {
         fail("Exception thrown");
      }
      assertTrue("prüfen ob ein Objekt vom Typ ITokenStream zurückgegeben wird", tokenStream instanceof ITokenStream);
   }
}
