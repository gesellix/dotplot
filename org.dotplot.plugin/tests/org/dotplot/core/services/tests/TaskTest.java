/**
 * 
 */
package org.dotplot.core.services.tests;

import java.util.Collection;
import java.util.Map;

import junit.framework.TestCase;

import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.ITaskPart;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.Task;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class TaskTest extends TestCase {

    private class TestJobPart implements ITaskPart {

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
	 * @see org.dotplot.services.IJobPart#getID()
	 */
	public String getID() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IJobPart#getRessources()
	 */
	public Collection<IRessource> getRessources() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IJobPart#getResult()
	 */
	public Object getResult() {
	    // TODO Auto-generated method stub
	    return null;
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
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IJobPart#setErrorHandler(org.dotplot.services
	 * .IProcessingErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IJobPart#setLocalRessources(java.util.Collection
	 * )
	 */
	public void setLocalRessources(Collection ressouceList)
		throws InsufficientRessourcesException {
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.util.ExecutionUnit#stop()
	 */
	public void stop() {
	}

    }

    private Task job;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.job = new Task("testjob", new ITaskResultMarshaler() {

	    public Object marshalResult(Map jobResult) {
		return null;
	    }
	}, true);
    }

    /*
     * Test method for 'org.dotplot.services.Job.addPart(IJobPart)'
     */
    public void testAddPart() {
	try {
	    this.job.addPart(null);
	    fail("exception must be thrown");
	} catch (NullPointerException e) {
	    assertTrue(this.job.isPartless());
	    this.job.addPart(new TestJobPart());
	    assertFalse(this.job.isPartless());
	} catch (Exception e) {
	    fail("no exception");
	}
    }

    /*
     * Test method for 'org.dotplot.services.Job.countParts()'
     */
    public void testCountParts() {
	assertEquals(0, this.job.countParts());
	this.job.addPart(new TestJobPart());
	assertEquals(1, this.job.countParts());
	this.job.addPart(new TestJobPart());
	assertEquals(2, this.job.countParts());
	this.job.addPart(new TestJobPart());
	assertEquals(3, this.job.countParts());
    }

    /*
     * Test method for 'org.dotplot.services.Job.isDone()'
     */
    public void testDoneIsDone() {
	assertFalse(this.job.isDone());
	this.job.done();
	assertTrue(this.job.isDone());
    }

    /*
     * Test method for 'org.dotplot.services.Job.getJobParts()'
     */
    public void testGetJobParts() {
	TestJobPart part1 = new TestJobPart();
	TestJobPart part2 = new TestJobPart();
	TestJobPart part3 = new TestJobPart();

	Collection parts = this.job.getParts();
	assertNotNull(parts);
	assertTrue(parts.isEmpty());
	this.job.addPart(part1);
	this.job.addPart(part2);
	this.job.addPart(part3);
	assertFalse(parts.isEmpty());
	assertEquals(3, parts.size());
    }

    /*
     * Test method for 'org.dotplot.services.Job.isPartless()'
     */
    public void testIsPartless() {
	TestJobPart part = new TestJobPart();
	assertTrue(this.job.isPartless());
	this.job.addPart(part);
	assertFalse(this.job.isPartless());
	this.job.removePart(part);
	assertTrue(this.job.isPartless());
    }

    /*
     * Test method for 'org.dotplot.services.Job.Job(String, IResultMarshaler)'
     */
    public void testJob() {
	try {
	    new Task("testjob", null, true);
	    fail("exception must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("no exception");
	}

	try {
	    new Task(null, this.job.getResultMarshaler(), true);
	    fail("exception must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("no exception");
	}

	try {
	    Task job = new Task("testjob", this.job.getResultMarshaler(), true);
	    assertEquals("testjob", job.getID());
	    assertTrue(job.isPartless());
	    assertEquals(0, job.countParts());
	    assertFalse(job.isDone());
	    assertNotNull(job.getParts());
	    assertTrue(job.getParts().isEmpty());
	    assertEquals(0, job.getParts().size());
	} catch (Exception e) {
	    fail("no exception");
	}

    }

    /*
     * Test method for 'org.dotplot.services.Job.removePart(IJobPart)'
     */
    public void testRemovePart() {
	try {
	    this.job.removePart(null);
	} catch (Exception e) {
	    fail("no exception");
	}

	TestJobPart part1 = new TestJobPart();
	TestJobPart part2 = new TestJobPart();
	TestJobPart part3 = new TestJobPart();

	Collection parts = this.job.getParts();
	assertNotNull(parts);
	assertTrue(parts.isEmpty());
	this.job.addPart(part1);
	this.job.addPart(part2);
	this.job.addPart(part3);
	assertFalse(parts.isEmpty());
	assertEquals(3, parts.size());

	assertTrue(parts.contains(part1));
	assertTrue(parts.contains(part2));
	assertTrue(parts.contains(part3));

	this.job.removePart(part1);

	assertFalse(parts.contains(part1));
	assertTrue(parts.contains(part2));
	assertTrue(parts.contains(part3));

	this.job.removePart(part2);
	assertFalse(parts.contains(part1));
	assertFalse(parts.contains(part2));
	assertTrue(parts.contains(part3));

	this.job.removePart(part3);
	assertFalse(parts.contains(part1));
	assertFalse(parts.contains(part2));
	assertFalse(parts.contains(part3));
    }
}
