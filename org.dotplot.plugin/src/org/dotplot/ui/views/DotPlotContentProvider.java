package org.dotplot.ui.views;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <code>DotPlotContentProvider</code> is a simple file content provider.
 *
 * @author Sascha Hemminger
 */
class DotPlotContentProvider implements ITreeContentProvider, IPropertyChangeListener
{
   private static final Object[] EMPTY = new Object[0];
   private FileFilter fileFilter;
   private CheckboxTreeViewer viewer;

   /**
    * Creates a new instance of the receiver.
    *
    * @param showFiles <code>true</code> files and folders are returned
    *                  by the receiver. <code>false</code> only folders are returned.
    */
   public DotPlotContentProvider(final boolean showFiles)
   {
      fileFilter = new FileFilter()
      {
         public boolean accept(File file)
         {
            return (file.isFile() || file.isDirectory() && showFiles);
         }
      };
   }

   /**
    * Searches all children of an element.
    *
    * @param parentElement the 'father' element
    *
    * @return an array of child-objects
    */
   public Object[] getChildren(Object parentElement)
   {
      if (parentElement instanceof File)
      {
         File[] children = ((File) parentElement).listFiles(fileFilter);
         if (children != null)
         {
            Arrays.sort(children);
            return children;
         }
      }

      return EMPTY;
   }

   public Object getParent(Object element)
   {
      if (element instanceof File)
      {
         return ((File) element).getParentFile();
      }
      else
      {
         return null;
      }
   }

   public boolean hasChildren(Object element)
   {
      return getChildren(element).length > 0;
   }

   /**
    * searches all elements related to to the given element.
    *
    * @param element the ancestor-element
    *
    * @return an array of all related elements
    */
   public Object[] getElements(Object element)
   {
      return getChildren(element);
   }

   public void dispose()
   {
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (CheckboxTreeViewer) viewer;
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
    */
   public void propertyChange(PropertyChangeEvent event)
   {
      getViewer().refresh();
   }

   /**
    * Use this to access the views basic viewer.
    *
    * @return the basic viewer
    *
    * @see #setViewer(CheckboxTreeViewer)
    */
   public CheckboxTreeViewer getViewer()
   {
      return viewer;
   }

   /**
    * sets this views basic viewer to a new one.
    *
    * @param viewer the new viewer
    *
    * @see #getViewer()
    */
   public void setViewer(CheckboxTreeViewer viewer)
   {
      this.viewer = viewer;
   }
}
