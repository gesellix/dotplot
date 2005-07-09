package org.dotplot.tokenizer.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Central Test-Suite of the Tokenizer Modul
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class AllTests
{

   public static Test suite()
   {
      TestSuite suite = new TestSuite("Test for org.dotplot.tokenizer.tests");
      //$JUnit-BEGIN$
      suite.addTest(new TestSuite(DefaultConfigurationTest.class));
      suite.addTest(new TestSuite(DefaultFileListTest.class));
      suite.addTest(new TestSuite(TokenizerTest.class));
      suite.addTest(new TestSuite(TokenTest.class));
      suite.addTest(new TestSuite(EOFTokenTest.class));
      suite.addTest(new TestSuite(EOLTokenTest.class));
      suite.addTest(new TestSuite(FileTokenizerTest.class));
      suite.addTest(new TestSuite(LineFilterTest.class));
      suite.addTest(new TestSuite(KeyWordFilterTest.class));
      suite.addTest(new TestSuite(TokenFilterContainerTest.class));
      suite.addTest(new TestSuite(SentenceFilterTest.class));
      //suite.addTest(new TestSuite(PDFtoTxtConverterTest.class));
      //$JUnit-END$
      return suite;
   }
}
