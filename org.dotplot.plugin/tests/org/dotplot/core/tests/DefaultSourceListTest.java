/**
 * 
 */
package org.dotplot.core.tests;

import junit.framework.TestCase;

import org.dotplot.core.BaseType;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.JavaType;
import org.dotplot.tokenizer.service.PHPType;
import org.dotplot.tokenizer.service.SourceCodeFileType;
import org.dotplot.tokenizer.service.TextType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class DefaultSourceListTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
    }

    /*
     * Test method for
     * 'org.dotplot.core.DefaultSourceList.getCombinedSourceType()'
     */
    public void testGetCombinedSourceType() {
	IPlotSource source1 = new DotplotFile("./testfiles/tokenizer/test.txt",
		JavaType.type);
	IPlotSource source2 = new DotplotFile("./testfiles/tokenizer/test.txt",
		PHPType.type);
	IPlotSource source3 = new DotplotFile("./testfiles/tokenizer/test.txt",
		TextType.type);
	IPlotSource source4 = new DotplotFile("./testfiles/tokenizer/test.txt",
		PdfType.type);

	ISourceList list = new DefaultSourceList();
	assertEquals(BaseType.type, list.getCombinedSourceType());

	list.add(source1);
	assertEquals(JavaType.type, list.getCombinedSourceType());

	list.add(source2);
	assertEquals(SourceCodeFileType.type, list.getCombinedSourceType());

	list.add(source3);
	assertEquals(TextType.type, list.getCombinedSourceType());

	list.add(source4);
	assertEquals(BaseType.type, list.getCombinedSourceType());
    }

}
