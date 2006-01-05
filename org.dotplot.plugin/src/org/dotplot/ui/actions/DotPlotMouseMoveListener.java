/*
 * Created on 28.06.2004
 */
package org.dotplot.ui.actions;

import java.awt.Dimension;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.ui.IWorkbenchWindow;

import org.dotplot.DotplotCreator;
import org.dotplot.image.IROIResult;
import org.dotplot.ui.views.DotPlotter;

/**
 * <code>DotPlotMouseMoveListener</code> provides information
 * for data lying under the cursor.
 *
 * @author Tobias Gesellchen
 * @see org.eclipse.swt.events.MouseMoveListener
 */
class DotPlotMouseMoveListener implements MouseMoveListener
{
   private DotplotCreator dp;
   private ImageData imageData;
   private DotPlotter plotterView;
   private float scale;

   /**
    * Constructs a DotPlotMouseMoveListener object.
    *
    * @param dp      the dotplotter-controller
    * @param plotter dotplot's plotterview
    */
   public DotPlotMouseMoveListener(
         IWorkbenchWindow parent, DotplotCreator dp, ImageData imgData, DotPlotter plotter, float scale)
   {
      this.dp = dp;
      this.imageData = imgData;
      this.plotterView = plotter;
      this.scale = scale;
   }

   public void mouseMove(MouseEvent event)
   {
      final Canvas shell = plotterView.getCanvas();
      final Point currentShift = DotPlotter.getCurrentShift();
      final java.awt.Point location = new java.awt.Point(event.x + currentShift.x, event.y + currentShift.y);
      final IROIResult res = dp.getDetailsForLoc(location, new Dimension(imageData.width, imageData.height), scale);

      if (res != null)
      {
         final String tooltip =
               res.getXFile() + "[" + (res.getXLineIndex() + 1) + "]" + ": " + res.getXToken() + "\n" +
               res.getYFile() + "[" + (res.getYLineIndex() + 1) + "]" + ": " + res.getYToken();

//         ToolTip tip = new ToolTip(plotterView.getCanvas(), tooltip);
         shell.setToolTipText(tooltip);
      }
      else
      {
         shell.setToolTipText(null);
      }
   }
}
