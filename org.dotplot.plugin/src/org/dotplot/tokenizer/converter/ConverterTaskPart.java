/**
 * 
 */
package org.dotplot.tokenizer.converter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.InsufficientRessourcesException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class ConverterTaskPart extends AbstractTaskPart {

    private Collection<? extends IRessource> ressources;

    private IConverter converter;

    private File targetDirectory;

    private boolean overwrite;

    private List<IPlotSource> result;

    private boolean errorOccured;

    /**
     * @param id
     * @param converter
     */
    public ConverterTaskPart(String id, IConverter converter,
	    File targetDirectory, boolean overwrite) {
	super(id);
	if (converter == null || targetDirectory == null) {
	    throw new NullPointerException();
	}
	this.errorOccured = false;
	this.converter = converter;
	this.targetDirectory = targetDirectory;
	this.overwrite = overwrite;
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
	this.result = new Vector<IPlotSource>();
	this.converter.setOverwrite(this.overwrite);
	for (IRessource r : this.ressources) {
	    if (r instanceof IPlotSource) {
		try {
		    this.result.add(this.converter.convert((IPlotSource) r,
			    this.targetDirectory));
		} catch (IOException e) {
		    this.errorOccured = true;
		    this.getErrorhandler().error(this, e);
		}
	    }
	}
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
	if (ressouceList == null) {
	    throw new InsufficientRessourcesException();
	}
	this.ressources = ressouceList;
    }

}
