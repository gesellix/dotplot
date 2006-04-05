package org.dotplot.image;

import org.apache.log4j.Logger;

import java.io.File;

import org.dotplot.core.IDotplot;
import org.dotplot.fmatrix.ITypeTableNavigator;

/**
 * The QImage gets the 2D-Floatingpoint-Array of the QMatrix and paints it on
 * the screen. The maxX value represents the width of the Matrix, the maxY value
 * the heigth.
 * 
 * @author Tobias Gesellchen
 */
public class QImage {
	private static Logger logger = Logger.getLogger(QImage.class.getName());

	private Dotplot dotplot = null;

	private int progress = 0;

	private int currentStep = 1;

	private IQImageConfiguration config;

	/**
	 * Initialization of the QImage controller with an ITypeTableNavigator
	 * containing the current Dotplot data.
	 * 
	 * @param nav
	 *            navigator for the current data
	 */
	public QImage(ITypeTableNavigator nav, IQImageConfiguration config) {
		logger.debug("lib-path:   " + System.getProperty("java.library.path"));
		logger.debug("user-dir:   " + System.getProperty("user.dir"));

		this.config = config;

		dotplot = new Dotplot(nav, this);
		runGC();
	}

	/**
	 * returns the dotplot for the currently set ITypeTableNavigator.
	 * 
	 * @return the dotplot
	 */
	public IDotplot getDotplot() {
		return getDotplot(false);
	}

	private IDotplot getDotplot(boolean onlyExport) {
		// IQImageConfiguration config = (IQImageConfiguration)
		// GlobalConfiguration.getInstance().get(
		// GlobalConfiguration.KEY_IMG_CONFIGURATION);

		if (config.isExportDotplotToFile()) {
			// TODO remove "invalid thread access" on monitor when ownThread is
			// set to true
			saveDotplot(dotplot, false, config);

			if (onlyExport) {
//				update(100, IDotplot.STEP_CONVERT_DATA);
				return null;
			}
		}

		return dotplot;
	}

	/**
	 * exports the dotplot to the file, that has been set in the plugin
	 * configuration.
	 * 
	 * @param dotplot
	 *            the dotplot to be exported
	 * @param useOwnThread
	 *            export in an own Thread to minimize blocking of the whole
	 *            application
	 */
	public static void saveDotplot(IDotplot dotplot, boolean useOwnThread,
			IQImageConfiguration config) {
		// IQImageConfiguration config = (IQImageConfiguration)
		// GlobalConfiguration.getInstance().get(
		// GlobalConfiguration.KEY_IMG_CONFIGURATION);

		String formatSelection = JAITools.EXPORT_FORMATS[config
				.getExportFormat()];
		String exportFileName = config.getExportFilename();
		int[][] currentLut = config.getLut();

		JAITools.saveDotplot(dotplot, new File(exportFileName + '.'
				+ formatSelection), formatSelection, currentLut,config.isOnlyExport(), useOwnThread);
	}

	/**
	 * Invokes the Garbage Collector to get more available memory.
	 */
	static void runGC() {
		Runtime rt = Runtime.getRuntime();
		rt.gc();
		if (logger.isDebugEnabled()) {
			logger.debug("Free Mem: " + rt.freeMemory());
		}
	}

	void update(int diff, int curStep) {
		update(diff, curStep, null);
	}

	void update(int diff, int curStep, String msg) {
		this.currentStep = curStep;
		this.progress += diff;
		this.progress = Math.min(100, progress);

		if (logger.isDebugEnabled()) {
			logger.debug(msg);
			logger.debug("Progress -- " + progress + " "
					+ IDotplot.STEPS[currentStep]);
		}

	}

	/**
	 * Returns the config.
	 * 
	 * @return - the config.
	 */
	public IQImageConfiguration getConfiguration() {
		return config;
	}

	/**
	 * Sets the config.
	 * 
	 * @param config
	 *            The config to set.
	 */
	public void setConfiguration(IQImageConfiguration config) {
		this.config = config;
	}
}
