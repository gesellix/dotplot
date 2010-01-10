/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceExtentionActivator;
import org.dotplot.core.services.IServiceHotSpot;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskProcessor;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.core.services.ServiceRegistry;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class ServiceManagerTest extends TestCase {

	private final class ServiceTestImpl implements IService {

		private String id;

		public ServiceTestImpl(String id) {
			this.id = id;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.dotplot.services.IService#addExtention(org.dotplot.services.
		 * IServiceHotSpot, java.lang.Object)
		 */
		public void addExtention(IServiceHotSpot spot, Object extention) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#addExtention(java.lang.String,
		 * org.dotplot.services.Extention)
		 */
		public void addExtention(String serviceHotSpotID, Extention extention)
				throws UnknownServiceHotSpotException {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#addExtention(java.lang.String,
		 * org.dotplot.services.IServicePlugin, java.lang.Object)
		 */
		public void addExtention(String serviceHotSpotID,
				IServiceExtentionActivator plugin, Object extentionObjection)
				throws UnknownServiceHotSpotException {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#createJob()
		 */
		public ITask createTask() {
			return null;
		}

		public Collection getExtentions(IServiceHotSpot hotSpot) {
			return null;
		}

		public Collection getExtentions(String hotSpotID) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#getHotSpot(java.lang.String)
		 */
		public IServiceHotSpot getHotSpot(String id) {
			return null;
		}

		public Map getHotSpots() {
			return null;
		}

		public String getID() {
			return this.id;
		}

		public IContext getResultContext() {
			return null;
		}

		public Class getResultContextClass() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#init()
		 */
		public void init() {
		}

		public boolean isPartAble() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.util.ExecutionUnit#isRunning()
		 */
		public boolean isRunning() {
			return false;
		}

		public void run() {
		}

		public void setErrorHandler(IErrorHandler handler) {
		}

		public void setFrameworkContext(IFrameworkContext context) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.IService#setServiceProcessor(org.dotplot.services
		 * .IServiceProcessor)
		 */
		public void setTaskProcessor(ITaskProcessor processor) {
		}

		public void setWorkingContext(IContext context)
				throws IllegalContextException {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.util.ExecutionUnit#stop()
		 */
		public void stop() {
		}

		public boolean workingContextIsCompatible(Class contextClass) {
			return false;
		}

	}

	IServiceRegistry manager;

	IService service1;

	IService service2;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.manager = new ServiceRegistry();
		this.service1 = new ServiceTestImpl("org.dotplot.test.service1");
		this.service2 = new ServiceTestImpl("org.dotplot.test.service2");
	}

	/*
	 * 
	 */
	public void testCombine() {
		ServiceRegistry manager1 = new ServiceRegistry();
		ServiceRegistry manager2 = new ServiceRegistry();

		try {
			manager1.combine(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			IService service1 = new ServiceTestImpl("test1");
			IService service2 = new ServiceTestImpl("test2");
			IService service3 = new ServiceTestImpl("test3");
			IService service4 = new ServiceTestImpl("test4");

			manager1.register(service1.getID(), service1);
			manager1.register(service2.getID(), service2);
			manager2.register(service3.getID(), service3);
			manager2.register(service4.getID(), service4);

			assertNotNull(manager1.getAll());
			assertEquals(2, manager1.getAll().size());
			assertSame(service1, manager1.get("test1"));
			assertSame(service2, manager1.get("test2"));

			manager1.combine(manager2);

			assertEquals(4, manager1.getAll().size());
			assertSame(service1, manager1.get("test1"));
			assertSame(service2, manager1.get("test2"));
			assertSame(service3, manager1.get("test3"));
			assertSame(service4, manager1.get("test4"));

			try {
				manager2.combine(manager1);
				fail("DuplicateServiceRegistrationException must be thrown");
			}
			catch (DuplicateRegistrationException e1) {
				assertEquals("test3", e1.getMessage());

				assertNotNull(manager2.getAll());
				assertEquals(2, manager2.getAll().size());
				assertSame(service3, manager2.get("test3"));
				assertSame(service4, manager2.get("test4"));
			}
			catch (Exception e) {
				fail("wrong Exception");
			}

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.services.ServiceManager.get(String)'
	 */
	public void testGetService() {
		assertTrue(this.manager.getAll().isEmpty());
		try {
			this.manager.register(this.service1.getID(), this.service1);
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception should be thrown");
		}
		assertFalse(this.manager.getAll().isEmpty());

		IService service = null;

		try {
			service = this.manager.get(null);
			fail("excption must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			service = this.manager.get("blub");
			fail("excption must be thrown");
		}
		catch (UnknownIDException e) {
			assertEquals("blub", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		try {
			service = this.manager.get(this.service1.getID());
		}
		catch (Exception e) {
			fail("no exception shold be thrown");
		}
		assertNotNull(service);
		assertSame(this.service1, service);
	}

	/*
	 * Test method for 'org.dotplot.services.ServiceManager.getAll()'
	 */
	public void testGetServices() {
		assertNotNull(this.manager.getAll());
		try {
			assertTrue(this.manager.getAll().isEmpty());
			this.manager.register(this.service1.getID(), this.service1);
			this.manager.register(this.service2.getID(), this.service2);
			assertFalse(this.manager.getAll().isEmpty());

			Map services = this.manager.getAll();
			assertEquals(2, services.size());
			assertTrue(services.containsKey(this.service1.getID()));
			assertTrue(services.containsKey(this.service2.getID()));
			assertTrue(services.containsValue(this.service1));
			assertTrue(services.containsValue(this.service2));
		}
		catch (Exception e) {
			fail("no exception should be thrown");
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.ServiceManager.registerService(IService)'
	 */
	public void testRegisterService() {
		try {
			this.manager.register(null, (IService) null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		assertTrue(this.manager.getAll().isEmpty());
		try {
			this.manager.register(this.service1.getID(), this.service1);
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception should be thrown");
		}
		assertFalse(this.manager.getAll().isEmpty());

		try {
			this.manager.register(this.service1.getID(), this.service1);
			fail("DuplicateServiceRegistrationException must be thrown");
		}
		catch (DuplicateRegistrationException e) {
			assertEquals(this.service1.getID(), e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.services.ServiceManager.ServiceManager()'
	 */
	public void testServiceManager() {
		Map map = this.manager.getAll();
		assertNotNull(map);
		assertTrue(map.isEmpty());
		assertEquals(0, map.size());
	}

	/*
	 * Test method for
	 * 'org.dotplot.services.ServiceManager.unRegisterService(String)'
	 */
	public void testUnRegisterServiceString() {

		try {
			this.manager.unregister((String) null);
			fail("exception must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong exception");
		}

		assertNull(this.manager.unregister("blub"));

		assertTrue(this.manager.getAll().isEmpty());
		try {
			this.manager.register(this.service1.getID(), this.service1);
		}
		catch (DuplicateRegistrationException e) {
			fail("no exception should be thrown");
		}
		assertFalse(this.manager.getAll().isEmpty());
		assertSame(this.service1, this.manager
				.unregister(this.service1.getID()));
		assertTrue(this.manager.getAll().isEmpty());
	}

}
