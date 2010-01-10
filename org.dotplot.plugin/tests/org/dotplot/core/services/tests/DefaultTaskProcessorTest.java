/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.services.DefaultErrorHandler;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.ITaskPart;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.TaskProcessor;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class DefaultTaskProcessorTest extends TestCase {

	private final class TestTaskPart implements ITaskPart {

		String id;

		boolean causeException;

		public TestTaskPart(String id, boolean causeException) {
			this.id = id;
			this.causeException = causeException;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.core.services.ITaskPart#errorOccured()
		 */
		public boolean errorOccured() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.ITaskPart#getID()
		 */
		public String getID() {
			return this.id;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.ITaskPart#getRessources()
		 */
		public Collection<IRessource> getRessources() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.ITaskPart#getResult()
		 */
		public Object getResult() {
			return "result:" + this.id;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.util.ExecutionUnit#isRunning()
		 */
		public boolean isRunning() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			if (this.causeException) {
				throw new RuntimeException("exception caused");
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.ITaskPart#setErrorHandler(org.dotplot.services
		 * .IProcessingErrorHandler)
		 */
		public void setErrorHandler(IErrorHandler handler) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.ITaskPart#setLocalRessources(java.util.Collection
		 * )
		 */
		public void setLocalRessources(
				Collection<? extends IRessource> ressouceList)
				throws InsufficientRessourcesException {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.util.ExecutionUnit#stop()
		 */
		public void stop() {
		}

	}

	private TaskProcessor processor;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.processor = new TaskProcessor();
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.DefaultTaskProcessor.setErrorHandler(IProcessingErrorHandler)'
	 */
	public void testGetSetErrorHandler() {
		assertNotNull(this.processor.getErrorHandler());
		assertTrue(this.processor.getErrorHandler() instanceof DefaultErrorHandler);
		IErrorHandler handler = new DefaultErrorHandler();
		this.processor.setErrorHandler(handler);
		assertSame(handler, this.processor.getErrorHandler());
		try {
			this.processor.setErrorHandler(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.services.DefaultTaskProcessor.getTaskResult()'
	 */
	public void testGetTaskResult() {
		assertNull(this.processor.getTaskResult());
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.DefaultTaskProcessor.processTask(ITask)'
	 */
	public void testProcessTask() {

		try {
			this.processor.process(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		DefaultErrorHandler handler = new DefaultErrorHandler();

		ITaskResultMarshaler marshaler = new ITaskResultMarshaler() {
			public Object marshalResult(Map taskResult) {
				StringBuffer buffer = new StringBuffer();
				Iterator iter = taskResult.keySet().iterator();
				while (iter.hasNext()) {
					buffer.append(taskResult.get(iter.next()).toString());
				}
				return buffer.toString();
			}
		};

		this.processor.setErrorHandler(handler);
		assertEquals("", handler.getErrorMessages());

		Task job = new Task("testjob", marshaler, true);
		job.addPart(new TestTaskPart("part1", true));
		this.processor.process(job);
		assertNull(this.processor.getTaskResult());
		assertEquals(
				"[1](fatal) at TaskProcessor:no id -> java.lang.RuntimeException:exception caused\n",
				handler.getErrorMessages());

		job = new Task("testjob", marshaler, true);

		job.addPart(new TestTaskPart("part1", false));
		job.addPart(new TestTaskPart("part2", false));
		job.addPart(new TestTaskPart("part3", false));

		assertFalse(job.isDone());
		this.processor.process(job);
		assertTrue(job.isDone());
		assertEquals("result:part1result:part2result:part3", this.processor
				.getTaskResult());
		assertNull(this.processor.getInvokingObject());
	}

	public void testProcessTaskObject() {
		Object invoker = new Object();

		DefaultErrorHandler handler = new DefaultErrorHandler();

		ITaskResultMarshaler marshaler = new ITaskResultMarshaler() {
			public Object marshalResult(Map taskResult) {
				StringBuffer buffer = new StringBuffer();
				Iterator iter = taskResult.keySet().iterator();
				while (iter.hasNext()) {
					buffer.append(taskResult.get(iter.next()).toString());
				}
				return buffer.toString();
			}
		};

		this.processor.setErrorHandler(handler);
		assertEquals("", handler.getErrorMessages());

		Task job = new Task("testjob", marshaler, true);
		job.addPart(new TestTaskPart("part1", true));

		try {
			this.processor.process(null, invoker);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.processor.process(job, null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		this.processor.process(job, invoker);
		assertNull(this.processor.getTaskResult());
		assertEquals(
				"[1](fatal) at TaskProcessor:no id -> java.lang.RuntimeException:exception caused\n",
				handler.getErrorMessages());

		job = new Task("testjob", marshaler, true);

		job.addPart(new TestTaskPart("part1", false));
		job.addPart(new TestTaskPart("part2", false));
		job.addPart(new TestTaskPart("part3", false));

		assertFalse(job.isDone());
		this.processor.process(job, invoker);
		assertTrue(job.isDone());
		assertEquals("result:part1result:part2result:part3", this.processor
				.getTaskResult());
		assertSame(invoker, this.processor.getInvokingObject());

		job = new Task("testjob", marshaler, true);

		job.addPart(new TestTaskPart("part1", false));
		job.addPart(new TestTaskPart("part2", false));
		job.addPart(new TestTaskPart("part3", false));

		assertFalse(job.isDone());
		this.processor.process(job);
		assertTrue(job.isDone());
		assertEquals("result:part1result:part2result:part3", this.processor
				.getTaskResult());
		assertNull(this.processor.getInvokingObject());
	}

}
