/**
 * 
 */
package org.dotplot.core.services.tests;

import junit.framework.TestCase;

import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IllegalContextException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class IllegalContextExceptionTest extends TestCase {

	IContext context;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.context = new IContext() {
		};
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.IllegalContextException.IllegalContextException(IContext)'
	 */
	public void testIllegalContextException() {
		Exception e;
		try {
			new IllegalContextException(null);
			fail("NullPointerException must be thrown.");
		}
		catch (NullPointerException n) {
			/* all clear */
		}

		e = new IllegalContextException(this.context);

		// the masseage is based on the on-the-fly implementation of the
		// IContext interface
		assertEquals(this.getClass().getName() + "$1", e.getMessage());
	}

}
