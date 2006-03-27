/**
 * 
 */
package org.dotplot.fmatrix.test;

import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.fmatrix.TypeTableNavigator;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FMatrixContextTest extends TestCase {

	/*
	 * Test method for 'org.dotplot.fmatrix.FMatrixContext.FMatrixContext(ITypeTableNavigator)'
	 */
	public void testFMatrixContext() {
		FMatrixContext context;
		
		try {
			new FMatrixContext(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			TypeTableNavigator navigator = new TypeTableNavigator(null);
			context = new FMatrixContext(navigator);
			assertSame(navigator, context.getTypeTableNavigator());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
