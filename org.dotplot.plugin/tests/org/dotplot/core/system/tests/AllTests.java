/**
 * 
 */
package org.dotplot.core.system.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.dotplot.core.system.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(CoreSystemTest.class);
		//$JUnit-END$
		return suite;
	}

}
