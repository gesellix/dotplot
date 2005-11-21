/*
 * Created on 31.05.2004
 */
package org.dotplot.fmatrix.test;

import junit.framework.TestCase;

import org.dotplot.fmatrix.Match;
import org.dotplot.fmatrix.TokenTable;
import org.dotplot.fmatrix.TypeTable;
import org.dotplot.fmatrix.TypeTableNavigator;

/**
 * tests functionality of TypeTableNavigator.
 *
 * @author Constantin von Zitzewitz
 * @version 0.1
 */
public class TypeTableNavigatorTest extends TestCase
{
   private TypeTableNavigator navigator;
   private TypeTable typeTable;
   private TokenTable tokenTable;

   public void setUp()
   {
      this.tokenTable = new TokenTable();
      this.typeTable = new TypeTable(this.tokenTable);
      this.navigator = new TypeTableNavigator(this.typeTable);
   }

   public void testSetUp()
   {
      assertNotNull("tokenTable object must not be null!", this.tokenTable);
      assertNotNull("typeTable object must not be null!", this.typeTable);
      assertNotNull("navigator object must not be null!", this.navigator);
   }

   public void testGetPostings()
   {
      // add some Types...
      int countPostings = 0;
      this.typeTable.addType("new_Token_1");
      countPostings++;
      this.typeTable.addType("new_Token_2");
      countPostings++;
      this.typeTable.addType("new_Token_3");
      countPostings++;
      this.typeTable.addType("new_Token_4");
      countPostings++;

      // creating postings...
      // shoud have 4 postings now...
      assertEquals("expecting counted number of postings!", countPostings, this.navigator.getNumberOfAllMatches());

      this.typeTable.addType("new_Token_3");

      countPostings = 0;
      Match tempMatch;
      while ((tempMatch = this.navigator.getNextMatch()) != null)
      {
         // ensure no endless loop
         if (countPostings > 20)
         {
            break;
         }

         countPostings++;
      }

      assertEquals("calculated number of postings must be same as actual!",
            countPostings,
            this.navigator.getNumberOfAllMatches());
   }

   public void testSetRegionOfInterest()
   {
      Match matchToCheck;
      int numberOfCheckedPostings = 0;
      int postX, postY;

      this.typeTable.addType("new_Token_1");
      this.typeTable.addType("new_Token_2");
      this.typeTable.addType("new_Token_3");
      this.typeTable.addType("new_Token_4");
      this.typeTable.addType("new_Token_5");
      this.typeTable.addType("new_Token_6");
      this.typeTable.addType("new_Token_7");
      this.typeTable.addType("new_Token_7");
      this.typeTable.addType("new_Token_8");
      this.typeTable.addType("new_Token_7");
      this.typeTable.addType("new_Token_9");
      this.typeTable.addType("new_Token_10");

      int originX = 1;
      int originY = 2;
      int width = 3;
      int height = 4;

      assertFalse("(1) Test if function returns false if value out of range",
            this.navigator.setRegionOfInterest(120, 1, -2, 3));

      assertTrue("(2) Test if function return true if values in range",
            this.navigator.setRegionOfInterest(originX, originY, width, height));

      matchToCheck = this.navigator.getNextMatch();

      assertFalse("(3) posting to check must not be null", (matchToCheck == null));

      int watchdog = 0;

      while (matchToCheck != null)
      {
         if (watchdog++ > 50)
         {
            assertFalse("...testloop runs endless", true);
         }
         //error if endless loop

         postX = matchToCheck.getX();
         postY = matchToCheck.getY();

//         assertTrue("(4) checkloop: if delivered postings in region of interist",
//               ((postX >= originX) && (postX <= width) && (postY >= originY) && (postY <= height)));
         //better: test each expression separately
         assertTrue(postX >= originX);
         assertTrue(postX <= width);
         assertTrue(postY >= originY);
         assertTrue(postY <= height);
         numberOfCheckedPostings++;
         //System.out.println("The test is checking posting: " + matchToCheck.toString());
         matchToCheck = this.navigator.getNextMatch();
      }
   }
}
