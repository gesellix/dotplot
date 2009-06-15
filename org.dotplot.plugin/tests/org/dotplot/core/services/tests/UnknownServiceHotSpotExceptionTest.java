/**
 * 
 */
package org.dotplot.core.services.tests;

import junit.framework.TestCase;

import org.dotplot.core.services.ServiceHotSpot;
import org.dotplot.core.services.UnknownServiceHotSpotException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class UnknownServiceHotSpotExceptionTest extends TestCase {

    /*
     * Test method for
     * 'org.dotplot.services.UnknownServiceHotSpotException.UnknownServiceHotSpotException(IServiceHotSpot)'
     */
    public void testUnknownServiceHotSpotExceptionIServiceHotSpot() {
	ServiceHotSpot spot = new ServiceHotSpot("test", String.class);
	Exception e = new UnknownServiceHotSpotException(spot);
	assertEquals("test", e.getMessage());
    }

    /*
     * Test method for
     * 'org.dotplot.services.UnknownServiceHotSpotException.UnknownServiceHotSpotException(String)'
     */
    public void testUnknownServiceHotSpotExceptionString() {
	Exception e = new UnknownServiceHotSpotException("test");
	assertEquals("test", e.getMessage());
    }

}
