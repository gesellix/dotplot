/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.fmatrix.LineInformation;

/**
 * tests LineInformation class.
 * 
 * @author Oguz Huryasar
 * @version 0.1
 */
public final class LineInformationContainerTest extends TestCase {

	private LineInformation container;

	@Override
	public void setUp() {
		this.container = new LineInformation();
	}

	public void testAdding() {
		this.container.addLineInformation(0, 17, 1, null);

		assertTrue("containers size must be greater than 0!", this.container
				.size() > 0);
	}

	public void testGetting() {
		this.container.addLineInformation(0, 17, 1, null);
		this.container.addLineInformation(18, 47, 2, null);
		this.container.addLineInformation(48, 60, 3, null);

		assertEquals("token 5 must return line 1", 1, this.container
				.getLineIndex(5));
		assertEquals("token 35 must return line 2", 2, this.container
				.getLineIndex(35));
		assertEquals("token 57 must return line 3", 3, this.container
				.getLineIndex(57));
	}

	public void testSetUp() {
		assertNotNull("container must not be null!", this.container);
	}
}
