/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.dotplot.core.plugins.MalformedVersionException;
import org.dotplot.core.plugins.Version;
import org.dotplot.core.plugins.VersionPattern;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class VersionTest extends TestCase {

	private Version version;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.version = new Version("1.0.0 beta 1");
	}

	/*
	 * Test method for 'org.dotplot.services.Version.compareTo(Object)'
	 */
	public void testCompareTo() {
		Set<Version> set = new TreeSet<Version>();
		Version[] vers = null;
		try {
			vers = new Version[] { new Version("1.0.6"),
					new Version("1.2 alpha"), new Version("1.2 beta"),
					new Version("1.02"), new Version("1.2.3 alpha 1"),
					new Version("1.2.3 alpha 3"), new Version("1.20"), };

			set.add(vers[5]);
			set.add(vers[4]);
			set.add(vers[0]);
			set.add(vers[6]);
			set.add(vers[2]);
			set.add(vers[1]);
			set.add(vers[3]);
		}
		catch (MalformedVersionException e) {
			fail("no exception!");
		}

		Version[] versions = set.toArray(new Version[0]);
		assertTrue(vers[3].isFinal());
		assertNotNull(vers);
		assertEquals(vers.length, versions.length);
		for (int i = 0; i < versions.length; i++) {
			assertEquals(String.valueOf(i), vers[i], versions[i]);
		}

	}

	/*
	 * Test method for 'org.dotplot.services.Version.equals(Object)'
	 */
	public void testEquals() {
		try {
			Version version1 = new Version("1.0 beta 5");
			Version version2 = new Version("01 beta 05");
			Version version3 = new Version("1 beta 6");
			assertEquals(version1, version1);
			assertEquals(version1, version2);
			assertFalse(version1.equals(version3));
			assertFalse(version2.equals(version3));
			assertFalse(version1.equals("blub"));
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	public void testGetMainVersionString() {
		Version version;
		try {
			version = new Version("1.0.0 alpha 5");
			assertEquals("1.0.0", version.getMainVersionString());
			version = new Version("1.10.0");
			assertEquals("1.10.0", version.getMainVersionString());
			version = new Version("1.0.0 alpha");
			assertEquals("1.0.0", version.getMainVersionString());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.getReleaseNumber()'
	 */
	public void testGetReleaseNumber() {
		Version version;
		try {
			version = new Version("1.0.0 alpha 5");
			assertEquals("5", version.getReleaseNumber());
			version = new Version("1.0.0 beta 2");
			assertEquals("2", version.getReleaseNumber());
			version = new Version("1.0.0");
			assertEquals("1", version.getReleaseNumber());
			version = new Version("1.0.0 beta");
			assertEquals("1", version.getReleaseNumber());

		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.getReleaseState()'
	 */
	public void testGetReleaseState() {
		Version version;
		try {
			version = new Version("1.0.0 alpha 5");
			assertEquals("alpha 5", version.getReleaseState());
			version = new Version("1.0.0 beta");
			assertEquals("beta", version.getReleaseState());
			version = new Version("1.0.0");
			assertEquals("", version.getReleaseState());
		}
		catch (MalformedVersionException e) {
			fail("no exception:" + e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.getReleaseStatus()'
	 */
	public void testGetReleaseStatus() {
		Version version;
		try {
			version = new Version("1.0.0 alpha");
			assertEquals(Version.ALPHA, version.getReleaseStatus());
			version = new Version("1.0.0 beta");
			assertEquals(Version.BETA, version.getReleaseStatus());
			version = new Version("1.0.0 rc");
			assertEquals(Version.RELEASE_CANDIDATE, version.getReleaseStatus());
			version = new Version("1.0.0");
			assertEquals(Version.FINAL, version.getReleaseStatus());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isAlpha()'
	 */
	public void testIsAlpha() {
		try {
			Version version = new Version("1.0.0 alpha 1");
			assertTrue(version.isAlpha());
			assertFalse(version.isBeta());
			assertFalse(version.isFinal());
			assertFalse(version.isReleaseCandidate());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isBeta()'
	 */
	public void testIsBeta() {
		try {
			Version version = new Version("1.0.0 beta 1");
			assertFalse(version.isAlpha());
			assertTrue(version.isBeta());
			assertFalse(version.isFinal());
			assertFalse(version.isReleaseCandidate());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isFinal()'
	 */
	public void testIsFinal() {
		try {
			Version version = new Version("1.0.0");
			assertFalse(version.isAlpha());
			assertFalse(version.isBeta());
			assertTrue(version.isFinal());
			assertFalse(version.isReleaseCandidate());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isReleaseCandidate()'
	 */
	public void testIsReleaseCandidate() {
		try {
			Version version = new Version("1.0.0 rc 1");
			assertFalse(version.isAlpha());
			assertFalse(version.isBeta());
			assertFalse(version.isFinal());
			assertTrue(version.isReleaseCandidate());
		}
		catch (MalformedVersionException e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isSubVersion(Version)'
	 */
	public void testIsSubVersion() {
		try {
			Version version1 = new Version("1.2 alpha");
			Version version2 = new Version("1.2 beta");
			assertTrue(version2.isSubVersion(version2));
			assertTrue(version2.isSubVersion(version1));
			assertFalse(version1.isSubVersion(version2));
		}
		catch (MalformedVersionException e) {
			fail("no exception!");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.isSuperVersion(Version)'
	 */
	public void testIsSuperVersion() {
		try {
			Version version1 = new Version("1.2 alpha");
			Version version2 = new Version("1.2 beta");
			assertTrue(version2.isSuperVersion(version2));
			assertFalse(version2.isSuperVersion(version1));
			assertTrue(version1.isSuperVersion(version2));
		}
		catch (MalformedVersionException e) {
			fail("no exception!");
		}
	}

	public void testNormalizes() {
		try {
			assertEquals("10.0.5", new Version("10.0.5").normalize());
			assertEquals("0.10.10", new Version("000.10.10").normalize());
			assertEquals("50.10.10", new Version("050.10.10.0.0").normalize());
			assertEquals("50", new Version("050").normalize());
			assertEquals("0", new Version("0").normalize());
			assertEquals("50.10.10 alpha 3", new Version(
					"050.10.10.0.0 alpha 03").normalize());
		}
		catch (MalformedVersionException e) {
			fail("no exception!");
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.toString()'
	 */
	public void testToString() {
		assertEquals("Version 1.0.0 beta 1", this.version.toString());
	}

	/*
	 * Test method for 'org.dotplot.services.Version.Version(String)'
	 */
	public void testVersionString() {
		try {
			new Version(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Version("bla und blub");
			fail("MalformedVersionException must be thrown");
		}
		catch (MalformedVersionException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			Version v = new Version("1.0.0 beta 1");
			assertSame(Version.DEFAULT_PATTERN, v.getPattern());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.services.Version.Version(String,
	 * VersionPattern)'
	 */
	public void testVersionStringVersionPattern() {
		VersionPattern pattern = new VersionPattern(
				Version.VERSION_PATTERN_MAIN_IDENTIFIER,
				Version.VERSION_PATTERN_ALPHA_IDENTIFIER,
				Version.VERSION_PATTERN_BETA_IDENTIFIER,
				Version.VERSION_PATTERN_REALEASE_CANDIDATE_IDENTIFIER,
				"[0-9]+[a-zA-Z]?");

		try {
			new Version(null, pattern);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Version("1.0.0 beta 1", null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			new Version("bla und blub", pattern);
			fail("MalformedVersionException must be thrown");
		}
		catch (MalformedVersionException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			Version v = new Version("1.0.0 beta 1", pattern);
			assertSame(pattern, v.getPattern());
			new Version("1.0.0 beta 11a", pattern);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
