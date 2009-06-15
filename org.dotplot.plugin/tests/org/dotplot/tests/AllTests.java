/*
 * Created on 23.05.2004
 */
package org.dotplot.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Testsuite für alle Tests des Packets org.dotplot
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.tests");

	// $JUnit-BEGIN$
	suite.addTest(new TestSuite(DotplotterTest.class));
	// $JUnit-END$
	return suite;
    }
}
