/**
 * 
 */
package org.dotplot.image.tests;

import java.awt.Dimension;
import java.awt.Rectangle;

import junit.framework.TestCase;

import org.dotplot.core.IDotplot;
import org.dotplot.image.IROIResult;
import org.dotplot.image.QImageContext;
import org.eclipse.swt.graphics.ImageData;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class QImageContextTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	@Override
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
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		IDotplot dotplot = new IDotplot() {

			public IROIResult getDetailsForROI(Rectangle roi, ImageData imgData) {
				return null;
			}

			public Object getImage(Class imageClass) {
				return null;
			}

			public float setTargetSize(Dimension size) {
				return 0;
			}
		};
		QImageContext context = new QImageContext(dotplot);
		assertSame(dotplot, context.getDotplot());
	}

}
