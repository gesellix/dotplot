/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

import org.dotplot.fmatrix.FileInformation;
import org.dotplot.fmatrix.LineInformation;
import org.dotplot.fmatrix.TokenInformation;

/**
 * Description
 *
 * @author Oguz Huryasar, Thorsten Ruehl
 * @version 0.2
 */
public class TokenInformationTest extends TestCase
{

   private TokenInformation tokenInformation;
   private File file1, file2, file3;
   private FileInformation fileContainer, fContainer2, fContainer3, fContainer4;

   public void setUp()
   {
      this.tokenInformation = new TokenInformation();
      try
      {
         file1 = File.createTempFile("txt1", "txt");
         file2 = File.createTempFile("txt2", "txt");
         file3 = File.createTempFile("txt3", "txt");
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      fileContainer = new FileInformation(0, file1);
      fContainer2 = new FileInformation(200, file2);
      fContainer3 = new FileInformation(300, file3);
      fContainer4 = new FileInformation(600, null);
   }

   public void tearDown()
   {
      if (file1.exists())
      {
         file1.delete();
      }
      if (file2.exists())
      {
         file2.delete();
      }
      if (file3.exists())
      {
         file3.delete();
      }
   }

   public void testSetUp()
   {
      assertNotNull("TokenInformation object must not be null!", this.tokenInformation);
   }

   public void testGetSize()
   {
      LineInformation someLineInformation = new LineInformation();
      this.tokenInformation.addLineInformationContainer(someLineInformation);
      assertTrue("expected size must be greater than 0!", this.tokenInformation.getLineInfoSize() > 0);
   }

   public void testFileInfoAcces()
   {
      this.tokenInformation.addFileInformation(this.fileContainer);
      this.tokenInformation.addFileInformation(this.fContainer2);
      this.tokenInformation.addFileInformation(this.fContainer3);
      this.tokenInformation.addFileInformation(this.fContainer4);

      assertEquals("(1) The fileInfoNumber has to be 3", 3, this.tokenInformation.getFileInfoNumber());

      assertEquals("(2) File ID from tokenindex 199 has to be 0", 0, this.tokenInformation.getFileIndex(199));

      assertEquals("(3) File ID from tokenindex 250 has to be 1", 1, this.tokenInformation.getFileIndex(250));

      assertEquals("(4) File ID from tokenindex 350 has to be 2", 2, this.tokenInformation.getFileIndex(350));

      try
      {
         assertEquals("(5) checking if filename of 1. entry is correct",
               this.file1.getCanonicalPath(),
               this.tokenInformation.getFileName(0));

         assertEquals("(6) checking if filename of 2. entry is correct",
               this.file2.getCanonicalPath(),
               this.tokenInformation.getFileName(1));

         assertEquals("(7) checking if filename of 3. entry is correct",
               this.file3.getCanonicalPath(),
               this.tokenInformation.getFileName(2));
      }
      catch (Exception e)
      {
         fail("no exception should be thrown");
      }
   }
}
