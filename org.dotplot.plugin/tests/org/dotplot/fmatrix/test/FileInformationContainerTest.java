/*
 * Created on Jun 24, 2004
 */
package org.dotplot.fmatrix.test;

import java.io.File;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.fmatrix.SourceInformation;
import org.dotplot.tokenizer.service.TextType;

/**
 * @author Thorsten Ruehl
 *         <p/>
 *         Tests for the FileInormationContainer-class. The class stores the
 *         information about the source files, for later processing.
 */
public final class FileInformationContainerTest extends TestCase {

	SourceInformation fileInformation;

	IPlotSource source1;

	/**
	 * Constructor for FileInformationTest.
	 */
	public FileInformationContainerTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		source1 = new DotplotFile(new File("./testfiles/fmatrix/test.txt"),
				TextType.type);
		fileInformation = new SourceInformation(0, source1);
	}

	public void testAdding() {
		assertTrue("(0) fileInformation must not be null",
				fileInformation != null);

		assertEquals("(1) filename of file1 and filenameentry has to be equal",
				source1.getName(), fileInformation.getSourcename());

		assertEquals("(2) startindex of fileInformation has to be 0", 0,
				fileInformation.getStartIndex());

		assertEquals(
				"(3) filesize of file1 has and filesizentry has to be equal",
				source1.size(), fileInformation.getSize());
	}
}
