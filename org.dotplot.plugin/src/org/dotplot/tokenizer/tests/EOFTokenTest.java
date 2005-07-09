/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.Token;

/**
 * TestClass for an EOFToken
 *
 * @author case
 * @version 1.0
 */
public class EOFTokenTest extends TestCase
{
   private EOFToken eoft;

   private String file = "c:\\WebDeploy.log";

   /**
    * Constructor for EOFTokenTest.
    *
    * @param arg0 -- the first argument
    */
   public EOFTokenTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      File file = new File(this.file);
      this.eoft = new EOFToken(file);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testEOFToken()
   {
      assertEquals("prüfen ob das richtige File zurückgegeben wird", this.eoft.getFile().getPath(), this.file);
      assertEquals("prüfen ob der Tokentyp stimmt", this.eoft.getType(), Token.TYPE_EOF);
   }
}
