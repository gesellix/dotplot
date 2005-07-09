/*
 * Created on 10.05.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.Token;

/**
 * Test Class for an EOLToken
 *
 * @author hg12201
 * @version 1.0
 */
public class EOLTokenTest extends TestCase
{

   EOLToken eolt;
   File file;

   /**
    * Constructor for EOLTokenTest.
    *
    * @param arg0 - the first argument
    */
   public EOLTokenTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.file = new File("c:\\WebDeploy.log");
      this.eolt = new EOLToken(this.file, 2);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testGetFile()
   {
      assertTrue("pruefen ob die Datei stimmt", this.eolt.getFile() == this.file);
   }

   public void testGetLine()
   {
      assertEquals("pruefen ob die Zeile stimmt", this.eolt.getLine(), 2);
   }

   public void testGetType()
   {
      assertEquals("pruefen ob der typ stimmt", this.eolt.getType(), Token.TYPE_EOL);
   }
}
