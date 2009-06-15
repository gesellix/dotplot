package org.dotplot.grid;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;

/**
 * Defines the requirements for an object that can be called from a GridPlotter
 * to signal the completion of a plot over the grid.
 * 
 * @author Tobias Gesellchen
 */
public interface GridCallback {
    /**
     * called, when all parts from the grid are received.
     * 
     * @param gridImages
     *            maps the client IDs to the corresponding image parts
     * @param size
     *            Dimension of the complete image
     * 
     * @throws IOException
     *             if an error occured.
     */
    public void imagesReceived(Map gridImages, Dimension size)
	    throws IOException;
}
