/*
 * Created on 23.06.2004
 */
package org.dotplot.image.ui;

import java.io.IOException;
import java.util.Observable;

import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.UnknownIDException;

/**
 * Controller for the configuration of the imaging module.
 */
public class ConfigQImageController extends ViewController {
	/**
	 * Create a new controller with the given DotplotCreator object and a
	 * special ConfigurationView.
	 * 
	 * @param dotplotCreator
	 *            the main controller
	 * @param cv
	 *            the desired ConfigurationView
	 */
	public ConfigQImageController(ConfigurationView cv) {
		super(cv);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ConfigQImageView) {
			ConfigQImageView qImageView = (ConfigQImageView) o;

			try {
				IConfigurationRegistry registry = qImageView.getRegistry();

				try {
					IQImageConfiguration config = (QImageConfiguration) registry
							.get(QImageService.QIMAGE_CONFIGURATION_ID);
					config.setExportDotplotToFile(qImageView.isExportDotplot());
					config.setExportFormat(qImageView.getSelectedFormat());
					config.setExportFilename(qImageView.getExportFileName()
							.getCanonicalPath());
					config.setUseLUT(qImageView.useLUT());
					config.setLut(qImageView.getChoosenLut(), qImageView
							.getBackground(), qImageView.getForeground());
					config.setScaleMode(qImageView.getScaleMode());
					config.setScaleUp(qImageView.doScaleUp());
					config.setShowFileSeparators(qImageView
							.showFileSeparators());
					config.setUseInformationMural(qImageView.useInfoMural());
				}
				catch (UnknownIDException e) {
					e.printStackTrace();
					// mist
				}

			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
