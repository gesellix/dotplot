/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.plugins.Version;
import org.dotplot.core.services.AbstractService;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceExtentionActivator;
import org.dotplot.core.services.IServiceHotSpot;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskPart;
import org.dotplot.core.services.ITaskProcessor;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.ServiceHotSpot;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class AbstractServiceTest extends TestCase {

	private final class AbstractServiceTester extends
			AbstractService<IFrameworkContext, ServiceHotSpot> implements
			ITaskResultMarshaler {

		/**
		 * @param theId
		 */
		public AbstractServiceTester(final String theId) {
			super(theId);
			this.addHotSpot(new ServiceHotSpot(spotID, String.class));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#createTask()
		 */
		@Override
		public ITask createTask() {
			Task job = new Task("testjob", this, true);

			job.addPart(new TestTaskPart("part1"));
			job.addPart(new TestTaskPart("part2"));
			job.addPart(new TestTaskPart("part3"));
			job.addPart(new TestTaskPart("part4"));

			return job;
		}

		/**
		 * 
		 * @return
		 */
		public IContext getFrameworkContext() {
			return this.frameworkContext;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#getResultContext()
		 */
		@Override
		public IContext getResultContext() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#getResultContextClass()
		 */
		@Override
		public Class<?> getResultContextClass() {
			return TestContext.class;
		}

		public String getResultString() {
			if (this.getTaskProcessor().getTaskResult() != null) {
				return this.getTaskProcessor().getTaskResult().toString();
			}
			else {
				return null;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#init()
		 */
		@Override
		public void init() {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.ITaskResultMarshaler#marchalResult(java.util
		 * .Map)
		 */
		public Object marshalResult(Map jobResult) {
			Iterator<?> iter = jobResult.values().iterator();
			StringBuffer buffer = new StringBuffer();
			while (iter.hasNext()) {
				buffer.append(iter.next().toString());
			}
			return buffer.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.IService#workingContextIsCompatible(java.lang
		 * .Class)
		 */
		@Override
		public boolean workingContextIsCompatible(Class<?> contextClass) {
			return TestContext.class.isAssignableFrom(contextClass);
		}

	}

	private final class TestContext implements IContext {
	}

	private final class TestPlugin implements IServiceExtentionActivator {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IServicePlugin#getID()
		 */
		public String getID() {
			return "org.dotplot.services.testplugin";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IServicePlugin#getVersion()
		 */
		public Version getVersion() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IServicePlugin#isActivated()
		 */
		public boolean isActivated() {
			return true;
		}

	}

	private class TestTaskPart implements ITaskPart {

		/**
		 * 
		 */
		private String id;

		public TestTaskPart(String newId) {
			this.id = newId;
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
		public void setLocalRessources(Collection ressouceList)
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

	private static final String spotID = "org.dotplot.services.tests.testspot";

	private IServiceExtentionActivator plugin;

	private AbstractServiceTester tester;

	private String id = "org.dotplot.service.test.abstractservicetester";

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.tester = new AbstractServiceTester(this.id);
		this.plugin = new TestPlugin();
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.AbstractService(String)'
	 */
	public void testAbstractService() {
		try {
			new AbstractServiceTester(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			new AbstractServiceTester("");
			fail("exception must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals("wrong id", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			new AbstractServiceTester("    ");
			fail("exception must be thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals("wrong id", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			IService<?, ?> service = new AbstractServiceTester(" test ");
			assertEquals("test", service.getID());
		}
		catch (Exception e) {
			fail("no exception");
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.getServiceExtentions(String)'
	 */
	public void testAddGetServiceExtentionsString() {
		Extention e1 = new Extention(this.plugin, "test1");
		Extention e2 = new Extention(this.plugin, "test2");

		Map spots = this.tester.getHotSpots();
		assertNotNull(spots);
		assertEquals(1, spots.size());
		assertNotNull(spots.get(spotID));
		IServiceHotSpot spot = (IServiceHotSpot) spots.get(spotID);

		try {
			this.tester.getHotSpot(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.getHotSpot("blub");
			fail("exception must be thrown");
		}
		catch (UnknownServiceHotSpotException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.addExtention("test", e1);
		}
		catch (UnknownServiceHotSpotException e) {
			assertEquals("test", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.tester.addExtention(null, e1);
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.tester.addExtention(spotID, null);
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.tester.addExtention(null, null);
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			this.tester.addExtention(spotID, e1);
			this.tester.addExtention(spotID, e2);

			Collection c = spot.getAllExtentions();
			Collection extentions = this.tester.getExtentions(spotID);

			assertNotNull(c);
			assertNotNull(extentions);
			assertEquals(c.size(), extentions.size());
			assertTrue(extentions.contains(e1));
			assertTrue(extentions.contains(e2));
		}
		catch (Exception e) {
			fail("no exception : " + e.getClass().getName());
		}

	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.addServiceExtentions(IServiceHotSpot,
	 * IServicePlugin, Object)'
	 */
	public void testAddServieceExtentionIServiceHotSpotIServicePluginObject() {

		try {
			this.tester.addExtention(null, plugin, "test");
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.addExtention(spotID, null, "test");
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.addExtention(spotID, plugin, null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.addExtention("blub", plugin, "test");
			fail("exception must be thrown");
		}
		catch (UnknownServiceHotSpotException e) {
			assertEquals("blub", e.getMessage());
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.addExtention(spotID, plugin, new Object());
			fail("exception must be thrown");
		}
		catch (IllegalArgumentException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception: " + e.getClass().getName());
		}

		try {
			this.tester.addExtention(spotID, plugin, "test1");
			this.tester.addExtention(spotID, plugin, "test2");

			Collection c = this.tester.getExtentions(spotID);
			assertNotNull(c);
			assertEquals(2, c.size());
			Iterator i = c.iterator();
			assertTrue(i.hasNext());
			Object o = i.next();
			assertTrue(o instanceof Extention);
			assertEquals("test1", ((Extention) o).getExtentionObject());
			assertTrue(i.hasNext());
			o = i.next();
			assertTrue(o instanceof Extention);
			assertEquals("test2", ((Extention) o).getExtentionObject());
			assertFalse(i.hasNext());

		}
		catch (Exception e) {
			fail("no exception");
		}

	}

	public void testGetHotSpot() {
		try {
			this.tester.getHotSpot(null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.getHotSpot("test");
			fail("exception must be thrown");
		}
		catch (UnknownServiceHotSpotException e) {
			assertEquals("test", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			IServiceHotSpot spot = this.tester.getHotSpot(spotID);
			assertNotNull(spot);
			assertEquals(spotID, spot.getID());
		}
		catch (Exception e) {
			fail("no exception");
		}

	}

	/*
	 * Test method for 'org.dotplot.services.AbstractService.getID()'
	 */
	public void testGetID() {
		assertEquals(id, this.tester.getID());
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.getServiceHotSpots()'
	 */
	public void testGetServiceHotSpots() {
		Map map = this.tester.getHotSpots();
		assertTrue(map.containsKey(spotID));
		assertTrue(((ServiceHotSpot) map.get(spotID))
				.isValidExtention(String.class));
	}

	public void testGetSetTaskProcessor() {
		ITaskProcessor processor = new ITaskProcessor() {

			public Object getTaskResult() {
				return null;
			}

			public boolean process(ITask job) {
				return false;
			}

			public boolean process(ITask task, Object invokingObject) {
				return false;
			}

			public void setErrorHandler(IErrorHandler handler) {
			}

			public void stop() {
			}
		};

		assertNotNull(this.tester.getTaskProcessor());
		this.tester.setTaskProcessor(processor);
		assertSame(processor, this.tester.getTaskProcessor());

		try {
			this.tester.setTaskProcessor(null);
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
	 * 'org.dotplot.services.AbstractService.workingContextIsCompatible(Class)'
	 */
	public void testGetSetWorkingContext() {

		assertNotNull(this.tester.getWorkingContext());
		assertTrue(this.tester.getWorkingContext() instanceof NullContext);

		try {
			this.tester.setWorkingContext(new IContext() {
			});
			fail("exception must be thrown");
		}
		catch (IllegalContextException e) {
			assertEquals(this.getClass().getName() + "$2", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			IContext context = new TestContext();
			this.tester.setWorkingContext(context);
			assertSame(context, this.tester.getWorkingContext());
		}
		catch (Exception e) {
			fail("no exception");
		}

		try {
			this.tester.setWorkingContext(null);
			assertNotNull(this.tester.getWorkingContext());
			assertTrue(this.tester.getWorkingContext() instanceof NullContext);
			/* all clear */
		}
		catch (Exception e) {
			fail("no exception");
		}

	}

	public void testRun() {
		// try {
		String testString = "result:part1result:part2result:part3result:part4";

		assertNull(this.tester.getResultString());
		this.tester.run();
		assertNotNull(this.tester.getResultString());
		assertEquals(testString, this.tester.getResultString());
		// }
		// catch(Exception e){
		// fail("no exception: "+e.getClass().getName());
		// }
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.setErrorHandler(IProcessingErrorHandler)'
	 */
	public void testSetErrorHandler() {
		try {
			this.tester.setErrorHandler(null);
			fail("excpetion must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		IErrorHandler handler = new IErrorHandler() {

			public void error(IJob j, Exception e) {
			}

			public void error(IService<?, ?> s, Exception e) {
			}

			public void error(ITaskPart t, Exception e) {
			}

			public void error(ITaskProcessor t, Exception e) {
			}

			public void fatal(IJob j, Exception e) {
			}

			public void fatal(IService<?, ?> s, Exception e) {
			}

			public void fatal(ITaskPart t, Exception e) {
			}

			public void fatal(ITaskProcessor t, Exception e) {
			}

			public void warning(IJob j, Exception e) {
			}

			public void warning(IService<?, ?> s, Exception e) {
			}

			public void warning(ITaskPart t, Exception e) {
			}

			public void warning(ITaskProcessor t, Exception e) {
			}
		};

		this.tester.setErrorHandler(handler);
		assertSame(handler, this.tester.getErrorHandler());
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.AbstractService.setFrameworkContext(FrameworkContext)'
	 */
	public void testSetFrameworkContext() {
		try {
			this.tester.setFrameworkContext(null);
			fail("excpetion must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		IFrameworkContext framework = new IFrameworkContext() {

			public IServiceRegistry getServiceRegistry() {
				return null;
			}

			public String getWorkingDirectory() {
				return null;
			}
		};
		this.tester.setFrameworkContext(framework);
		assertSame(framework, this.tester.getFrameworkContext());
	}

}
