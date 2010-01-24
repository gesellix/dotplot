/*
 * Created on 28.06.2004
 */
package org.dotplot.eclipse.actions;

import java.awt.Dimension;

import org.dotplot.core.ContextFactory;
import org.dotplot.eclipse.views.DotPlotter;
import org.dotplot.image.IROIResult;
import org.dotplot.image.Util;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * <code>DotPlotMouseMoveListener</code> provides information for data lying
 * under the cursor.
 * 
 * @author Tobias Gesellchen
 * @see org.eclipse.swt.events.MouseMoveListener
 */
class DotPlotMouseMoveListener implements MouseMoveListener {
	private ImageData imageData;

	private DotPlotter plotterView;

	private float scale;

	/**
	 * Constructs a DotPlotMouseMoveListener object.
	 * 
	 * @param dp
	 *            the dotplotter-controller
	 * @param plotter
	 *            dotplot's plotterview
	 */
	public DotPlotMouseMoveListener(IWorkbenchWindow parent, ImageData imgData,
			DotPlotter plotter, float scale) {
		this.imageData = imgData;
		this.plotterView = plotter;
		this.scale = scale;
	}

	public void mouseMove(MouseEvent event) {
		final Canvas shell = plotterView.getCanvas();
		final Point currentShift = DotPlotter.getCurrentShift();
		final java.awt.Point location = new java.awt.Point(event.x
				+ currentShift.x, event.y + currentShift.y);
		IROIResult roiResult;
		try {
			roiResult = Util.getDetailsForLoc(location, new Dimension(
					imageData.width, imageData.height), scale, ContextFactory
					.getContext().getCurrentTypeTable().createNavigator());
		}
		catch (Exception e) {
			roiResult = null;
		}
		final IROIResult res = roiResult;

		if (res != null) {
			final String tooltip = res.getXFile() + " (" + res.getXLineIndex()
					+ ")" + "\n" + res.getYFile() + " (" + res.getYLineIndex()
					+ ")";

			// ToolTip tip = new ToolTip(plotterView.getCanvas(), tooltip);
			shell.setToolTipText(tooltip);
		}
	}
}
