/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.VersionPattern;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class VersionPatternTest extends TestCase {

	private VersionPattern pattern;

	private static final String MAIN = "([0-9]*\\.)*[0-9]+";

	private static final String ALPHA = "alpha";

	private static final String BETA = "beta";

	private static final String RC = "rc";

	private static final String RCN = "[0-9]+";

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, RCN);
	}

	/*
	 * Test method for 'org.dotplot.services.VersionPattern.equals(Object)'
	 */
	public void testEqualsObject() {
		VersionPattern one = new VersionPattern(MAIN, ALPHA, BETA, RC, RCN);
		VersionPattern two = new VersionPattern(MAIN, BETA, ALPHA, RC, RCN);
		assertEquals(this.pattern, one);
		assertFalse(this.pattern.equals(two));
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.VersionPattern.getAlphaIdentifier()'
	 */
	public void testGetAlphaIdentifier() {
		assertEquals(ALPHA, pattern.getAlphaIdentifier());
	}

	/*
	 * Test method for 'org.dotplot.services.VersionPattern.getBetaIdentifier()'
	 */
	public void testGetBetaIdentifier() {
		assertEquals(BETA, pattern.getBetaIdentifier());
	}

	/*
	 * Test method for 'org.dotplot.services.VersionPattern.getMainIdentifier()'
	 */
	public void testGetMainIdentifier() {
		assertEquals(MAIN, pattern.getMainIdentifier());
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.VersionPattern.getReleaseCandidateIdentifier()'
	 */
	public void testGetReleaseCandidateIdentifier() {
		assertEquals(RC, pattern.getReleaseCandidateIdentifier());

	}

	/*
	 * Test method for
	 * 'org.dotplot.services.VersionPattern.getReleaseNumberIdentifier()'
	 */
	public void testGetReleaseNumberIdentifier() {
		assertEquals(RCN, pattern.getReleaseNumberIdentifier());
	}

	public void testGetReleaseStateIdentifier() {
		VersionPattern pattern;
		StringBuffer buffer;

		pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, RCN);
		buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")");
		assertEquals(buffer.toString(), pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, ALPHA, null, RC, RCN);
		buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")");
		assertEquals(buffer.toString(), pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, null, BETA, RC, RCN);
		buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")");
		assertEquals(buffer.toString(), pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, ALPHA, BETA, null, RCN);
		buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append(")");
		assertEquals(buffer.toString(), pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, null);
		buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")");
		assertEquals(buffer.toString(), pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, null, null, null, null);
		assertEquals("", pattern.getReleaseStateIdentifier());

		pattern = new VersionPattern(MAIN, null, null, null, RCN);
		assertEquals("", pattern.getReleaseStateIdentifier());

	}

	/*
	 * Test method for 'org.dotplot.services.VersionPattern.matches(String)'
	 */
	public void testMatches() {
		assertTrue(this.pattern.matches("1.10.4 alpha 5"));
		assertTrue(this.pattern.matches("1.10.4 beta 05"));
		assertTrue(this.pattern.matches("1.10.4 beta 5"));
		assertTrue(this.pattern.matches("1.10.4 rc 57"));
		assertTrue(this.pattern.matches("1.10.4 rc"));
		assertTrue("1.10.4".matches("([0-9]*\\.)*[0-9]+"));
		assertTrue("1.10.4"
				.matches("([0-9]*\\.)*[0-9]+( (alpha|beta|rc)( [0-9]+)?)?"));
		assertTrue(this.pattern.matches("1.10.4"));
		assertFalse(this.pattern.matches("1.10."));
		assertFalse(this.pattern.matches("1.1k.4"));
		assertFalse(this.pattern.matches("1.10.4alpha"));
		assertFalse(this.pattern.matches("1.10.4 diudel"));
		assertFalse(this.pattern.matches("1.10.4 beta 1.0"));
		assertFalse(this.pattern.matches("1.10.4 beta d"));
	}

	/*
	 * Test method for 'org.dotplot.services.VersionPattern.toString()'
	 */
	public void testToString() {
		VersionPattern pattern;
		StringBuffer buffer;

		pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, RCN);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		buffer.append("( (");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")( ");
		buffer.append(RCN);
		buffer.append(")?)?");
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, ALPHA, null, RC, RCN);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		buffer.append("( (");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")( ");
		buffer.append(RCN);
		buffer.append(")?)?");
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, null, BETA, RC, RCN);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		buffer.append("( (");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append(")( ");
		buffer.append(RCN);
		buffer.append(")?)?");
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, ALPHA, BETA, null, RCN);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		buffer.append("( (");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append(")( ");
		buffer.append(RCN);
		buffer.append(")?)?");
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, null);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		buffer.append("( (");
		buffer.append(ALPHA);
		buffer.append("|");
		buffer.append(BETA);
		buffer.append("|");
		buffer.append(RC);
		buffer.append("))?");
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, null, null, null, null);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		assertEquals(buffer.toString(), pattern.toString());

		pattern = new VersionPattern(MAIN, null, null, null, RCN);
		buffer = new StringBuffer();
		buffer.append(MAIN);
		assertEquals(buffer.toString(), pattern.toString());

	}

	/*
	 * Test method for
	 * 'org.dotplot.services.VersionPattern.VersionPattern(String, String,
	 * String, String, String)'
	 */
	public void testVersionPattern() {
		VersionPattern pattern;
		try {
			pattern = new VersionPattern(null, ALPHA, BETA, RC, RCN);
			assertEquals("", pattern.getMainIdentifier());
			pattern = new VersionPattern(MAIN, null, BETA, RC, RCN);
			assertEquals("", pattern.getAlphaIdentifier());
			pattern = new VersionPattern(MAIN, ALPHA, null, RC, RCN);
			assertEquals("", pattern.getBetaIdentifier());
			pattern = new VersionPattern(MAIN, ALPHA, BETA, null, RCN);
			assertEquals("", pattern.getReleaseCandidateIdentifier());
			pattern = new VersionPattern(MAIN, ALPHA, BETA, RC, null);
			assertEquals("", pattern.getReleaseNumberIdentifier());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
