/*
 * Created on 01.06.2004
 */
package org.dotplot.fmatrix.profiler;

import org.dotplot.fmatrix.test.FMatrixManagerTest;

/**
 * profile the fmatrix manager.
 *
 * @author Constantin von Zitzewitz
 * @version 0.1
 */
public class ManagerProfiler
{
   /**
    * entry point for the profiler.
    *
    * @param args comandline arguments
    */
   public static void main(String[] args)
   {
      FMatrixManagerTest test = new FMatrixManagerTest();
      test.setUp();
      test.testRun();
   }
}
