/**
 * 
 */
package org.dotplot.core.plugins.ressources.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.ressources.tests");
	// $JUnit-BEGIN$
	suite.addTestSuite(DirectoryRessourceTest.class);
	suite.addTestSuite(FileRessourceTest.class);
	// $JUnit-END$
	return suite;
    }

}
