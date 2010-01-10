/**
 * 
 */
package org.dotplot.core.tests;

import junit.framework.TestCase;

import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.IInterceptor;
import org.dotplot.core.plugins.IPluginHotSpot;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.plugins.Version;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IServiceExtentionActivator;
import org.dotplot.core.services.ITask;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class DotplotServiceTest extends TestCase {

	private final class TestDotplotService extends DotplotService {

		/**
		 * @param id
		 */
		public TestDotplotService(String id) {
			super(id);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.core.DotplotService#createResultContext()
		 */
		@Override
		protected IContext createResultContext() {
			return this.getWorkingContext();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#createTask()
		 */
		@Override
		public ITask createTask() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IService#getResultContextClass()
		 */
		@Override
		public Class getResultContextClass() {
			return null;
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
		 * org.dotplot.core.DotplotService#registerDefaultConfiguration(org.
		 * dotplot.core.IConfigurationRegistry)
		 */
		@Override
		public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.dotplot.services.IService#workingContextIsCompatible(java.lang
		 * .Class)
		 */
		@Override
		public boolean workingContextIsCompatible(Class contextClass) {
			return true;
		}

	}

	private class TestInterceptor implements IInterceptor {

		private StringBuffer buffer;

		TestInterceptor(StringBuffer buffer) {
			this.buffer = buffer;
		}

		public void execute(IPluginHotSpot hotSpot,
				DotplotService interceptedService, IContext workingContext,
				DotplotContext dotplotContext) {
			if (workingContext != null) {
				buffer.append("interceptor called\n");
				buffer.append("hotspot:");
				buffer.append(hotSpot.getID());
				buffer.append("\n");
				buffer.append("service:");
				buffer.append(interceptedService.getID());
				buffer.append("\n");
				buffer.append("workingcontext:");
				buffer.append(workingContext.getClass().getName());
				buffer.append("\n");
				buffer.append("frameworkcontext:");
				buffer.append(dotplotContext.getClass().getName());
				buffer.append("\n");
				buffer.append("\n");
			}
		}
	}

	private class TestPlugin implements IServiceExtentionActivator {

		private String id;

		private boolean active;

		public TestPlugin(String id, boolean isActivated) {
			this.id = id;
			this.active = isActivated;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.dotplot.services.IServicePlugin#getID()
		 */
		public String getID() {
			return this.id;
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
			return this.active;
		}

		public void setInactive() {
			this.active = false;
		}

	}

	private DotplotService service;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.service = new TestDotplotService("org.dotplot.core.testservice");
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotService.DotplotService(String)'
	 */
	public void testDotplotService() {
		DotplotService service1, service2;
		IPluginHotSpot before, after;

		final String SERVICE1 = "org.dotplot.core.testservice1";
		final String SERVICE2 = "org.dotplot.core.testservice2";

		service1 = new TestDotplotService(SERVICE1);
		service2 = new TestDotplotService(SERVICE2);

		try {
			before = service1.getHotSpot(SERVICE1 + ".Before");
			after = service1.getHotSpot(SERVICE1 + ".After");
			assertNotNull(before);
			assertNotNull(after);
			assertEquals(SERVICE1 + ".Before", before.getID());
			assertEquals(SERVICE1 + ".After", after.getID());
			assertSame(IInterceptor.class, before.getExtentionClass());
			assertSame(IInterceptor.class, after.getExtentionClass());

			before = service2.getHotSpot(SERVICE2 + ".Before");
			after = service2.getHotSpot(SERVICE2 + ".After");
			assertNotNull(before);
			assertNotNull(after);
			assertEquals(SERVICE2 + ".Before", before.getID());
			assertEquals(SERVICE2 + ".After", after.getID());
			assertSame(IInterceptor.class, before.getExtentionClass());
			assertSame(IInterceptor.class, after.getExtentionClass());

		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.core.DotplotService.run()'
	 */
	public void testRun() {
		StringBuffer buffer = new StringBuffer();
		StringBuffer reference = new StringBuffer();
		IInterceptor interceptor = new TestInterceptor(buffer);
		TestPlugin active = new TestPlugin("active plugin", true);
		TestPlugin inactive = new TestPlugin("inactive plugin", true);

		try {
			// simple run
			this.service.run();

			this.service.setWorkingContext(new IContext() {
			});
			this.service.setFrameworkContext(new DotplotContext("."));

			this.service.addExtention("org.dotplot.core.testservice.Before",
					active, interceptor);
			this.service.addExtention("org.dotplot.core.testservice.After",
					inactive, interceptor);

			PluginHotSpot spot = this.service
					.getHotSpot("org.dotplot.core.testservice.Before");
			assertEquals(1, spot.getAllExtentions().size());

			spot = this.service
					.getHotSpot("org.dotplot.core.testservice.After");
			assertEquals(1, spot.getAllExtentions().size());

			reference.append("interceptor called\n");
			reference.append("hotspot:");
			reference.append("org.dotplot.core.testservice.Before");
			reference.append("\n");
			reference.append("service:");
			reference.append("org.dotplot.core.testservice");
			reference.append("\n");
			reference.append("workingcontext:");
			reference.append("org.dotplot.core.tests.DotplotServiceTest$1");
			reference.append("\n");
			reference.append("frameworkcontext:");
			reference.append("org.dotplot.core.DotplotContext");
			reference.append("\n");
			reference.append("\n");

			reference.append("interceptor called\n");
			reference.append("hotspot:");
			reference.append("org.dotplot.core.testservice.After");
			reference.append("\n");
			reference.append("service:");
			reference.append("org.dotplot.core.testservice");
			reference.append("\n");
			reference.append("workingcontext:");
			reference.append("org.dotplot.core.tests.DotplotServiceTest$1");
			reference.append("\n");
			reference.append("frameworkcontext:");
			reference.append("org.dotplot.core.DotplotContext");
			reference.append("\n");
			reference.append("\n");

			// run with active plugins
			this.service.run();

			assertEquals(reference.toString(), buffer.toString());
			buffer.setLength(0);
			reference.setLength(0);
			inactive.setInactive();

			reference.append("interceptor called\n");
			reference.append("hotspot:");
			reference.append("org.dotplot.core.testservice.Before");
			reference.append("\n");
			reference.append("service:");
			reference.append("org.dotplot.core.testservice");
			reference.append("\n");
			reference.append("workingcontext:");
			reference.append("org.dotplot.core.tests.DotplotServiceTest$1");
			reference.append("\n");
			reference.append("frameworkcontext:");
			reference.append("org.dotplot.core.DotplotContext");
			reference.append("\n");
			reference.append("\n");

			// run with one inactive plugin
			this.service.run();
			assertEquals(reference.toString(), buffer.toString());

		}
		catch (Exception e) {
			e.printStackTrace();
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
