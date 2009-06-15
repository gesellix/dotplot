package org.dotplot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;
import org.dotplot.grid.PlotJob;
import org.dotplot.image.IQImageConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides several static methods for file handling, like im-/export or display
 * of Dialogs.
 * 
 * @author Tobias Gesellchen
 */
public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class
	    .getName());

    /**
     * Writes the given Object GZIP-compressed into the given OutputStream.
     * 
     * @param os
     *            target
     * @param obj
     *            source
     * 
     * @throws IOException
     *             if an error occured
     */
    public static void exportGZIPed(OutputStream os, Object obj)
	    throws IOException {
	ObjectOutputStream oos = new ObjectOutputStream(
		new GZIPOutputStream(os));
	oos.writeObject(obj);
	oos.close();
    }

    /**
     * Writes the plot to the file.
     * 
     * @param plot
     *            source
     * @param file
     *            target
     */
    public static void exportPlotJob(PlotJob plot, File file) {
	try {
	    exportGZIPed(new FileOutputStream(file), plot);
	} catch (Exception e) {
	    logger.error("error exporting PlotJob", e);
	    return;
	}

	logger.debug("PlotJob export successful");
    }

    /**
     * Writes the current QImageConfiguration from the GlobalConfiguration to
     * the given File.
     * 
     * @param file
     *            target
     */
    public static void exportQImageConfig(File file, IQImageConfiguration config) {
	try {
	    exportGZIPed(new FileOutputStream(file), config);
	} catch (Exception e) {
	    logger.error("error exporting QImageConfiguration", e);
	    return;
	}

	logger.debug("QImageConfiguration export successful");
    }

    /**
     * Reads a GZIP-compressed Object from the given InputStream.
     * 
     * @param is
     *            source
     * 
     * @return the Object from the InputStream
     * 
     * @throws IOException
     *             if an error occured
     * @throws ClassNotFoundException
     *             if no serialized Object could be found
     */
    public static Object importGZIPed(InputStream is) throws IOException,
	    ClassNotFoundException {
	ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(is));
	Object o = ois.readObject();
	ois.close();
	return o;
    }

    // public static void testExportImport(ITypeTableNavigator navigator)
    // {
    // // TODO EXPORT--IMPORT Tamerza-Palace
    //
    // File typeTable1 = new File("types1.dat.gz");
    // File typeTable2 = new File("types2.dat.gz");
    //
    // if (typeTable2.exists())
    // {
    // logger.debug("typeTable2 exists -> delete typeTable1");
    //
    // if (typeTable1.exists())
    // {
    // if (!typeTable1.delete())
    // {
    // logger.debug("delete on typeTable1 failed.");
    // }
    // else
    // {
    // logger.debug("deleted.");
    // }
    // }
    // }
    //
    // try
    // {
    // if (typeTable1.exists())
    // {
    // logger.debug("typeTable1 exists");
    //
    // OutputStream os = new FileOutputStream(typeTable2);
    // InputStream is = new FileInputStream(typeTable1);
    //
    // logger.debug("export to " + typeTable2.getCanonicalPath());
    // exportGZIPed(os, navigator.getTypeTable());
    //
    // logger.debug("import from " + typeTable1.getCanonicalPath());
    // navigator.setTypeTable((TypeTable) importGZIPed(is));
    // }
    // else
    // {
    // logger.debug("typeTable1 does not exist");
    //
    // OutputStream os = new FileOutputStream(typeTable1);
    //
    // logger.debug("export to " + typeTable1.getCanonicalPath());
    // exportGZIPed(os, navigator.getTypeTable());
    // }
    // }
    // catch (Exception e)
    // {
    // logger.error("error occured during import/export", e);
    // }
    // }

    /**
     * Reads a PlotJob from the given file.
     * 
     * @param file
     *            source
     * 
     * @return the PlotJob
     */
    public static PlotJob importPlotJob(File file) {
	PlotJob plot = null;
	try {
	    plot = (PlotJob) importGZIPed(new FileInputStream(file));
	} catch (Exception e) {
	    logger.error("error importing PlotJob", e);
	    return null;
	}

	logger.debug("PlotJob import successful");
	return plot;
    }

    /**
     * Reads a QImageConfiguration from the given file and updates the
     * GlobalConfiguration.
     * 
     * @param file
     *            source
     */
    public static IQImageConfiguration importQImageConfig(File file) {
	IQImageConfiguration config = null;
	try {
	    config = (IQImageConfiguration) importGZIPed(new FileInputStream(
		    file));
	} catch (Exception e) {
	    logger.error("error importing QImageConfiguration", e);
	}

	if (config != null) {
	    logger.debug("QImageConfiguration import successful");
	}
	return config;
    }

    /**
     * Displays a SWT DirectoryDialog.
     * 
     * @param shell
     *            the shell.
     * @param title
     *            the title.
     * 
     * @return the selected directory name or null, if the user clicked
     *         "cancel".
     */
    public static String showDirectoryDialog(Shell shell, String title) {
	DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
	dialog.setText(title);
	return dialog.open();
    }

    /**
     * Displays a SWT FileDialog.
     * 
     * @param shell
     *            the shell.
     * @param title
     *            the title.
     * @param filterExtensions
     *            list of extensions to filter the displayed files. May be null.
     * 
     * @return the selected file name or null, if the user clicked "cancel".
     */
    public static String showFileDialog(Shell shell, String title,
	    String[] filterExtensions) {
	FileDialog dialog = new FileDialog(shell, SWT.OPEN);
	dialog.setFilterExtensions(filterExtensions);
	dialog.setText(title);
	return dialog.open();
    }
}
