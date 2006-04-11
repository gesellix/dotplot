/*
 * Created on 31.05.2004
 */
package org.dotplot.eclipse.views;

import java.io.File;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * a simple table label provider.
 *
 * @author Roland Helmrich
 */
class DotPlotTableLabelProvider implements ITableLabelProvider {
   public DotPlotTableLabelProvider() {
      super();
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
    */
   public Image getColumnImage(Object arg0, int arg1) {
      return null;
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
    */
   public String getColumnText(Object element, int index) {
      if (element instanceof File) {
         if (index == 0) {
            //return ((File) element).getName();
            return ((File) element).getPath();
         }
         else if (index == 1) {
//            return Long.toString(((File) element).length());

            if (((File) element).isDirectory()) {
               // directories don't have a size (sum up the containing file sizes?)
               return "";
            }
            else {
               return getHumanReadableSize(((File) element).length());
            }
         }
      }
      //return super.getText(element);
      return "";
   }

   /**
    * Creates a human readable String for the given size
    *
    * @param size size in bytes
    *
    * @return the file size including measure
    */
   private static String getHumanReadableSize(long size) {
      //      return Long.toString(size);
      final String[] fileSizeNames = new String[]{" Bytes", " KB", " MB", " GB", " TB", " PB", " EB", " ZB", " YB"};

      final int index = (int) Math.floor(Math.log(size) / Math.log(1024));

      if (size == 0) {
         return size + fileSizeNames[0];
      }
      else {
         return ((double) Math.round((size / Math.pow(1024, index)) * 100)) / 100 + fileSizeNames[index];
      }
   }

   /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
   */
   public void addListener(ILabelProviderListener arg0) {
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
    */
   public void dispose() {
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
    */
   public boolean isLabelProperty(Object arg0, String arg1) {
      return false;
   }

   /* (non-Javadoc)
    * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
    */
   public void removeListener(ILabelProviderListener arg0) {
   }
}
