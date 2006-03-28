/*
 * Created on 14.12.2004
 */
package org.dotplot.eclipse.actions.contextMenu;

import org.apache.log4j.Logger;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IDotplot;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.JAITools;
import org.dotplot.image.QImage;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.util.FileUtil;
import org.dotplot.util.UnknownIDException;

/**
 * Action to export a displayed image into an image file.
 * 
 * @author Tobias Gesellchen
 */
public class ExportAction extends Action {

	private IWorkbenchWindow parent;

	/**
	 * create the action.
	 * 
	 * @param text
	 *            the title to be displayed on the context menu
	 * @param creator
	 *            link to get the current dotplot
	 * @param parent
	 *            the parent
	 */
	public ExportAction(String text, IWorkbenchWindow parent) {
		this.parent = parent;
		setText(text);
	}

	/**
	 * asks the user for the target file and exports the dotplot.
	 */
	public void run() {
		DotplotContext context = ContextFactory.getContext();
		IQImageConfiguration config;
		try {
			config = (IQImageConfiguration) context.getConfigurationRegistry()
					.get(QImageService.ID_CONFIGURATION_QIMAGE);
		}
		catch (UnknownIDException e) {
			config = new QImageConfiguration();
		}
		
		IDotplot dotplot = context.getCurrentDotplot(); 
		if (dotplot != null) {
			String filename = FileUtil.showFileDialog(parent.getShell(),
					"Select a target file or enter a file name",
					new String[] { "." + JAITools.EXPORTFORMAT_JPEG });
			if (filename != null) {
				File file = new File(filename);

				Logger.getLogger(ExportAction.class.getName()).info(
						"export image to " + file.getAbsolutePath());

				final int endIndex = filename.lastIndexOf("."
						+ JAITools.EXPORT_FORMATS[config.getExportFormat()]);
				String name = filename;
				if (endIndex != -1) {
					name = filename.substring(0, endIndex);
				}
				config.setExportFilename(name);
				config.setExportFormat(JAITools.JPG); // JPEG

				QImage.saveDotplot(dotplot, false, config);
			}
		}
	}
}
