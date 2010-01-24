package org.dotplot.core.services;

import java.util.Collection;
import java.util.Vector;

/**
 * Default implementation of the interface <code>ITaskPart</code>.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public abstract class AbstractTaskPart implements ITaskPart {

	/**
	 * The id.
	 */
	private String id;

	/**
	 * A <code>Collection</code> to administer the <code>TaskPart</code>'s
	 * ressources.
	 */
	private Collection<? extends IRessource> ressources;

	/**
	 * The <code>ErrorHandler</code> of the <code>TaskPart</code>.
	 */
	private IErrorHandler handler;

	protected boolean isRunning;

	protected boolean interruped;

	/**
	 * Creates a new <code>AbstractTaskPart</code>.
	 * 
	 * @param id
	 *            The id of the <code>TaskPart</code>.
	 */
	public AbstractTaskPart(String id) {
		if (id == null) {
			throw new NullPointerException();
		}
		if ("".equals(id)) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.ressources = new Vector<IRessource>();
		this.handler = new DefaultErrorHandler();
		this.isRunning = false;
		this.interruped = false;
	}

	/**
	 * Returns the <code>ErrorHandler</code>.
	 * 
	 * @return The <code>ErrorHandler</code>.
	 */
	public IErrorHandler getErrorhandler() {
		return this.handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.ITaskPart#getID()
	 */
	public String getID() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.ITaskPart#getRessources()
	 */
	public Collection<? extends IRessource> getRessources() {
		return this.ressources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.util.ExecutionUnit#isRunning()
	 */
	public synchronized boolean isRunning() {
		return this.isRunning;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.ITaskPart#setErrorHandler(org.dotplot.core.
	 * services.IErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {
		if (handler == null) {
			throw new NullPointerException();
		}
		this.handler = handler;
	}

	/**
	 * Sets the ressources of the <code>TaskPart</code>.
	 * 
	 * @param ressources
	 *            - the ressources.
	 * @throws NullPointerException
	 *             if ressources is <code>null</code>.
	 */
	public void setRessources(Collection<? extends IRessource> ressources) {
		if (ressources == null) {
			throw new NullPointerException();
		}
		this.ressources = ressources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.util.ExecutionUnit#stop()
	 */
	public synchronized void stop() {
		if (this.isRunning) {
			this.interruped = true;
		}
	}

}
