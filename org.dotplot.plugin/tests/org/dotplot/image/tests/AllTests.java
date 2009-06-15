/**
 * 
 */
package org.dotplot.image.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.image.tests");
	// $JUnit-BEGIN$
	suite.addTestSuite(QImageServiceTest.class);
	suite.addTestSuite(QImageTaskPartTest.class);
	suite.addTestSuite(QImageConfigurationTest.class);
	suite.addTestSuite(QImageContextTest.class);
	// $JUnit-END$
	return suite;
    }

}
