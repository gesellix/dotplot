/**
 * 
 */
package org.dotplot.core.plugins.tests;

import junit.framework.TestCase;

import org.dotplot.core.plugins.BatchJob;
import org.dotplot.core.plugins.JobRegistry;
import org.dotplot.core.services.IJob;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public final class JobRegistryTest extends TestCase {

	private JobRegistry registry;

	/*
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.registry = new JobRegistry();
	}

	/*
	 * Test method for 'org.dotplot.core.plugins.JobRegistry.getJob(String)'
	 */
	public void testGetJob() {

		try {
			this.registry.get(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.get("test1");
			fail("UnknownIDException must be thrown");
		}
		catch (UnknownIDException e) {
			assertEquals("test1", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		IJob job = new BatchJob();

		try {
			this.registry.register("test1", job);
			assertSame(job, this.registry.get("test1"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

	}

	/*
	 * Test method for 'org.dotplot.core.plugins.JobRegistry.getJobs()'
	 */
	public void testGetJobs() {

	}

	/*
	 * Test method for 'org.dotplot.core.plugins.JobRegistry.JobRegistry()'
	 */
	public void testJobRegistry() {

	}

	/*
	 * Test method for 'org.dotplot.core.plugins.JobRegistry.registerJob(String,
	 * IJob)'
	 */
	public void testRegisterJob() {
		IJob job1 = new BatchJob();
		IJob job2 = new BatchJob();

		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());

		try {
			this.registry.register(null, job1);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("test", null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			this.registry.register("testjob1", job1);
			this.registry.register("testjob2", job2);
			assertEquals(2, this.registry.getAll().size());
			assertSame(job1, this.registry.get("testjob1"));
			assertSame(job2, this.registry.get("testjob2"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}

		try {
			this.registry.register("testjob1", job1);
			fail("DuplicateRegistrationException must be thrown");
		}
		catch (DuplicateRegistrationException e) {
			assertEquals("testjob1", e.getMessage());
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
	}

	/*
	 * Test method for
	 * 'org.dotplot.core.plugins.JobRegistry.unregisterJob(String)'
	 */
	public void testUnregisterJob() {
		IJob job1 = new BatchJob();
		IJob job2 = new BatchJob();

		assertNotNull(this.registry.getAll());
		assertEquals(0, this.registry.getAll().size());

		try {
			this.registry.unregister(null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/* all clear */
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

		try {
			assertNull(this.registry.unregister("test1"));
			this.registry.register("test1", job1);
			this.registry.register("test2", job2);
			assertEquals(2, this.registry.getAll().size());
			assertTrue(this.registry.getAll().containsKey("test1"));
			assertTrue(this.registry.getAll().containsKey("test2"));

			assertSame(job2, this.registry.unregister("test2"));
			assertEquals(1, this.registry.getAll().size());
			assertTrue(this.registry.getAll().containsKey("test1"));
			assertFalse(this.registry.getAll().containsKey("test2"));
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
