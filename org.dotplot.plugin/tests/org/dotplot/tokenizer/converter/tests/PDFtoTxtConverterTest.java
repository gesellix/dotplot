/*
 * Created on 01.06.2004
 */
package org.dotplot.tokenizer.converter.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.tokenizer.converter.PDFtoTxtConverter;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.TextType;

/**
 * Test Class for a PDFtoTxtConverter
 * 
 * @author hg12201
 * @version 1.0
 */
public class PDFtoTxtConverterTest extends TestCase {

    private PDFtoTxtConverter pttc;

    private File file;

    /**
     * Constructor for PDFtoTxtConverterTest.
     * 
     * @param arg0
     *            - the first argument
     */
    public PDFtoTxtConverterTest(String arg0) {
	super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.pttc = new PDFtoTxtConverter();
	this.file = new File(
		"testfiles/converter/Zuordnung_Module_Schwerpunkte.pdf");
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    /*
     * Test for File convert(File)
     */
    public void testConvertFile() {
	File file = null;
	try {
	    file = this.pttc.convert(this.file);
	    assertTrue("pruefen ob das tempfile angelegt wurde", file.exists());
	    assertEquals("dateinamen überprüfen", new String(this.file
		    .getName()
		    + ".txt"), file.getName());
	    file.deleteOnExit();
	} catch (IOException e) {
	    fail("Exception geworfen :" + e.getMessage());
	}
    }

    public void testConvertFile1() {
	File origin = this.file;
	File destination = new File("./testfiles/converter/pdfout.txt");

	try {
	    destination = this.pttc.convertFile(origin, destination);
	} catch (Exception e) {
	    fail("Exception geworfen :" + e.getMessage());
	}

	assertTrue("pruefen ob die datei angelegt wurde", destination.exists());
    }

    /*
     * Test for File convert(File, File)
     */
    public void testConvertFileFile() {
	File file = null;
	try {
	    File dest = new File("./testfiles/converter");

	    file = this.pttc.convert(this.file, dest);
	    assertTrue("pruefen ob das tempfile angelegt wurde", file.exists());
	    assertEquals("dateinamen überprüfen", new String(this.file
		    .getName()
		    + ".txt"), file.getName());
	    file.deleteOnExit();
	} catch (IOException e) {
	    e.printStackTrace();
	    fail("Exception geworfen :" + e.getMessage());
	}
    }

    public void testConvertIPlotSource() {
	DotplotFile file = new DotplotFile(this.file, PdfType.type);
	IPlotSource result;
	File resultFile = null;
	try {

	    try {
		this.pttc.convert((IPlotSource) null);
		fail("NullPointerException must be thrown");
	    } catch (NullPointerException e) {
		/* all clear */
	    } catch (Exception e) {
		fail("wrong Exception");
	    }

	    try {
		IPlotSource text = new DotplotFile(new File(
			"./testfiles/converter/pdfout.txt"));
		this.pttc.convert(text);
		fail("IllegalArgumentException must be thrown");
	    } catch (IllegalArgumentException e) {
		/* all clear */
	    } catch (Exception e) {
		fail("wrong Exception");
	    }

	    result = this.pttc.convert(file);
	    assertTrue(result instanceof DotplotFile);
	    assertEquals(TextType.type, result.getType());
	    resultFile = ((DotplotFile) result).getFile();
	    assertNotNull(resultFile);
	    assertTrue("pruefen ob das tempfile angelegt wurde", resultFile
		    .exists());
	    assertEquals("dateinamen überprüfen", new String(this.file
		    .getName()
		    + ".txt"), resultFile.getName());
	    assertTrue(resultFile.delete());
	    assertFalse(resultFile.exists());
	} catch (IOException e) {
	    fail("Exception geworfen :" + e.getMessage());
	} finally {
	    if (resultFile != null) {
		resultFile.delete();
	    }
	}
    }

    public void testConvertIPlotSourceFile() {
	DotplotFile file = new DotplotFile(this.file, PdfType.type);
	IPlotSource result;
	File resultFile = null;
	try {
	    File dest = new File("./testfiles/converter");

	    try {
		this.pttc.convert(file, null);
		fail("NullPointerException must be thrown");
	    } catch (NullPointerException e) {
		/* all clear */
	    } catch (Exception e) {
		fail("wrong Exception");
	    }

	    try {
		this.pttc.convert((IPlotSource) null, dest);
		fail("NullPointerException must be thrown");
	    } catch (NullPointerException e) {
		/* all clear */
	    } catch (Exception e) {
		fail("wrong Exception");
	    }

	    try {
		IPlotSource text = new DotplotFile(new File(
			"./testfiles/converter/pdfout.txt"));
		this.pttc.convert(text, dest);
		fail("IllegalArgumentException must be thrown");
	    } catch (IllegalArgumentException e) {
		/* all clear */
	    } catch (Exception e) {
		fail("wrong Exception");
	    }

	    result = this.pttc.convert(file, dest);
	    assertNotNull(result);
	    assertTrue(result instanceof DotplotFile);
	    assertEquals(TextType.type, result.getType());
	    resultFile = ((DotplotFile) result).getFile();
	    assertNotNull(resultFile);
	    assertTrue("pruefen ob das tempfile angelegt wurde", resultFile
		    .exists());
	    assertEquals("dateinamen überprüfen", new String(this.file
		    .getName()
		    + ".txt"), resultFile.getName());
	    assertEquals(dest.getCanonicalFile(), resultFile.getParentFile());
	    assertTrue(resultFile.delete());
	    assertFalse(resultFile.exists());
	} catch (IOException e) {
	    e.printStackTrace();
	    fail("Exception geworfen :" + e.getMessage());
	} finally {
	    if (resultFile != null) {
		resultFile.delete();
	    }
	}
    }

    public void testGetSourceType() {
	assertEquals(PdfType.type, this.pttc.getSourceType());
    }

    public void testGetTargetType() {
	assertEquals(TextType.type, this.pttc.getTargetType());
    }

    public void testSetOverwrite() {
	DotplotFile file = new DotplotFile(this.file, PdfType.type);
	IPlotSource result;
	File resultFile = null;
	try {
	    File dest = new File("./testfiles/converter");

	    result = this.pttc.convert(file, dest);
	    assertNotNull(result);
	    assertTrue(result instanceof DotplotFile);
	    resultFile = ((DotplotFile) result).getFile();
	    assertNotNull(resultFile);
	    assertTrue("pruefen ob das tempfile angelegt wurde", resultFile
		    .exists());
	    assertEquals("dateinamen überprüfen", new String(this.file
		    .getName()
		    + ".txt"), resultFile.getName());
	    assertEquals(dest.getCanonicalFile(), resultFile.getParentFile());

	    long last;
	    last = resultFile.lastModified();
	    result = this.pttc.convert(file, dest);
	    resultFile = ((DotplotFile) result).getFile();

	    assertTrue(last == resultFile.lastModified());

	    long milis = System.currentTimeMillis();
	    milis = milis + 2000L;

	    // busy waiting for 1 second to ensure an modificationtime diference
	    while (milis > System.currentTimeMillis()) {
		;
	    }

	    last = resultFile.lastModified();
	    this.pttc.setOverwrite(true);
	    result = this.pttc.convert(file, dest);
	    resultFile = ((DotplotFile) result).getFile();

	    assertFalse(last == resultFile.lastModified());

	    last = resultFile.lastModified();
	    this.pttc.setOverwrite(false);
	    result = this.pttc.convert(file, dest);
	    resultFile = ((DotplotFile) result).getFile();

	    assertTrue(last == resultFile.lastModified());
	    assertTrue(resultFile.delete());
	    assertFalse(resultFile.exists());
	} catch (IOException e) {
	    e.printStackTrace();
	    fail("Exception geworfen :" + e.getMessage());
	} finally {
	    if (resultFile != null) {
		resultFile.delete();
	    }
	}
    }
}
