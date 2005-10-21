package org.dotplot.ui.views;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * <code>DotPlotLister</code> is a view that provides a list of all files to plot.
 *
 * @author Sascha Hemminger & Roland Helmrich
 * @see ViewPart
 */
public class DotPlotLister extends ViewPart
{
   private DotPlotTable fileTable;

   /**
    * Default constructor for the lister.
    */
   public DotPlotLister()
   {
   }

   /**
    * creates the table.
    *
    * @param parent the parent composite
    *
    * @see ViewPart#createPartControl
    */
   public void createPartControl(Composite parent)
   {
      this.fileTable = new DotPlotTable(parent, SWT.LEFT | SWT.MULTI | SWT.VERTICAL | SWT.H_SCROLL | SWT.V_SCROLL);
   }

   /**
    * get access to the shown table
    *
    * @return the table
    */
   protected DotPlotTable getTable()
   {
      return this.fileTable;
   }

   /**
    * empty implementation.
    *
    * @see ViewPart#setFocus
    */
   public void setFocus()
   {
   }

   /**
    * reads the checked files and shows them in the list.
    *
    * @param selected array of input files
    */
   public void setInputFiles(Object[] selected)
   {
      //get the old filelist
      DotPlotFileList fileList = this.fileTable.getFileList();

      //remove the elements of old filelist from the table
      for (int i = 0; i < fileList.size(); i++)
      {
         this.fileTable.remove(fileList.elementAt(i));
      }

      //remove the elements of the filelist
      fileList.removeAllElements();

      int i;
      long completeLength = 0;
      //insert new elements in the filelist and in the table
      for (i = 0; i < selected.length; i++)
      {
         fileList.addElement(selected[i]);
         this.fileTable.insert(selected[i], i);

         if (selected[i] instanceof File)
         {
            completeLength += ((File) selected[i]).length();
         }
      }

      Object sumObj = fileTable.getElementAt(i);
      if (sumObj != null && sumObj instanceof SumFile)
      {
         // remove, it will be inserted again with the new "completeLength"
         fileTable.remove(sumObj);
      }

      // add "sum:" row, only if it makes sense
      if (i > 0)
      {
         this.fileTable.insert(new SumFile(completeLength), 0);
      }
   }

   /**
    * Dummy File to let the row "sum:" appear in the list of files.
    */
   private class SumFile extends File
   {
      /**
       * for being Serializable
       */
      private static final long serialVersionUID = -6910462730903187069L;
      long length;

      public SumFile(long length)
      {
         super("");
         this.length = length;
      }

      public String getPath()
      {
         return "sum (bytes):";
      }

      public long length()
      {
         return length;
      }

      public void setLength(long len)
      {
         length = len;
      }
   }
}
