/*
 * Created on 18.06.2004
 */
package org.dotplot.ui.monitor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

/**
 * <code>DotPlotProgressDialog</code> shows two progressbars and should only be used by DotPlotProgressMonitor.
 *
 * @author Sascha Hemminger
 * @see org.dotplot.ui.monitor.DotPlotProgressMonitor
 */
class DotPlotProgressDialog
{
   private ProgressBar barOverall;
   private ProgressBar barModule;
   private Composite messagesAndBars;
   private Composite buttonArea;
   private Label message;
   private Label module;
   private Label overall;
   private Button cancel;
   private Shell shell;

   DotPlotProgressDialog()
   {
      shell = new Shell();
      shell.setText("Generating DotPlot");
      shell.setBounds(100, 100, 580, 250);

      this.messagesAndBars = new Composite(shell, SWT.NONE);
      this.messagesAndBars.setBounds(10, 10, 540, 150);
      this.buttonArea = new Composite(shell, SWT.NONE);
      buttonArea.setBounds(10, 160, 540, 50);

      message = new Label(this.messagesAndBars, SWT.NONE);
      message.setBounds(10, 10, 350, 30);

      module = new Label(this.messagesAndBars, SWT.NONE);
      module.setBounds(10, 50, 120, 20);

      barModule = new ProgressBar(this.messagesAndBars, SWT.NONE);
      barModule.setBounds(150, 50, 390, 20);
      barModule.setMaximum(100);

      overall = new Label(this.messagesAndBars, SWT.NONE);
      overall.setText("Overall progress:");
      overall.setBounds(10, 90, 120, 20);

      barOverall = new ProgressBar(this.messagesAndBars, SWT.NONE);
      barOverall.setBounds(150, 90, 390, 20);
      barOverall.setMaximum(100);

      cancel = new Button(this.buttonArea, SWT.NONE);
      cancel.setText("Cancel");
      cancel.setBounds(430, 10, 110, 35);
      cancel.addSelectionListener(DotPlotProgressMonitor.getInstance());
   }

   int getMaximum()
   {
      return this.barModule.getMaximum();
   }

   void stepModule(final int i)
   {
      checkActiveState(new ThreadAwareCallback()
      {
         public void isActive(boolean state)
         {
            if (state)
            {
               shell.getDisplay().syncExec(new Runnable()
               {
                  public void run()
                  {
                     barModule.setSelection(i);
                  }
               });
            }
         }
      });
   }

   void step(final int i)
   {
      checkActiveState(new ThreadAwareCallback()
      {
         public void isActive(boolean state)
         {
            if (state)
            {
               shell.getDisplay().syncExec(new Runnable()
               {
                  public void run()
                  {
                     barOverall.setSelection(i);
                  }
               });
            }
         }
      });
   }

   void setModule(final String moduleName)
   {
      checkActiveState(new ThreadAwareCallback()
      {
         public void isActive(boolean state)
         {
            if (state)
            {
               shell.getDisplay().syncExec(new Runnable()
               {
                  public void run()
                  {
                     DotPlotProgressDialog.this.module.setText(moduleName);
                  }
               });
            }
         }
      });
   }

   void setMessage(final String message)
   {
      checkActiveState(new ThreadAwareCallback()
      {
         public void isActive(boolean state)
         {
            if (state)
            {
               shell.getDisplay().syncExec(new Runnable()
               {
                  public void run()
                  {
                	  if(message != null){
                		  DotPlotProgressDialog.this.message.setText(message);
                	  }
                	  else {
                		  DotPlotProgressDialog.this.message.setText("no message");
                	  }
                  }
               });
            }
         }
      });
   }

   void close()
   {
      checkActiveState(new ThreadAwareCallback()
      {
         public void isActive(boolean state)
         {
            if (state)
            {
               shell.getDisplay().syncExec(new Runnable()
               {
                  public void run()
                  {
                     shell.dispose();
                  }
               });
            }
         }
      });
   }

   void open()
   {
      this.shell.open();
   }

   private void checkActiveState(final ThreadAwareCallback callback)
   {
      shell.getDisplay().syncExec(new Runnable()
      {
         public void run()
         {
            callback.isActive(shell != null && !shell.isDisposed() && shell.isVisible());
         }
      });
   }

   private interface ThreadAwareCallback
   {
      public void isActive(boolean state);
   }
}
