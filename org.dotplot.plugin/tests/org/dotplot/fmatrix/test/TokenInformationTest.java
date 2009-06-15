/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.fmatrix.LineInformation;
import org.dotplot.fmatrix.SourceInformation;
import org.dotplot.fmatrix.TokenInformation;
import org.dotplot.tokenizer.service.TextType;

/**
 * Description
 * 
 * @author Oguz Huryasar, Thorsten Ruehl
 * @version 0.2
 */
public class TokenInformationTest extends TestCase {

    private TokenInformation tokenInformation;

    private File file1, file2, file3;

    private IPlotSource source1, source2, source3;

    private SourceInformation fileContainer, fContainer2, fContainer3,
	    fContainer4;

    @Override
    public void setUp() {
	this.tokenInformation = new TokenInformation();
	try {
	    file1 = File.createTempFile("txt1", "txt");
	    file2 = File.createTempFile("txt2", "txt");
	    file3 = File.createTempFile("txt3", "txt");

	    source1 = new DotplotFile(file1, TextType.type);
	    source2 = new DotplotFile(file2, TextType.type);
	    source3 = new DotplotFile(file3, TextType.type);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	fileContainer = new SourceInformation(0, source1);
	fContainer2 = new SourceInformation(200, source2);
	fContainer3 = new SourceInformation(300, source3);
	fContainer4 = new SourceInformation(600, null);

    }

    @Override
    public void tearDown() {
	if (file1.exists()) {
	    file1.delete();
	}
	if (file2.exists()) {
	    file2.delete();
	}
	if (file3.exists()) {
	    file3.delete();
	}
    }

    public void testFileInfoAcces() {
	this.tokenInformation.addSourceInformation(this.fileContainer);
	this.tokenInformation.addSourceInformation(this.fContainer2);
	this.tokenInformation.addSourceInformation(this.fContainer3);
	this.tokenInformation.addSourceInformation(this.fContainer4);

	assertEquals("(1) The fileInfoNumber has to be 3", 3,
		this.tokenInformation.getFileInfoNumber());

	assertEquals("(2) File ID from tokenindex 199 has to be 0", 0,
		this.tokenInformation.getFileIndex(199));

	assertEquals("(3) File ID from tokenindex 250 has to be 1", 1,
		this.tokenInformation.getFileIndex(250));

	assertEquals("(4) File ID from tokenindex 350 has to be 2", 2,
		this.tokenInformation.getFileIndex(350));

	try {
	    assertEquals("(5) checking if filename of 1. entry is correct",
		    this.file1.getCanonicalPath(), this.tokenInformation
			    .getFileName(0));

	    assertEquals("(6) checking if filename of 2. entry is correct",
		    this.file2.getCanonicalPath(), this.tokenInformation
			    .getFileName(1));

	    assertEquals("(7) checking if filename of 3. entry is correct",
		    this.file3.getCanonicalPath(), this.tokenInformation
			    .getFileName(2));

	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
    }

    public void testGetSize() {
	LineInformation someLineInformation = new LineInformation();
	this.tokenInformation.addLineInformationContainer(someLineInformation);
	assertTrue("expected size must be greater than 0!",
		this.tokenInformation.getLineInfoSize() > 0);
    }

    public void testSetUp() {
	assertNotNull("TokenInformation object must not be null!",
		this.tokenInformation);
    }
}
