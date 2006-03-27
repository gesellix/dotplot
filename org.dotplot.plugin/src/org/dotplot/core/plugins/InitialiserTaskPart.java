/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;

import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.InsufficientRessourcesException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class InitialiserTaskPart extends AbstractTaskPart {

	/**
	 * 
	 */
	private IServiceRegistry registry;
	
	/**
	 * 
	 */
	private IFrameworkContext context;

	/**
	 * Creates a new <code>InitialiserTaskPart</code>.
	 * @param id
	 * @param context
	 */
	public InitialiserTaskPart(String id, IFrameworkContext context) {
		super(id);
		if( context == null) throw new NullPointerException();
		this.registry = context.getServiceRegistry();
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#getResult()
	 */
	public Object getResult() {
		return new Boolean(true);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#setLocalRessources(java.util.Collection)
	 */
	public void setLocalRessources(Collection<? extends  IRessource> ressouceList) throws InsufficientRessourcesException {}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		for(IService s: this.registry.getAll().values()){
			s.setFrameworkContext(this.context);
			s.init();
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#errorOccured()
	 */
	public boolean errorOccured() {
		return false;
	}

}
