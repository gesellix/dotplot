/**
 * 
 */
package org.dotplot.tokenizer.converter.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.dotplot.tokenizer.converter.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConverterServiceTest.class);
		suite.addTestSuite(PDFtoTxtConverterTest.class);
		suite.addTestSuite(ConverterTaskPartTest.class);
		suite.addTestSuite(SourceListContextTest.class);
		suite.addTestSuite(DefaultConverterConfigurationTest.class);
		//$JUnit-END$
		return suite;
	}

}
