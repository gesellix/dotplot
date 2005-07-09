/*
 * Created on 03.06.2004
 */
package org.dotplot.ui.views;

import java.io.File;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <code>DotPlotFilter</code> provides a file filter for the DotPlotNavigator.
 *
 * @author Sascha Hemminger
 * @see org.dotplot.ui.views.DotPlotNavigator
 */
class DotPlotFilter extends ViewerFilter
{
   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
    */
   public boolean select(Viewer viewer, Object parentElement, Object element)
   {
      if (element instanceof File)
      {
         File file = (File) element;
         String filename = file.getName().toLowerCase();

         if (file.isDirectory()
               || filename.endsWith(".txt")
               || filename.endsWith(".xml")
               || filename.endsWith(".htm")
               || filename.endsWith(".html")
               || filename.endsWith(".css")
               || filename.endsWith(".js")
               || filename.endsWith(".java")
               || filename.endsWith(".cpp")
               || filename.endsWith(".c")
               || filename.endsWith(".cc")
               || filename.endsWith(".cs")
               || filename.endsWith(".h")
               || filename.endsWith(".hpp")
               || filename.endsWith(".php")
               || filename.endsWith(".py")
               || filename.endsWith(".ini")
               || filename.endsWith(".cfg")
               || filename.endsWith(".conf")
               || filename.endsWith(".properties")
               || filename.endsWith(".csv")
               || filename.endsWith(".doc")
               || filename.endsWith(".rtf")
               || filename.endsWith(".pdf")
               || filename.endsWith(".tex")
               || filename.endsWith(".log"))
         {
            return true;
         }
      }

      return false;
   }
}
