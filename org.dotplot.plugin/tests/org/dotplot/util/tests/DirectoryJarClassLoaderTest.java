/**
 * 
 */
package org.dotplot.util.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.dotplot.util.DirectoryJarClassLoader;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class DirectoryJarClassLoaderTest extends TestCase {

    /*
     * Test method for
     * 'testpackage.DirectoryJarClassLoader.DirectoryJarClassLoader(File)'
     */
    public final void testDirectoryJarClassLoaderFile() {
	File directory = null;
	try {
	    directory = new File("testfiles/utiltests").getCanonicalFile();
	} catch (IOException e1) {
	    fail("unexpected Exception was thrown");
	}

	try {
	    new DirectoryJarClassLoader(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("NullPointerException must be thrown");
	}

	try {
	    new DirectoryJarClassLoader(new File("testfiles/testplugin.xml"));
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    assertEquals("The assigned argument must be an existing directory",
		    e.getMessage());
	} catch (Exception e) {
	    fail("IllegalArgumentException must be thrown");
	}

	File noDir = null;
	try {
	    noDir = new File("testfiles/blub").getCanonicalFile();
	    assertFalse(noDir.exists());
	    assertTrue(noDir.mkdir());
	    assertTrue(noDir.exists());
	    assertTrue(noDir.isDirectory());
	    assertTrue(noDir.delete());
	    assertFalse(noDir.exists());

	    new DirectoryJarClassLoader(noDir);
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    assertEquals("The assigned argument must be an existing directory",
		    e.getMessage());
	} catch (Exception e) {
	    fail("IllegalArgumentException must be thrown");
	} finally {
	    if (noDir != null) {
		if (noDir.exists()) {
		    assertTrue(noDir.delete());
		}
	    }
	}

	try {
	    new DirectoryJarClassLoader(directory);
	    /* all clear */
	} catch (Exception e) {
	    fail("No Exception must be thrown: " + e.getClass().getName());
	}
    }

    /*
     * Test method for
     * 'testpackage.DirectoryJarClassLoader.DirectoryJarClassLoader(File,
     * ClassLoader)'
     */
    public final void testDirectoryJarClassLoaderFileClassLoader() {
	File directory = null;
	try {
	    directory = new File("testfiles").getCanonicalFile();
	} catch (IOException e1) {
	    fail("unexpected Exception was thrown");
	}

	try {
	    new DirectoryJarClassLoader(null, ClassLoader
		    .getSystemClassLoader());
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("NullPointerException must be thrown");
	}

	try {
	    new DirectoryJarClassLoader(new File("testfiles/testplugin.xml"),
		    ClassLoader.getSystemClassLoader());
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    assertEquals("The assigned argument must be an existing directory",
		    e.getMessage());
	} catch (Exception e) {
	    fail("IllegalArgumentException must be thrown");
	}

	try {
	    new DirectoryJarClassLoader(directory, null);
	    /* all clear */
	} catch (Exception e) {
	    fail("No Exception must be thrown");
	}

	File noDir = null;
	try {
	    noDir = new File("testfiles/blub").getCanonicalFile();
	    assertFalse(noDir.exists());
	    assertTrue(noDir.mkdir());
	    assertTrue(noDir.exists());
	    assertTrue(noDir.isDirectory());
	    assertTrue(noDir.delete());
	    assertFalse(noDir.exists());

	    new DirectoryJarClassLoader(noDir, ClassLoader
		    .getSystemClassLoader());
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    assertEquals("The assigned argument must be an existing directory",
		    e.getMessage());
	} catch (Exception e) {
	    fail("IllegalArgumentException must be thrown");
	} finally {
	    if (noDir != null) {
		if (noDir.exists()) {
		    assertTrue(noDir.delete());
		}
	    }
	}

	try {
	    new DirectoryJarClassLoader(directory, ClassLoader
		    .getSystemClassLoader());
	    /* all clear */
	} catch (Exception e) {
	    fail("No Exception must be thrown");
	}
    }

    /*
     * Test method for 'testpackage.DirectoryJarClassLoader.findClass(String)'
     */
    public final void testFindClassString() {
	File directory = null;
	try {
	    directory = new File("testfiles/utiltests").getCanonicalFile();
	} catch (IOException e1) {
	    fail("unexpected Exception was thrown");
	}

	DirectoryJarClassLoader loader;
	try {
	    loader = new DirectoryJarClassLoader(directory, ClassLoader
		    .getSystemClassLoader());
	    Class object = loader.loadClass("test.ReflectionTestImpl2");
	    assertNotNull(object);
	    Object o = object.newInstance();
	    assertEquals("test.ReflectionTestImpl2", o.getClass().getName());
	} catch (Exception e) {
	    // e.printStackTrace();
	    fail("no exception: " + e.getClass().getName());
	} catch (Error e) {
	    // e.printStackTrace();
	    fail("no error: " + e.getMessage());
	}
    }

    /*
     * Test method for 'testpackage.DirectoryJarClassLoader.getDirectory()'
     */
    public final void testGetDirectory() {
	File directory = null;
	try {
	    directory = new File("testfiles").getCanonicalFile();
	} catch (IOException e1) {
	    fail("unexpected Exception was thrown");
	}
	DirectoryJarClassLoader loader;
	try {
	    loader = new DirectoryJarClassLoader(directory);
	    assertEquals(directory, loader.getDirectory());
	    assertSame(directory, loader.getDirectory());
	} catch (Exception e) {
	    fail("No Exception must be thrown");
	}
    }

}
