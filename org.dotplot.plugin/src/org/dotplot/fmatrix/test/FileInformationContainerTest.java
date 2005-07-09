/*
 * Created on Jun 24, 2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.fmatrix.FileInformation;

/**
 * @author Thorsten Ruehl
 *         <p/>
 *         Tests for the FileInormationContainer-class. The class stores the information
 *         about the source files, for later processing.
 */
public class FileInformationContainerTest extends TestCase
{

   FileInformation fileInformation;
   File file1;

   /**
    * Constructor for FileInformationTest.
    */
   public FileInformationContainerTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();

      file1 = new File("/home/smeo/temp/txt1.txt");
      fileInformation = new FileInformation(0, file1);
   }

   public void testAdding()
   {
      assertTrue("(0) fileInformation must not be null", fileInformation != null);

      assertEquals("(1) filename of file1 and filenameentry has to be equal",
            file1.getName(),
            fileInformation.getFilename());

      assertEquals("(2) startindex of fileInformation has to be 0",
            0,
            fileInformation.getStartIndex());

      assertEquals("(3) filesize of file1 has and filesizentry has to be equal",
            file1.length(),
            fileInformation.getFileSize());
   }
}
