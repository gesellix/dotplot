/*
 * Created on 03.06.2004
 */
package org.dotplot.eclipse.views;

import java.io.File;
import java.util.Set;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <code>DotPlotFilter</code> provides a file filter for the DotPlotNavigator.
 * 
 * @author Sascha Hemminger
 * @see org.dotplot.eclipse.views.DotPlotNavigator
 */
class DotPlotFilter extends ViewerFilter {

    private Set<String> endings;

    public DotPlotFilter() {
	DotplotContext context = ContextFactory.getContext();
	this.endings = context.getTypeBindingRegistry().getAll().keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
     * .Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
	if (element instanceof File) {
	    String fileending;
	    File file = (File) element;
	    String filename = file.getName().toLowerCase();
	    int index = filename.lastIndexOf(".");
	    if (index > -1) {
		fileending = filename.substring(index);
	    } else {
		fileending = "";
	    }

	    return (file.isDirectory() || this.endings.contains(fileending));

	}

	return false;
    }
}
