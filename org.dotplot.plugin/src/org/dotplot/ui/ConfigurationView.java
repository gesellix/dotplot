/*
 * Created on 26.05.2004
 */
package org.dotplot.ui;

import java.util.Observable;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.dotplot.core.ConfigurationRegistry;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IConfigurationRegistry;

/**
 * The View of the MVC - design pattern.
 * <p />
 * Model - IGUIDotplotter Controller - ViewController
 * <p />
 * The Controller is observing the View. When something changes the Controller
 * manipulates the Model in the way the View dictates. Then the View uses the
 * changed Model to show the user the changes he caused.
 * <p />
 * The Controller observes a View by calling the addObserver(Observer) method.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.ui.ConfigurationViews
 * @see org.dotplot.IGUIDotplotter
 * @see org.dotplot.ui.ViewController
 */
public abstract class ConfigurationView extends Observable {
	/**
	 * The tab-name of the <code>ConfigurationView</code>
	 */
	private String name = "ConfView";

	/**
	 * The changelistener. This listener listens for changings in the view. To
	 * avoid calling observers when nothing is changed the
	 * <code>Observable</code> class is calling its <code>Observers</code>
	 * only if the <code>Observable</code> is marked that it has been changed
	 * by calling the <code>setChanged</code> method. This listener calls that
	 * method an developers could associate this listener with their
	 * <code>Composites</code>.
	 * 
	 * @see java.util.Observer
	 * @see java.util.Observable
	 */
	protected SelectionListener changeListener;

	/**
	 * Listens for actions of the Applybutton
	 */
	protected SelectionListener applyListener;

	/**
	 * Listens for actions of the Resetbutton.
	 */
	protected SelectionListener resetListener;

	protected DotplotContext context;

	protected IConfigurationRegistry registry;

	/**
	 * Creates a new <code>ConfigurationView</code>.
	 */
	public ConfigurationView(){
		this(new DotplotContext(".","."));
	}
	
	/**
	 * Creates a <code>ConfigurationView</code>.
	 * 
	 * @param dotplotter
	 *            the interface of the model of the MVC - pattern.
	 */
	public ConfigurationView(DotplotContext context) {
		this.registry = new ConfigurationRegistry();
		this.context = context;
		final ConfigurationView cv = this;
		this.changeListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cv.setChanged();
			}
		};

		this.applyListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cv.notifyObservers();
			}
		};

		this.resetListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cv.reset();
			}
		};
	}
	
	/**
	 * Returns the registry.
	 * 
	 * @return - the registry.
	 */
	public IConfigurationRegistry getRegistry() {
		return registry;
	}

	/**
	 * Sets the registry.
	 * 
	 * @param registry
	 *            The registry to set.
	 */
	public void setRegistry(IConfigurationRegistry registry) {
		if(registry == null) throw new NullPointerException();
		this.registry = registry;
	}

	/**
	 * Draws the <code>ConfigurationView</code>.
	 * 
	 * @param parent
	 *            the parent <code>Composite</code> of the
	 *            <code>ConfigurationView</code>
	 */
	public abstract void draw(Composite parent);

	/**
	 * Resets the <code>ConfigurationView</code>. Is called, when the
	 * resetbutton is pushed.
	 */
	public void reset() {
	}

	/**
	 * Returns the name of this <code>ConfigurationView</code>.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a new name for this <code>ConfigurationView</code>
	 * 
	 * @param newName
	 *            the new Name
	 */
	protected void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Gets the actual listener for the Applybutton.
	 * 
	 * @return the listener
	 */
	public SelectionListener getApplyListener() {
		return applyListener;
	}

	/**
	 * Gets the actual listener for the Resetbutton.
	 * 
	 * @return the listener
	 * 
	 * @see #setResetListener(org.eclipse.swt.events.SelectionListener)
	 */
	public SelectionListener getResetListener() {
		return resetListener;
	}

	/**
	 * Sets a new listener for the Applybutton.
	 * 
	 * @param listener
	 *            the new listener
	 */
	protected void setApplyListener(SelectionListener listener) {
		applyListener = listener;
	}

	/**
	 * Sets a new listener for the Resetbutton.
	 * 
	 * @param listener
	 *            the new listener
	 * 
	 * @see #getResetListener()
	 */
	public void setResetListener(SelectionListener listener) {
		resetListener = listener;
	}

	/**
	 * Refreshs the <code>Composites</code> of the
	 * <code>ConfigurationView</code>. This method should be called, when all
	 * <code>Composites</code> should be redrawn.
	 */
	public void refresh() {
	}

	public DotplotContext getContext() {
		return this.context;
	}

	/**
	 * Enable/Disable the given Control. If <code>comp</code> is an instance
	 * of Composite, its children will be enabled/disabled, too.
	 */
	protected static void setEnabledRecursive(Control comp, boolean enable) {
		if (comp == null) {
			return;
		}

		comp.setEnabled(enable);

		if (comp instanceof Composite) {
			Control[] children = ((Composite) comp).getChildren();
			for (int i = 0; i < children.length; i++) {
				setEnabledRecursive(children[i], enable);
			}
		}
	}

	/**
	 * Sets the context.
	 * @param context The context to set.
	 */
	public void setContext(DotplotContext context) {
		if(context == null) throw new NullPointerException();
		this.context = context;
	}
}
