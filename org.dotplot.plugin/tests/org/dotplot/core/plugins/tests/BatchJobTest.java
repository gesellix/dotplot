/**
 * 
 */
package org.dotplot.core.plugins.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dotplot.core.plugins.BatchJob;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.DefaultErrorHandler;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskProcessor;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.ServiceRegistry;
import org.dotplot.core.services.Task;
import org.dotplot.util.DuplicateRegistrationException;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class BatchJobTest extends TestCase {

	private class TestTaskPart extends AbstractTaskPart {

		private String result;
		
		/**
		 * @param id
		 */
		public TestTaskPart(String id) {
			super(id);
		}

		/* (non-Javadoc)
		 * @see org.dotplot.services.ITaskPart#getResult()
		 */
		public Object getResult() {
			return this.result;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.services.ITaskPart#setLocalRessources(java.util.Collection)
		 */
		public void setLocalRessources(Collection ressouceList) throws InsufficientRessourcesException {}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {			
			this.result = "Part:"+this.getID();
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
	
	private class ResultContext implements IContext {
		private String result;
		
		
		public String getResult(){
			return this.result;
		}
		
		public void setResult(String result){
			this.result = result;
		}

	}

	private class ResultContext1 extends ResultContext {}
	private class ResultContext2 extends ResultContext {}

	private class TestService extends AbstractService implements ITaskResultMarshaler{

		ResultContext context;
		IContext input;
		
		public TestService(String id, ResultContext result, IContext input) {
			super(id);
			this.context = result;
			this.input = input;
		}

		public boolean workingContextIsCompatible(Class contextClass) {
			return this.input.getClass().isAssignableFrom(contextClass);
		}

		public void init() {
			
		}

		public IContext getResultContext() {
			StringBuffer buffer = new StringBuffer();
			if(this.getWorkingContext() instanceof ResultContext){
				buffer.append(((ResultContext)this.getWorkingContext()).getResult());
			}
			buffer.append(this.getID());
			buffer.append(":\n");
			buffer.append(this.getTaskProcessor().getTaskResult().toString());
			buffer.append("\n");
			this.context.setResult(buffer.toString());
			return this.context;
		}

		public Class getResultContextClass() {
			return this.context.getClass();
		}

		public ITask createTask() {
			ITask task = new Task("testtask",this, false);
			
			task.addPart(new TestTaskPart("task1"));
			task.addPart(new TestTaskPart("task2"));
			
			return task;
		}

		/* (non-Javadoc)
		 * @see org.dotplot.services.ITaskResultMarshaler#marshalResult(java.util.Map)
		 */
		public Object marshalResult(Map taskResult) {
			StringBuffer result = new StringBuffer();
			Iterator iter = taskResult.keySet().iterator();
			while(iter.hasNext()){
				result.append(taskResult.get(iter.next()));
				result.append("\n");
			}
			
			return result.toString();
		}
		
	}
	
	private BatchJob job;
	private IFrameworkContext frameworkContext;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.job = new BatchJob();
		this.frameworkContext = new IFrameworkContext(){

			private ServiceRegistry manager;
			
			public IServiceRegistry getServiceRegistry() {
				if(manager == null){
					manager = new ServiceRegistry();
				}
				return manager;
			}

			public String getWorkingDirectory() {
				return null;
			}};
	}

	/*
	 * Test method for 'org.dotplot.services.BatchJob.BatchJob()'
	 */
	public void testBatchJob() {
		IErrorHandler handler = this.job.getErrorHandler();
		assertNotNull(handler);
		assertTrue( handler instanceof DefaultErrorHandler);
		
		List<String> batch = this.job.getServiceBatch();
		assertNotNull(batch);
		assertEquals(0, batch.size());
	}

	/*
	 * Test method for 'org.dotplot.services.BatchJob.addServiceToTop(String)'
	 */
	public void testAddServiceToTop() {
		String service1 = "org.dotplot.services.test.testservice1";
		String service2 = "org.dotplot.services.test.testservice2";
		String service3 = "org.dotplot.services.test.testservice3";
		
		try {
			this.job.addService(null);
			fail("NullPointerException must be thrown");
		} 
		catch (NullPointerException e) {
			/*all clear*/
		} 
		catch (Exception e) {
			fail("wrong exception");
		}
		
		try {
			this.job.addService(service1);
			this.job.addService(service2);
			this.job.addService(service3);
			
			List<String> batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(3, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service2, batch.get(1));
			assertEquals(service3, batch.get(2));
			
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
	 * Test method for 'org.dotplot.services.BatchJob.addServiceAt(String, int)'
	 */
	public void testAddServiceAt() {
		String service1 = "org.dotplot.services.test.testservice1";
		String service2 = "org.dotplot.services.test.testservice2";
		String service3 = "org.dotplot.services.test.testservice3";
		String service4 = "org.dotplot.services.test.testservice4";
		String service5 = "org.dotplot.services.test.testservice5";
		String service6 = "org.dotplot.services.test.testservice6";
		
		try {
			this.job.addService(service1, -1);
			fail("IllegalArgumentException must be thrown");
		} 
		catch (IllegalArgumentException e) {
			assertEquals("-1", e.getMessage());
		} 
		catch (Exception e) {
			fail("wrong exception");
		}
		
		try {
			this.job.addService(null, 0);
			fail("NullPointerException must be thrown");
		} 
		catch (NullPointerException e) {
			/*all clear*/
		} 
		catch (Exception e) {
			fail("wrong exception");
		}
		
		try {
			this.job.addService(service1);
			this.job.addService(service2);
			this.job.addService(service3);
			
			List<String> batch;
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(3, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service2, batch.get(1));
			assertEquals(service3, batch.get(2));
			
			this.job.addService(service4,1);
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(4, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service4, batch.get(1));
			assertEquals(service2, batch.get(2));
			assertEquals(service3, batch.get(3));

			this.job.addService(service5,3);
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(5, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service4, batch.get(1));
			assertEquals(service2, batch.get(2));
			assertEquals(service5, batch.get(3));
			assertEquals(service3, batch.get(4));
			
			this.job.addService(service6, 1000);
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(6, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service4, batch.get(1));
			assertEquals(service2, batch.get(2));
			assertEquals(service5, batch.get(3));
			assertEquals(service3, batch.get(4));
			assertEquals(service6, batch.get(5));
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
	 * Test method for 'org.dotplot.services.BatchJob.removeServiceFromTop()'
	 */
	public void testRemoveServiceFromTop() {
		String service1 = "org.dotplot.services.test.testservice1";
		String service2 = "org.dotplot.services.test.testservice2";
		String service3 = "org.dotplot.services.test.testservice3";
				
		try {
			this.job.addService(service1);
			this.job.addService(service2);
			this.job.addService(service3);

			List<String> batch;
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(3, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service2, batch.get(1));
			assertEquals(service3, batch.get(2));

			assertEquals(service3, this.job.removeService());
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(2, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service2, batch.get(1));
	
			assertEquals(service2, this.job.removeService());
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(1, batch.size());
			assertEquals(service1, batch.get(0));

			assertEquals(service1, this.job.removeService());
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(0, batch.size());

			assertNull(this.job.removeService());
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(0, batch.size());

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
	 * Test method for 'org.dotplot.services.BatchJob.removeServiceAt(int)'
	 */
	public void testRemoveServiceAt() {
		String service1 = "org.dotplot.services.test.testservice1";
		String service2 = "org.dotplot.services.test.testservice2";
		String service3 = "org.dotplot.services.test.testservice3";
		
		try {
			this.job.removeService(-1);
			fail("IllegalArgumentException must be thrown");
		} 
		catch (IllegalArgumentException e) {
			assertEquals("-1", e.getMessage());
		} 
		catch (Exception e) {
			fail("wrong exception");
		}
		
		try {
			this.job.addService(service1);
			this.job.addService(service2);
			this.job.addService(service3);
			
			List<String> batch;
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(3, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service2, batch.get(1));
			assertEquals(service3, batch.get(2));
			
			assertEquals(service2, this.job.removeService(1));
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(2, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service3, batch.get(1));
			
			this.job.addService(service2);
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(3, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service3, batch.get(1));
			assertEquals(service2, batch.get(2));

			assertEquals(service2, this.job.removeService(2));
			
			batch = job.getServiceBatch();
			assertNotNull(batch);
			assertEquals(2, batch.size());
			assertEquals(service1, batch.get(0));
			assertEquals(service3, batch.get(1));

			try {
				this.job.removeService(1000);
				fail("Exception must be thrown");
			}
			catch(ArrayIndexOutOfBoundsException e){
				/*all clear*/
			}
			catch(Exception e){
				fail("wrong exception");
			}
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
	 * Test method for 'org.dotplot.services.BatchJob.process(ServiceManager, FrameworkContext)'
	 */
	public void testProcess() {		
		ResultContext context = new ResultContext1();				

		try {
			this.job.process(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.frameworkContext.getServiceRegistry().register("test1",new TestService("test1", new ResultContext1(), new NullContext()));
			this.frameworkContext.getServiceRegistry().register("test2",new TestService("test2", new ResultContext2(),new ResultContext1()));
			this.frameworkContext.getServiceRegistry().register("test3",new TestService("test3", context, new ResultContext2()));
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception");
		}

		this.job.addService("test1");
		this.job.addService("test2");
		this.job.addService("test3");
		assertTrue(this.job.validatePreconditions(this.frameworkContext.getServiceRegistry()));

		
		assertNull(context.getResult());
		this.job.process(this.frameworkContext);
		StringBuffer buffer = new StringBuffer();
		buffer.append("test1:\n");
		buffer.append("Part:task1\n");
		buffer.append("Part:task2\n");
		buffer.append("\n");
		buffer.append("test2:\n");
		buffer.append("Part:task1\n");
		buffer.append("Part:task2\n");
		buffer.append("\n");
		buffer.append("test3:\n");
		buffer.append("Part:task1\n");
		buffer.append("Part:task2\n");
		buffer.append("\n");
				
		assertEquals(buffer.toString(), context.getResult());
		
		this.job.addService("blub");
		assertEquals("", ((DefaultErrorHandler)this.job.getErrorHandler()).getErrorMessages());

		
		this.job.process(this.frameworkContext);
		assertEquals("[1](fatal) at Job:no id -> blub\n", ((DefaultErrorHandler)this.job.getErrorHandler()).getErrorMessages());

		this.job.removeService();
		this.job.removeService();

		try {
			this.frameworkContext.getServiceRegistry().register("test4",new TestService("test4", new ResultContext1(), new ResultContext1()));
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception");
		}

		this.job.addService("test4");
		
		this.job.process( this.frameworkContext);
		buffer = new StringBuffer();
		buffer.append("[1](fatal) at Job:no id -> blub\n[2](fatal) at Job:no id -> ");
		buffer.append("org.dotplot.core.plugins.tests.BatchJobTest$ResultContext2\n");

		assertEquals(buffer.toString(), ((DefaultErrorHandler)this.job.getErrorHandler()).getErrorMessages());
		
	}

	/*
	 * Test method for 'org.dotplot.services.BatchJob.validatePreconditions(ServiceManager, FrameworkContext)'
	 */
	public void testValidatePreconditions() {
		
		try {
			this.job.validatePreconditions(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			//keine registrierten services
			assertTrue(this.job.validatePreconditions(this.frameworkContext.getServiceRegistry()));			
		}
		catch (Exception e) {
			fail("no exception");
		}
		
		try {
			this.frameworkContext.getServiceRegistry().register("test1",new TestService("test1", new ResultContext1(), new NullContext()));
			this.frameworkContext.getServiceRegistry().register("test2",new TestService("test2", new ResultContext2(),new ResultContext1()));
			this.frameworkContext.getServiceRegistry().register("test3",new TestService("test3", new ResultContext1(),new ResultContext2()));
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception");
		}
		
		this.job.addService("test1");
		this.job.addService("test2");
		this.job.addService("test3");
		assertTrue(this.job.validatePreconditions(this.frameworkContext.getServiceRegistry()));
		
		
		this.job.addService("test3");		
		assertFalse(this.job.validatePreconditions(this.frameworkContext.getServiceRegistry()));

	}

	public void testSetTaskProcessor(){
		try {
			this.job.setTaskProcessor(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			this.job.setTaskProcessor(new ITaskProcessor(){

				public Object getTaskResult() {
					return null;
				}

				public void setErrorHandler(IErrorHandler handler) {}

				public boolean process(ITask job) {
					return false;}

				public boolean process(ITask task, Object invokingObject) {
					return false;
				}

				public void stop() {
				}
				});
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
	
}
