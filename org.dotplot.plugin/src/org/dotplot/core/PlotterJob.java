/**
 * 
 */
package org.dotplot.core;

import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.image.QImageContext;
import org.dotplot.tokenizer.converter.SourceListContext;
import org.dotplot.util.UnknownIDException;

/**
 * A Job for creating dotplots.
 * <p>
 * The dotplot is based on the sourcelist assigned to the
 * <code>DotplotContext</code> and stored there after it's creation.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see DotplotContext
 * @see DotplotContext#getSourceList()
 * @see DotplotContext#setCurrentDotplot(IDotplot)
 */
public class PlotterJob extends AbstractJob<DotplotContext> {

    /**
     * The needed <code>services</code>.
     */
    private static final String[] services = new String[] {
	    "org.dotplot.standard.Converter", "org.dotplot.standard.Tokenizer",
	    "org.dotplot.standard.Filter", "org.dotplot.standard.FMatrix",
	    "org.dotplot.standard.QImage", };

    /**
     * Creates a new <code>PlotterJob</code>.
     */
    public PlotterJob() {
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.dotplot.core.services.IJob#process(org.dotplot.core.services.
     * IFrameworkContext)
     */
    public boolean process(DotplotContext context) {
	IService<DotplotContext, PluginHotSpot> currentService;

	if (context == null) {
	    throw new NullPointerException();
	}
	IServiceRegistry registry = context.getServiceRegistry();

	try {
	    IContext workingContext = new SourceListContext(context
		    .getSourceList());

	    for (int i = 0; i < services.length; i++) {
		currentService = registry.get(services[i]);
		currentService.setErrorHandler(this.getErrorHandler());
		currentService.setFrameworkContext(context);
		currentService.setWorkingContext(workingContext);
		currentService.setTaskProcessor(this.getTaskProcessor());
		currentService.run();
		workingContext = currentService.getResultContext();
		if (workingContext instanceof FMatrixContext) {
		    ITypeTable table = ((FMatrixContext) workingContext)
			    .getTypeTableNavigator().getTypeTable();
		    context.setCurrentTypeTable(table);
		    context.setCurrentTypeTable(table);
		}
	    }

	    context.setCurrentDotplot(((QImageContext) workingContext)
		    .getDotplot());
	} catch (UnknownIDException e) {
	    e.printStackTrace();
	    this.getErrorHandler().fatal(this, e);
	    return false;
	} catch (IllegalContextException e) {
	    e.printStackTrace();
	    this.getErrorHandler().fatal(this, e);
	    return false;
	} catch (Exception e) {
	    e.printStackTrace();
	    this.getErrorHandler().fatal(this, e);
	    return false;
	}
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core
     * .services.IServiceRegistry)
     */
    public boolean validatePreconditions(IServiceRegistry registry) {
	for (int i = 0; i < services.length; i++) {
	    try {
		registry.get(services[i]);
	    } catch (UnknownIDException e) {
		return false;
	    }
	}
	return true;
    }
}
