/*
 * Created on 26.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.tokenizer.DefaultConfiguration;
import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Tokenizer;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.scanner.DefaultScanner;

/**
 * test FMatrixManager
 * Create a token stream(from tokenizer) to assert
 * FMatrixManager functionality.
 *
 * @author Constantin von Zitzewitz
 * @version 0.1
 */
public class FMatrixManagerTest extends TestCase
{

   // ################## FMatrix #############################################
   private FMatrixManager manager; // fMatrixManager

   // ################## Tokenizer ###########################################
   private Tokenizer tokenizer; // delivers the tokens
   private IConfiguration configuration; // for tokenizer
   private DefaultFileList fileList; // for tokenizer
   private ITokenStream tokenStream; // for tokenizer

   public void setUp()
   {

      this.tokenizer = new Tokenizer();
      this.configuration = new DefaultConfiguration();
      this.fileList = new DefaultFileList();
      this.tokenStream = null;

      // this.fileList.add(new File("txt/bibel/bibel_halb.txt"));
      // this.fileList.add(new File("txt/bibel/bibel.txt"));

      this.fileList.add(new File("txt/spiegel/1.txt"));
      this.fileList.add(new File("txt/sueddeutsche/1.txt"));

      // this.fileList.add(new File("ChangeLog"));
      // this.fileList.add(new File("ChangeLog"));

      // this.fileList.add(new File("FAQ"));
      // this.fileList.add(new File("FAQ"));

      //this.fileList.add(new File("PJunitTestFile.pdf"));
      //this.fileList.add(new File("PJunitTestFile.pdf"));

      this.configuration.setFileList(fileList);
      this.configuration.setScanner(new DefaultScanner());

      this.tokenizer.setConfiguration(this.configuration);

      try
      {
         this.tokenStream = tokenizer.getTokenStream();
      }
      catch (TokenizerException e)
      {
         System.out.println(e.getMessage());
         System.exit(1);
      }

      this.manager = new FMatrixManager(this.tokenStream);
   }

   public void testSetUp()
   {
      assertNotNull("manager must not be null!", this.manager);
      assertNotNull("tokenizer must not be null!", this.tokenizer);
      assertNotNull("configuration must not be null!", this.configuration);
      assertNotNull("Filelist must not be null!", this.fileList);
      assertNotNull("tokenStream must not be null!", this.tokenStream);
      assertTrue("token stream must be an ITokenStream", this.tokenStream instanceof ITokenStream);
   }

   public void testRun()
   {
//      long startingTime = System.currentTimeMillis();
      this.manager.addTokens();
//      System.out.println("adding took " +
//            ((float) (System.currentTimeMillis() - startingTime) / 1000) +
//            " seconds.");

      ITypeTableNavigator navigator = this.manager.getTypeTableNavigator();

      //System.out.println("POSTINGS: " + navigator.getNumberOfAllMatches());

      long countPostings = 0;
      while ((navigator.getNextMatch()) != null)
      {
         if ((countPostings % 5000000) == 0)
         {
            //System.out.println("found " + countPostings + " postings...");
         }
         countPostings++;
      }

      assertEquals("calculated number of tokens must be same as found",
            navigator.getNumberOfAllMatches(),
            countPostings);

//      System.out.println("found " + countPostings + " postings...");
//      System.out.println("retreiving postings took " +
//            ((float) (System.currentTimeMillis() - startingTime) / 1000) +
//            " seconds.");
   }
}
