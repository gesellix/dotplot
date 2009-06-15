/**
 * 
 */
package org.dotplot.tokenizer.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.tokenizer.tests");
	// $JUnit-BEGIN$
	suite.addTest(org.dotplot.tokenizer.service.tests.AllTests.suite());
	suite.addTest(org.dotplot.tokenizer.filter.tests.AllTests.suite());
	suite.addTest(org.dotplot.tokenizer.converter.tests.AllTests.suite());
	suite.addTestSuite(EOLTokenTest.class);
	suite.addTestSuite(EOFTokenTest.class);
	suite.addTestSuite(TokenTest.class);
	// $JUnit-END$
	return suite;
    }

}
