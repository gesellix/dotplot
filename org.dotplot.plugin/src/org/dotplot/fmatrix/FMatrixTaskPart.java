/**
 * 
 */
package org.dotplot.fmatrix;

import java.util.Collection;

import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.tokenizer.service.ITokenStream;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class FMatrixTaskPart extends AbstractTaskPart {

	private ITokenStream tokenStream;

	private IFMatrixConfiguration config;

	private ISourceList sourceList;

	private ITypeTableNavigator result;

	/**
	 * Creates a new <code>FMatrixTaskPart</code>.
	 * 
	 * @param id
	 * @param stream
	 */
	public FMatrixTaskPart(String id, ITokenStream stream,
			IFMatrixConfiguration config) {
		super(id);
		if (stream == null || config == null) {
			throw new NullPointerException();
		}
		this.tokenStream = stream;
		this.config = config;
		this.sourceList = new DefaultSourceList();
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
	 * @see org.dotplot.core.services.ITaskPart#getResult()
	 */
	public Object getResult() {
		return this.result;
	}

	public ISourceList getSourceList() {
		return this.sourceList;
	}

	/**
	 * Returns the <code>TokenStream</code> assiged to this
	 * <code>FMatrixTaskPart</code>.
	 * 
	 * @return - the <code>TokenStream</code>.
	 */
	public ITokenStream getTokenStream() {
		return tokenStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		FMatrixManager manager = new FMatrixManager(this.tokenStream,
				this.config);
		manager.setSourceList(this.sourceList);
		manager.addTokens();
		this.result = manager.getTypeTableNavigator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.ITaskPart#setLocalRessources(java.util.Collection
	 * )
	 */
	public void setLocalRessources(Collection<? extends IRessource> ressouceList)
			throws InsufficientRessourcesException {
		// Damit die ressourceList nicht null sein darf
		if (ressouceList == null) {
			throw new NullPointerException();
		}
		for (IRessource res : ressouceList) {
			if (res instanceof IPlotSource) {
				this.sourceList.add((IPlotSource) res);
			}
		}
	}

}
