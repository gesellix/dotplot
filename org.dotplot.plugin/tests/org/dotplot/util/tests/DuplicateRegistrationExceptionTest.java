/**
 * 
 */
package org.dotplot.util.tests;

import junit.framework.TestCase;

import org.dotplot.util.DuplicateRegistrationException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class DuplicateRegistrationExceptionTest extends TestCase {

	/*
	 * Test method for
	 * 'org.dotplot.core.DuplicateJobRegistrationException.DuplicateJobRegistrationException(String)'
	 */
	public void testDuplicateJobRegistrationException() {
		assertEquals("test", new DuplicateRegistrationException("test")
				.getMessage());
		assertEquals("null", new DuplicateRegistrationException(null)
				.getMessage());
	}

}
