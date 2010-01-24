package org.dotplot.tokenizer.converter.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.IConfiguration;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.converter.DefaultConverterConfiguration;
import org.dotplot.tokenizer.converter.IConverterConfiguration;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.TextType;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class DefaultConverterConfigurationTest {

	/**
	 * 
	 */
	private DefaultConverterConfiguration config;

	/**
	 * @see TestCase#setUp()
	 */
	@Before
	public void setUp() {
		this.config = new DefaultConverterConfiguration();
	}

	/**
	 * 
	 */
	@Test
	public void testCopy() {
		IConfiguration copy = this.config.copy();
		assertNotNull(copy);
		assertTrue(copy instanceof DefaultConverterConfiguration);
		DefaultConverterConfiguration config = (DefaultConverterConfiguration) copy;
		assertEquals(config.getConvertedFilesDirectory(), this.config
				.getConvertedFilesDirectory());
		assertEquals(config.keepConvertedFiles(), this.config
				.keepConvertedFiles());
		assertEquals(config.overwriteConvertedFiles(), this.config
				.overwriteConvertedFiles());
		assertTrue(config.getConverterRegistry().keySet().containsAll(
				this.config.getConverterRegistry().keySet()));
		assertTrue(config.getConverterRegistry().values().containsAll(
				this.config.getConverterRegistry().values()));
		assertEquals(config.getTargetType(), this.config.getTargetType());
	}

	/**
	 * Test method for
	 * org.dotplot.tokenizer.converter.DefaultConverterConfiguration
	 * .DefaultConverterConfiguration()'.
	 */
	@Test
	public void testDefaultConverterConfiguration() {
		assertFalse(this.config.overwriteConvertedFiles());
		assertNotNull(this.config.getConvertedFilesDirectory());
		assertEquals(new File("."), this.config.getConvertedFilesDirectory());
	}

	/**
	 * 
	 */
	@Test
	public void testGetConverterID() {
		Map<ISourceType, String> registry = this.config.getConverterRegistry();

		assertNotNull(registry);
		assertTrue(registry.isEmpty());

		assertNull(this.config.getConverterID(TextType.type));

		registry.put(TextType.type, "text");
		registry.put(PdfType.type, "pdf");

		assertEquals("text", this.config.getConverterID(TextType.type));
		assertEquals("pdf", this.config.getConverterID(PdfType.type));
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testGetConverterIDnull() {
		this.config.getConverterID(null);
	}

	/**
	 * @throws IOException
	 *             in case of an Exception
	 * 
	 */
	@Test
	public void testObjectForm() throws IOException {
		File f = new File(".");
		f = f.getAbsoluteFile();

		IConfiguration c = this.config.objectForm(f.toString() + ";true;true");
		assertNotNull(c);
		assertTrue(c instanceof IConverterConfiguration);
		IConverterConfiguration config = (IConverterConfiguration) c;
		assertEquals(f.getCanonicalPath(), config.getConvertedFilesDirectory()
				.getCanonicalPath());
		assertTrue(config.keepConvertedFiles());
		assertTrue(config.overwriteConvertedFiles());
		assertTrue(config.getConvertFiles());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testObjectFormNULL() {
		this.config.objectForm(null);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testObjectFormWRONGvalue1() {
		this.config.objectForm("blub-asd");
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testObjectFormWRONGvalue2() {
		this.config.objectForm("blub-asd;;;;;;");
	}

	/**
	 * 
	 */
	@Test
	public void testSerializedForm() {
		this.config.setConvertFiles(true);
		this.config.setKeepConvertedFiles(true);
		this.config.setConvertedFilesDirectory(new File("."));
		assertEquals(".;true;false", this.config.serializedForm());
	}

	/**
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.getCoverte
	 * d F i l e s D i r e c t o r y ( ) ' .
	 * 
	 * @throws IOException
	 *             in case of an Exception
	 */
	@Test
	public void testSetGetCovertedFilesDirectory() throws IOException {
		File dir1 = new File("./testfiles").getCanonicalFile();
		File dir2 = new File("./testfiles/converter").getCanonicalFile();

		this.config.setConvertedFilesDirectory(dir1);
		assertEquals(dir1, this.config.getConvertedFilesDirectory());
		this.config.setConvertedFilesDirectory(dir2);
		assertEquals(dir2, this.config.getConvertedFilesDirectory());
	}

	/**
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.getCover t
	 * e d F i l e s D i r e c t o r y ( ) ' .
	 * 
	 * @throws IOException
	 *             in case of an Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGetCovertedFilesDirectoryWRONfile() throws IOException {
		File file = new File("./testfiles/core/test.txt").getCanonicalFile();
		this.config.setConvertedFilesDirectory(file);
	}

	/**
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.getCoverte
	 * d F i l e s D i r e c t o r y ( ) ' .
	 * 
	 * @throws IOException
	 *             in case of an Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGetCovertedFilesDirectoryWRONGdirectory()
			throws IOException {
		File dir = new File("./testfiles/converter/test").getCanonicalFile();
		this.config.setConvertedFilesDirectory(dir);
	}

	/**
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.DefaultConverterConfiguration.setOverwri
	 * t e F i l e s ( b o o l e a n ) ' .
	 */
	@Test
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

	/**
	 * 
	 */
	@Test
	public void testSetGetTargetType() {
		assertEquals(TextType.type, this.config.getTargetType());
	}
}
