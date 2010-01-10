/**
 * 
 */
package org.dotplot.tokenizer.converter.tests;

import java.io.File;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.tokenizer.converter.ConverterTaskPart;
import org.dotplot.tokenizer.converter.IConverter;
import org.dotplot.tokenizer.converter.PDFtoTxtConverter;
import org.dotplot.tokenizer.converter.PdfType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class ConverterTaskPartTest extends TestCase {

	private ConverterTaskPart part;

	private IConverter converter;

	private File targetDir;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.converter = new PDFtoTxtConverter();
		this.targetDir = new File("./testfiles/converter/");
		this.part = new ConverterTaskPart("part", this.converter,
				this.targetDir, true);
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterTaskPart.ConverterTaskPart(String)'
	 */
	public void testConverterTaskPart() {
		assertNull(this.part.getResult());

		try {
			new ConverterTaskPart(null, this.converter, this.targetDir, true);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new ConverterTaskPart("test", null, this.targetDir, true);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new ConverterTaskPart("test", this.converter, null, true);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new ConverterTaskPart("test", this.converter, this.targetDir, true);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterTaskPart.getResult()'
	 */
	public void testGetResult() {
		try {
			File destination = new File(
					"./testfiles/converter/Zuordnung_Module_Schwerpunkte.pdf.txt");
			File source = new File(
					"./testfiles/converter/Zuordnung_Module_Schwerpunkte.pdf");
			DotplotFile dotplotFile = new DotplotFile(source, PdfType.type);

			List<IPlotSource> list = new Vector<IPlotSource>();
			list.add(dotplotFile);
			this.part.setLocalRessources(list);

			this.part.run();
			Object o = this.part.getResult();

			assertNotNull(o);
			assertTrue(o instanceof List);
			list = (List<IPlotSource>) o;
			assertEquals(1, list.size());
			IPlotSource s = list.get(0);
			assertNotNull(s);

			assertTrue(s instanceof DotplotFile);
			DotplotFile d = (DotplotFile) s;
			File target = d.getFile();

			assertEquals(destination.getCanonicalFile(), target);
			assertTrue(target.delete());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.converter.ConverterTaskPart.setLocalRessources(Collection<?
	 * extends IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			this.part.setLocalRessources(null);
			fail("InsufficientRessourcesException must be thrown");
		}
		catch (InsufficientRessourcesException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			List<IPlotSource> list = new Vector<IPlotSource>();
			this.part.setLocalRessources(list);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
}
