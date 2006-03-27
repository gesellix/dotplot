package org.dotplot.core.services.tests;

import java.util.Collection;
import java.util.Vector;

import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.DefaultErrorHandler;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;

import junit.framework.TestCase;

public class AbstractTaskPartTest extends TestCase {

	private class ConcreteTaskPart extends AbstractTaskPart {

		public ConcreteTaskPart(String id) {
			super(id);
		}

		public Object getResult() {
			return null;
		}

		public void run() {
		}

		public void setLocalRessources(Collection ressouceList)
				throws InsufficientRessourcesException {

		}

		/* (non-Javadoc)
		 * @see org.dotplot.core.services.ITaskPart#errorOccured()
		 */
		public boolean errorOccured() {
			return false;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.util.ExecutionUnit#stop()
		 */
		public void stop() {
		}

		/* (non-Javadoc)
		 * @see org.dotplot.util.ExecutionUnit#isRunning()
		 */
		public boolean isRunning() {
			return false;
		}

	}

	private AbstractTaskPart part;

	protected void setUp() throws Exception {
		super.setUp();
		this.part = new ConcreteTaskPart("partid");
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractTaskPart.AbstractTaskPart(String)'
	 */
	public void testAbstractTaskPart() {
		try {
			new ConcreteTaskPart(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			new ConcreteTaskPart("");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			AbstractTaskPart part = new ConcreteTaskPart("test");
			assertNotNull(part.getErrorhandler());
			assertTrue(part.getErrorhandler() instanceof DefaultErrorHandler);
			assertNotNull(part.getRessources());
			assertEquals(0, part.getRessources().size());
		}
		catch (Exception e) {
			StringBuffer b = new StringBuffer();
			b.append("wrong exception:");
			b.append(e.getClass().getName());
			b.append(":");
			b.append(e.getMessage());
			fail(b.toString());
		}
	}

	/*
	 * Test method for 'org.dotplot.services.AbstractTaskPart.getID()'
	 */
	public void testGetID() {
		assertEquals("partid", this.part.getID());
	}

	/*
	 * Test method for 'org.dotplot.services.AbstractTaskPart.getRessources()'
	 */
	public void testGetSetRessources() {
		Vector<IRessource> v = new Vector<IRessource>();

		try {
			this.part.setRessources(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.part.setRessources(v);
			assertSame(v, this.part.getRessources());
		}
		catch (Exception e) {
			StringBuffer b = new StringBuffer();
			b.append("wrong exception:");
			b.append(e.getClass().getName());
			b.append(":");
			b.append(e.getMessage());
			fail(b.toString());
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractTaskPart.setErrorHandler(IErrorHandler)'
	 */
	public void testGetSetErrorHandler() {
		IErrorHandler handler = new DefaultErrorHandler();
		try {
			this.part.setErrorHandler(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.part.setErrorHandler(handler);
			assertSame(handler, this.part.getErrorhandler());
		}
		catch (Exception e) {
			StringBuffer b = new StringBuffer();
			b.append("wrong exception:");
			b.append(e.getClass().getName());
			b.append(":");
			b.append(e.getMessage());
			fail(b.toString());
		}

	}
}
