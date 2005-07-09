/*
 * Created on 15.06.2004
 */
package org.dotplot.ui.configuration.controller.tests;

import junit.framework.TestCase;

import org.dotplot.DotplotCreator;
import org.dotplot.tokenizer.scanner.JavaScanner;
import org.dotplot.tokenizer.tokenfilter.GeneralTokenFilter;
import org.dotplot.ui.configuration.controller.SelectTokenTypesController;
import org.dotplot.ui.configuration.views.ConfigurationView;
import org.dotplot.ui.configuration.views.SelectTokenScannerView;
import org.dotplot.ui.configuration.views.SelectTokenTypesView;

/**
 * Test for <code>SelectTokenTypesControllerTest</code>
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class SelectTokenTypesControllerTest extends TestCase
{

   private SelectTokenTypesController controller;

   /**
    * Constructor for SelectTokenTypesControllerTest.
    */
   public SelectTokenTypesControllerTest(String arg0)
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
      this.controller = new SelectTokenTypesController(dotplotCreator, new SelectTokenTypesView(dotplotCreator));
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

      DotplotCreator dotplotCreator = (DotplotCreator) this.controller.getDotplotter();
      assertTrue("prüfen ob auch ein DotplotCreator zurückgegeben wurde", dotplotCreator instanceof DotplotCreator);

      dotplotCreator.getTokenizerConfiguration().setTokenFilter(new GeneralTokenFilter(new int[5]));
      SelectTokenScannerView view1 = new SelectTokenScannerView(dotplotCreator);
      JavaScanner s = new JavaScanner();
      dotplotCreator.getTokenizerConfiguration().setScanner(s);
      dotplotCreator.setTokenTypes(s.getTokenTypes());

      this.controller.update(view1, null);
      assertNull("prüfen ob der GeneralTokenFilter rausgenommen wurde",
            dotplotCreator.getTokenizerConfiguration().getTokenFilter());

   }

   /*
    * Test für void SelectTokenTypesController(DotplotCreator)
    */
   public void testSelectTokenTypesControllerDotplotter()
   {
      DotplotCreator d = new DotplotCreator();
      this.controller = new SelectTokenTypesController(d, new SelectTokenTypesView(d));
      assertSame("prüfen ob das was rein ging auch wieder raus kommt", d, this.controller.getDotplotter());
   }

   /*
    * Test für void SelectTokenTypesController(DotplotCreator, ConfigurationView)
    */
   public void testSelectTokenTypesControllerDotplotterConfigurationView()
   {
      DotplotCreator d = new DotplotCreator();
      ConfigurationView view = new SelectTokenTypesView(new DotplotCreator());
      this.controller = new SelectTokenTypesController(d, view);
      assertSame("prüfen ob das was rein ging auch wieder raus kommt", view, this.controller.getConfigurationView());
      assertSame("prüfen ob das was rein ging auch wieder raus kommt", d, this.controller.getDotplotter());
   }
}
