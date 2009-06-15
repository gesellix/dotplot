/*
 * Created on 06.06.2004
 */
package org.dotplot.eclipse.views;

import java.io.File;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * a simple contentprovider.
 * 
 * @author Roland Helmrich
 */
class DotPlotTableContentProvider implements IStructuredContentProvider {
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * gives the elements related to the root.
     * 
     * @param rootElement
     *            the root object
     * 
     * @return an array of child-objects
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object rootElement) {
	Object[] kids = null;
	kids = ((File) rootElement).listFiles();
	return kids == null ? new Object[0] : kids;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
     * .viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    }
}
