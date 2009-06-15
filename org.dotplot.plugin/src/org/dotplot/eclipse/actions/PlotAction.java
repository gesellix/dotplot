package org.dotplot.eclipse.actions;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.IDotplot;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.eclipse.perspective.DotPlotPerspective;
import org.dotplot.eclipse.views.DotPlotNavigator;
import org.dotplot.eclipse.views.DotPlotter;
import org.dotplot.eclipse.views.MergeView;
import org.dotplot.grid.GridCallback;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.grid.GridPlotter;
import org.dotplot.grid.PlotJob;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.JAITools;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageService;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.DefaultFilterConfiguration;
import org.dotplot.tokenizer.filter.FilterService;
import org.dotplot.tokenizer.filter.IFilterConfiguration;
import org.dotplot.ui.monitor.DotPlotProgressMonitor;
import org.dotplot.util.FileCopy;
import org.dotplot.util.FileUtil;
import org.dotplot.util.MailUtil;
import org.dotplot.util.UnknownIDException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.WorkbenchException;

/**
 * <code>PlotAction</code> creates a DotPlot from all checked files in the
 * navigator.
 * 
 * @author Sascha Hemminger & Roland Helmrich
 * @see IWorkbenchWindowActionDelegate
 */
public class PlotAction implements IWorkbenchWindowActionDelegate,
	SelectionListener, GridCallback {
    private IWorkbenchWindow window;

    protected static final String EMPTY_SELECTION = "Nothing to plot, select a plottable file in the DotPlot Navigator first!";

    protected static final String ERROR_CREATING_VIEWS = "Error while creating views. Please try to close and reopen this perspective.";

    protected static final String ERROR_PARSING_FILES = "Error while parsing files.";

    protected static final String ERROR_UNKNOWN_PLOTTER_JOB = "No Plotterjob registered to the system.";

    private static final Logger logger = Logger.getLogger(PlotAction.class
	    .getName());

    private void aggregateAndShowPlot(Map gridImages, Dimension size) {
	DotPlotter plotter = (DotPlotter) window.getActivePage().findView(
		DotPlotPerspective.DOTPLOTTER);

	ImageData imgData = null;
	try {
	    imgData = new ImageData(JAITools.aggregateImages(gridImages, size,
		    false).getAbsolutePath());
	} catch (IOException e) {
	    logger.error("Error aggregating image(s)", e);
	}

	if (imgData == null) {
	    showMessage("An error occured when aggregating the image(s) from grid!");
	}

	final float scale = (float) size.width
		/ (float) ContextFactory.getContext().getCurrentTypeTable()
			.createNavigator().getSize().width;

	plotter.showImage(imgData);
	plotter.setMouseMoveListener(new DotPlotMouseMoveListener(window,
		imgData, plotter, scale));
    }

    protected void createAndShowPlot(DotPlotter view) {
	DotplotContext context = ContextFactory.getContext();

	IQImageConfiguration tempConfig;
	try {
	    tempConfig = (IQImageConfiguration) context
		    .getConfigurationRegistry().get(
			    QImageService.QIMAGE_CONFIGURATION_ID);
	} catch (UnknownIDException e1) {
	    tempConfig = new QImageConfiguration();
	}
	final IQImageConfiguration imageConfig = tempConfig;

	IDotplot dplot = initDotplot(imageConfig.isOnlyExport());

	if (dplot == null) {
	    view.showImage(null);
	} else {

	    GridConfiguration config;
	    try {
		config = (GridConfiguration) context.getConfigurationRegistry()
			.get(QImageService.ID_GRID_CONFIGURATION);
	    } catch (UnknownIDException e) {
		config = new GridConfiguration();
	    }

	    final GridConfiguration gridConfig = config;

	    boolean isGridWorking = false;
	    if (gridConfig.isGridActive()) {
		logger.info("grid active");

		PlotJob plotJob = new PlotJob(imageConfig, context
			.getCurrentTypeTable());

		try {
		    isGridWorking = GridPlotter.createPlot(plotJob, this);
		} catch (ConnectionException e) {
		    logger.error("Error on trying to plot over grid", e);
		    isGridWorking = false;
		}
	    }

	    if (isGridWorking) {
		logger.info("waiting for image(s) from grid...");
	    } else {
		logger.info("grid not active");

		// Grid not ready - use local resources
		float scale = 1;
		if (imageConfig.getScaleMode() == 0) {
		    scale = dplot.setTargetSize(context.getCurrentTypeTable()
			    .createNavigator().getSize());
		} else {
		    scale = dplot.setTargetSize(view.getSize());
		}

		final ImageData data = (ImageData) dplot
			.getImage(IDotplot.IMG_SWT_IMAGEDATA);
		IFilterConfiguration filterconfig;
		try {
		    filterconfig = (IFilterConfiguration) context
			    .getConfigurationRegistry().get(
				    FilterService.FILTER_CONFIGURATION_ID);
		} catch (UnknownIDException e) {
		    filterconfig = new DefaultFilterConfiguration();
		}

		if (filterconfig.getFilterList().size() > 0) {
		    view.removeMouseMoveListener();
		} else {
		    view.setMouseMoveListener(new DotPlotMouseMoveListener(
			    window, data, view, scale));
		}
		view.showImage(data);
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {
    }

    private void handleEmptyFileList(DotplotContext context) throws Exception {
	if (MessageDialog.openQuestion(window.getShell(), "No files selected",
		"Open PlotJob from file?")) {
	    // no files selected -> try loading TypeTable from file
	    String filename = FileUtil.showFileDialog(window.getShell(),
		    "Select a PlotJob or press \"Cancel\"", new String[] {
			    "*.dgz", "*" });

	    if (filename != null) {
		File file = new File(filename);
		logger.info("using Plot from file " + file.getAbsolutePath());
		context.setCurrentTypeTable(FileUtil.importPlotJob(file)
			.getTypeTable());
	    } else {
		throw new Exception("empty selection");
	    }
	} else {
	    throw new Exception("empty selection");
	}
    }

    /**
     * Will be called from the grid when all image parts are ready.
     * 
     * @param gridImages
     *            Map of clients to their image parts
     * @param size
     *            complete target size of the image
     */
    public void imagesReceived(final Map gridImages, final Dimension size) {
	logger.info("got complete image -> aggregate and show it");

	DotplotContext context = ContextFactory.getContext();

	GridConfiguration config = null;
	try {
	    context.getConfigurationRegistry().get(
		    QImageService.ID_GRID_CONFIGURATION);
	} catch (UnknownIDException e) {
	    config = new GridConfiguration();
	}

	final GridConfiguration gridConfig = config;

	DotPlotProgressMonitor.getInstance().close();

	final Shell shell = window.getShell();

	if (gridConfig.isEnableNotification()) {
	    logger.debug("Using email notification.");

	    // plotted files
	    final ISourceList iFileList = context.getSourceList();

	    StringBuffer message = new StringBuffer(
		    "Your plot has been finished.");
	    if (iFileList != null) {
		message.append("\n\nThe following files have been plotted:");

		for (IPlotSource source : iFileList) {
		    message.append('\n').append(source.toString());
		}
	    }

	    try {
		if (gridConfig.isNotifyAttachImage()) {
		    Vector indexVector = new Vector();
		    Iterator indexIter = gridImages.keySet().iterator();
		    while (indexIter.hasNext()) {
			indexVector.add(indexIter.next());
		    }
		    Integer[] indices = (Integer[]) indexVector
			    .toArray(new Integer[0]);
		    Arrays.sort(indices);

		    File[] attachments = new File[indices.length];
		    String[] filenames = new String[indices.length];
		    for (int i = 0; i < indices.length; i++) {
			Integer index = indices[i];
			attachments[i] = (File) gridImages.get(index);
			final String sourceFile = attachments[i]
				.getAbsolutePath();
			filenames[i] = "gridPlot_"
				+ index
				+ sourceFile.substring(sourceFile
					.lastIndexOf('.'));
		    }

		    MailUtil.postMailBySMTP(gridConfig.getNotifySMTPHost(),
			    gridConfig.getNotifySMTPUser(), gridConfig
				    .getNotifySMTPPass(), gridConfig
				    .getNotifyEmailFrom(), gridConfig
				    .getNotifyEmailTo(), gridConfig
				    .getNotifyEmailSubject(), message
				    .toString(), attachments, filenames);
		} else {
		    MailUtil.postMailBySMTP(gridConfig.getNotifySMTPHost(),
			    gridConfig.getNotifySMTPUser(), gridConfig
				    .getNotifySMTPPass(), gridConfig
				    .getNotifyEmailFrom(), gridConfig
				    .getNotifyEmailTo(), gridConfig
				    .getNotifyEmailSubject(), message
				    .toString());
		}

		logger.info("Notification mail sent.");
	    } catch (final MessagingException msgExc) {
		shell.getDisplay().syncExec(new Runnable() {
		    public void run() {
			MessageDialog.openError(shell, "Notification failed!",
				msgExc.getMessage());
		    }
		});
		logger.warn("Mail notification failed", msgExc);
	    }
	}

	shell.getDisplay().syncExec(new Runnable() {
	    public void run() {
		if (MessageDialog
			.openQuestion(
				shell,
				"Plot finished",
				"The image parts from grid are available. Aggregate them and show plot?\n\n"
					+ "Aggregation will need much memory and CPU time, "
					+ "if you've got an image manipulation program you may combine the parts yourself.\n\n"
					+ "In this case, choose \"no\" and you will be asked where to save the image parts. "
					+ "Attention: existing files (\"gridPlot_*\") will be overwritten!")) {
		    aggregateAndShowPlot(gridImages, size);
		} else {
		    moveImageParts(gridImages);
		}
	    }
	});
    }

    // private boolean isFilterEnabled(ITokenFilter tokenFilter)
    // {
    // if (tokenFilter instanceof TokenFilterContainer)
    // {
    // ITokenFilter[] filters = ((TokenFilterContainer)
    // tokenFilter).getTokenFilters();
    // for (int i = 0; i < filters.length; i++)
    // {
    // if (isFilterEnabled(filters[i]))
    // {
    // return true;
    // }
    // }
    // }
    // else if (tokenFilter != null)
    // {
    // return true;
    // }
    //
    // return false;
    // }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
     * IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {
	this.window = window;
    }

    protected IDotplot initDotplot(boolean onlyForExport) {
	DotplotContext context = ContextFactory.getContext();
	IDotplot dplot = null;
	String jobID = "org.dotplot.jobs.PlotterJob";
	try {
	    // update DotplotContext with current file selection
	    if (!updateFileList(context)) {
		try {
		    handleEmptyFileList(context);
		    // typetable is set
		    jobID = "org.dotplot.jobs.ImagerJob";
		} catch (Exception e) {
		    // loading failed -> tell user to select files in the
		    // navigator
		    showMessage(EMPTY_SELECTION);
		    return null;
		}
	    }

	    // initialize IDotplot through file(s) or TypeTable
	    if (context.executeJob(jobID)) {
		dplot = context.getCurrentDotplot();
	    } else {
		logger.error("error while processing plotterjob");
		return null;
	    }
	} catch (TokenizerException te) {
	    logger.error("error while parsing files", te);
	    showMessage(ERROR_PARSING_FILES);
	    return null;
	} catch (UnknownIDException e) {
	    logger.error("unknown plotter job", e);
	    showMessage(ERROR_UNKNOWN_PLOTTER_JOB);
	    return null;
	}

	return dplot;
    }

    private void moveImageParts(Map gridImages) {
	String targetfolder = FileUtil.showDirectoryDialog(window.getShell(),
		"Select a target folder or press \"Cancel\"");

	if (targetfolder != null) {
	    File file = new File(targetfolder);
	    logger.info("moving image(s) to " + file.getAbsolutePath());

	    try {
		Vector indexVector = new Vector();
		Iterator indexIter = gridImages.keySet().iterator();
		while (indexIter.hasNext()) {
		    indexVector.add(indexIter.next());
		}
		Integer[] indices = (Integer[]) indexVector
			.toArray(new Integer[0]);
		Arrays.sort(indices);

		for (int i = 0; i < indices.length; i++) {
		    Integer index = indices[i];

		    final String sourceFile = ((File) gridImages.get(index))
			    .getAbsolutePath();

		    // break if a problem occurs, overwrite older files
		    FileCopy.copy(sourceFile,
			    targetfolder
				    + File.separatorChar
				    + "gridPlot_"
				    + index
				    + sourceFile.substring(sourceFile
					    .lastIndexOf('.')), false, 1);
		}
		logger.info("ready.");
	    } catch (FileNotFoundException e) {
		logger.error("File not found!", e);
	    } catch (IOException e) {
		logger.error("I/O Exception", e);
	    }
	} else {
	    // ignore
	    // throw new IllegalArgumentException("empty selection");
	}
    }

    /**
     * <code>plot</code> is THE action that generates the dotplot.
     */
    protected void plot() {
	logger.debug("starting plot");

	// initialise perspective with needed views
	if (!showViews()) {
	    // an error occurred, break operation
	    return;
	}

	// DotplotCreator dotplotCreator = GlobalConfiguration.getInstance()
	// .getDotplotCreator();

	DotPlotter plotterV = (DotPlotter) window.getActivePage().findView(
		DotPlotPerspective.DOTPLOTTER);
	MergeView mergerV = (MergeView) window.getActivePage().findView(
		DotPlotPerspective.DOTPLOTDIFF);

	plotterV.setMouseListener(new DotPlotMouseListener(window, mergerV));

	createAndShowPlot(plotterV);
    }

    /**
     * Starts the plot.
     * 
     * @param action
     *            the action proxy
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
	plot();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }

    private void showMessage(String text) {
	DotplotContext context = ContextFactory.getContext();
	try {
	    context.getGuiService().showErrorMessage("Dotplot", text);
	} catch (UnknownIDException e) {
	    // dieser aufruf kann eigentlich raus, aber bleibt zur sicherheit
	    // nochmal drin
	    MessageDialog.openInformation(window.getShell(), "DotPlot", text);
	}
    }

    private boolean showViews() {
	try {
	    window.getWorkbench().showPerspective(
		    "org.dotplot.plugin.perspective1", window);

	    window.getActivePage().showView(DotPlotPerspective.DOTPLOTNAV);
	    window.getActivePage().showView(DotPlotPerspective.DOTPLOTDIFF);
	    window.getActivePage().showView(DotPlotPerspective.DOTPLOTTER);
	} catch (WorkbenchException e) {
	    logger.error("error while creating views", e);

	    showMessage(ERROR_CREATING_VIEWS);
	    return false;
	}

	return true;
    }

    private boolean updateFileList(DotplotContext context) {
	DotPlotNavigator navigatorV = (DotPlotNavigator) window.getActivePage()
		.findView(DotPlotPerspective.DOTPLOTNAV);
	if (navigatorV.isEmpty()) {
	    return false;
	}

	if (navigatorV.isDirty()) {
	    context.setSourceList(navigatorV.getFileList());
	    navigatorV.setNotDirty();
	}

	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent event) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent event) {
	this.plot();
	Control control = (Control) event.widget;
	if (!control.isDisposed()) {
	    Shell shell = control.getShell();
	    if (!shell.isDisposed()) {
		shell.dispose();
	    }
	}
    }
}
