/*
 * Created on 29.04.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.FileTokenizer;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.scanner.DefaultScanner;
import org.dotplot.tokenizer.scanner.JavaScanner;
import org.dotplot.tokenizer.scanner.TextScanner;

/**
 * Test Class for a FileTokenizer
 *
 * @author Christian Gerhardt Jan Schillings Sebastian Lorberg
 * @version 1.0
 */
public class FileTokenizerTest extends TestCase
{

   private FileTokenizer ft;

   /**
    * Constructor for FileTokenizerTest.
    *
    * @param arg0 - the first argument
    */
   public FileTokenizerTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      DefaultFileList list = new DefaultFileList();
      list.add(new File(""));
      this.ft = new FileTokenizer(list, new TestScanner());
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testGetNextToken() throws TokenizerException
   {
      assertEquals("pruefen ob das 1. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 1));
      assertEquals("pruefen ob das 2. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 1));
      assertEquals("pruefen ob das 3. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 1));
      assertEquals("pruefen ob das 1. EOL Token raus kommt", this.ft.getNextToken(), new EOLToken(1));
      assertEquals("pruefen ob das 4. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 2));
      assertEquals("pruefen ob das 5. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 2));
      assertEquals("pruefen ob das 6. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 2));
      assertEquals("pruefen ob das 7. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 2));
      assertEquals("pruefen ob das 2. Token raus kommt", this.ft.getNextToken(), new EOLToken(2));
      assertEquals("pruefen ob das 3. Token raus kommt", this.ft.getNextToken(), new EOLToken(2));
      assertEquals("pruefen ob das 8. Tokens stimmt", this.ft.getNextToken(), new Token("bla", Token.TYPE_STRING, 3));
      assertEquals("pruefen ob ein EOF Token raus kommt", new EOFToken(new File("")), this.ft.getNextToken());
      assertEquals("pruefen ob ein EOS Token raus kommt", new EOSToken(), this.ft.getNextToken());
      int eosTokens = 0;

      for (int i = 0; i < 200; i++)
      {
         if (this.ft.getNextToken() instanceof EOSToken)
         {
            eosTokens++;
         }
      }

      assertEquals("pruefen ob 200 EOS Token raus kamen", 200, eosTokens);
   }

   public void testCooseScanner()
   {
      DefaultFileList fileList = new DefaultFileList();
      fileList.add(new File("txt/bild/1.txt"));
      fileList.add(new File("src/org/dotplot/DotplotCreator.java"));
      fileList.add(new File("html/toc.html"));

      try
      {
         this.ft = new FileTokenizer(fileList, null);
         assertNotNull("prüfen das ein scanner gewählt wurde", this.ft.getActualScanner());

         //test the first token
         Token token = ft.getNextToken();
         assertTrue("Prüfen ob der text scanner gewählt wurde", ft.getActualScanner() instanceof TextScanner);

         while (!(token instanceof EOSToken))
         {
            token = ft.getNextToken();

            if (token instanceof EOFToken)
            {
               if (token.getFile().equals(new File("txt/bild/1.txt")))
               {

                  //with sending the EOFToken a new scanner is choosen
                  assertTrue("Prüfen ob der Java scanner gewählt wurde", ft.getActualScanner() instanceof JavaScanner);
               }
               else if (token.getFile().equals(new File("src/org/dotplot/DotplotCreator.java")))
               {

                  assertTrue("Prüfen ob der Defaultscanner gewählt wurde",
                        ft.getActualScanner() instanceof DefaultScanner);

               }
               else if (token.getFile().equals(new File("html/toc.html")))
               {
                  //nothing to do
               }
               else
               {
                  fail("wrong file:" + token.getFile().toString());
               }
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         fail("eine Ausnahme ist aufgetreten: " + e.getMessage());
      }
   }
}
