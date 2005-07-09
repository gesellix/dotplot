/*
 * Created on 31.05.2004
 */
package org.dotplot.ui.views;

import java.io.File;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * a simple table label provider.
 *
 * @author Roland Helmrich
 */
class DotPlotTableLabelProvider implements ITableLabelProvider
{
   public DotPlotTableLabelProvider()
   {
      super();
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
    */
   public Image getColumnImage(Object arg0, int arg1)
   {
      return null;
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
    */
   public String getColumnText(Object element, int index)
   {
      if (element instanceof File)
      {
         if (index == 0)
         {
            //return ((File) element).getName();
            return ((File) element).getPath();
         }
         else if (index == 1)
         {
            return Long.toString(((File) element).length());
         }
      }
      //return super.getText(element);
      return "";
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
    */
   public void addListener(ILabelProviderListener arg0)
   {
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
    */
   public void dispose()
   {
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
    */
   public boolean isLabelProperty(Object arg0, String arg1)
   {
      return false;
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
    */
   public void removeListener(ILabelProviderListener arg0)
   {
   }
}
