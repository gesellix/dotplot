/*
 * Created on 01.06.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.fmatrix.Match;

/**
 * test functionality of Match class.
 *
 * @author Constantin von Zitzewitz
 * @version 0.1
 */
public class MatchTest extends TestCase
{

   private Match match;
   private int someY;
   private int someX;
   private double someWeight;

   public void setUp()
   {
      this.someY = 5;
      this.someX = 7;
      this.someWeight = 1.0;
      this.match = new Match(this.someY, this.someX, this.someWeight);
   }

   public void testSetUp()
   {
      assertNotNull("Match must not be null!", this.match);
   }

   public void testGetting()
   {
      assertEquals("y-value from getter-method must be same as previously set value!",
            this.someY,
            this.match.getY());
      assertEquals("x-value from getter-method must be same as previously set value!",
            this.someX,
            this.match.getX());
      assertTrue("weight value from getter-method must be same as previously set value!",
            this.someWeight == this.match.getWeight());
   }
}
