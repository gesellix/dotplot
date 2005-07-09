/*
 * Created on 27.05.2004
 */
package org.dotplot.ui.configuration.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import org.dotplot.IGUIDotplotter;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.ITokenFilter;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.tokenfilter.GeneralTokenFilter;
import org.dotplot.tokenizer.tokenfilter.KeyWordFilter;
import org.dotplot.tokenizer.tokenfilter.LineFilter;
import org.dotplot.tokenizer.tokenfilter.SentenceFilter;
import org.dotplot.tokenizer.tokenfilter.TokenFilterContainer;

/**
 * the view for the filter settings.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenTypesView extends ConfigurationView
{
   /**
    * a checkbox for the handling of token types.
    *
    * @author Christian Gerhardt <case42@gmx.net>
    * @version 1.0, 27.6.04
    */
   private class TokenTypesCheckBox extends Composite
   {
      private int tokenType;

      private Button button;

      /**
       * create the checkbox.
       *
       * @param parent    parent composite
       * @param tokenType token type to be used
       * @param name      the name
       */
      public TokenTypesCheckBox(Composite parent, int tokenType, String name)
      {
         super(parent, SWT.NONE);
         this.tokenType = tokenType;
         this.setLayout(new FillLayout());
         this.button = new Button(this, SWT.CHECK);
         this.button.setText(name);
         this.button.setToolTipText("filter this token");
      }

      public int getType()
      {
         return this.tokenType;
      }

      public String getTokenName()
      {
         return this.button.getText();
      }

      public boolean getSelection()
      {
         return this.button.getSelection();
      }

      public void addSelectionListener(SelectionListener s)
      {
         this.button.addSelectionListener(s);
      }

      public void setSelection(boolean set)
      {
         this.button.setSelection(set);
      }
   }

   private TokenType tokentypes[];
   private TokenTypesCheckBox checkBoxes[];
   private Composite root;

   private Button lineCheckBox;
   private Button filterLinesCheckBox;
   private Button sentenceCheckBox;
   private Combo punctuationCombo;
   private Scale keyWordFilterSettings;

   /**
    * Creates a new <code>SelectTokenTypesView</code>.
    *
    * @param dotplotter the IGUIDotplotter
    */
   public SelectTokenTypesView(IGUIDotplotter dotplotter)
   {
      super(dotplotter);
      this.setName("Filter settings");
      this.tokentypes = this.getDotplotter().getTokenTypes();
      this.checkBoxes = new TokenTypesCheckBox[0];
   }

   public void draw(Composite parent)
   {
      GridLayout layout = new GridLayout(1, false);
      parent.setLayout(layout);
      GridData gData = new GridData(GridData.FILL_BOTH);
      parent.setLayoutData(gData);
      this.createFilterGroup(parent);

      Group g = new Group(parent, SWT.SHADOW_ETCHED_OUT);
      g.setText("Tokentypes");
      g.setLayout(new FillLayout());
      gData = new GridData(GridData.FILL_BOTH);
      g.setLayoutData(gData);

      final ScrolledComposite sc1 = new ScrolledComposite(g, SWT.V_SCROLL);
      final Composite c1 = new Composite(sc1, SWT.NONE);
      sc1.setContent(c1);
      this.drawTypeList(c1);
      c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
      this.root = parent;
      this.reset();
   }

   /**
    * reads the current setting from the dotplotter.
    */
   public void updateTokenTypes()
   {
      this.tokentypes = this.getDotplotter().getTokenTypes();
   }

   protected void drawTypeList(Composite parent)
   {
      parent.setLayout(new GridLayout(1, true));

      this.checkBoxes = new TokenTypesCheckBox[this.tokentypes.length];

      this.createGroup(parent, "keywords", TokenType.KIND_KEYWORD);
      this.createGroup(parent, "operators", TokenType.KIND_OPERATOR);
      this.createGroup(parent, "punctuation marks", TokenType.KIND_PUNCTUATION_MARK);
      this.createGroup(parent, "other tokens", TokenType.KIND_OTHER);
   }

   /**
    * returns the selected types.
    *
    * @return the selection
    */
   public int[] getCheckedTypes()
   {
      int[] checkedTypes = new int[this.countCheckedTypes()];
      int j = 0;
      for (int i = 0; i < this.checkBoxes.length; i++)
      {
         if (this.checkBoxes[i].getSelection())
         {
            checkedTypes[j] = this.checkBoxes[i].getType();
            j++;
         }
      }
      return checkedTypes;
   }

   private int countCheckedTypes()
   {
      int count = 0;
      for (int i = 0; i < this.checkBoxes.length; i++)
      {
         if (this.checkBoxes[i].getSelection())
         {
            count++;
         }
      }
      return count;
   }

   private void createFilterGroup(Composite parent)
   {
      Group g = new Group(parent, SWT.NONE);
      g.setText("Filter");
      g.setLayout(new GridLayout(3, false));

      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      g.setLayoutData(gd);

      this.drawKeywordTokenSettings(g);

      gd = new GridData();
      final Button bLines = new Button(g, SWT.CHECK);
      bLines.setLayoutData(gd);
      this.lineCheckBox = bLines;

      gd = new GridData();
      final Button bFilterLines = new Button(g, SWT.CHECK);
      bFilterLines.setLayoutData(gd);
      this.filterLinesCheckBox = bFilterLines;

      gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
      final Button bSentences = new Button(g, SWT.CHECK);
      bSentences.setLayoutData(gd);
      this.sentenceCheckBox = bSentences;

      gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
      final Combo cSelectMark = new Combo(g, SWT.READ_ONLY | SWT.DROP_DOWN);
      cSelectMark.setLayoutData(gd);
      this.punctuationCombo = cSelectMark;
      cSelectMark.setToolTipText("this token marks the end of a sentence");

      SelectionListener filterListener = new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            Button source = (Button) e.getSource();
            if (source == bLines)
            {
               if (source.getSelection())
               {
                  bSentences.setEnabled(false);
                  cSelectMark.setEnabled(false);
               }
               else
               {
                  bSentences.setEnabled(true);
                  cSelectMark.setEnabled(true);
               }
            }
            else if (source == bSentences)
            {
               if (source.getSelection())
               {
                  bLines.setEnabled(false);
                  bFilterLines.setEnabled(false);
                  bFilterLines.setSelection(false);
               }
               else
               {
                  bLines.setEnabled(true);
               }
            }
         }
      };

      bLines.setText("use LineFilter");
      bLines.addSelectionListener(this.changeListener);
      bLines.addSelectionListener(new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            if (bLines.getSelection())
            {
               bFilterLines.setEnabled(true);
            }
            else
            {
               bFilterLines.setEnabled(false);
               bFilterLines.setSelection(false);
            }
         }
      });
      bLines.setToolTipText("concatinates all tokens in a line");

      bFilterLines.setText("ignore empty lines");
      bFilterLines.addSelectionListener(this.changeListener);
      bFilterLines.setToolTipText("all empty lines will be ignored for the dotplot");

      bSentences.setText("filter sentences");
      String[] marks = this.getPunktuationMarks();
      bSentences.setToolTipText("concatinates all tokens until the end of sentence token appeares");

      if (marks.length > 0)
      {
         bLines.addSelectionListener(filterListener);

         bSentences.addSelectionListener(this.changeListener);
         bSentences.addSelectionListener(filterListener);
         cSelectMark.setItems(marks);
      }
      else
      {
         bSentences.setEnabled(false);
         cSelectMark.setEnabled(false);
      }
   }

   private void drawKeywordTokenSettings(Composite parent)
   {
      GridData gd = new GridData();
      gd.verticalSpan = 3;

      Group ng = new Group(parent, SWT.NONE);
      ng.setText("Keywordfilter");
      ng.setLayout(new GridLayout(3, true));
      ng.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
      Label l1 = new Label(ng, SWT.NONE);
      l1.setText("none");
      l1.setToolTipText("no keyword will pass");
      l1.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
      Label l2 = new Label(ng, SWT.NONE);
      l2.setText("off");
      l2.setToolTipText("don't use the keywordfilter");
      l2.setLayoutData(gd);

      gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
      Label l3 = new Label(ng, SWT.NONE);
      l3.setText("only");
      l3.setToolTipText("only keywords will pass");
      l3.setLayoutData(gd);

      gd = new GridData();
      gd.horizontalSpan = 3;
      final Scale keyWordFilterSettings = new Scale(ng, SWT.HORIZONTAL);
      this.keyWordFilterSettings = keyWordFilterSettings;
      keyWordFilterSettings.setIncrement(1);
      keyWordFilterSettings.setPageIncrement(1);
      keyWordFilterSettings.setMinimum(0);
      keyWordFilterSettings.setMaximum(2);
      keyWordFilterSettings.setSelection(1);
      keyWordFilterSettings.setLayoutData(gd);

      keyWordFilterSettings.addSelectionListener(this.changeListener);
   }

   /**
    * @param parent    - the parent Composite of this group
    * @param name      - the name of this group
    * @param tokenKind - the tokenkind which is represented by this group
    */
   private void createGroup(Composite parent, String name, int tokenKind)
   {
      Group g = null;
      boolean first = true;

      for (int i = 0; i < this.tokentypes.length; i++)
      {
         if (this.tokentypes[i].getKind() == tokenKind)
         {
            if (first)
            {
               g = new Group(parent, SWT.NONE);
               g.setText(name);

               GridData gd = new GridData(GridData.FILL);
               gd.widthHint = 450;
               g.setLayoutData(gd);

               RowLayout layout = new RowLayout();
               layout.wrap = true;
               layout.justify = true;
               layout.pack = false;
               g.setLayout(layout);
               first = false;
            }

            TokenTypesCheckBox t = new TokenTypesCheckBox(g,
                  this.tokentypes[i].getType(),
                  this.tokentypes[i].getName());

            t.addSelectionListener(this.changeListener);
            this.checkBoxes[i] = t;
         }
      }
   }

   public void refresh()
   {
      if (root != null && !root.isDisposed())
      {
         Control[] children = root.getChildren();
         this.tokentypes = this.getDotplotter().getTokenTypes();

         for (int i = 0; i < children.length; i++)
         {
            children[i].dispose();
         }

         draw(this.root);
         this.reset();
         this.root.layout();
      }
   }

   protected String[] getPunktuationMarks()
   {
      String[] marks = null;
      int j = 0;

      for (int i = 0; i < this.tokentypes.length; i++)
      {
         if (this.tokentypes[i].getKind() == TokenType.KIND_PUNCTUATION_MARK)
         {
            j++;
         }
      }

      marks = new String[j];
      j = 0;

      for (int i = 0; i < this.tokentypes.length; i++)
      {
         if (this.tokentypes[i].getKind() == TokenType.KIND_PUNCTUATION_MARK)
         {
            marks[j] = this.tokentypes[i].getName() + "  ";
            j++;
         }
      }

      return marks;
   }

   public void reset()
   {
      IConfiguration config = this.getDotplotter().getTokenizerConfiguration();
      ITokenFilter filter = config.getTokenFilter();

      if (root != null)
      {
         this.keyWordFilterSettings.setSelection(1);
         this.lineCheckBox.setSelection(false);
         this.sentenceCheckBox.setSelection(false);
         this.filterLinesCheckBox.setSelection(false);
         this.filterLinesCheckBox.setEnabled(false);

         for (int i = 0; i < this.checkBoxes.length; i++)
         {
            this.checkBoxes[i].setSelection(false);
         }
      }
      this.resetTokenFilters(filter);
   }

   private void resetTokenFilters(ITokenFilter filter)
   {
      if (filter instanceof KeyWordFilter)
      {
         switch (((KeyWordFilter) filter).getModus())
         {
            case KeyWordFilter.LET_THROUGH_NO_KEYWORDS:
               this.keyWordFilterSettings.setSelection(0);
               break;

            case KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS:
               this.keyWordFilterSettings.setSelection(2);
               break;
         }
      }
      else if (filter instanceof LineFilter)
      {
         this.sentenceCheckBox.setSelection(false);
         this.sentenceCheckBox.setEnabled(false);
         this.punctuationCombo.setEnabled(false);
         this.lineCheckBox.setEnabled(true);
         this.lineCheckBox.setSelection(true);
         this.filterLinesCheckBox.setEnabled(true);
         this.filterLinesCheckBox.setSelection(!((LineFilter) filter).isReturningEmptyLines());
      }
      else if (filter instanceof SentenceFilter)
      {
         this.sentenceCheckBox.setSelection(true);
         this.sentenceCheckBox.setEnabled(true);
         this.punctuationCombo.setEnabled(true);
         this.lineCheckBox.setEnabled(false);
         this.lineCheckBox.setSelection(false);
         this.filterLinesCheckBox.setSelection(false);
         this.filterLinesCheckBox.setEnabled(false);
         String mark = ((SentenceFilter) filter).getEndOfSentenceToken().getValue() + "  ";
         String[] items = this.punctuationCombo.getItems();

         for (int i = 0; i < items.length; i++)
         {
            if (mark.equals(items[i]))
            {
               this.punctuationCombo.select(i);
               break;
            }
         }
      }
      else if (filter instanceof TokenFilterContainer)
      {
         ITokenFilter[] filters = ((TokenFilterContainer) filter).getTokenFilters();

         for (int i = 0; i < filters.length; i++)
         {
            this.resetTokenFilters(filters[i]);
         }
      }
      else if (filter instanceof GeneralTokenFilter)
      {
         int[] filterList = ((GeneralTokenFilter) filter).getFilterList();

         for (int i = 0; i < this.checkBoxes.length; i++)
         {
            for (int j = 0; j < filterList.length; j++)
            {
               if (this.checkBoxes[i].getType() == filterList[j])
               {
                  this.checkBoxes[i].setSelection(true);
                  break;
               }
            }
         }
      }
   }

   /**
    * returns the token types.
    *
    * @return the token types
    */
   public TokenType[] getTokentypes()
   {
      return tokentypes;
   }

   /**
    * returns the keyword filter.
    *
    * @return the filter, or null
    */
   public KeyWordFilter getKeyWordFilter()
   {
      //    if (this.keyWordCheckBox.getSelection()) {
      switch (this.keyWordFilterSettings.getSelection())
      {
         case 0:
            return new KeyWordFilter(KeyWordFilter.LET_THROUGH_NO_KEYWORDS);
         case 2:
            return new KeyWordFilter(KeyWordFilter.LET_THROUGH_ONLY_KEYWORDS);
      }
      //    }
      return null;
   }

   /**
    * returns the line filter.
    *
    * @return the filter, or null
    */
   public LineFilter getLineFilter()
   {
      if (this.lineCheckBox.getSelection())
      {
         LineFilter lf = new LineFilter();
         if (this.filterLinesCheckBox.getSelection())
         {
            lf.dontReturnEmptyLines();
         }
         return lf;
      }
      return null;
   }

   /**
    * returns the sentence filter.
    *
    * @return the filter, or null
    */
   public SentenceFilter getSentenceFilter()
   {
      if (this.sentenceCheckBox.getSelection())
      {
         String mark = this.punctuationCombo.getText().trim();
         if (!mark.equals(""))
         {
            for (int i = 0; i < this.tokentypes.length; i++)
            {
               if (this.tokentypes[i].getName().equals(mark))
               {
                  SentenceFilter filter = new SentenceFilter();
                  filter.setEndOfSentenceToken(this.tokentypes[i].getToken());
                  return filter;
               }
            }
         }
      }
      return null;
   }

   /**
    * returns the general tokan filter.
    *
    * @return the filter, or null
    */
   public GeneralTokenFilter getGeneralTokenFilter()
   {
      if (this.getCheckedTypes().length > 0)
      {
         return new GeneralTokenFilter(this.getCheckedTypes());
      }
      return null;
   }
}
