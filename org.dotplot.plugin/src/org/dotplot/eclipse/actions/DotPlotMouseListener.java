/*
 * Created on 28.06.2004
 */
package org.dotplot.eclipse.actions;

import java.awt.Rectangle;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.IDotplot;
import org.dotplot.eclipse.actions.contextMenu.ConfigureAction;
import org.dotplot.eclipse.actions.contextMenu.ExportAction;
import org.dotplot.eclipse.actions.contextMenu.SavePlotJobAction;
import org.dotplot.eclipse.views.MergeView;
import org.dotplot.image.IROIResult;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * <code>DotPlotMouseListener</code> provides dragging a rectangle as region of
 * interest and activates it in the controller.
 * 
 * @author Sascha Hemminger
 * @see org.eclipse.swt.events.MouseAdapter
 */
class DotPlotMouseListener extends MouseAdapter {
	private IWorkbenchWindow parent;

	private Point start, end;

	private MergeView merger;

	/**
	 * Constructs a DotPlotMouseListener object.
	 * 
	 * @param dp
	 *            the dotplotter-controller
	 * @param merger
	 *            dotplot's mergeview
	 */
	public DotPlotMouseListener(IWorkbenchWindow parent, MergeView merger) {
		this.parent = parent;
		this.merger = merger;
	}

	private MenuManager createMenuManager() {
		MenuManager menu = new MenuManager("");
		MenuManager menuExport = new MenuManager("Export");

		menu.add(new ConfigureAction("Configure...", parent));
		menu.add(menuExport);
		menuExport.add(new ExportAction("Export to file", parent));
		menuExport.add(new SavePlotJobAction("Save plot as job", parent));

		return menu;
	}

	// calculates the rectangle from two points
	private Rectangle getChoice() {
		Point[] result = new Point[2];

		if (this.start != null && this.end != null) {
			result[0] = new Point(Math.min(start.x, end.x), (Math.min(start.y,
					end.y)));
			result[1] = new Point(Math.max(start.x, end.x), (Math.max(start.y,
					end.y)));
		}

		return new Rectangle(Math.max(0, result[0].x),
				Math.max(0, result[0].y), (result[1].x - result[0].x),
				(result[1].y - result[0].y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events
	 * .MouseEvent)
	 */
	@Override
	public void mouseDown(MouseEvent event) {
		if (event.button == 1) { // left button
			this.start = new Point(event.x, event.y);
		}

		if (event.button == 3) { // right button
			Shell shell = parent.getShell();
			Menu popup = createMenuManager().createContextMenu(shell);

			shell.setMenu(popup);
			popup.setVisible(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.
	 * MouseEvent)
	 */
	@Override
	public void mouseUp(MouseEvent event) {
		if (event.button == 1) {
			this.end = new Point(event.x, event.y);

			if (this.start != null) {
				IDotplot dotplot = ContextFactory.getContext()
						.getCurrentDotplot();

				if (dotplot != null) {
					IROIResult res = dotplot.getDetailsForROI(this.getChoice(),
							null);
					if (res != null) {
						merger.setText(res.getXFile(), res.getXLineIndex(), res
								.getYFile(), res.getYLineIndex(), this
								.getChoice());
						// TODO rectangle is experimental
						merger.getViewSite().getWorkbenchWindow()
								.getActivePage().bringToTop(merger);
					}
				}
			}
		}
	}
}
