/*
 * Created on 03.06.2004
 */
package org.dotplot.tokenizer.tests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.dotplot.tokenizer.scanner.CPlusPlusScanner;
import org.dotplot.tokenizer.scanner.IScanner;
import org.dotplot.ui.configuration.ConfigurationFramework;
import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Test Class for the ConfigurationFramework
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class ConfigurationTestFramework extends ConfigurationFramework
{

   public ConfigurationTestFramework()
   {
      super();
   }

   public static void main(String[] args)
   {
      ConfigurationTestFramework application = new ConfigurationTestFramework();
      application.doModal(application.init());
   }

   public void doModal(Shell shell)
   {
      shell.open();
      Display display = shell.getDisplay();
      while (!shell.isDisposed())
      {
         if (!display.readAndDispatch())
         {
            display.sleep();
         }
      }
      display.dispose();
   }

   public Shell init()
   {
      IScanner s = new CPlusPlusScanner();
      GlobalConfiguration.getInstance().getDotplotCreator().setTokenTypes(s.getTokenTypes());
      Display display = new Display();
      Shell shell = this.open(display);
      shell.setText("Configuration Testing Framework");
      shell.pack();
      return shell;
   }

   public Shell open(Display display)
   {
      Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
      shell.setSize(600, 400);
      shell.setLayout(new FillLayout());
      this.setCancelListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            ((Button) e.widget).getShell().dispose();
         }
      });
      this.draw(shell);
      return shell;
   }
}
