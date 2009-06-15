/*
 * Created on Jun 25, 2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;

import org.dotplot.core.IPlotSource;

/**
 * Class stores all processed information of the used source files (dotplot
 * sourcefiles) for later retrieval.
 * 
 * @author Thorsten Ruehl
 */
public class SourceInformation implements Serializable {
    /**
     * for being Serializable
     */
    private static final long serialVersionUID = 9172755793072510646L;

    private int startindex;

    private long size;

    private String sourcename;

    // private static final Logger logger =
    // Logger.getLogger(SourceInformation.class.getName());

    /**
     * Create a new FileInformation using the given values.
     * 
     * @param startindex
     *            the startindex
     * @param sourceObject
     *            the corresponding File
     */
    public SourceInformation(int startindex, IPlotSource sourceObject) {
	this.startindex = startindex;
	if (sourceObject != null) {
	    sourcename = sourceObject.getName();
	    size = sourceObject.size();
	} else {
	    sourcename = new String("no sourcename given");
	    size = -1;
	}
    }

    /**
     * Gets this object's file size.
     * 
     * @return a long representing the file size value
     */
    public long getSize() {
	return this.size;
    }

    /**
     * Gets this object's filename.
     * 
     * @return a String representing the filename value
     */
    public String getSourcename() {
	return this.sourcename;
    }

    /**
     * Returns the start index value.
     * 
     * @return an int representing the start index value
     */
    public int getStartIndex() {
	return this.startindex;
    }

    @Override
    public String toString() {
	return new String("sourcename: " + this.sourcename + "\nsize: "
		+ this.size + " Bytes" + "\nstartindex: " + this.startindex);
    }
}
