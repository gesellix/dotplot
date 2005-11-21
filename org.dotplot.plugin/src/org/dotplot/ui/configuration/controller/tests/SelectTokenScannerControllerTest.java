/*
 * Created on 15.06.2004
 */
package org.dotplot.ui.configuration.controller.tests;

import junit.framework.TestCase;

import org.dotplot.DotplotCreator;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.scanner.IScanner;
import org.dotplot.tokenizer.scanner.JavaScanner;
import org.dotplot.ui.configuration.controller.SelectTokenScannerController;
import org.dotplot.ui.configuration.views.SelectTokenScannerView;

/**
 * Insert Description for <code>SelectTokenScannerControllerTest</code>
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenScannerControllerTest extends TestCase
{

   private SelectTokenScannerController controller;

   /**
    * Constructor for SelectTokenScannerControllerTest.
    */
   public SelectTokenScannerControllerTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      final DotplotCreator dotplotCreator = new DotplotCreator();
      this.controller = new SelectTokenScannerController(dotplotCreator, new SelectTokenScannerView(dotplotCreator));
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testUpdate()
   {
      TokenType[] tt;
      IScanner scanner;
      DotplotCreator d;
      SelectTokenScannerView view;

      d = (DotplotCreator) this.controller.getDotplotter();
      assertTrue("prüfen ob ein DotplotCreator zurückgegeben wurde", d instanceof DotplotCreator);
      view = new SelectTokenScannerView(d);

      //update mit dem scanner "none";
      this.controller.update(view, null);

      assertTrue("prüft ob das dirtybit am dotplotter gesetzt wurde", d.isDirty());
      tt = d.getTokenTypes();
      scanner = d.getTokenizerConfiguration().getScanner();

      assertNull("scanner muß NULL sein", scanner);
      assertTrue("keine Tokentypen dürfen gesetzt sein", tt.length == 0);

      //reset
      try
      {
         this.setUp();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      d = (DotplotCreator) this.controller.getDotplotter();
      assertTrue("prüfen ob ein DotplotCreator zurückgegeben wurde", d instanceof DotplotCreator);
      view = new SelectTokenScannerView(d);

      //update mit dem scanner "JavaScanner";
      view.setSelectedScanner("JavaScanner");
      this.controller.update(view, null);

      assertTrue("prüft ob das dirtybit am dotplotter gesetzt wurde", d.isDirty());
      tt = d.getTokenTypes();
      scanner = d.getTokenizerConfiguration().getScanner();

      assertNotNull("darf nicht NULL sein", scanner);
      assertTrue("Scanner muß vom typ Javascanner sein", scanner instanceof JavaScanner);
      assertSame("die richtigen Tokentypen müssen eingetragen worden sein",
            scanner.getTokenTypes(),
            d.getTokenTypes());
   }
}
