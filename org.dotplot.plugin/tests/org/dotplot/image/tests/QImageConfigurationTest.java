/**
 * 
 */
package org.dotplot.image.tests;

import java.awt.Color;

import junit.framework.TestCase;

import org.dotplot.core.IConfiguration;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.JAITools;
import org.dotplot.image.LUTs;
import org.dotplot.image.QImageConfiguration;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class QImageConfigurationTest extends TestCase {

	IQImageConfiguration config;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		config = new QImageConfiguration();
	}

	public void testCopy() {
		IConfiguration copy = this.config.copy();
		assertNotNull(copy);
		assertTrue(copy instanceof QImageConfiguration);
		QImageConfiguration config = (QImageConfiguration) copy;

		assertEquals(config.doScaleUp(), this.config.doScaleUp());
		assertEquals(config.getExportFilename(), this.config
				.getExportFilename());
		assertEquals(config.getExportFormat(), this.config.getExportFormat());
		// assertEquals(config.getLut(), this.config.getLut());
		assertEquals(config.getLutBackground(), this.config.getLutBackground());
		assertEquals(config.getLutForeground(), this.config.getLutForeground());
		assertEquals(config.getLutTitle(), this.config.getLutTitle());
		assertEquals(config.getScaleMode(), this.config.getScaleMode());
		assertEquals(config.isExportDotplotToFile(), this.config
				.isExportDotplotToFile());
		assertEquals(config.isOnlyExport(), this.config.isOnlyExport());
		assertEquals(config.showFileSeparators(), this.config
				.showFileSeparators());
		assertEquals(config.useInformationMural(), this.config
				.useInformationMural());
		assertEquals(config.useLUT(), this.config.useLUT());

	}

	/*
	 * Test method for 'org.dotplot.image.QImageConfiguration.doScaleUp()'
	 */
	public void testDoSetScaleUp() {
		assertTrue(this.config.doScaleUp());
		this.config.setScaleUp(false);
		assertFalse(this.config.doScaleUp());
		this.config.setScaleUp(true);
		assertTrue(this.config.doScaleUp());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.getExportFilename()'
	 */
	public void testGetSetExportFilename() {
		assertEquals("/dotplot", this.config.getExportFilename());

		try {
			this.config.setExportFilename(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setExportFilename("test");
			assertEquals("test", this.config.getExportFilename());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.image.QImageConfiguration.getExportFormat()'
	 */
	public void testGetSetExportFormat() {
		assertEquals(JAITools.JPG, this.config.getExportFormat());
		try {
			this.config.setExportFormat(-34);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(JAITools.JPG, this.config.getExportFormat());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setExportFormat(34);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(JAITools.JPG, this.config.getExportFormat());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setExportFormat(JAITools.EXPORT_FORMATS.length);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(JAITools.JPG, this.config.getExportFormat());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.setExportFormat(JAITools.PNG);
			assertEquals(JAITools.PNG, this.config.getExportFormat());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.image.QImageConfiguration.getLut()'
	 */
	public void testGetSetLut() {
		assertTrue(this.config.useLUT());
		assertEquals("inverted_gray", this.config.getLutTitle());

		int[][] base, quest;
		base = LUTs.inverted_gray();
		quest = this.config.getLut();

		assertNotNull(base);
		assertNotNull(quest);
		assertEquals(base.length, quest.length);
		assertTrue(base.length > 0);
		assertEquals(base[0].length, quest[0].length);
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[i].length; j++) {
				assertEquals(String.valueOf(i) + " " + String.valueOf(j),
						base[i][j], quest[i][j]);
			}
		}

		this.config.setLut("red", null, null);
		assertEquals("red", this.config.getLutTitle());

		base = LUTs.red();
		quest = this.config.getLut();

		assertNotNull(base);
		assertNotNull(quest);
		assertEquals(base.length, quest.length);
		assertTrue(base.length > 0);
		assertEquals(base[0].length, quest[0].length);
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[i].length; j++) {
				assertEquals(String.valueOf(i) + " " + String.valueOf(j),
						base[i][j], quest[i][j]);
			}
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.getLutBackground()'
	 */
	public void testGetSetLutBackground() {
		Color red = Color.RED;
		Color blue = Color.BLUE;
		assertNotNull(this.config.getLutBackground());

		this.config.setLutBackground(red);
		assertEquals(red, this.config.getLutBackground());

		this.config.setLutBackground(blue);
		assertEquals(blue, this.config.getLutBackground());

		this.config.setLutBackground(null);
		assertEquals(blue, this.config.getLutBackground());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.getLutForeground()'
	 */
	public void testGetSetLutForeground() {
		Color red = Color.RED;
		Color blue = Color.BLUE;
		assertNotNull(this.config.getLutForeground());

		this.config.setLutForeground(red);
		assertEquals(red, this.config.getLutForeground());

		this.config.setLutForeground(blue);
		assertEquals(blue, this.config.getLutForeground());

		this.config.setLutForeground(null);
		assertEquals(blue, this.config.getLutForeground());
	}

	/*
	 * Test method for 'org.dotplot.image.QImageConfiguration.getScaleMode()'
	 */
	public void testGetSetScaleMode() {
		this.config.setScaleMode(5);
		assertEquals(5, this.config.getScaleMode());
		this.config.setScaleMode(6);
		assertEquals(6, this.config.getScaleMode());
		this.config.setScaleMode(3);
		assertEquals(3, this.config.getScaleMode());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.setExportDotplotToFile(boolean)'
	 */
	public void testIsSetExportDotplotToFile() {
		assertFalse(this.config.isExportDotplotToFile());
		this.config.setExportDotplotToFile(true);
		assertTrue(this.config.isExportDotplotToFile());
		this.config.setExportDotplotToFile(false);
		assertFalse(this.config.isExportDotplotToFile());
	}

	public void testObjectForm() {
		try {
			this.config.objectForm(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.objectForm("asdfasf");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config.objectForm("asdfasf;;;;;;;;;;;;;;;;;;");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.config
					.objectForm("false;1;/dotplot;true;inverted_gray;-1;-1d6777216;1;true;true;true");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		IConfiguration copy = this.config
				.objectForm("false;1;/dotplot;true;inverted_gray;-1;-16777216;1;true;true;true");
		assertNotNull(copy);
		assertTrue(copy instanceof QImageConfiguration);
		QImageConfiguration config = (QImageConfiguration) copy;

		assertEquals(config.doScaleUp(), this.config.doScaleUp());
		assertEquals(config.getExportFilename(), this.config
				.getExportFilename());
		assertEquals(config.getExportFormat(), this.config.getExportFormat());
		// assertEquals(config.getLut(), this.config.getLut());
		assertEquals(config.getLutBackground(), this.config.getLutBackground());
		assertEquals(config.getLutForeground(), this.config.getLutForeground());
		assertEquals(config.getLutTitle(), this.config.getLutTitle());
		assertEquals(config.getScaleMode(), this.config.getScaleMode());
		assertEquals(config.isExportDotplotToFile(), this.config
				.isExportDotplotToFile());
		assertEquals(config.isOnlyExport(), this.config.isOnlyExport());
		assertEquals(config.showFileSeparators(), this.config
				.showFileSeparators());
		assertEquals(config.useInformationMural(), this.config
				.useInformationMural());
		assertEquals(config.useLUT(), this.config.useLUT());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.QImageConfiguration()'
	 */
	public void testQImageConfiguration() {
		QImageConfiguration config = new QImageConfiguration();
		assertEquals("/dotplot", config.getExportFilename());
		assertEquals(JAITools.JPG, config.getExportFormat());
		assertFalse(config.isOnlyExport());
		assertNotNull(config.getLutBackground());
		assertNotNull(config.getLutForeground());
		assertTrue(config.showFileSeparators());
		assertTrue(config.useLUT());
		assertFalse(config.isExportDotplotToFile());
		assertTrue(config.useInformationMural());
		assertTrue(config.doScaleUp());
		assertEquals("inverted_gray", config.getLutTitle());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.QImageConfiguration(boolean, int,
	 * String, boolean, String, Color, Color, int, boolean, boolean, boolean)'
	 */
	public void testQImageConfigurationBooleanIntStringBooleanStringColorColorIntBooleanBooleanBoolean() {
		QImageConfiguration config = new QImageConfiguration(true,
				JAITools.PNG, "/test", false, "red", null, null, 0, false,
				false, false);

		assertEquals("/test", config.getExportFilename());
		assertEquals(JAITools.PNG, config.getExportFormat());
		assertFalse(config.isOnlyExport());
		assertNotNull(config.getLutBackground());
		assertNotNull(config.getLutForeground());
		assertFalse(config.showFileSeparators());
		assertFalse(config.useLUT());
		assertTrue(config.isExportDotplotToFile());
		assertFalse(config.useInformationMural());
		assertFalse(config.doScaleUp());
		assertEquals("red", config.getLutTitle());
	}

	public void testSerializedForm() {
		assertEquals(
				"false;1;/dotplot;true;inverted_gray;-1;-16777216;1;true;true;true",
				this.config.serializedForm());
	}

	/*
	 * Test method for 'org.dotplot.image.QImageConfiguration.isOnlyExport()'
	 */
	public void testSetIsOnlyExport() {
		assertFalse(this.config.isOnlyExport());
		this.config.setOnlyExport(true);
		assertTrue(this.config.isOnlyExport());
		this.config.setOnlyExport(false);
		assertFalse(this.config.isOnlyExport());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.setShowFileSeparators(boolean)'
	 */
	public void testSetShowFileSeparators() {
		assertTrue(this.config.showFileSeparators());
		this.config.setShowFileSeparators(false);
		assertFalse(this.config.showFileSeparators());
		this.config.setShowFileSeparators(true);
		assertTrue(this.config.showFileSeparators());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.setUseLUT(boolean)'
	 */
	public void testSetUseLUT() {
		assertTrue(this.config.useLUT());
		this.config.setUseLUT(false);
		assertFalse(this.config.useLUT());
		this.config.setUseLUT(true);
		assertTrue(this.config.useLUT());
	}

	/*
	 * Test method for
	 * 'org.dotplot.image.QImageConfiguration.useInformationMural()'
	 */
	public void testUseSetInformationMural() {
		assertTrue(this.config.useInformationMural());
		this.config.setUseInformationMural(false);
		assertFalse(this.config.useInformationMural());
		this.config.setUseInformationMural(true);
		assertTrue(this.config.useInformationMural());
	}

}
