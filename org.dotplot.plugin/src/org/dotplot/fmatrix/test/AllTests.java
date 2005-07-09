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
      suite.addTest(new TestSuite(FMatrixManagerTest.class));
      suite.addTest(new TestSuite(MatchTest.class));
      suite.addTest(new TestSuite(TokenTableTest.class));
      suite.addTest(new TestSuite(TokenTypeTest.class));
      suite.addTest(new TestSuite(TypeTableNavigatorTest.class));
      suite.addTest(new TestSuite(TypeTableTest.class));
      suite.addTest(new TestSuite(LineInformationContainerTest.class));
      suite.addTest(new TestSuite(TokenInformationTest.class));
      suite.addTest(new TestSuite(LineInformationContainerTest.class));
      //$JUnit-END$
      return suite;
   }
}
