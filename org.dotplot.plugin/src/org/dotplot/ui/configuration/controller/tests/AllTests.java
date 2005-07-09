/*
 * Created on 15.06.2004
 */
package org.dotplot.ui.configuration.controller.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Insert Description for <code>AllTests</code>
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class AllTests
{

   public static Test suite()
   {
      TestSuite suite = new TestSuite("Test for org.dotplot.ui.configuration.controller.tests");
      //$JUnit-BEGIN$
      suite.addTest(new TestSuite(SelectTokenTypesControllerTest.class));
      suite.addTest(new TestSuite(SelectTokenScannerControllerTest.class));
      //$JUnit-END$
      return suite;
   }
}
