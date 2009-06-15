/**
 * 
 */
package org.dotplot.core.services.tests;

import junit.framework.TestCase;

import org.dotplot.core.services.InsufficientRessourcesException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class InsufficientRessourcesExceptionTest extends TestCase {

    /*
     * Test method for
     * 'org.dotplot.services.InsufficientRessourcesException.InsufficientRessourcesException()'
     */
    public void testInsufficientRessourcesException() {
	/* could not be tested */
    }

    /*
     * Test method for
     * 'org.dotplot.services.InsufficientRessourcesException.InsufficientRessourcesException(String)'
     */
    public void testInsufficientRessourcesExceptionString() {
	Exception e;
	e = new InsufficientRessourcesException(null);
	assertNull(e.getMessage());
	e = new InsufficientRessourcesException(
		"org.dotplot.services.testService");
	assertEquals("org.dotplot.services.testService", e.getMessage());
    }

}
