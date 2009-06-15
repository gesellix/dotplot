/**
 * 
 */
package org.dotplot.util.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.util.tests");
	// $JUnit-BEGIN$
	suite.addTestSuite(RegistryTest.class);
	suite.addTestSuite(UnknownIDExceptionTest.class);
	suite.addTestSuite(DOMTreeIteratorTest.class);
	suite.addTestSuite(JarFileClassLoaderTest.class);
	suite.addTestSuite(DirectoryJarClassLoaderTest.class);
	suite.addTestSuite(DuplicateRegistrationExceptionTest.class);
	// $JUnit-END$
	return suite;
    }

}
