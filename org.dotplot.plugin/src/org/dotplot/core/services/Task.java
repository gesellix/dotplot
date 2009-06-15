/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;
import java.util.Vector;

/**
 * Default implementation of the <code>ITask</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class Task implements ITask {

    /**
     * The if of the <code>Task</code>.
     */
    private String id;

    /**
     * Indicator if the <code>Task</code> is done.
     */
    private boolean done;

    /**
     * Storrage for <code>TaskParts</code>.
     */
    private Collection<ITaskPart> parts;

    /**
     * The <code>Task</code>'s <code>ResultMarshaler</code>.
     */
    private ITaskResultMarshaler marshaler;

    /**
     * Indicator if the <code>Task</code> is partable.
     */
    private boolean partable;

    /**
     * Creates a new <code>Task</code>.
     * 
     * @param id
     *            The id of the <code>Task</code>.
     * @param marshaler
     *            The <code>TaskResultMarshaler</code> of the <code>Task</code>.
     * @param partable
     *            Indicator if the <code>Task</code> is partable.
     */
    public Task(String id, ITaskResultMarshaler marshaler, boolean partable) {
	if (id == null || marshaler == null) {
	    throw new NullPointerException();
	}
	this.done = false;
	this.parts = new Vector<ITaskPart>();
	this.id = id;
	this.marshaler = marshaler;
	this.partable = partable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#addPart(org.dotplot.services.IJobPart)
     */
    public void addPart(ITaskPart part) {
	if (part == null) {
	    throw new NullPointerException();
	}
	this.parts.add(part);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#countParts()
     */
    public int countParts() {
	return this.parts.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#done()
     */
    public void done() {
	this.done = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#getID()
     */
    public String getID() {
	return this.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#getJobParts()
     */
    public Collection<ITaskPart> getParts() {
	return this.parts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#getResultMarchaler()
     */
    public ITaskResultMarshaler getResultMarshaler() {
	return this.marshaler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#isDone()
     */
    public boolean isDone() {
	return this.done;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#isPartAble()
     */
    public boolean isPartAble() {
	return this.partable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#isPartless()
     */
    public boolean isPartless() {
	return this.parts.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.services.IJob#removePart(org.dotplot.services.IJobPart)
     */
    public void removePart(ITaskPart part) {
	this.parts.remove(part);
    }
}
