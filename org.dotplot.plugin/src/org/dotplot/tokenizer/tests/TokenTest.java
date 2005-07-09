/*
 * Created on 29.04.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.Token;

/**
 * Test Class for a Token
 *
 * @author case
 * @version 1.0
 */
public class TokenTest extends TestCase
{

   private Token token;

   /**
    * Constructor for TokenTest.
    *
    * @param arg0 - the first argument
    */
   public TokenTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.token = new Token("TestToken", 1, 2);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   /*
    * Test für String toString()
    */
   public void testToString()
   {
      assertEquals("prüfen ob der test stimmt", this.token.toString(), "Token(1) \"TestToken\" in line 2");
   }

   public void testGetFile()
   {
      File file = new File("c:\\WebDeploy.log");
      this.token.setFile(file);
      assertTrue("prüfen ob das was rein ging auch das ist was raus kommt", this.token.getFile() == file);
   }

   public void testGetValue()
   {
      assertEquals("prüfen ob der test stimmt", this.token.getValue(), "TestToken");
   }

   public void testSetFile()
   {
      File file = new File("c:\\WebDeploy.log");
      this.token.setFile(file);
      assertTrue("prüfen ob das was rein ging auch das ist was raus kommt", this.token.getFile() == file);
   }

   public void testGetType()
   {
      assertEquals("prüfen ob der test stimmt", this.token.getType(), 1);
   }

   public void testGetLine()
   {
      assertEquals("prüfen ob der test stimmt", this.token.getLine(), 2);
   }

   public void testSetType()
   {
      this.token.setType(3);
      assertTrue("prüfen ob das was rein ging auch das ist was raus kommt", this.token.getType() == 3);
   }

   public void testSetLine()
   {
      this.token.setLine(4);
      assertTrue("prüfen ob das was rein ging auch das ist was raus kommt", this.token.getLine() == 4);
   }

   public void testEquals()
   {
      Token token1 = new Token("bla", 1);
      Token token2 = new Token("bla", 1);

      assertEquals("pruefen ob die beiden tokens gleich sind", token1, token2);
   }
}
