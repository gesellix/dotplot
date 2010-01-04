/**
 * 
 */
package org.dotplot.core.services.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.Version;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IServiceExtentionActivator;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class ExtentionTest extends TestCase {

    private class TestPlugin implements IServiceExtentionActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#getID()
	 */
	public String getID() {
	    return "org.dotplot.services.testplugin";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#getVersion()
	 */
	public Version getVersion() {
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#isActivated()
	 */
	public boolean isActivated() {
	    return true;
	}
    }

    private TestPlugin plugin;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.plugin = new TestPlugin();
    }

    public void testAddGetParameter() {
	Extention ex = new Extention(this.plugin, "test");
	assertEquals(0, ex.getParameters().size());
	assertNull(ex.getParameter("test"));
	try {
	    ex.addParameter(null, "test");
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    ex.addParameter("test", null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    ex.getParameter(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    ex.addParameter("test", "value");
	    assertEquals("value", ex.getParameter("test"));
	    ex.addParameter("test", "value2");
	    assertEquals("value2", ex.getParameter("test"));
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for 'org.dotplot.services.Extention.Extention(IServicePlugin,
     * Object)'
     */
    public void testExtention() {
	Extention e = new Extention(this.plugin, "test");
	assertSame(this.plugin, e.getActivator());
	assertEquals("test", e.getExtentionObject());
    }
}
