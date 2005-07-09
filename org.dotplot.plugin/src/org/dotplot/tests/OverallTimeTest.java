package org.dotplot.tests;

import java.io.File;

import org.eclipse.swt.graphics.ImageData;

import org.dotplot.DotplotCreator;
import org.dotplot.image.IDotplot;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.ui.views.DotPlotFileList;

/**
 * <code>OverallTimeTest</code> provides a mainclass for profiling without starting a workbench
 *
 * @author Sascha Hemminger
 */
public class OverallTimeTest
{
   private File testFile;
   private DotPlotFileList testFileList;

   public OverallTimeTest(File file)
   {
      this.testFile = file;
      testFileList = new DotPlotFileList();
      testFileList.add(file);
      testFileList.add(file);
   }

   /**
    * provide a plottable file as program argument
    * the dotplot will represent a selfcomparison
    */
   public static void main(String[] args)
   {
      OverallTimeTest test = new OverallTimeTest(new File(args[0]));
      test.completeRun();
   }

   /**
    * <code>completeRun</code> builds a dotplot from provided files
    */
   public void completeRun()
   {
      DotplotCreator dp = new DotplotCreator();
      IDotplot plot;
      dp.setFileList(testFileList);

      try
      {
         plot = dp.getDotplot();
         plot.getImage(ImageData.class);
      }
      catch (TokenizerException te)
      {
         te.printStackTrace();
      }
   }
}
