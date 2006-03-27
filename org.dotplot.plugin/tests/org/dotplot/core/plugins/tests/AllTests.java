/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.dotplot.core.plugins.tests");
		//$JUnit-BEGIN$
		suite.addTest(org.dotplot.core.plugins.ressources.tests.AllTests.suite());
		suite.addTestSuite(PluginLoadingTaskPartTest.class);
		suite.addTestSuite(PluginRegistryTest.class);
		suite.addTestSuite(VersionPatternTest.class);
		suite.addTestSuite(PluginIntegrationServiceTest.class);
		suite.addTestSuite(VersionTest.class);
		suite.addTestSuite(PluginTest.class);
		suite.addTestSuite(PluginLoadingJobTest.class);
		suite.addTestSuite(JobRegistryTest.class);
		suite.addTestSuite(PluginJarFileTest.class);
		suite.addTestSuite(InitializerServiceTest.class);
		suite.addTestSuite(PluginIntegrationTaskPartTest.class);
		suite.addTestSuite(PluginLoadingServiceTest.class);
		suite.addTestSuite(BatchJobTest.class);
		//$JUnit-END$
		return suite;
	}

}
