/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import org.dotplot.core.DotplotContext;
import org.dotplot.core.plugins.PluginJarFile;
import org.dotplot.core.plugins.Version;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class PluginJarFileTest extends TestCase {

	private File with, without, dir, wrong;

	private DotplotContext context;

	public PluginJarFileTest() {
		super();
		context = new DotplotContext(".");
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.dir = new File("./testfiles/core");
		this.dir = this.dir.getCanonicalFile();
		this.wrong = new File(this.dir, "test.txt");
		this.with = new File(this.dir, "pluginjartestwithxml.jar");
		this.without = new File(this.dir, "pluginjartestwithoutxml.jar");
	}

	public void testGetID() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			assertEquals("org.dotplot.core.test", jar.getID());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testGetInfo() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			assertEquals("Einführender Test für die plugin.xml", jar.getInfo());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testGetName() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			assertEquals("testplugin", jar.getName());
		}
		catch (Exception e) {
			e.printStackTrace();

			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testGetPluginDependencys() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			Map<String, Version> dependencys = jar.getPluginDependencys();
			assertNotNull(dependencys);
			assertEquals(1, dependencys.size());
			assertTrue(dependencys.containsKey("org.dotplot.testplugin"));
			Version v = new Version("1.2");
			assertEquals(v, dependencys.get("org.dotplot.testplugin"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.PluginJarFile.getPluginXMLInputStream()'
	 */
	public void testGetPluginXMLInputStream() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			InputStream stream = jar.getPluginXMLInputStream();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			assertEquals("<?xml version=\"1.0\"?>", in.readLine());
			assertNotNull(jar.getPluginDependencys());
			assertEquals(1, jar.getPluginDependencys().size());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testGetProvider() {
		try {
			PluginJarFile jar = new PluginJarFile(this.with, this.context
					.getPluginSchema());
			assertEquals("Christian Gerhardt", jar.getProvider());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.PluginJarFile.PluginJarFile(File)'
	 */
	public void testPluginJarFileFile() {
		try {
			new PluginJarFile(this.wrong, this.context.getPluginSchema());
			fail("ZipException must be thrown");
		}
		catch (ZipException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception:" + e.getClass().getName());
		}

		try {
			new PluginJarFile(this.without, this.context.getPluginSchema());
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginJarFile(this.with, this.context.getPluginSchema());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.core.PluginJarFile.PluginJarFile(File,
	 * boolean)'
	 */
	public void testPluginJarFileFileBoolean() {
		try {
			new PluginJarFile(this.wrong, this.context.getPluginSchema(), true);
			fail("ZipException must be thrown");
		}
		catch (ZipException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception:" + e.getClass().getName());
		}

		try {
			new PluginJarFile(this.without, this.context.getPluginSchema(),
					true);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginJarFile(this.with, this.context.getPluginSchema(), true);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.core.PluginJarFile.PluginJarFile(String)'
	 */
	public void testPluginJarFileString() {
		try {
			new PluginJarFile(this.wrong.getAbsolutePath(), null);
			fail("ZipException must be thrown");
		}
		catch (ZipException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception:" + e.getClass().getName());
		}

		try {
			new PluginJarFile(this.without.getAbsolutePath(), null);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginJarFile(this.with.getAbsolutePath(), this.context
					.getPluginSchema());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.PluginJarFile.PluginJarFile(String,
	 * boolean)'
	 */
	public void testPluginJarFileStringBoolean() {
		try {
			new PluginJarFile(this.wrong.getAbsolutePath(), this.context
					.getPluginSchema(), true);
			fail("ZipException must be thrown");
		}
		catch (ZipException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception:" + e.getClass().getName());
		}

		try {
			new PluginJarFile(this.without.getAbsolutePath(), this.context
					.getPluginSchema(), true);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new PluginJarFile(this.with.getAbsolutePath(), this.context
					.getPluginSchema(), true);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
