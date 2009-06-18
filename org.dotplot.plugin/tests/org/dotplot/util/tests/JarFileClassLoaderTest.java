/**
 * 
 */
package org.dotplot.util.tests;

import java.io.File;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import org.dotplot.util.JarFileClassLoader;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class JarFileClassLoaderTest extends TestCase {

    private File directory;

    private File file1;

    private File file2;

    private JarFile jarFile1;

    private JarFile jarFile2;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.directory = new File("./testfiles").getCanonicalFile();
	this.file1 = new File("./testfiles/utiltests/testplugin.xml")
		.getCanonicalFile();
	this.file2 = new File("./testfiles/utiltests/testpackage.jar")
		.getCanonicalFile();
	this.jarFile1 = new JarFile(new File(
		"./testfiles/utiltests/ctestjar.jar").getCanonicalFile());
	this.jarFile2 = new JarFile(new File(
		"./testfiles/utiltests/testpackage.jar").getCanonicalFile());
    }

    /*
     * Test method for 'org.dotplot.util.JarFileClassLoader.findClass(String)'
     */
    public void testFindClassString() {
	JarFileClassLoader loader;
	try {
	    loader = new JarFileClassLoader(this.jarFile2, ClassLoader
		    .getSystemClassLoader());
	    loader = new JarFileClassLoader(this.jarFile1, loader);
	    Class object = loader.loadClass("test.ReflectionTestImpl2");
	    assertNotNull(object);
	    Object o = object.newInstance();
	    assertEquals("test.ReflectionTestImpl2", o.getClass().getName());
	} catch (Exception e) {
	    // e.printStackTrace();
	    fail("no exception: " + e.getClass().getName());
	} catch (Error e) {
	    e.printStackTrace();
	    fail("no error: " + e.getMessage());
	}
    }

    /*
     * Test method for 'org.dotplot.util.JarFileClassLoader.getJarFile()'
     */
    public void testGetJarFile() {
	try {
	    JarFileClassLoader loader = new JarFileClassLoader(this.jarFile1);
	    assertEquals(this.jarFile1, loader.getJarFile());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(File)'
     */
    public void testJarFileClassLoaderFile() {
	try {
	    new JarFileClassLoader((File) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.directory);
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.file1);
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.file2);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(File,
     * ClassLoader)'
     */
    public void testJarFileClassLoaderFileClassLoader() {
	try {
	    new JarFileClassLoader((File) null, ClassLoader
		    .getSystemClassLoader());
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.file2, null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.directory, ClassLoader
		    .getSystemClassLoader());
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.file1, ClassLoader
		    .getSystemClassLoader());
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.file2, ClassLoader
		    .getSystemClassLoader());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(JarFile)'
     */
    public void testJarFileClassLoaderJarFile() {
	try {
	    new JarFileClassLoader((JarFile) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.jarFile1);
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(JarFile,
     * ClassLoader)'
     */
    public void testJarFileClassLoaderJarFileClassLoader() {
	try {
	    new JarFileClassLoader((JarFile) null, ClassLoader
		    .getSystemClassLoader());
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.jarFile1, null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new JarFileClassLoader(this.jarFile1, ClassLoader
		    .getSystemClassLoader());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

}
