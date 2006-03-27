/*
 * Created on 12.05.2004
 */
package org.dotplot;

import org.apache.log4j.Logger;

import java.io.File;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IDotplot;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.GridPlotter;
import org.dotplot.grid.PlotJob;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.QImage;
import org.dotplot.util.FileUtil;
import org.dotplot.util.UnknownIDException;

/**
 * Commandline entrypoint into the dotplot system.
 * @author Christian Gerhardt <Christian Gerhardt>
 */
public final class DotplotCreator {
	
	/**
	 * Logger for debug informations.
	 */
	private static Logger logger = Logger.getLogger(DotplotCreator.class
			.getName());

//	/**
//	 * Der aktuelle Dotplot.
//	 */
//	private static IDotplot dotplot;

//	/**
//	 * <code>true</code>, wenn sich an den Einstellungen was geaendert hat
//	 */
//	private static boolean dirty = true;

//	private static boolean navigatorReady = false;

	public final static String VM_PARAM_IMG_JAI_ENABLED = "image.highquality";

	public final static String VM_PARAM_GRID_MAXEDGE = "grid.maxedge";

	public final static String VM_PARAM_GRID_ENABLED = "grid.enabled";

	public final static String VM_PARAM_GRID_NOTIFY = "grid.notify";

	public final static String VM_PARAM_HEADLESS = "headless";
	
	/**
	 * Creates a new <code>DotplotCreator</code>.
	 * <p>
	 * Private to prevent the creation of objects.
	 * </p>
	 */
	private DotplotCreator(){}
	
	/**
	 * Entry point to convert a given PlotJob directly to an image.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			System.err.println("Usage: " + DotplotCreator.class.getName()
					+ " <PlotJob filename> <Destination image>");
			System.exit(1);
		}

		processPlotJob(args[0], args[1]);
	}

	/**
	 * Converts a PlotJob in <code>source</code> into an image and saves the
	 * result in <code>destination</code>.
	 * 
	 * @param source
	 *            filename of a plottable PlotJob
	 * @param destination
	 *            filename for the destination image
	 */
	public static void processPlotJob(final String source,
			final String destination) {
		DotplotContext context = ContextFactory.getContext();

		final File plotjobFile = new File(source);
		PlotJob plot = FileUtil.importPlotJob(plotjobFile);
		logger.info("PlotJob: " + plotjobFile.getAbsolutePath());

		IQImageConfiguration imgConfig = plot.getImageConfig();
		GridConfiguration gridConfig = plot.getGridConfig();

		if (destination == null || destination.equals(".")) {
			String parent = plotjobFile.getParent();
			imgConfig.setExportFilename((parent != null ? parent : "")
					+ "plotjob");
		}
		else {
			imgConfig.setExportFilename(destination);
		}

		// imgConfig.setExportFormat(2);
		// imgConfig.setLut("blue_yellow", null, null);
		// imgConfig.setLut("sin_rgb", null, null);

		imgConfig.setExportDotplotToFile(false);
		imgConfig.setOnlyExport(true);

		context.setCurrentTypeTable(plot.getTypeTable());
		try {
			context.executeJob("org.dotplot.jobs.ImagerJob");
			IDotplot dotplot = context.getCurrentDotplot();
			if (dotplot != null) {
				if (gridConfig.isGridActive()) {
					logger.debug("using grid");
					try {
						if (!GridPlotter.createPlot(plot, null)) {
							// Grid not ready - use local resources
							QImage.saveDotplot(dotplot, false, imgConfig);
						}
					}
					catch (ConnectionException e) {
						logger.error("Error on trying to plot over grid", e);
					}
				}
				else {
					QImage.saveDotplot(dotplot, false, imgConfig);
				}
			}
		}
		catch (UnknownIDException e1) {
			logger.error("PlotterJob is unknown", e1);
		}
	}

//	/**
//	 * Setzt die aktuelle Dateiliste.
//	 * 
//	 * @param fileList -
//	 *            die Dateiliste
//	 */
//	public static void setFileList(ISourceList sourceList) {
//		DotplotContext context = ContextFactory.getContext();
//		context.setSourceList(sourceList);
//		setDirty();
//	}

//	/**
//	 * Update Navigator and corresponding TypeTable.
//	 */
//	public static void setTypeTable(TypeTable typeTable) {
//		DotplotContext context = ContextFactory.getContext();
//		if (typeTable != null) {
//			navigatorReady = true;
//			context.setCurrentTypeTable(typeTable);
//		}
//		else {
//			context.setCurrentTypeTable(null);
//			navigatorReady = false;
//		}
//		setDirty();
//	}

//	/**
//	 * Generiert einen Dotplot. Der Dotplot wird zwischengespeichert, und wieder
//	 * ausgegeben solange sich an den Einstellungen nichts geändert hat. Nur bei
//	 * neuen Einstellungen wird ein neuer Dotplot erstellt.
//	 * 
//	 * @return der Dotplot
//	 */
//	public static IDotplot get_Dotplot(boolean onlyExport) {
//		if (dirty) {
//			DotplotContext context = ContextFactory.getContext();
//			if (!navigatorReady) {								
//				logger.debug("creating PlotterJob");
//				PlotterJob job = new PlotterJob();
//				
//				if (context.executeJob(job)) {
//					dotplot = context.getCurrentDotplot();
//					logger.debug("setting dotplot");
//				}
//				else {
//					logger.fatal("processing failed");
//					logger.fatal(job.getErrorHandler().toString());
//				}
//				
////				logger.debug("init navigator...");
////
////				// only 2 modules in progress bar - tokenizer is fast enough,
////				// doesn't need any progress indicator
////				DotPlotProgressMonitor.getInstance().showProgress(1);
////
////				initNavigator(); // throws TokenizerException
//			}
//			else {
////				logger.debug("don't init navigator");
//
//				// if not "headless", enable DotPlotProgressMonitor
//				if (!onlyExport) {
//					// only 1 module ("QImage"), since the others won't be used
//					DotPlotProgressMonitor.getInstance().showProgress(1);
//				}
//				
//				logger.debug("creating ImagerJob");
//				ImagerJob job = new ImagerJob();
//				if (context.executeJob(job)) {
//					dotplot = context.getCurrentDotplot(); 
//					logger.debug("setting dotplot");
//				}
//				else {
//					logger.fatal("processing failed");
//					logger.fatal(job.getErrorHandler().toString());
//				}
//			}
//
//			dirty = false;
//		}
//		return dotplot;
//	}

//	/**
//	 * Setzt das Dirtybit.
//	 * 
//	 * @see org.dotplot.IDotplotCreator#setDirty()
//	 */
//	public static void setDirty() {
//		dirty = true;
//	}
}
