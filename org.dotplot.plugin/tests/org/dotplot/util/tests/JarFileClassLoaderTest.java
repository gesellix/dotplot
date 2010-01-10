/**
 * 
 */
package org.dotplot.util.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.jar.JarFile;

import org.dotplot.util.JarFileClassLoader;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class JarFileClassLoaderTest {

	/**
	 * 
	 */
	private File directory;

	/**
	 * 
	 */
	private File file1;

	/**
	 * 
	 */
	private File file2;

	/**
	 * 
	 */
	private JarFile jarFile1;

	/**
	 * 
	 */
	private JarFile jarFile2;

	/**
	 * 
	 * @throws Exception
	 *             in case of an Exception.
	 */
	@Before
	public void setUp() throws Exception {
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

	/**
	 * Test method for 'org.dotplot.util.JarFileClassLoader.findClass(String)'.
	 * 
	 * @throws Exception
	 *             in case of an Exception.
	 */
	@Test
	public void testFindClassString() throws Exception {
		JarFileClassLoader loader;
		loader = new JarFileClassLoader(this.jarFile2, ClassLoader
				.getSystemClassLoader());
		loader = new JarFileClassLoader(this.jarFile1, loader);
		Class<?> object = loader.loadClass("test.ReflectionTestImpl2");
		assertNotNull(object);
		Object o = object.newInstance();
		assertEquals("test.ReflectionTestImpl2", o.getClass().getName());
	}

	/**
	 * Test method for 'org.dotplot.util.JarFileClassLoader.getJarFile()'.
	 */
	@Test
	public void testGetJarFile() {
		JarFileClassLoader loader = new JarFileClassLoader(this.jarFile1);
		assertEquals(this.jarFile1, loader.getJarFile());
	}

	/**
	 * Test method for
	 * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(File)'.
	 */
	@Test
	public void testJarFileClassLoaderFile() {
		new JarFileClassLoader(this.file2);
	}

	/**
	 * Test method for
	 * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(File,
	 * ClassLoader)'.
	 */
	@Test
	public void testJarFileClassLoaderFileClassLoader() {
		new JarFileClassLoader(this.file2, ClassLoader.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderFileClassLoaderCLASSLOADERnull() {
		new JarFileClassLoader(this.file2, null);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJarFileClassLoaderFileClassLoaderFILEisDIRECTORY() {
		new JarFileClassLoader(this.directory, ClassLoader
				.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJarFileClassLoaderFileClassLoaderFILEnoJARFILE() {
		new JarFileClassLoader(this.file1, ClassLoader.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderFileClassLoaderFILEnull() {
		new JarFileClassLoader((File) null, ClassLoader.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJarFileClassLoaderFileFILEisDIRECTORY() {
		new JarFileClassLoader(this.directory);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testJarFileClassLoaderFileFILEisNOjarfile() {
		new JarFileClassLoader(this.file1);
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderFileFILEnull() {
		new JarFileClassLoader((File) null);
	}

	/**
	 * Test method for
	 * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(JarFile)'.
	 */
	@Test
	public void testJarFileClassLoaderJarFile() {
		new JarFileClassLoader(this.jarFile1);
	}

	/**
	 * Test method for
	 * 'org.dotplot.util.JarFileClassLoader.JarFileClassLoader(JarFile,
	 * ClassLoader)'.
	 */
	@Test
	public void testJarFileClassLoaderJarFileClassLoader() {
		new JarFileClassLoader(this.jarFile1, ClassLoader
				.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderJarFileClassLoaderCLASSLOADERnull() {
		new JarFileClassLoader(this.jarFile1, null);
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderJarFileClassLoaderFILEnull() {
		new JarFileClassLoader((JarFile) null, ClassLoader
				.getSystemClassLoader());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testJarFileClassLoaderJarFileNULL() {
		new JarFileClassLoader((JarFile) null);
	}

}
