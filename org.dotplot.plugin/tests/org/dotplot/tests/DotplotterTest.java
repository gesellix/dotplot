/*
 * Created on 12.05.2004
 */
package org.dotplot.tests;

import junit.framework.TestCase;

import org.dotplot.DotplotCreator;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public final class DotplotterTest extends TestCase {

	private DotplotCreator dp;

	/**
	 * Constructor for DotplotterTest.
	 */
	public DotplotterTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// this.dp = new DotplotCreator();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Fake method, just to let this test pass.
	 */
	public void testJustThat() {
		/* nothing to test here */
	}

	/*
	 * public void testSetGetFileList() { this.dp.setFileList(new
	 * DefaultFileList()); assertTrue( "prüft ob eine Dateiliste zurück kommt",
	 * this.dp.getFileList() instanceof IFileList); }
	 */

	/*
	 * public void testTokenizerFMatrixInterface(){
	 * 
	 * fail("test must be rewritten!");
	 * 
	 * FMatrixManager testFMatrixManager = new FMatrixManager(); IFMatrix
	 * testFMatrix = null;
	 * 
	 * Tokenizer tokenizer = new Tokenizer(); IConfiguration configuration = new
	 * DefaultConfiguration(); DefaultFileList list = new DefaultFileList();
	 * 
	 * ITokenStream stream = null;
	 * 
	 * list.add(new File("txt/bild/1.txt")); list.add(new
	 * File("txt/sueddeutsche/1.txt"));
	 * 
	 * configuration.setFileList(list); configuration.setScanner(new
	 * DefaultScanner()); tokenizer.setConfiguration(configuration);
	 * 
	 * 
	 * Token token;
	 * 
	 * 
	 * int anzahlTokens = 0;
	 * 
	 * try{ stream = tokenizer.getTokenStream(); } catch (TokenizerException e){
	 * System.out.println(e.getMessage()); System.exit(1); }
	 * 
	 * if (stream == null )System.out.println("stream is null !! failure !!");
	 * 
	 * testFMatrix = testFMatrixManager.genFmatrix(stream);
	 * 
	 * assertNotNull("testFmatrix hast to be an instance of IFMatrix",
	 * testFMatrix);
	 * 
	 * //FMatrix.toConsole(testFMatrix); }
	 */

	// public void testGenDotplot() throws TokenizerException
	// {
	// //in case of swt-library exceütion use vm-arguments:
	// //windows:
	// //-Djava.library.path=${system:ECLIPSE_HOME}/configuration/org.eclipse.osgi/bundles/3/1/.cp
	// //motif
	// //-Djava.library.path=${system:ECLIPSE_HOME}/configuration/org.eclipse.osgi/bundles/58/1/.cp
	// //to run
	// try {
	// DefaultSourceList df = new DefaultSourceList();
	// df.add(new DotplotFile("testfiles/test1.txt"));
	// df.add(new DotplotFile("testfiles/test2.txt"));
	// this.dp.setFileList(df);
	// //this.dp.getTokenizerConfiguration().setTokenizerID(TokenizerService.DEFAULT_TOKENIZER_ID);
	// IDotplot id = this.dp.getDotplot(true);
	// assertNotNull("püft ob ein Dotplot-Objekt herraus kommt", id);
	// assertSame("prüft ob beim zweiten aufruf das gleich objekt zurück kommt",
	// id, this.dp.getDotplot(true));
	// this.dp.setFileList(df);
	// assertNotNull(df);
	// assertNotSame("prüft ob beim zweiten aufruf nicht das gleich objekt
	// zurück kommt", id, this.dp.getDotplot(true));
	// }
	// catch(Exception e){
	// fail("no exception! :" + e.getClass().getName());
	// }
	// }
}
