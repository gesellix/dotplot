package org.dotplot.eclipse.actions;

import java.io.File;

import org.apache.log4j.Logger;
import org.dotplot.DotplotCreator;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IDotplot;
import org.dotplot.eclipse.views.DotPlotter;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.PlotJob;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.util.FileUtil;
import org.dotplot.util.UnknownIDException;
import org.eclipse.swt.widgets.Shell;

/**
 * <code>PlotJobAction</code> is there to provide the export of a PlotJob.
 * 
 * @author Tobias Gesellchen
 */
public class PlotJobAction extends PlotAction {
	private static final Logger logger = Logger.getLogger(PlotJobAction.class
			.getName());

	/**
	 * exports the currently loaded TypeTable into a file. The user will be
	 * asked for the destination.
	 * 
	 * @param dotplotCreator
	 *            link to get the TypeTable through the FMatrixController
	 * @param shell
	 *            to be used as parent for the file selection dialog
	 */
	public static void savePlotJob(Shell shell) {
		DotplotContext context = ContextFactory.getContext();
		IQImageConfiguration config;
		try {
			config = (IQImageConfiguration) context.getConfigurationRegistry()
					.get(QImageService.QIMAGE_CONFIGURATION_ID);
		}
		catch (UnknownIDException e) {
			config = new QImageConfiguration();
		}

		GridConfiguration gridconfig;
		try {
			gridconfig = (GridConfiguration) ContextFactory.getContext()
					.getConfigurationRegistry().get(
							QImageService.ID_GRID_CONFIGURATION);
		}
		catch (UnknownIDException e) {
			gridconfig = new GridConfiguration();
		}

		PlotJob plot = new PlotJob(config, gridconfig, context
				.getCurrentTypeTable());

		String filename = FileUtil.showFileDialog(shell,
				"Select a target file or enter a file name", new String[] {
						"*.dgz", "*" });
		if (filename != null) {
			File file = new File(filename);

			logger.info("export PlotJob to " + file.getAbsolutePath());
			FileUtil.exportPlotJob(plot, file);
		}
	}

	protected void createAndShowPlot(DotplotCreator dotplotCreator,
			DotPlotter view) {
		IDotplot dplot = initDotplot(true);

		if (dplot == null) {
			logger.error("Dotplot could not be created!");
		}
		else {
			savePlotJob(view.getParent().getShell());
		}
	}
}
