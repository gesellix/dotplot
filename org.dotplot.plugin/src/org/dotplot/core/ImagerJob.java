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
import org.dotplot.util.UnknownIDException;

/**
 * Creates a new <code>Dotplot</code> based on the current
 * <code>TypeTabele</code>. <code>
 * The dotplot is assiged to the <code>DotplotContext</code>
 * . </code>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see DotplotContext
 * @see DotplotContext#getCurrentTypeTable()
 * @see DotplotContext#setCurrentDotplot(IDotplot)
 */
public class ImagerJob extends AbstractJob<DotplotContext> {

	/**
	 * The ids of the needed <code>Services</code>.
	 */
	private static final String[] services = new String[] { "org.dotplot.standard.QImage", };

	/**
	 * Creates a new <code>ImagerJob</code>.
	 */
	public ImagerJob() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IJob#process(C)
	 */
	public boolean process(DotplotContext context) {
		IService<DotplotContext, PluginHotSpot> currentService;

		if (context == null) {
			throw new NullPointerException();
		}
		IServiceRegistry registry = context.getServiceRegistry();

		try {
			IContext workingContext = new FMatrixContext(context
					.getCurrentTypeTable().createNavigator());

			for (int i = 0; i < services.length; i++) {
				currentService = registry.get(services[i]);
				currentService.setErrorHandler(this.getErrorHandler());
				currentService.setFrameworkContext(context);
				currentService.setWorkingContext(workingContext);
				currentService.setTaskProcessor(this.getTaskProcessor());
				currentService.run();
				workingContext = currentService.getResultContext();
				if (workingContext instanceof FMatrixContext) {
					context
							.setCurrentTypeTable(((FMatrixContext) workingContext)
									.getTypeTableNavigator().getTypeTable());
				}
			}

			context.setCurrentDotplot(((QImageContext) workingContext)
					.getDotplot());
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this, e);
			return false;
		}
		catch (IllegalContextException e) {
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
			}
			catch (UnknownIDException e) {
				return false;
			}
		}
		return true;
	}

}
