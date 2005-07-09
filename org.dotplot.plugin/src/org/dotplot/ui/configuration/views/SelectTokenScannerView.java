/*
 * Created on 27.05.2004
 */
package org.dotplot.ui.configuration.views;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.dotplot.IGUIDotplotter;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * the configuration view for the scanner settings.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenScannerView extends ConfigurationView
{
   private String selectedScanner;
   private Combo scannerSelecter;
   private boolean convertFiles;
   private boolean keepConvertedFiles;
   private String conversionDirectory;

   private Button convertFilesButton;
   private Button keepConvertedFilesButton;
   private Button browseButton;
   private Text conversionDirectoryText;

   private DirectoryDialog fileBrowser;

   /**
    * creates the view.
    *
    * @param dotplotter the IGUIDotplotter
    */
   public SelectTokenScannerView(IGUIDotplotter dotplotter)
   {
      super(dotplotter);
      this.setName("Scanner settings");
      this.selectedScanner = "none";
      this.conversionDirectory = new String("");
      this.keepConvertedFiles = false;
      this.convertFiles = false;
   }

   /**
    * @see org.dotplot.ui.configuration.views.ConfigurationView#draw(org.eclipse.swt.widgets.Composite)
    */
   public void draw(Composite parent)
   {
      this.fileBrowser = new DirectoryDialog(parent.getShell(), SWT.OPEN);
      this.fileBrowser.setMessage("Choose a directory to store your converted files.");
      parent.setLayout(new GridLayout());
      this.createSelectScannerComboBox(this.createGroup(parent, "Scannerselection"));
      this.createConverterSettings(this.createGroup(parent, "Conversionsettings"));
      this.reset();
   }

   private Composite createGroup(Composite parent, String name)
   {
      Group g = new Group(parent, SWT.SHADOW_ETCHED_OUT);
      g.setText(name);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      g.setLayoutData(gd);
      g.setLayout(new GridLayout(2, false));
      return g;
   }

   private void createConverterSettings(Composite parent)
   {
      GridData gd;
      final SelectionListener sl = this.changeListener;

      parent.setLayout(new GridLayout(3, false));

      gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
      gd.horizontalSpan = 3;
      this.convertFilesButton = new Button(parent, SWT.CHECK);
      this.convertFilesButton.setText("convert pdf - files");
      this.convertFilesButton.setToolTipText("pdf-files will be converted into a readable format of the dotplotter");
      this.convertFilesButton.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
      Label l = new Label(parent, SWT.NONE);
      l.setText("Directory:");
      l.setLayoutData(gd);

      gd = new GridData(GridData.FILL_HORIZONTAL);
      this.conversionDirectoryText = new Text(parent, SWT.SINGLE | SWT.BORDER);
      this.conversionDirectoryText.setToolTipText("Directory to store converted files.");
      this.conversionDirectoryText.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
      this.browseButton = new Button(parent, SWT.PUSH);
      this.browseButton.setText("Browse");
      this.browseButton.setLayoutData(gd);

      //filling Label
      l = new Label(parent, SWT.NONE);

      gd = new GridData();
      gd.horizontalSpan = 2;
      this.keepConvertedFilesButton = new Button(parent, SWT.CHECK);
      this.keepConvertedFilesButton.setText("keep converted files");
      this.keepConvertedFilesButton.setToolTipText("converted files will not be deleted after generating the dotplot");

      final Button kcfb = this.keepConvertedFilesButton;
      final Text cdt = this.conversionDirectoryText;
      final Button browse = this.browseButton;
      final DirectoryDialog browser = this.fileBrowser;
      final SelectTokenScannerView stsv = this;

      //listeners
      this.convertFilesButton.addSelectionListener(this.changeListener);
      this.convertFilesButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            boolean set = ((Button) e.widget).getSelection();
            stsv.convertFiles = set;
            browse.setEnabled(set);
            kcfb.setEnabled(set);
            cdt.setEnabled(set);
         }
      });

      this.keepConvertedFilesButton.addSelectionListener(this.changeListener);
      this.keepConvertedFilesButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            stsv.keepConvertedFiles = kcfb.getSelection();
         }
      });

      this.conversionDirectoryText.addSelectionListener(this.changeListener);
      this.conversionDirectoryText.addModifyListener(new ModifyListener()
      {
         public void modifyText(ModifyEvent arg0)
         {
            sl.widgetSelected(null);
         }
      });

      this.browseButton.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            browser.setFilterPath(cdt.getText());
            browser.open();
            cdt.setText(browser.getFilterPath());
         }
      });
   }

   private void createSelectScannerComboBox(Composite parent)
   {
      TreeMap scannerTypes = (TreeMap) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_TOKENIZER_SCANNER_TYPES);
      Collection cScannerTypes = scannerTypes.values();

      GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
      gd.horizontalAlignment = 3;
      Label l = new Label(parent, SWT.NONE);
      l.setText("selected Scanner:");
      l.setLayoutData(gd);

      Combo selectScannerComboBox = new Combo(parent, SWT.BORDER | SWT.READ_ONLY | SWT.FLAT | SWT.DROP_DOWN);
      gd = new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_BEGINNING);
      selectScannerComboBox.setLayoutData(gd);

      selectScannerComboBox.add("none");
      selectScannerComboBox.select(0);

      Iterator it = cScannerTypes.iterator();
      while (it.hasNext())
      {
         selectScannerComboBox.add((String) it.next());
      }
      selectScannerComboBox.addSelectionListener(this.changeListener);
      selectScannerComboBox.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            selectedScanner = ((Combo) e.getSource()).getText();
         }
      });
      this.scannerSelecter = selectScannerComboBox;
   }

   /**
    * returns the current setting.
    *
    * @return the selected scanner
    *
    * @see #setSelectedScanner(String)
    */
   public String getSelectedScanner()
   {
      return this.selectedScanner;
   }

   /**
    * sets the scanner to be used.
    *
    * @param scanner the scanner
    *
    * @see #getSelectedScanner()
    */
   public void setSelectedScanner(String scanner)
   {
      this.selectedScanner = scanner;
   }

   public void reset()
   {
      //reset ScannerSelecter
      String scanner;
      IConfiguration config = this.getDotplotter().getTokenizerConfiguration();

      if (config.getScanner() == null)
      {
         scanner = "none";
      }
      else
      {
         scanner = config.getScanner().getClass().getName();
      }

      String[] items = this.scannerSelecter.getItems();
      for (int i = 0; i < items.length; i++)
      {
         if (scanner.equals("org.dotplot.tokenizer.scanner." + items[i]))
         {
            this.scannerSelecter.select(i);
            break;
         }
         else if (scanner.equals(items[i]))
         {
            this.scannerSelecter.select(i);
            break;
         }
      }
      this.selectedScanner = this.scannerSelecter.getText();

      //reset converter
      boolean set = config.getConvertFiles();
      this.convertFilesButton.setSelection(set);
      this.conversionDirectoryText.setEnabled(set);
      this.keepConvertedFilesButton.setEnabled(set);
      this.browseButton.setEnabled(set);

      this.conversionDirectoryText.setText(config.getConvertDirectory().getAbsolutePath());
      this.keepConvertedFilesButton.setSelection(
            ((Boolean) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_TOKENIZER_SAVE_CONVERTED_FILES)).booleanValue());

      this.keepConvertedFiles = this.keepConvertedFilesButton.getSelection();
      this.convertFiles = this.convertFilesButton.getSelection();
      this.conversionDirectory = this.conversionDirectoryText.getText();
   }

   /**
    * returns the current setting.
    *
    * @return the conversion directory
    */
   public File getConversionDirectory()
   {
      return new File(this.conversionDirectory);
   }

   /**
    * returns the current setting.
    *
    * @return the state
    */
   public boolean getConvertFilesState()
   {
      return this.convertFiles;
   }

   /**
    * returns the current setting.
    *
    * @return keep converted files?
    */
   public boolean getKeepConvertedFilesState()
   {
      return this.keepConvertedFiles;
   }
}
