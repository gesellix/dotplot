/*
 * Created on 12.05.2004
 */
package org.dotplot.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.DotplotCreator;
import org.dotplot.image.IDotplot;
import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.scanner.DefaultScanner;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotterTest extends TestCase
{

   private DotplotCreator dp;

   /**
    * Constructor for DotplotterTest.
    */
   public DotplotterTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.dp = new DotplotCreator();
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   /*
   public void testSetGetFileList() {
      this.dp.setFileList(new DefaultFileList());
      assertTrue(
         "prüft ob eine Dateiliste zurück kommt",
         this.dp.getFileList() instanceof IFileList);
   }
   */

   /*
   public void testTokenizerFMatrixInterface(){

   	fail("test must be rewritten!");

   	FMatrixManager testFMatrixManager = new FMatrixManager();
   	IFMatrix testFMatrix = null;

   	Tokenizer tokenizer = new Tokenizer();
   	IConfiguration configuration = new DefaultConfiguration();
   	DefaultFileList list = new DefaultFileList();

   	ITokenStream stream = null;

   	list.add(new File("txt/bild/1.txt"));
   	list.add(new File("txt/sueddeutsche/1.txt"));

   		configuration.setFileList(list);
   		configuration.setScanner(new DefaultScanner());
   		tokenizer.setConfiguration(configuration);


   		Token token;


   		int anzahlTokens = 0;

   		try{
   		  stream = tokenizer.getTokenStream();
   	  	} catch (TokenizerException e){
   			System.out.println(e.getMessage());
   			System.exit(1);
   		}

   		if (stream == null )System.out.println("stream is null !! failure !!");

   		testFMatrix = testFMatrixManager.genFmatrix(stream);

   		assertNotNull("testFmatrix  hast to be an instance of IFMatrix", testFMatrix);

   		//FMatrix.toConsole(testFMatrix);
   }
   */

   public void testGenDotplot() throws TokenizerException
   {
      DefaultFileList df = new DefaultFileList();
      df.add(new File("txt/bild/1.txt"));
      df.add(new File("txt/spiegel/1.txt"));
      this.dp.setFileList(df);
      this.dp.getTokenizerConfiguration().setScanner(new DefaultScanner());
      IDotplot id = this.dp.getDotplot();
      assertNotNull("püft ob ein Dotplot-Objekt herraus kommt", id);
      assertSame("prüft ob beim zweiten aufruf das gleich objekt zurück kommt", id, this.dp.getDotplot());
      this.dp.setFileList(df);
      assertNotNull(df);
      assertNotSame("prüft ob beim zweiten aufruf nicht das gleich objekt zurück kommt", id, this.dp.getDotplot());
   }

   public void testGetSetTokentypes()
   {
      assertEquals("pruefen ob am anfang die Tokentypenliste leer ist", 0, this.dp.getTokenTypes().length);
      TokenType[] tt = {
         new TokenType(1, "class", TokenType.KIND_KEYWORD), new TokenType(2, "void", TokenType.KIND_KEYWORD)
      };
      this.dp.setTokenTypes(tt);
      assertSame("pruefen ob das was rein ging auch wieder raus kommt", tt, this.dp.getTokenTypes());
   }
}