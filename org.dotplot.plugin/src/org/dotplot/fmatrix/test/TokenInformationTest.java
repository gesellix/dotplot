/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import java.io.File;

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
      file1 = new File("/home/smeo/temp/txt1.txt");
      file2 = new File("/home/smeo/temp/txt2.txt");
      file3 = new File("/home/smeo/temp/txt3.txt");

      fileContainer = new FileInformation(0, file1);
      fContainer2 = new FileInformation(200, file2);
      fContainer3 = new FileInformation(300, file3);
      fContainer4 = new FileInformation(600, null);

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

      assertEquals("(2) File ID from tokenindex 199 has to be 0", 0, this.tokenInformation.getFileIdByIndex(199));

      assertEquals("(3) File ID from tokenindex 250 has to be 1", 1, this.tokenInformation.getFileIdByIndex(250));

      assertEquals("(4) File ID from tokenindex 350 has to be 2", 2, this.tokenInformation.getFileIdByIndex(350));

      assertEquals("(5) checking if filename of 1. entry is correct",
            this.file1.getName(),
            this.tokenInformation.getFileNameByFileId(0));

      assertEquals("(6) checking if filename of 2. entry is correct",
            this.file2.getName(),
            this.tokenInformation.getFileNameByFileId(1));

      assertEquals("(7) checking if filename of 3. entry is correct",
            this.file3.getName(),
            this.tokenInformation.getFileNameByFileId(2));
   }
}
