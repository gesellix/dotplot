/**
 * 
 */
package org.dotplot.image.tests;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.dotplot.core.IDotplot;
import org.dotplot.image.IROIResult;
import org.dotplot.image.QImageContext;
import org.eclipse.swt.graphics.ImageData;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class QImageContextTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'org.dotplot.image.QImageContext.QImageContext(IDotplot)'
	 */
	public void testQImageContext() {
		try {
			new QImageContext(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		IDotplot dotplot = new IDotplot(){

			public Object getImage(Class imageClass) {
				return null;
			}

			public float setTargetSize(Dimension size) {
				return 0;
			}

			public IROIResult getDetailsForROI(Rectangle roi, ImageData imgData) {
				return null;
			}};
		QImageContext context = new QImageContext(dotplot);
		assertSame(dotplot, context.getDotplot());
	}

}
