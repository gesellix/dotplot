/**
 * 
 */
package org.dotplot.image;

import java.util.Collection;

import org.dotplot.core.IDotplot;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.fmatrix.ITypeTableNavigator;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class QImageTaskPart extends AbstractTaskPart {

	private ITypeTableNavigator navigator;
	private IQImageConfiguration config;
	private IDotplot result;
	
	
	/**
	 * Creates a new <code>QImageTaskPart</code>.
	 * @param id
	 * @param navigator
	 * @throws NullPointerException
	 */
	public QImageTaskPart(String id, ITypeTableNavigator navigator, IQImageConfiguration config) {
		super(id);
		if(navigator == null || config == null) throw new NullPointerException();
		this.navigator = navigator;
		this.config = config;
		this.result = null;
	}

	public ITypeTableNavigator getNavigator(){
		return this.navigator;
	}
	
	public IQImageConfiguration getConfiguration(){
		return this.config;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#getResult()
	 */
	public Object getResult() {
		return this.result;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#setLocalRessources(java.util.Collection)
	 */
	public void setLocalRessources(Collection<? extends IRessource> ressouceList)
			throws InsufficientRessourcesException {
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			QImage qImage = new QImage(this.navigator, this.config);
			this.result = qImage.getDotplot();
		}
		catch(Exception e){
			this.getErrorhandler().fatal(this, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.ITaskPart#errorOccured()
	 */
	public boolean errorOccured() {
		return false;
	}

}
