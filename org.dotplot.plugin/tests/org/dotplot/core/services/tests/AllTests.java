/**
 * 
 */
package org.dotplot.core.services.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite(
		"Test for org.dotplot.core.services.tests");
	// $JUnit-BEGIN$
	suite.addTestSuite(AbstractJobTest.class);
	suite.addTestSuite(DefaultTaskProcessorTest.class);
	suite.addTestSuite(ServiceManagerTest.class);
	suite.addTestSuite(AbstractTaskPartTest.class);
	suite.addTestSuite(IllegalContextExceptionTest.class);
	suite.addTestSuite(ServiceHotSpotTest.class);
	suite.addTestSuite(AbstractServiceTest.class);
	suite.addTestSuite(UnknownServiceHotSpotExceptionTest.class);
	suite.addTestSuite(InsufficientRessourcesExceptionTest.class);
	suite.addTestSuite(ExtentionTest.class);
	suite.addTestSuite(TaskTest.class);
	suite.addTestSuite(IllegalServiceExtentionExceptionTest.class);
	// $JUnit-END$
	return suite;
    }

}
