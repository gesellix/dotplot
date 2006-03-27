/**
 * 
 */
package org.dotplot.core.services.tests;

import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.DefaultErrorHandler;
import org.dotplot.core.services.TaskProcessor;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskProcessor;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class AbstractJobTest extends TestCase {

	/*
	 * Test method for 'org.dotplot.services.AbstractJob.getErrorHandler()'
	 */
	public void testGetSetErrorHandler() {
		AbstractJob job = new AbstractJob(){

			public boolean process( IFrameworkContext context) {
				return false;				
			}

			public boolean validatePreconditions(IServiceRegistry manager) {
				return false;
			}};

		assertNotNull(job.getErrorHandler());
		
		try {
			job.setErrorHandler(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("no exception");
		}
		
		IErrorHandler handler = new DefaultErrorHandler();
		
		try {
			job.setErrorHandler(handler);
			assertSame(handler, job.getErrorHandler());
		}
		catch (Exception e) {
			fail("no exception");
		}
	}

	public void testGetSetTaskprocessor(){
		AbstractJob job = new AbstractJob(){

			public boolean process( IFrameworkContext context) {
				return false;				
			}

			public boolean validatePreconditions(IServiceRegistry manager) {
				return false;
			}};
			
		assertNotNull(job.getTaskProcessor());
		assertTrue(job.getTaskProcessor() instanceof TaskProcessor);
		
		try {
			job.setTaskProcessor(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			ITaskProcessor processor = new ITaskProcessor(){

				public Object getTaskResult() {
					return null;
				}

				public void setErrorHandler(IErrorHandler handler) {
				}

				public boolean process(ITask job) {
					return false;
				}

				public boolean process(ITask task, Object invokingObject) {
					return false;
				}

				public void stop() {
				}};
			
			job.setTaskProcessor(processor);
			assertSame(processor, job.getTaskProcessor());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
	
}
