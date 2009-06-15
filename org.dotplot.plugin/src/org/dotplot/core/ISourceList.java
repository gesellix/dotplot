package org.dotplot.core;

import java.util.List;

/**
 * Interface for a list of <code>PlotSources</code>.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface ISourceList extends List<IPlotSource> {

    /**
     * Derives the combined <code>SourceType</code> of the list.
     * <p>
     * The combined <code>SourceType</code> must be assigneable to all
     * <code>PlotSources</code> of the list.
     * </p>
     * 
     * @return The combined <code>SourceType</code>
     */
    public ISourceType getCombinedSourceType();
}
