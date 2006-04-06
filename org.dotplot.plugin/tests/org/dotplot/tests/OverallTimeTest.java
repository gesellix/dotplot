package org.dotplot.tests;

import java.io.File;

import org.eclipse.swt.graphics.ImageData;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IDotplot;
import org.dotplot.eclipse.views.DotPlotFileList;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.util.UnknownIDException;

/**
 * <code>OverallTimeTest</code> provides a mainclass for profiling without
 * starting a workbench
 * 
 * @author Sascha Hemminger
 */
public class OverallTimeTest {
	private DotplotFile testFile;

	private DotPlotFileList testFileList;

	public OverallTimeTest(File file) {
		this.testFile = new DotplotFile(file);
		testFileList = new DotPlotFileList();
		testFileList.add(this.testFile);
		testFileList.add(this.testFile);
	}

	/**
	 * provide a plottable file as program argument the dotplot will represent a
	 * selfcomparison
	 */
	public static void main(String[] args) {
		OverallTimeTest test = new OverallTimeTest(new File(args[0]));
		test.completeRun();
	}

	/**
	 * <code>completeRun</code> builds a dotplot from provided files
	 */
	public void completeRun() {
		DotplotContext context = ContextFactory.getContext();
		// DotplotCreator dp = new DotplotCreator();
		IDotplot plot;
		context.setSourceList(testFileList);

		try {
			context.executeJob("org.dotplot.jobs.PlotterJob");
			plot = context.getCurrentDotplot();
			plot.getImage(ImageData.class);
		}
		catch (TokenizerException te) {
			te.printStackTrace();
		}
		catch (UnknownIDException e) {
			e.printStackTrace();
		}
	}
}