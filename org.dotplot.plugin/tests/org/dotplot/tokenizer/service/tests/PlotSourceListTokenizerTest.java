/**
 * 
 */
package org.dotplot.tokenizer.service.tests;

import junit.framework.TestCase;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.DefaultScanner;
import org.dotplot.tokenizer.service.JavaType;
import org.dotplot.tokenizer.service.PHPType;
import org.dotplot.tokenizer.service.PlotSourceListTokenizer;
import org.dotplot.tokenizer.service.TextType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class PlotSourceListTokenizerTest extends TestCase {

	private PlotSourceListTokenizer streamer;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.streamer = new PlotSourceListTokenizer(new DefaultScanner());
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.PlotSourceStreamer.addPlotSource(IPlotSource)'
	 */
	public void testAddPlotSource() {
		try {
			this.streamer.addPlotSource(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			IPlotSource source1 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");
			IPlotSource source2 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");

			this.streamer.addPlotSource(source1);
			this.streamer.addPlotSource(source2);

			assertNotNull(this.streamer.getSourceList());
			assertEquals(2, this.streamer.getSourceList().size());
			assertTrue(this.streamer.getSourceList().contains(source1));
			assertTrue(this.streamer.getSourceList().contains(source2));

			this.streamer.getNextToken();

			try {
				this.streamer.addPlotSource(source1);
				fail("UnsupportedOperationException must be thrown");
			}
			catch (UnsupportedOperationException e) {
				/* all clear */
			}
			catch (Exception e) {
				fail("wrong Exception");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.tokenizer.PlotSourceStreamer.getNextToken()'
	 */
	public void testGetNextToken() {
		try {
			IPlotSource source1 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");
			IPlotSource source2 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");

			this.streamer.addPlotSource(source1);
			this.streamer.addPlotSource(source2);

			assertNotNull(this.streamer.getSourceList());
			assertEquals(2, this.streamer.getSourceList().size());
			assertTrue(this.streamer.getSourceList().contains(source1));
			assertTrue(this.streamer.getSourceList().contains(source2));

			String[] text = { "to", "be", "or", "not", "to", "be." };
			Token token;

			for (int i = 0; i < text.length; i++) {
				token = this.streamer.getNextToken();
				assertEquals(text[i], token.getValue());
				assertSame(source1, token.getSource());
			}

			assertTrue(this.streamer.getNextToken() instanceof EOFToken);

			for (int i = 0; i < text.length; i++) {
				token = this.streamer.getNextToken();
				assertEquals(text[i], token.getValue());
				assertSame(source2, token.getSource());
			}

			assertTrue(this.streamer.getNextToken() instanceof EOFToken);

			assertEquals("EOSToken", this.streamer.getNextToken().getValue());
			assertEquals("EOSToken", this.streamer.getNextToken().getValue());
			assertEquals("EOSToken", this.streamer.getNextToken().getValue());
			assertEquals("EOSToken", this.streamer.getNextToken().getValue());

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	public void testGetStreamType() {
		IPlotSource source1 = new DotplotFile("./testfiles/tokenizer/test.txt",
				JavaType.type);
		IPlotSource source2 = new DotplotFile("./testfiles/tokenizer/test.txt",
				PHPType.type);
		IPlotSource source3 = new DotplotFile("./testfiles/tokenizer/test.txt",
				TextType.type);
		IPlotSource source4 = new DotplotFile("./testfiles/tokenizer/test.txt",
				PdfType.type);

		ISourceList list = new DefaultSourceList();
		assertEquals(list.getCombinedSourceType(), this.streamer
				.getStreamType());

		list.add(source1);
		this.streamer.addPlotSource(source1);
		assertEquals(list.getCombinedSourceType(), this.streamer
				.getStreamType());

		list.add(source2);
		this.streamer.addPlotSource(source2);
		assertEquals(list.getCombinedSourceType(), this.streamer
				.getStreamType());

		list.add(source3);
		this.streamer.addPlotSource(source3);
		assertEquals(list.getCombinedSourceType(), this.streamer
				.getStreamType());

		list.add(source4);
		this.streamer.addPlotSource(source4);
		assertEquals(list.getCombinedSourceType(), this.streamer
				.getStreamType());
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.PlotSourceStreamer.PlotSourceStreamer(ITokenizer)'
	 */
	public void testPlotSourceStreamer() {
		assertNotNull(this.streamer.getSourceList());
		assertEquals(0, this.streamer.getSourceList().size());
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.PlotSourceStreamer.removePlotSource(IPlotSource)'
	 */
	public void testRemovePlotSource() {
		try {
			this.streamer.removePlotSource(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			IPlotSource source1 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");
			IPlotSource source2 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");

			this.streamer.addPlotSource(source1);
			this.streamer.addPlotSource(source2);

			assertNotNull(this.streamer.getSourceList());
			assertEquals(2, this.streamer.getSourceList().size());
			assertTrue(this.streamer.getSourceList().contains(source1));
			assertTrue(this.streamer.getSourceList().contains(source2));

			this.streamer.removePlotSource(source1);

			assertNotNull(this.streamer.getSourceList());
			assertEquals(1, this.streamer.getSourceList().size());
			assertFalse(this.streamer.getSourceList().contains(source1));
			assertTrue(this.streamer.getSourceList().contains(source2));

			this.streamer.getNextToken();

			try {
				this.streamer.removePlotSource(source1);
				fail("UnsupportedOperationException must be thrown");
			}
			catch (UnsupportedOperationException e) {
				/* all clear */
			}
			catch (Exception e) {
				fail("wrong Exception");
			}

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

}
