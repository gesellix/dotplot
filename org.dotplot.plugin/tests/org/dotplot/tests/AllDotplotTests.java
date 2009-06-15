/*
 * Created on 12.05.2004
 */
package org.dotplot.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestSuit für den DotplotCreator. Alle Teams sollten hier ihre Testcases/suits
 * eintragen um eine Zentrale "Teststelle" zu haben. (beeindruckt den Kunden)
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class AllDotplotTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for org.dotplot.tests");
	// $JUnit-BEGIN$

	// Modulspezifische Tests
	// suite.addTest(AllTests.suite());
	suite.addTest(org.dotplot.tokenizer.tests.AllTests.suite());
	suite.addTest(org.dotplot.fmatrix.test.AllTests.suite());
	suite.addTest(org.dotplot.util.tests.AllTests.suite());
	suite.addTest(org.dotplot.core.tests.AllTests.suite());
	suite.addTest(org.dotplot.image.tests.AllTests.suite());
	suite.addTest(org.dotplot.eclipse.tests.AllTests.suite());
	// $JUnit-END$
	return suite;
    }
}
