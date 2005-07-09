package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.DefaultConfiguration;
import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.IFileList;
import org.dotplot.tokenizer.scanner.DefaultScanner;
import org.dotplot.tokenizer.scanner.IScanner;

/**
 * Test Class for the DefaultConfiguration
 *
 * @author case
 * @version 1.0
 */
public class DefaultConfigurationTest extends TestCase
{

   private DefaultConfiguration defCon;

   /**
    * Constructor for DefaultConfigurationTest.
    *
    * @param arg0 - the first argument
    */
   public DefaultConfigurationTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.defCon = new DefaultConfiguration();
   }

   public void testSetScanner()
   {
      IScanner scanner = new DefaultScanner();
      this.defCon.setScanner(scanner);
      assertTrue("prüfen ob der scanner der rein ging auch wieder raus kommt", this.defCon.getScanner() == scanner);
   }

   public void testGetScanner()
   {

      assertTrue("prüfen ob ein objekt vom typ IScanner zurückgegeben wird",
            this.defCon.getScanner() instanceof IScanner);
   }

   public void testGetFileList()
   {
      assertTrue("prüfen ob ein objekt vom typ IFileList zurückgegeben wird",
            this.defCon.getFileList() instanceof IFileList);
   }

   public void testSetFileList()
   {
      IFileList fileList = new DefaultFileList();
      this.defCon.setFileList(fileList);
      assertTrue("prüfen ob die filelist die rein ging auch wieder raus kommt", this.defCon.getFileList() == fileList);
   }

   public void testGetSetConvertFiles()
   {
      assertFalse("prüfen ob der anfangswert stimmt", this.defCon.getConvertFiles());
      this.defCon.setConvertFiles(true);
      assertTrue("prüfen ob das setzen geklappt hat", this.defCon.getConvertFiles());
   }

   public void testGetSetConvertDirectory()
   {
      assertNull("prüfen ob der anfangswert stimmt", this.defCon.getConvertDirectory());
      File dir = new File("");
      this.defCon.setConvertDirectory(dir);
      assertSame("prüfen ob das setzen geklappt hat", dir, this.defCon.getConvertDirectory());
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }
}
