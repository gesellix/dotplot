/**
 * 
 */
package org.dotplot.eclipse.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.dotplot.eclipse.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(EclipseUIServiceTest.class);
		//$JUnit-END$
		return suite;
	}

}