/*
 * Created on 26.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.ISourceList;
import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.tokenizer.service.ITokenizerConfiguration;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TokenizerService;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.util.UnknownIDException;

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
   private ITokenizerConfiguration configuration; // for tokenizer
   private ISourceList fileList; // for tokenizer
   private ITokenStream tokenStream; // for tokenizer

   public void setUp()
   {
	   DotplotContext context = ContextFactory.getContext();
	   context.setNoGui(true);
	   
//      this.tokenizer = new Tokenizer();
      try {
		this.configuration = (ITokenizerConfiguration) context.getConfigurationRegistry().get(TokenizerService.ID_CONFIGURATION_TOKENIZER);
      }
      catch (UnknownIDException e1) {
    	  // TODO Auto-generated catch block
    	  e1.printStackTrace();
      }
      
      this.fileList = new DefaultSourceList();
      this.tokenStream = null;

      // this.fileList.add(new File("txt/bibel/bibel_halb.txt"));
      // this.fileList.add(new File("txt/bibel/bibel.txt"));

      this.fileList.add(context.createDotplotFile(new File("testfiles/fmatrix/test.txt")));
      this.fileList.add(context.createDotplotFile(new File("testfiles/fmatrix/test.txt")));

      // this.fileList.add(new File("ChangeLog"));
      // this.fileList.add(new File("ChangeLog"));

      // this.fileList.add(new File("FAQ"));
      // this.fileList.add(new File("FAQ"));      

      //this.fileList.add(new File("PJunitTestFile.pdf"));
      //this.fileList.add(new File("PJunitTestFile.pdf"));

      //this.configuration.setSourceList(this.fileList);
      this.configuration.setTokenizerID(TokenizerService.ID_TOKENIZER_DEFAULT);

//      this.tokenizer.setConfiguration(this.configuration);

      try
      {
         //this.tokenStream = tokenizer.getTokenStream();
      }
      catch (TokenizerException e)
      {
         System.out.println(e.getMessage());
         System.exit(1);
      }

      this.manager = new FMatrixManager(this.tokenStream, new DefaultFMatrixConfiguration());
      this.manager.setSourceList(this.fileList);
   }

   public void tearDown(){
	   ContextFactory.getContext().setNoGui(false);	   
   }
  
   
//   Christian Gerhardt: "Wozu ist dieser Test gut?".
   
//   public void testSetUp()
//   {
//	   fail("redesign this test");
//      assertNotNull("manager must not be null!", this.manager);
//      assertNotNull("configuration must not be null!", this.configuration);
//      assertNotNull("Filelist must not be null!", this.fileList);
//      assertNotNull("tokenStream must not be null!", this.tokenStream);
//      assertTrue("token stream must be an ITokenStream", this.tokenStream instanceof ITokenStream);
//   }

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
