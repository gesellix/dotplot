/**
 * 
 */
package org.dotplot.tokenizer.filter.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite(
		"Test for org.dotplot.tokenizer.filter.tests");
	// $JUnit-BEGIN$
	suite.addTestSuite(TokenFilterContainerTest.class);
	suite.addTestSuite(SentenceFilterTest.class);
	suite.addTestSuite(GeneralTokenFilterTest.class);
	suite.addTestSuite(LineFilterTest.class);
	suite.addTestSuite(FilterServiceTest.class);
	suite.addTestSuite(FilterTaskPartTest.class);
	suite.addTestSuite(DefaultFilterConfigurationTest.class);
	suite.addTestSuite(KeyWordFilterTest.class);
	// $JUnit-END$
	return suite;
    }

}
