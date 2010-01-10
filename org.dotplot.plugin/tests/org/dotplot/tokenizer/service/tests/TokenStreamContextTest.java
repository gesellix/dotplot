/**
 * 
 */
package org.dotplot.tokenizer.service.tests;

import junit.framework.TestCase;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.ISourceList;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TokenStreamContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class TokenStreamContextTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * Test method for
	 * 'org.dotplot.tokenizer.TokenStreamContext.TokenStreamContext(ITokenStream)'
	 */
	public void testTokenStreamContext() {
		ISourceList list = new DefaultSourceList();

		ITokenStream stream = new ITokenStream() {

			public Token getNextToken() throws TokenizerException {
				return null;
			}

			public ISourceType getStreamType() {
				return null;
			}
		};

		try {
			new TokenStreamContext(null, list);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new TokenStreamContext(stream, null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			TokenStreamContext context = new TokenStreamContext(stream, list);
			assertSame(stream, context.getTokenStream());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

}
