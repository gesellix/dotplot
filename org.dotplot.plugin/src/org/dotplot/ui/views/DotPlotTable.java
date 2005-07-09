/*
 * Created on 31.05.2004
 */
package org.dotplot.ui.views;

import java.io.File;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * a special table that shows the selected input files to plot.
 *
 * @author Roland Helmrich
 */
public class DotPlotTable extends TableViewer
{
   private DotPlotFileList fileList = new DotPlotFileList();
   private final Table table = getTable();

   private DragSource dragSource;
   private DropTarget dropTarget;

   /**
    * Constructs a DotPlotTable object.
    *
    * @param parent the Composite that shows the table (e.g. a window)
    * @param style  SWT Style
    */
   public DotPlotTable(Composite parent, int style)
   {
      super(parent, style);

      DotPlotTableContentProvider contentProvider = new DotPlotTableContentProvider();
      DotPlotTableLabelProvider labelProvider = new DotPlotTableLabelProvider();

      setContentProvider(contentProvider);
      setLabelProvider(labelProvider);

      TableColumn columnNames = new TableColumn(table, SWT.LEFT);
      columnNames.setText("Filename");

      TableColumn columnSizes = new TableColumn(table, SWT.RIGHT);
      columnSizes.setText("Size");

      columnNames.setWidth(500);
      columnSizes.setWidth(100);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      //-------------------------------------------------
      //Dragsource
      dragSource = new DragSource(table, DND.DROP_MOVE);
      dragSource.setTransfer(new Transfer[]{FileTransfer.getInstance()});

      dragSource.addDragListener(new DragSourceAdapter()
      {
         public void dragSetData(DragSourceEvent event)
         {
            //Examine whether the action one supports
            if (FileTransfer.getInstance().isSupportedType(event.dataType))
            {
               TableItem[] items = table.getSelection();
               String[] data = new String[items.length];

               for (int i = 0; i < items.length; i++)
               {
                  data[i] = ((File) items[i].getData()).getAbsolutePath();
               }
               event.data = data;
            }
         }

         public void dragFinished(DragSourceEvent event)
         {
            if (event.detail == DND.DROP_MOVE)
            {
               table.remove(table.getSelectionIndex());
            }
         }
      });

      //-------------------------------------------------
      //Droptarget
      dropTarget = new DropTarget(table, DND.DROP_MOVE);
      dropTarget.setTransfer(new Transfer[]{FileTransfer.getInstance()});

      dropTarget.addDropListener(new DropTargetAdapter()
      {
         //this event occurs when the user releases the mouse over the drop target
         public void drop(DropTargetEvent event)
         {
            int index = 0;

            if (event.data == null)
            {
               event.detail = DND.DROP_NONE;
               return;
            }

            String[] saFiles = (String[]) event.data;

            for (int i = 0; i < saFiles.length; ++i)
            {
               File f = new File(saFiles[i]);

               if (!f.exists())
               {
                  continue;
               }

               TableItem item = (TableItem) event.item;
               index = table.indexOf(item);
               insertItem(f, index);
               table.update();
            }
         }
      });
   }

   /**
    * inserts a file into the table.
    *
    * @param f   file to insert
    * @param idx position where to insert the file
    */
   public void insertItem(File f, int idx)
   {
      super.insert(f, idx);
   }

   /**
    * gives a plottable filelist.
    *
    * @return the list of plottable files
    */
   public DotPlotFileList getFileList()
   {
      return this.fileList;
   }
}
