/**
 * 
 */
package org.dotplot.tokenizer.service.tests;

import java.util.Collection;
import java.util.Vector;

import junit.framework.TestCase;

import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.service.DefaultScanner;
import org.dotplot.tokenizer.service.PlotSourceListTokenizer;
import org.dotplot.tokenizer.service.TokenizerTaskPart;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class TokenizerTaskPartTest extends TestCase {

	private TokenizerTaskPart part;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.part = new TokenizerTaskPart("Part", new DefaultScanner());
	}

	/*
	 * Test method for 'org.dotplot.tokenizer.TokenizerTaskPart.getResult()'
	 */
	public void testGetResult() {
		try {
			assertNull(this.part.getResult());

			IPlotSource source1 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");
			IPlotSource source2 = new DotplotFile(
					"./testfiles/tokenizer/test.txt");

			Collection<IRessource> list = new Vector<IRessource>();
			list.add(source1);
			list.add(source2);
			this.part.setLocalRessources(list);
			this.part.run();

			Object result = this.part.getResult();
			assertNotNull(result);
			assertTrue(result instanceof PlotSourceListTokenizer);

			PlotSourceListTokenizer streamer = (PlotSourceListTokenizer) result;

			String[] text = { "to", "be", "or", "not", "to", "be." };
			Token token;

			for (int i = 0; i < text.length; i++) {
				token = streamer.getNextToken();
				assertEquals(text[i], token.getValue());
				assertSame(source1, token.getSource());
			}

			assertTrue(streamer.getNextToken() instanceof EOFToken);

			for (int i = 0; i < text.length; i++) {
				token = streamer.getNextToken();
				assertEquals(text[i], token.getValue());
				assertSame(source2, token.getSource());
			}

			assertTrue(streamer.getNextToken() instanceof EOFToken);

			assertEquals("EOSToken", streamer.getNextToken().getValue());
			assertEquals("EOSToken", streamer.getNextToken().getValue());
			assertEquals("EOSToken", streamer.getNextToken().getValue());
			assertEquals("EOSToken", streamer.getNextToken().getValue());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.TokenizerTaskPart.setLocalRessources(Collection<IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			this.part.setLocalRessources(null);
			fail("InsufficientRessourcesException must be thrown");
		}
		catch (InsufficientRessourcesException e) {
			// all clear
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			Collection<IRessource> list = new Vector<IRessource>();
			this.part.setLocalRessources(list);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.TokenizerTaskPart.TokenizerTaskPart(String)'
	 */
	public void testTokenizerTaskPart() {
		assertNull(this.part.getResult());
	}

}
