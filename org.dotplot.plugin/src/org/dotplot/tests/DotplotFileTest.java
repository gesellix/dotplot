/*
 * Created on 23.05.2004
 */
package org.dotplot.tests;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

import org.dotplot.tokenizer.DotplotFile;
import org.dotplot.tokenizer.IConverter;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotFileTest extends TestCase
{

   class SimpleConverter implements IConverter
   {

      /* (non-Javadoc)
       * @see org.dotplot.tokenizer.IConverter#convert(java.io.File)
       */
      public File convert(File file)
      {
         File f = null;

         try
         {
            f = File.createTempFile(file.getName(), ".tmp");
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }

         return f;
      }

      /* (non-Javadoc)
       * @see org.dotplot.tokenizer.IConverter#convert(java.io.File, java.io.File)
       */
      public File convert(File file, File directory)
      {
         File f = null;

         try
         {
            f = File.createTempFile(file.getName(), null, directory);
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }

         return f;
      }
   }

   private DotplotFile df;
   private SimpleConverter sc;

   /**
    * Constructor for DotplotFileTest.
    */
   public DotplotFileTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.df = new DotplotFile("txt/bild/1.txt");
      this.sc = new SimpleConverter();
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testDelete()
   {
      this.df = new DotplotFile("test.txt");
      this.df.setConverter(this.sc);

      try
      {
         this.df.createNewFile();
      }
      catch (IOException e)
      {
         fail("IOFehler!");
      }

      assertTrue("Prüfen obs die Datei gibt", this.df.exists());
      assertTrue("datei konvertieren", this.df.convertFile());
      assertTrue("prüfen ob die konvertierte datei existiert", this.df.getConvertedFile().exists());
      assertTrue("löschen der Datei", this.df.delete());
      assertFalse("Prüfen ob die Datei gelöscht wurde", this.df.exists());
      assertFalse("Prüfen ob die konvertierte Datei gelöscht wurde", this.df.getConvertedFile().exists());
   }

   /**
    * Test for void DotplotFile(File, String)
    */
   public void testDotplotFileFileString()
   {
      DotplotFile df = new DotplotFile(new File("txt/bild"), "1.txt");
      assertEquals("prüfen ob der Konstruktor Funktionierte", this.df, df);
   }

   /**
    * Test for void DotplotFile(String, String)
    */
   public void testDotplotFileStringString()
   {
      DotplotFile df = new DotplotFile("txt/bild", "1.txt");
      assertEquals("prüfen ob der Konstruktor Funktionierte", this.df, df);
   }

   /**
    * Test for void DotplotFile(String, boolean)
    */
   public void testDotplotFileStringboolean()
   {
      DotplotFile df = new DotplotFile("txt/bild/1.txt", true);
      assertEquals("prüfen ob die Konstruktoren stimmen", this.df, df);
      assertTrue("prüfen ob es ein konvertiertes File gibt", df.getConvertedFile() instanceof File);
   }

   /**
    * Test for void DotplotFile(File, String, boolean)
    */
   public void testDotplotFileFileStringboolean()
   {
      DotplotFile df = new DotplotFile(new File("txt/bild"), "1.txt", true);
      assertEquals("prüfen ob der Konstruktor Funktionierte", this.df, df);
      assertTrue("prüfen ob es ein konvertiertes File gibt", df.getConvertedFile() instanceof File);
   }

   /**
    * Test for void DotplotFile(String, String, boolean)
    */
   public void testDotplotFileStringStringboolean()
   {
      DotplotFile df = new DotplotFile("txt/bild", "1.txt", true);
      assertEquals("prüfen ob der Konstruktor Funktionierte", this.df, df);
      assertTrue("prüfen ob es ein konvertiertes File gibt", df.getConvertedFile() instanceof File);
   }

   /**
    * Test for boolean equals(Object)
    */
   public void testEqualsObject()
   {
      assertTrue("prüfen ob Equals mit der Original Datei geht", this.df.equals(new File("txt/bild/1.txt")));
      this.df.setConverter(this.sc);
      this.df.convertFile();
      assertTrue("prüfen ob Equals mit der konvertierten Datei geht", this.df.equals(this.df.getConvertedFile()));
   }

   public void testGetFile()
   {
      assertEquals("prüfen ob zuerst die original Datei zurückgegeben wird",
            new File("txt/bild/1.txt"),
            this.df.getFile());
      this.df.setConverter(this.sc);
      this.df.convertFile();
      assertEquals("prüfen ob nun die konvertierte Datei zurückgegeben wird",
            this.df.getConvertedFile(),
            this.df.getFile());
   }

   public void testGetOrigin()
   {
      assertEquals("prüfen ob die dateien stimmen", new File("txt/bild/1.txt"), this.df.getOrigin());
   }

   public void testGetConvertedFile()
   {
      assertNull("prüfen ob vor konvertierung Null zurückgegeben wird", this.df.getConvertedFile());
      this.df.setConverter(this.sc);
      this.df.convertFile();
      assertTrue("prüfen ob ein FileObjekt zurückgegeben wird", this.df.getConvertedFile() instanceof File);
   }

   public void testConvertFile()
   {
      this.df.setConverter(this.sc);
      assertTrue("prüfen ob die konvertierungsfunktion das richtige rausgibt", this.df.convertFile());

   }

   public void testGetSetConverter()
   {
      this.df.setConverter(this.sc);
      assertSame("prüfen ob die COnverter gleich sind", this.sc, this.df.getConverter());
   }
}
