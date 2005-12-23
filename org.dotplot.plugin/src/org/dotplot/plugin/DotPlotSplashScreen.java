/*
 * Created on 01.07.2004
 * @author gg
 * @version 0.1
 */
package org.dotplot.plugin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.dotplot.ui.DotPlotPerspective;
import org.dotplot.ui.views.DotPlotNavigator;

/**
 * First try to implement a nice Splashscreen.
 *
 * @author Tobias Gesellchen
 */
public class DotPlotSplashScreen
{
//   java.awt.Image image;
//   java.awt.Image back;

   private final static int SHOWTIME = 2500;

   /**
    * only for testing.
    *
    * @param args command line arguments
    */
   public static void main(String[] args)
   {
      try
      {
         new DotPlotSplashScreen(DotplotPlugin.getResource("icons/dp_splash_400x360_v0_2.jpg"));
      }
      catch (IOException e)
      {
         // ignore
         //e.printStackTrace();
      }
   }

   /**
    * should construct a splashscreen some day...
    *
    * @param splashURL image URL
    */
   public DotPlotSplashScreen(final URL splashURL)
   {
      //Aufruf:
/*
      try
      {
         final URL imageURL = new URL(descriptor.getInstallURL().toString() + "icons/dp_splash_400x360_v0_2.jpg");
         new DotPlotSplashScreen(Platform.asLocalURL(imageURL));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
*/

//      new Thread()
//      {
//         public void run()
//         {
      Platform.endSplash();
//            initSplashAWT(splashURL);
      initSplashSWT(splashURL);
//         }
//      }.start();
   }

   private void initSplashSWT(URL splashURL)
   {
      new SplashShell(splashURL);
   }

   private class SplashShell
   {
      public SplashShell(final URL splashImage)
      {
         final Display display = (Display.getCurrent() == null) ? Display.getDefault() : Display.getCurrent();
         display.asyncExec(new Runnable()
         {
            public void run()
            {
               initAndShowSplash(display, splashImage);
            }
         });
      }

      private void initAndShowSplash(final Display display, final URL splashImage)
      {
         final Shell shell = new Shell(display, SWT.MODELESS);
         final Image image = new Image(shell.getDisplay(), splashImage.getFile());

         shell.addListener(SWT.Paint, new Listener()
         {
            public void handleEvent(Event e)
            {
               onShellPainted(e, image, new Point(0, 0), shell);
            }
         });

         final Rectangle bounds = image.getBounds();

         final int w = bounds.width;
         final int h = bounds.height;

         Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
         shell.setBounds((d.width - w) / 2, (d.height - h) / 3, w, h);
         shell.open();
         shell.redraw();

         try
         {
            Thread.sleep(SHOWTIME);
         }
         catch (InterruptedException e)
         {
            // ignore
            //e.printStackTrace();
         }

         shell.close();
         workbenchToFront();
      }

      private void onShellPainted(Event e, final Image image, final Point origin, final Canvas shell)
      {
         final GC gc = e.gc;

         final Rectangle rect = image.getBounds();
         final Rectangle client = shell.getClientArea();

         final int w_r = rect.width;
         final int h_r = rect.height;
         final int w_c = client.width;
         final int h_c = client.height;

         final int x = Math.max(5, Math.min(w_r, w_r - 100));
         final int y = Math.max(5, Math.min(h_r, h_r - 90));

         final int marginWidth = w_c - w_r;
         final int marginHeight = h_c - h_r;

         final FontData fontData = gc.getFont().getFontData()[0];

         gc.drawImage(image, origin.x, origin.y);

         gc.setFont(new Font(shell.getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD)));
         gc.drawText(DotplotPlugin.getVersionInfo(), x, y, true);

         if (marginWidth > 0)
         {
            gc.fillRectangle(w_r, 0, marginWidth, h_c);
         }
         if (marginHeight > 0)
         {
            gc.fillRectangle(0, h_r, w_c, marginHeight);
         }
      }
   }

//   private void initSplashAWT(URL splashURL)
//   {
//      final SplashFrame splash = new SplashFrame(splashURL);
//      splash.setUndecorated(true);
//
//      splash.pack();
//      splash.show();
//      splash.toFront();
//
//      try
//      {
//         Thread.sleep(SHOWTIME);
//      }
//      catch (InterruptedException e)
//      {
//         // ignore
//         //e.printStackTrace();
//      }
//
//      splash.hide();
//      splash.dispose();
//
//      workbenchToFront();
//   }

//   private class SplashFrame extends Frame
//   {
//      public SplashFrame(URL splashURL)
//      {
//         image = Toolkit.getDefaultToolkit().getImage(splashURL);
//
//         this.addWindowListener(new WindowAdapter()
//         {
//            public void windowClosing(WindowEvent e)
//            {
//               hide();
//               dispose();
//            }
//         });
//
//         setLayout(new BorderLayout());
//         add(new JLabel(new ImageIcon(image)));
//      }
//
//      public void paint(Graphics g)
//      {
//         if (back != null)
//         {
//            g.drawImage(back, 0, 0, this);
//         }
//         g.drawImage(image, 0, 0, this);
//      }
//
//      public void show()
//      {
//         int w = image.getWidth(this);
//         int h = image.getHeight(this);
//
//         if (w != -1 && h != -1)
//         {
//            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//            setBounds((d.width - w) / 2, (d.height - h) / 3, w, h);
//            try
//            {
//               back = new Robot().createScreenCapture(getBounds());
//            }
//            catch (AWTException e)
//            {
//               //never mind
//            }
//         }
//
//         super.show();
//      }
//
//      public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int w, int h)
//      {
//         if ((infoflags & WIDTH + HEIGHT) != 0)
//         {
//            show();
//         }
//
//         return super.imageUpdate(img, infoflags, x, y, w, h);
//      }
//   }

   private static void workbenchToFront()
   {
      final IWorkbench parent = PlatformUI.getWorkbench();
      if (parent != null)
      {
         final Shell workbenchShell = parent.getActiveWorkbenchWindow().getShell();
         workbenchShell.getDisplay().syncExec(new Runnable()
         {
            public void run()
            {
               workbenchShell.forceActive();
               DotPlotNavigator navigatorV = (DotPlotNavigator) parent.getActiveWorkbenchWindow()
                     .getActivePage()
                     .findView(DotPlotPerspective.DOTPLOTNAV);
               navigatorV.getViewer().getTree().forceFocus();
            }
         });
      }
   }
}
