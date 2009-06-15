package org.dotplot.grid;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dotplot.core.ContextFactory;
import org.dotplot.grid.framework.ConnectionException;
import org.dotplot.grid.framework.Identity;
import org.dotplot.grid.framework.MessageCollaborator;
import org.dotplot.image.QImageService;
import org.dotplot.util.FileUtil;
import org.dotplot.util.UnknownIDException;

/**
 * Represents the controller when a plot is done over the grid.
 * 
 * @author Tobias Gesellchen
 */
public class GridPlotter {
    private static GridCallback callback;

    private static PlotJob plot;

    private static Map gridImages;

    private static int connectedClients = 0;

    private static MessageCollaborator bigBoss = null;

    private static final Logger logger = Logger.getLogger(GridPlotter.class
	    .getName());

    // public static void main(final String[] args) throws ConnectionException
    // {
    // String localAddress = args[0];
    // int localPort = Integer.parseInt(args[1]);
    // final File file = new File(args[2]);
    // final File dest = new File(args[3]);
    //
    // createPlot(FileUtil.importPlotJob(file), new GridCallback()
    // {
    // public void imagesReceived(Map gridImages, Dimension size) throws
    // IOException
    // {
    // File temp = JAITools.aggregateImages(gridImages, size);
    // if (!temp.renameTo(dest))
    // {
    // logger.warn("Destination file " + dest + "could not be written. But have
    // a look at " + temp);
    // }
    // }
    // });
    // }

    /**
     * create the given PlotJob over the grid and give a signal back to the
     * callback when all clients returned their images.
     * 
     * @param plot
     *            the PlotJob
     * @param callback
     *            receiver of the image(s)
     * 
     * @return true, if the job could be started
     * 
     * @throws ConnectionException
     *             if an error occured
     */
    public static boolean createPlot(PlotJob plot, GridCallback callback)
	    throws ConnectionException {
	setPlot(plot);
	setCallback(callback);

	GridConfiguration config;
	try {
	    config = (GridConfiguration) ContextFactory.getContext()
		    .getConfigurationRegistry().get(
			    QImageService.ID_GRID_CONFIGURATION);
	} catch (UnknownIDException e) {
	    config = new GridConfiguration();
	}

	final GridConfiguration gridConfig = config;

	return createPlot(plot, gridConfig.getMediatorAddress(), gridConfig
		.getMediatorPort());
    }

    private static boolean createPlot(final PlotJob plot,
	    final String mediatorAddress, final int mediatorPort)
	    throws ConnectionException {
	final Vector members = GridServer.getInstance().getMembers();
	connectedClients = members.size();

	// no "GridClient", since this Collaborator only has to send the job and
	// shall not plot itself
	if (bigBoss == null || bigBoss.getConnectionInfo() == null) {
	    bigBoss = new MessageCollaborator(getBigBossID(), mediatorAddress,
		    mediatorPort);
	    while (bigBoss.getIdentity() == null) {
		; // wait for ID
	    }
	    try {
		// wait some time to let the grid know about me
		// before I will send messages
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		// catch silently
		// e.printStackTrace();
	    }
	} else {
	    connectedClients -= 1;
	}

	if (connectedClients == 0) {
	    logger.info("no client connected!");
	    return false;
	}

	members.remove(bigBoss.getIdentity());
	plot.setCollaborators(members);

	final int max = Integer.getInteger("grid.maxedge",
		plot.getTypeTable().createNavigator().getSize().width)
		.intValue();
	logger.info("maximum edge length: " + max);
	final Rectangle maxRect = new Rectangle(max, max);
	Dimension targetSize = plot.getTypeTable().createNavigator().getSize();
	if (!maxRect
		.contains(new Rectangle(targetSize.width, targetSize.height))) {
	    plot.setTargetSize(maxRect.getSize());
	}

	final File plotFile = new File("plotjob.dgz");
	FileUtil.exportPlotJob(plot, plotFile);

	try {
	    bigBoss.broadcast(GridClient.TAG_PLOTJOB, plotFile);

	    // used to buffer revceived images from grid before delivering to
	    // GridCallback
	    gridImages = new HashMap();
	} catch (IOException e) {
	    logger.error("Error creating plot", e);
	    return false;
	}

	return true;
    }

    static String getBigBossID() {
	return "BigBoss" + Integer.getInteger("grid.bigboss.id", 0);
    }

    static synchronized void onGridImageReceived(Identity srcID, Object data)
	    throws IOException {
	logger.debug("got image from " + srcID + ": " + data);
	// System.err.println("size: " + size);

	// gridImages.put(new Integer(srcID.getId()), data);
	gridImages.put(new Integer(plot.getCollaborators().indexOf(srcID)),
		data);
	if ((gridImages.size() == connectedClients) && (callback != null)) {
	    callback.imagesReceived(gridImages, plot.getTargetSize());
	    // bigBoss.disconnect();
	}
    }

    private static void setCallback(GridCallback callback) {
	GridPlotter.callback = callback;
    }

    private static void setPlot(PlotJob plot) {
	GridPlotter.plot = plot;
    }
}
