/**
 * 
 */
package org.dotplot.core.plugins.ressources.tests;

import java.io.File;

import junit.framework.TestCase;

import org.dotplot.core.plugins.ressources.DirectoryRessource;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class DirectoryRessourceTest extends TestCase {

    private File file, dir;

    private DirectoryRessource dr;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.file = new File("./testfiles/ressources/test.txt")
		.getCanonicalFile();
	this.dir = new File("./testfiles/ressources").getCanonicalFile();
	this.dr = new DirectoryRessource(this.dir);

    }

    /*
     * Test method for
     * 'org.dotplot.ressources.DirectoryRessource.DirectoryRessource(File)'
     */
    public void testDirectoryRessourceFile() {
	try {
	    new DirectoryRessource((File) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource("./testfiles/ressources/test.txt");
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource("bla.txt");
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource("./testfiles/ressources");
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.ressources.DirectoryRessource.DirectoryRessource(String)'
     */
    public void testDirectoryRessourceString() {
	try {
	    new DirectoryRessource((String) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource(this.file);
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource(new File("bla.txt"));
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DirectoryRessource(this.dir);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.ressources.DirectoryRessource.getInputStream()'
     */
    public void testGetInputStream() {
	assertNull(this.dr.getInputStream());
    }

    /*
     * Test method for 'org.dotplot.ressources.DirectoryRessource.getURL()'
     */
    public void testGetURL() {
	try {
	    assertNotNull(this.dr.getURL());
	    assertEquals(this.dir.toURI().toURL(), this.dr.getURL());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

}
