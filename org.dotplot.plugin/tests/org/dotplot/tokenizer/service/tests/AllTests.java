/**
 * 
 */
package org.dotplot.tokenizer.service.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.dotplot.tokenizer.service.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(TokenizerServiceTest.class);
		suite.addTestSuite(PlotSourceListTokenizerTest.class);
		suite.addTestSuite(TokenStreamContextTest.class);
		suite.addTestSuite(TokenizerTaskPartTest.class);
		suite.addTestSuite(DefaultTokenizerConfigurationTest.class);
		//$JUnit-END$
		return suite;
	}

}
