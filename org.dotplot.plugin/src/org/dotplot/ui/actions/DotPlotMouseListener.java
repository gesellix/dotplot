/*
 * Created on 28.06.2004
 */
package org.dotplot.ui.actions;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

import org.dotplot.DotplotCreator;
import org.dotplot.image.IROIResult;
import org.dotplot.ui.actions.contextMenu.ConfigureAction;
import org.dotplot.ui.actions.contextMenu.ExportAction;
import org.dotplot.ui.actions.contextMenu.SavePlotJobAction;
import org.dotplot.ui.views.MergeView;

/**
 * <code>DotPlotMouseListener</code> provides dragging a rectangle as
 * region of interest and activates it in the controller.
 *
 * @author Sascha Hemminger
 * @see org.eclipse.swt.events.MouseAdapter
 */
class DotPlotMouseListener extends MouseAdapter
{
   private IWorkbenchWindow parent;
   private DotplotCreator dp;
   private Point start, end;
   private MergeView merger;

   /**
    * Constructs a DotPlotMouseListener object.
    *
    * @param dp     the dotplotter-controller
    * @param merger dotplot's mergeview
    */
   public DotPlotMouseListener(IWorkbenchWindow parent, DotplotCreator dp, MergeView merger)
   {
      this.parent = parent;
      this.dp = dp;
      this.merger = merger;
   }

   /* (non-Javadoc)
    * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
    */
   public void mouseDown(MouseEvent event)
   {
      if (event.button == 1)
      { // left button
         this.start = new Point(event.x, event.y);
      }

      if (event.button == 3)
      { // right button
         Shell shell = parent.getShell();
         Menu popup = createMenuManager().createContextMenu(shell);

         shell.setMenu(popup);
         popup.setVisible(true);
      }
   }

   private MenuManager createMenuManager()
   {
      MenuManager menu = new MenuManager("");
      MenuManager menuExport = new MenuManager("Export");

      menu.add(new ConfigureAction("Configure...", parent));
      menu.add(menuExport);
      menuExport.add(new ExportAction("Export to file", dp, parent));
      menuExport.add(new SavePlotJobAction("Save plot as job", dp, parent));

      return menu;
   }

   /* (non-Javadoc)
    * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
    */
   public void mouseUp(MouseEvent event)
   {
      if (event.button == 1)
      {
         this.end = new Point(event.x, event.y);

         if (this.start != null)
         {
            Rectangle choice = getChoice();
            IROIResult res = dp.getDetailsForROI(choice, null);
            if (res != null)
            {
               merger.setText(res.getXFile(),
                     res.getXLineIndex(),
                     res.getYFile(),
                     res.getYLineIndex(),
                     choice);
               //TODO rectangle is experimental
               merger.getViewSite().getWorkbenchWindow().getActivePage().bringToTop(merger);
            }
         }
      }
   }

   // calculates the rectangle from two points
   private Rectangle getChoice()
   {
      final int minDimension = 5;

      Point[] result = new Point[2];

      if (this.start != null && this.end != null)
      {
         result[0] = new Point(Math.min(start.x, end.x), (Math.min(start.y, end.y)));
         result[1] = new Point(Math.max(start.x, end.x), (Math.max(start.y, end.y)));
      }

      java.awt.Point origin = new java.awt.Point(
            Math.max(0, result[0].x),
            Math.max(0, result[0].y));
      Dimension dimension = new Dimension(
            Math.max(minDimension, (result[1].x - result[0].x)),
            Math.max(minDimension, (result[1].y - result[0].y)));

      return new Rectangle(origin, dimension);
   }
}
