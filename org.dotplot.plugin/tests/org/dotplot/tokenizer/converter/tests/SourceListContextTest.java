/**
 * 
 */
package org.dotplot.tokenizer.converter.tests;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.ISourceList;
import org.dotplot.tokenizer.converter.SourceListContext;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class SourceListContextTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.SourceListContext.SourceListContext(List<IPlotSource>)'
	 */
	public void testSourceListContext() {
		ISourceList list = new DefaultSourceList();
		SourceListContext context = new SourceListContext(list);
		assertSame(list, context.getSourceList());
	}
}
