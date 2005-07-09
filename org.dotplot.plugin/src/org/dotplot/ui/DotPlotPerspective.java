package org.dotplot.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * <code>DotPlotPerspective</code> is the perspective that provides all Dotplot-related views and actions.
 *
 * @author Sascha Hemminger
 * @see IPerspectiveFactory
 */
public class DotPlotPerspective implements IPerspectiveFactory
{
   /**
    * <code>DOTPLOTTER</code> Name that references the DotPlotter view.
    */
   public static final String DOTPLOTTER = new String("org.dotplot.plugin.view1");

   /**
    * <code>DOTPLOTNAV</code> Name that references the DotPlot Navigator view.
    */
   public static final String DOTPLOTNAV = new String("org.dotplot.plugin.view2");

   /**
    * <code>DOTPLOTLIST</code> Name that references the DotPlotLister view.
    */
   public static final String DOTPLOTLIST = new String("org.dotplot.plugin.view3");

   /**
    * <code>DOTPLOTDIFF</code> Name that references the DotPlot Merge view.
    */
   public static final String DOTPLOTDIFF = new String("org.dotplot.merger");

   /**
    * create the initial layout.
    *
    * @param layout the layout
    *
    * @see IPerspectiveFactory#createInitialLayout
    */
   public void createInitialLayout(IPageLayout layout)
   {
      layout.setEditorAreaVisible(false);

      String editorArea = layout.getEditorArea();
      IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.25f, editorArea);

      layout.addView(DOTPLOTTER, IPageLayout.BOTTOM, 0.25f, editorArea);
      left.addView(DOTPLOTNAV);
      layout.addView(DOTPLOTLIST, IPageLayout.RIGHT, 0.25f, editorArea);
   }
}
