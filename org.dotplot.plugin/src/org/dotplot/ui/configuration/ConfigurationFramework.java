/*
 * Created on 17.06.2004
 */
package org.dotplot.ui.configuration;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * Der Rahmen für die Konfiguration.
 * Die <code>ConfigurationViews</code> die in der <code>GlobalConfiguration</code>
 * angegeben sind, werden mit einem Rahmenwerk versehen. Für jeden <code>ConfigurationView</code>
 * wird ein eigener Tab erzeugt, der über einen Apply und einen Reset Knopf verfügt.
 * Durch den Apply-Knopf werden die Observer der <code>ConfigurationView</code> angestoßen,
 * und durch den Reset Knopf, die <code>reset()</code> Methode gestartet.
 *
 * @author Christian gerhardt <case42@gmx.net>
 */
public class ConfigurationFramework
{
   /**
    * Listener for the <code>cancelbutton</code>.
    */
   private SelectionListener cancelListener;

   /**
    * Listener for the <code>plottbutton</code>
    */
   private SelectionListener plotListener;

   /**
    * Der Container fuer die einzelnen <code>ConfigurationViews</code>
    *
    * @see org.dotplot.ui.configuration.views.ConfigurationView
    * @see org.dotplot.ui.configuration.ConfigurationViews
    */
   private ConfigurationViews cvs;

   /**
    * Erzeugt ein <code>ConfigurationFramework</code>.
    */
   public ConfigurationFramework()
   {
      cancelListener = new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            ((Button) e.widget).getShell().dispose();
         }
      };

      plotListener = new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            ((Button) e.widget).getShell().dispose();
         }
      };

      cvs = (ConfigurationViews) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_CONFIGURATION_VIEWS);
   }

   /**
    * Draws the <code>ConfigurationFramework</code>.
    * This method generates a <code>TabFolder</code> for the associated <code>ConfigurationViews</code>,
    * in which each <code>ConfigurationView</code> is displayed in a single <code>Tab</code>.
    * <code>
    * Shell s = new Shell();
    * ConfigurationFramework framework = new ConfigurationFramework();
    * framework.draw(s);
    * </code>
    *
    * @param parent the parent Composite of the <code>ConfigurationFramework</code>
    */
   public void draw(Composite parent)
   {
      Composite co = new Composite(parent, SWT.NONE);
      Layout layout = new GridLayout(2, true);
      co.setLayout(layout);

      GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
      // HORIZONTAL_ALIGN_FILL);
      gd.horizontalSpan = 2;
      gd.verticalSpan = 2;

      Label l1 = new Label(co, SWT.LEFT);
      l1.setText("Configure your dotpot");

      final TabFolder tf = new TabFolder(co, SWT.NULL);
      tf.setLayoutData(gd);

      // add configuration views as tabs
      Iterator iter = cvs.values().iterator();
      while (iter.hasNext())
      {
         this.drawTab(tf, (ConfigurationView) iter.next());
      }

      Composite c2 = new Composite(co, SWT.NONE);
      GridData gd2 = new GridData(GridData.HORIZONTAL_ALIGN_END);
      gd2.horizontalSpan = 2;
      c2.setLayoutData(gd2);
      c2.setLayout(new RowLayout());

      Button btnPlot = new Button(c2, SWT.PUSH);
      btnPlot.setText("Plot");

      btnPlot.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            cvs.notifyObservers();
         }
      });

      // this has to be added _after_ the SelectionListener for the notification of the Oberservers
      // to let them apply changes made by the user before starting the plot.
      btnPlot.addSelectionListener(plotListener);

      Button btnCancel = new Button(c2, SWT.PUSH);
      btnCancel.setText("Cancel");
      btnCancel.addSelectionListener(cancelListener);

      co.pack(true);
   }

   /**
    * Draws a <code>Tab</code> for a <code>ConfigurationView</code>.
    *
    * @param folder the tabfolder in which the folder should be displayed.
    * @param view   the associated ConfigurationView
    */
   protected void drawTab(TabFolder folder, ConfigurationView view)
   {
      Composite c1, c2, c3;
      TabItem ti;
      GridData gd;

      ti = new TabItem(folder, SWT.NULL);
      ti.setText(view.getName());

      c1 = new Composite(folder, SWT.NONE);
      c1.setLayout(new GridLayout(1, false));
      ti.setControl(c1);

      c2 = new Composite(c1, SWT.NONE);
      view.draw(c2);

      gd = new GridData(GridData.FILL_BOTH);
      c2.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
      c3 = new Composite(c1, SWT.NONE);
      c3.setLayout(new RowLayout());
      c3.setLayoutData(gd);

      Button b = new Button(c3, SWT.PUSH);
      b.setText("Apply");
      b.addSelectionListener(view.getApplyListener());

      b = new Button(c3, SWT.PUSH);
      b.setText("Reset");
      b.addSelectionListener(view.getResetListener());
   }

   /**
    * Gets the listener for the Cancelbutton.
    *
    * @return the Listener
    *
    * @see #setCancelListener(org.eclipse.swt.events.SelectionListener)
    */
   public SelectionListener getCancelListener()
   {
      return cancelListener;
   }

   /**
    * Gets the listener for the Plottbutton.
    *
    * @return the listener
    *
    * @see #setPlotListener(org.eclipse.swt.events.SelectionListener)
    */
   public SelectionListener getPlotListener()
   {
      return plotListener;
   }

   /**
    * Sets a new listener for the Cancelbutton.
    *
    * @param listener the new listener
    *
    * @see #getCancelListener()
    */
   public void setCancelListener(SelectionListener listener)
   {
      cancelListener = listener;
   }

   /**
    * Sets a new Listener for the Plotbutton.
    *
    * @param listener the new listener
    *
    * @see #getPlotListener()
    */
   public void setPlotListener(SelectionListener listener)
   {
      plotListener = listener;
   }
}
