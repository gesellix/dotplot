/**
 * 
 */
package org.dotplot.core.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.core.tests");
	// $JUnit-BEGIN$
	suite.addTest(org.dotplot.core.plugins.tests.AllTests.suite());
	suite.addTest(org.dotplot.core.services.tests.AllTests.suite());
	suite.addTest(org.dotplot.core.system.tests.AllTests.suite());
	suite.addTestSuite(DotplotServiceTest.class);
	suite.addTestSuite(DotplotContextTest.class);
	suite.addTestSuite(ContextFactoryTest.class);
	suite.addTestSuite(ConfigurationRegistryTest.class);
	suite.addTestSuite(DotplotFileTest.class);
	suite.addTestSuite(TypeBindingRegistryTest.class);
	suite.addTestSuite(DefaultSourceListTest.class);
	// $JUnit-END$
	return suite;
    }

}
