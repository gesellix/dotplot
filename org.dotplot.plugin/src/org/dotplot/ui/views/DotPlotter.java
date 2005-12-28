package org.dotplot.ui.views;

import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.part.ViewPart;

import org.dotplot.plugin.DotplotPlugin;

/**
 * <code>DotPlotter</code> shows the DotPlot.
 *
 * @author Sascha Hemminger & Roland Helmrich
 * @see ViewPart
 */
public class DotPlotter extends ViewPart
{
   private Composite parent;
   private Canvas canvas;
   private MouseListener mouseListener;
   private MouseMoveListener mouseMoveListener;
   private static Image currentImage;
   private static Point currentShift = new Point(0, 0);

   /**
    * creates the control.
    *
    * @param parent the parent
    *
    * @see ViewPart#createPartControl
    */
   public void createPartControl(Composite parent)
   {
      this.parent = parent;
      canvas = new Canvas(parent, SWT.SHELL_TRIM
//            | SWT.NO_BACKGROUND
            | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL
//            | SWT.NONE
      );

      showSplashDelayed(1000);
   }

   private void showSplashDelayed(final int delay)
   {
      new Thread()
      {
         public void run()
         {
            try
            {
               Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            showImage(null);
         }
      }.start();
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
    * Shows the given image <code>data</code> on the internal Canvas.
    * It internally uses some sort of thread-synchronization to avoid errors.
    *
    * @param data the image to be shown, or null to clear the display
    *
    * @see org.eclipse.swt.widgets.Display#syncExec(Runnable)
    */
   public void showImage(final ImageData data)
   {
      if (parent == null)
      {
         return;
      }
      Display display = parent.getDisplay();

      // as this method could be called from other threads,
      // use the built-in synchronization
      display.syncExec(new Runnable()
      {
         public void run()
         {
            handleImageDisplay(data);
         }
      });
   }

   /**
    * Shows the given image <code>data</code> on the internal Canvas.
    * You should prefer <code>showImage(org.eclipse.swt.graphics.ImageData)</code>
    * to avoid thread-synchronisation errors.
    *
    * @param data the image to be shown, or null to clear the display
    *
    * @see #showImage(org.eclipse.swt.graphics.ImageData)
    */
   public void handleImageDisplay(final ImageData data)
   {
      final Display display = parent.getDisplay();
      final Canvas shell = canvas;

      final ScrollBar hBar = shell.getHorizontalBar();
      final ScrollBar vBar = shell.getVerticalBar();

      final Point origin = new Point(0, 0);

      if (currentImage != null)
      {
         currentImage.dispose();
         currentImage = null;
      }

      if (data != null)
      {
         currentImage = new Image(display, data);
      }

      // show "empty image"?
      if (currentImage == null)
      {
//         int width = getSize().width;
//         int height = getSize().height;
//         currentImage = new Image(display, width, height);
         try
         {
            final ImageData splashdata = new ImageData(
                  DotplotPlugin.getResource("icons/dp_splash_400x360_v0_2.jpg").getFile());

            currentImage = new Image(display, splashdata);

            GC gc = new GC(currentImage);

            final FontData fontData = gc.getFont().getFontData()[0];

//         gc.fillRectangle(0, 0, splashdata.width, splashdata.height);
//         gc.setForeground(new Color(display, 255, 0, 0));
//         gc.drawLine(0, 0, splashdata.width, splashdata.height);
//         gc.drawLine(0, splashdata.height, splashdata.width, 0);
            gc.setForeground(new Color(display, 0, 0, 0));

            gc.setFont(new Font(shell.getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD)));
            gc.drawText(DotplotPlugin.getVersionInfo(), 10, 10, false);

//         gc.drawText("No image", 10, 10);

            gc.dispose();
         }
         catch (MalformedURLException e)
         {
            e.printStackTrace();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }

      hBar.addListener(SWT.Selection, new Listener()
      {
         public void handleEvent(Event e)
         {
            onHorizontalBarSelected(hBar, origin, shell);
         }
      });

      vBar.addListener(SWT.Selection, new Listener()
      {
         public void handleEvent(Event e)
         {
            onVerticalBarSelected(vBar, origin, shell);
         }
      });

      shell.addListener(SWT.Resize, new Listener()
      {
         public void handleEvent(Event e)
         {
            onShellResized(shell, hBar, vBar, origin);
         }
      });

      shell.addListener(SWT.Paint, new Listener()
      {
         public void handleEvent(Event e)
         {
            onShellPainted(e, origin, shell);
         }
      });

      // force recalculation of scrollbar lengths
      // and force shell.redraw()
      onShellResized(shell, hBar, vBar, origin);

      while (!shell.isDisposed() && !display.readAndDispatch())
      {
         shell.redraw();
         display.sleep();
      }
   }

   private static void onHorizontalBarSelected(final ScrollBar hBar, final Point origin, final Canvas shell)
   {
      int hSelection = hBar.getSelection();
      int destX = -hSelection - origin.x;
      Rectangle rect = currentImage.getBounds();
      shell.scroll(destX, 0, 0, 0, rect.width, rect.height, false);
      origin.x = -hSelection;
      shell.redraw();

      setCurrentShift(new Point(hSelection, getCurrentShift().y));
   }

   private static void onVerticalBarSelected(final ScrollBar vBar, final Point origin, final Canvas shell)
   {
      int vSelection = vBar.getSelection();
      int destY = -vSelection - origin.y;
      Rectangle rect = currentImage.getBounds();
      shell.scroll(0, destY, 0, 0, rect.width, rect.height, false);
      origin.y = -vSelection;
      shell.redraw();

      setCurrentShift(new Point(getCurrentShift().x, vSelection));
   }

   private static void onShellPainted(Event e, final Point origin, final Canvas shell)
   {
      GC gc = e.gc;
      gc.drawImage(currentImage, origin.x, origin.y);

      Rectangle rect = currentImage.getBounds();
      Rectangle client = shell.getClientArea();

      int marginWidth = client.width - rect.width;
      int marginHeight = client.height - rect.height;

      if (marginWidth > 0)
      {
         gc.fillRectangle(rect.width, 0, marginWidth, client.height);
      }
      if (marginHeight > 0)
      {
         gc.fillRectangle(0, rect.height, client.width, marginHeight);
      }
   }

   private static void onShellResized(
         final Canvas shell, final ScrollBar hBar, final ScrollBar vBar, final Point origin)
   {
      Rectangle rect = currentImage.getBounds();
      Rectangle client = shell.getClientArea();
      hBar.setMaximum(rect.width);
      vBar.setMaximum(rect.height);
      hBar.setThumb(Math.min(rect.width, client.width));
      vBar.setThumb(Math.min(rect.height, client.height));
      int hPage = rect.width - client.width;
      int vPage = rect.height - client.height;
      int hSelection = hBar.getSelection();
      int vSelection = vBar.getSelection();
      if (hSelection >= hPage)
      {
         if (hPage <= 0) hSelection = 0;
         origin.x = -hSelection;
      }
      if (vSelection >= vPage)
      {
         if (vPage <= 0) vSelection = 0;
         origin.y = -vSelection;
      }
      shell.redraw();
   }

   /**
    * sets the current shift, done by use of the scrollbars.
    *
    * @param shift amount of the shift
    *
    * @see #getCurrentShift()
    */
   public static void setCurrentShift(Point shift)
   {
      currentShift = shift;
   }

   /**
    * returns the amount of the current shift, done by use of the scrollbars.
    *
    * @return the shift
    *
    * @see #setCurrentShift(org.eclipse.swt.graphics.Point)
    */
   public static Point getCurrentShift()
   {
      return currentShift;
   }

   /**
    * gives the initial size of the DotPlotter view.
    *
    * @return the initial size of the window.
    */
   public Dimension getSize()
   {
      // avoid painting "behind" the scrollbars
      final int border = 25;
      return new Dimension(this.canvas.getSize().x - border, this.canvas.getSize().y - border);
   }

   /**
    * use this one if you want to provide regions of interest,
    * sets the canvas' MouseListener.
    *
    * @param m Listener for the canvas
    */
   public void setMouseListener(MouseListener m)
   {
      if (this.mouseListener == null)
      {
         this.canvas.addMouseListener(m);
         this.mouseListener = m;
      }
   }

   public void removeMouseMoveListener()
   {
      canvas.getDisplay().syncExec(new Runnable()
      {
         public void run()
         {
            if (mouseMoveListener != null)
            {
               canvas.removeMouseMoveListener(mouseMoveListener);
               mouseMoveListener = null;
            }
         }
      });
   }

   /**
    * use this one if you want to provide regions of interest for mouse moves,
    * sets the canvas' MouseMoveListener.
    *
    * @param m Listener for the canvas
    */
   public void setMouseMoveListener(final MouseMoveListener m)
   {
      canvas.getDisplay().syncExec(new Runnable()
      {
         public void run()
         {
            if (mouseMoveListener == null)
            {
               canvas.addMouseMoveListener(m);
               mouseMoveListener = m;
            }
            else
            {
               canvas.removeMouseMoveListener(mouseMoveListener);
               canvas.addMouseMoveListener(m);
               mouseMoveListener = m;
            }
         }
      });
   }

   /**
    * returns the internal canvas, the plot will be painted to.
    *
    * @return the canvas
    */
   public Canvas getCanvas()
   {
      return canvas;
   }

   /**
    * gives the parent composite.
    *
    * @return parent composite
    */
   public Composite getParent()
   {
      return parent;
   }
}
