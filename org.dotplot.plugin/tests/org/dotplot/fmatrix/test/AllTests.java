/*
 * Created on 03.06.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author case
 *         <p/>
 *         TestSuite for the whole fmatrix package
 */
public class AllTests
{

   public static Test suite()
   {
      TestSuite suite = new TestSuite("Test for org.dotplot.fmatrix.test");
      //$JUnit-BEGIN$
      suite.addTestSuite(FMatrixManagerTest.class);
      suite.addTestSuite(MatchTest.class);
      suite.addTestSuite(TokenTableTest.class);
      suite.addTestSuite(TokenTypeTest.class);
      suite.addTestSuite(TypeTableNavigatorTest.class);
      suite.addTestSuite(TypeTableTest.class);
      suite.addTestSuite(LineInformationContainerTest.class);
      suite.addTestSuite(TokenInformationTest.class);
      suite.addTestSuite(LineInformationContainerTest.class);
      suite.addTestSuite(FMatrixServiceTest.class);
      suite.addTestSuite(FMatrixTaskPartTest.class);
      suite.addTestSuite(FMatrixContextTest.class);
      suite.addTestSuite(DefaultFMatrixConfigurationTest.class);
      //$JUnit-END$
      return suite;
   }
}
