/**
 * 
 */
package org.dotplot.core.tests;

import java.io.File;

import org.dotplot.core.DotplotFile;
import org.dotplot.tokenizer.service.JavaType;
import org.dotplot.tokenizer.service.TextType;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class DotplotFileTest extends TestCase {

	private File file = new File("./testfiles/core/test.txt");
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotFile.DotplotFile(File)'
	 */
	public void testDotplotFileFile() {
		try {
			new DotplotFile((File)null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			DotplotFile file = new DotplotFile(this.file);
			assertEquals(TextType.type, file.getType());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotFile.DotplotFile(String)'
	 */
	public void testDotplotFileString() {
		try {
			new DotplotFile((String)null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			DotplotFile file = new DotplotFile(this.file.getAbsolutePath());
			assertEquals(TextType.type, file.getType());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotFile.DotplotFile(File, ISourceType)'
	 */
	public void testDotplotFileFileSourceType() {
		try {
			new DotplotFile((File)null, JavaType.type);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new DotplotFile(this.file, null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			DotplotFile file = new DotplotFile(this.file, JavaType.type);
			assertEquals(JavaType.type, file.getType());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotFile.DotplotFile(String, ISourceType)'
	 */
	public void testDotplotFileStringSourceType() {
		try {
			new DotplotFile((String)null, JavaType.type);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new DotplotFile(this.file.getAbsolutePath(), null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			DotplotFile file = new DotplotFile(this.file.getAbsolutePath(), JavaType.type);
			assertEquals(JavaType.type, file.getType());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotFile.getType()'
	 */
	public void testGetType() {
		try {
			DotplotFile file = new DotplotFile(this.file);
			assertNotNull(file.getType());
			assertEquals(TextType.type, file.getType());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
