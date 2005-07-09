/*
 * Created on 01.06.2004
 */
package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

import org.dotplot.tokenizer.PDFtoTxtConverter;

/**
 * Test Class for a PDFtoTxtConverter
 *
 * @author hg12201
 * @version 1.0
 */
public class PDFtoTxtConverterTest extends TestCase
{

   private PDFtoTxtConverter pttc;

   /**
    * Constructor for PDFtoTxtConverterTest.
    *
    * @param arg0 - the first argument
    */
   public PDFtoTxtConverterTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.pttc = new PDFtoTxtConverter();
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   /*
    * Test for File convert(File)
    */
   public void testConvertFile()
   {
      File file = null;
      try
      {
         file = this.pttc.convert(new File("pdf/examples/Hausübung1.pdf"));
         assertTrue("pruefen ob das tempfile angelegt wurde", file.exists());
         assertEquals("dateinamen überprüfen", new String("Hausübung1.pdf.cnv"), file.getName());
         file.deleteOnExit();
      }
      catch (IOException e)
      {
         fail("Exception geworfen :" + e.getMessage());
      }
   }

   /*
    * Test for File convert(File, File)
    */
   public void testConvertFileFile()
   {
      File file = null;
      try
      {
         File dest = new File("test");

         file = this.pttc.convert(new File("pdf/examples/Hausübung1.pdf"), dest);
         assertTrue("pruefen ob das tempfile angelegt wurde", file.exists());
         assertEquals("dateinamen überprüfen", new String("Hausübung1.pdf.cnv"), file.getName());
         file.deleteOnExit();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail("Exception geworfen :" + e.getMessage());
      }
   }

   public void testConvertFile1()
   {
      File origin = new File("pdf/examples/Hausübung1.pdf");
      File destination = new File("src/org/dotplot/tokenizer/tests/pdfout.txt");

      try
      {
         destination = this.pttc.convertFile(origin, destination);
      }
      catch (Exception e)
      {
         fail("Exception geworfen :" + e.getMessage());
      }

      assertTrue("pruefen ob die datei angelegt wurde", destination.exists());
   }
}
