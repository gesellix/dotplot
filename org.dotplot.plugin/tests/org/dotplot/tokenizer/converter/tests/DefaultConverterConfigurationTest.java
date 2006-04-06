/**
 * 
 */
package org.dotplot.tokenizer.converter.tests;

import java.io.File;
import java.util.Map;

import org.dotplot.core.IConfiguration;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.converter.DefaultConverterConfiguration;
import org.dotplot.tokenizer.converter.IConverterConfiguration;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.TextType;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class DefaultConverterConfigurationTest extends TestCase {

	private DefaultConverterConfiguration config;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.config = new DefaultConverterConfiguration();
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.DefaultConverterConfiguration()'
	 */
	public void testDefaultConverterConfiguration() {
		assertFalse(this.config.overwriteConvertedFiles());
		assertNotNull(this.config.getConvertedFilesDirectory());
		assertEquals(new File("."), this.config.getConvertedFilesDirectory());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.getCovertedFilesDirectory()'
	 */
	public void testSetGetCovertedFilesDirectory() {
		File dir1 = new File("./testfiles");
		File dir2 = new File("./testfiles/converter");
		File dir3 = new File("./testfiles/converter/test");
		File file = new File("./testfiles/core/test.txt");
				
		try {
			this.config.setConvertedFilesDirectory(dir1);
			assertEquals(dir1, this.config.getConvertedFilesDirectory());
			this.config.setConvertedFilesDirectory(dir2);
			assertEquals(dir2, this.config.getConvertedFilesDirectory());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
		try {
			this.config.setConvertedFilesDirectory(dir3);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(dir2, this.config.getConvertedFilesDirectory());			
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.config.setConvertedFilesDirectory(file);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(dir2, this.config.getConvertedFilesDirectory());			
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
	}


	/*
	 * Test method for 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.setOverwriteFiles(boolean)'
	 */
	public void testSetGetOverwriteFiles() {
		this.config.setOverwriteConvertedFiles(true);
		assertTrue(this.config.overwriteConvertedFiles());
		this.config.setOverwriteConvertedFiles(false);
		assertFalse(this.config.overwriteConvertedFiles());
		this.config.setOverwriteConvertedFiles(true);
		assertTrue(this.config.overwriteConvertedFiles());
		this.config.setOverwriteConvertedFiles(false);
		assertFalse(this.config.overwriteConvertedFiles());
		this.config.setOverwriteConvertedFiles(true);
		assertTrue(this.config.overwriteConvertedFiles());
		this.config.setOverwriteConvertedFiles(false);
		assertFalse(this.config.overwriteConvertedFiles());
	}

	public void testSetGetTargetType(){
		assertEquals(TextType.type, this.config.getTargetType());
	}
	
	public void testGetConverterID(){
		Map<ISourceType, String> registry = this.config.getConverterRegistry();
		
		assertNotNull(registry);
		assertTrue(registry.isEmpty());
		
		try {
			this.config.getConverterID(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			assertNull(this.config.getConverterID(TextType.type));
			
			registry.put(TextType.type, "text");
			registry.put(PdfType.type, "pdf");
			
			assertEquals("text", this.config.getConverterID(TextType.type));
			assertEquals("pdf", this.config.getConverterID(PdfType.type));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	public void testCopy(){
		IConfiguration copy = this.config.copy();
		assertNotNull(copy);
		assertTrue(copy instanceof DefaultConverterConfiguration);
		DefaultConverterConfiguration config = (DefaultConverterConfiguration)copy;
		assertEquals(config.getConvertedFilesDirectory(), this.config.getConvertedFilesDirectory());
		assertEquals(config.keepConvertedFiles(), this.config.keepConvertedFiles());
		assertEquals(config.overwriteConvertedFiles(), this.config.overwriteConvertedFiles());
		assertTrue(config.getConverterRegistry().keySet().containsAll(this.config.getConverterRegistry().keySet()));
		assertTrue(config.getConverterRegistry().values().containsAll(this.config.getConverterRegistry().values()));
		assertEquals(config.getTargetType(), this.config.getTargetType());
	}
	
	public void testSerializedForm(){
		this.config.setConvertFiles(true);
		this.config.setKeepConvertedFiles(true);
		this.config.setConvertedFilesDirectory(new File("."));
		assertEquals(".;true;false", this.config.serializedForm());
	}
	
	public void testObjectForm(){
		try {
			this.config.objectForm(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.config.objectForm("blub-asd");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.objectForm("blub-asd;;;;;;");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		IConfiguration c = this.config.objectForm("c:\\windows;true;true");
		assertNotNull(c);
		assertTrue(c instanceof IConverterConfiguration);
		IConverterConfiguration config = (IConverterConfiguration)c;
		assertEquals(new File("c:\\windows"),config.getConvertedFilesDirectory());
		assertTrue(config.keepConvertedFiles());
		assertTrue(config.overwriteConvertedFiles());
		assertTrue(config.getConvertFiles());
	}

}