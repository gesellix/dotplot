/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;

import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IServiceHotSpot;
import org.dotplot.core.services.IllegalServiceExtentionException;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class IllegalServiceExtentionExceptionTest extends TestCase {

	IServiceHotSpot spot;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.spot = new IServiceHotSpot(){

			public String getID() {
				return "org.dotplot.test.testspot";
			}

			public Class getExtentionClass() {
				return null;
			}

			public boolean isValidExtention(Extention extention) {
				return false;
			}

			public boolean isValidExtention(Class extentionClass) {
				return false;
			}

			public Collection<Extention> getAllExtentions() {
				return null;
			}

			public Collection<Extention> getActiveExtentions() {
				return null;
			}

			public void addExtention(Extention extention) {}

			public void removeExtention(Extention extention) {}

			public Collection<Object> getObjectsFromActivatedExtentions() {
				return null;
			}

			
		};
	}

	/*
	 * Test method for 'org.dotplot.services.IllegalServiceExtentionException.IllegalServiceExtentionException(IServiceHotSpot)'
	 */
	public void testIllegalServiceExtentionException() {
		Exception e = new IllegalServiceExtentionException(this.spot);
		assertEquals("Illegal extention for org.dotplot.test.testspot", e.getMessage());
	}

}
