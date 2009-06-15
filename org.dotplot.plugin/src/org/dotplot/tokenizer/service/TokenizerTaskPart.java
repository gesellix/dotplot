/**
 * 
 */
package org.dotplot.tokenizer.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class TokenizerTaskPart extends AbstractTaskPart {

    private ITokenizer tokenizer;

    private List<IPlotSource> ressources;

    private PlotSourceListTokenizer result;

    private boolean errorOccured;

    /**
     * @param id
     */
    public TokenizerTaskPart(String id, ITokenizer tokenizer) {
	super(id);
	if (tokenizer == null) {
	    throw new NullPointerException();
	}
	this.errorOccured = false;
	this.tokenizer = tokenizer;
	this.ressources = new LinkedList<IPlotSource>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#errorOccured()
     */
    public boolean errorOccured() {
	return this.errorOccured;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.ITaskPart#getResult()
     */
    public Object getResult() {
	return this.result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
	this.result = new PlotSourceListTokenizer(this.tokenizer);
	for (IPlotSource source : this.ressources) {
	    this.result.addPlotSource(source);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.ITaskPart#setLocalRessources(java.util.Collection
     * )
     */
    public void setLocalRessources(
	    Collection<? extends IRessource> ressourceList)
	    throws InsufficientRessourcesException {
	if (ressourceList == null) {
	    throw new InsufficientRessourcesException("ressourcelist is null");
	}
	for (IRessource res : ressourceList) {
	    if (res instanceof IPlotSource) {
		this.ressources.add((IPlotSource) res);
	    }
	}
    }

}
