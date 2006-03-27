/**
 * 
 */
package org.dotplot.util.tests;

import org.dotplot.util.UnknownIDException;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class UnknownIDExceptionTest extends TestCase {

	/*
	 * Test method for 'org.dotplot.core.UnknownJobException.UnknownJobException(String)'
	 */
	public void testUnknownJobException() {
		assertEquals("test", new UnknownIDException("test").getMessage());		
		assertEquals("null", new UnknownIDException(null).getMessage());
	}

}
