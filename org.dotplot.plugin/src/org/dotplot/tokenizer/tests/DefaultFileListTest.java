package org.dotplot.tokenizer.tests;

import junit.framework.TestCase;

import java.io.File;

import org.dotplot.tokenizer.DefaultFileList;

/**
 * Test Class for the DefaultFileList
 *
 * @author case
 * @version 1.0
 */
public class DefaultFileListTest extends TestCase
{

   private DefaultFileList defFL;

   /**
    * Constructor for DefaultFileListTest.
    *
    * @param arg0 -- the first argument
    */
   public DefaultFileListTest(String arg0)
   {
      super(arg0);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception
   {
      super.setUp();
      this.defFL = new DefaultFileList();
   }

   public void testCount()
   {
      this.defFL.add(new File("c:\\WebDeploy.log"));
      assertEquals(this.defFL.count(), 1);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }
}
