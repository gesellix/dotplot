package org.dotplot.util.tests;

import static org.junit.Assert.assertEquals;

import org.dotplot.util.UnknownIDException;
import org.junit.Test;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class UnknownIDExceptionTest {

	/**
	 * Test method for
	 * 'org.dotplot.core.UnknownJobException.UnknownJobException(String)'.
	 */
	@Test
	public void testUnknownJobException() {
		assertEquals("test", new UnknownIDException("test").getMessage());
		assertEquals("null", new UnknownIDException(null).getMessage());
	}
}
