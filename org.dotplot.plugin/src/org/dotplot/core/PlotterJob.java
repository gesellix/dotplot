package org.dotplot.core;

import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITaskMonitor;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.image.QImageContext;
import org.dotplot.tokenizer.converter.SourceListContext;
import org.dotplot.util.UnknownIDException;
import org.eclipse.core.runtime.IProgressMonitor;

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
public class PlotterJob extends JobWithProgress {

	/**
	 * The needed <code>services</code>.
	 */
	private static final String[] services = new String[] {
			"org.dotplot.standard.Converter", 
			"org.dotplot.standard.Tokenizer",
			"org.dotplot.standard.Filter", 
			"org.dotplot.standard.FMatrix",
			"org.dotplot.standard.QImage", };

	/**
	 * Creates a new <code>PlotterJob</code>.
	 */
	public PlotterJob() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core.services.IServiceRegistry)
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


	/* (non-Javadoc)
	 * @see org.dotplot.core.JobWithProgress#process(org.dotplot.core.DotplotContext, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected boolean process(DotplotContext context, final IProgressMonitor monitor) {
		IService<DotplotContext, PluginHotSpot> currentService;

		if (context == null || monitor == null) throw new NullPointerException();
		IServiceRegistry registry = context.getServiceRegistry();
		
		ITypeTable oldTable = null;
				
		this.getTaskProcessor().setTaskMonitor(new ITaskMonitor(){

			String taskName;
			
			public boolean isCanceled() {
				return monitor.isCanceled();
			}

			public void setTaskName(String name) {
				this.taskName = name;
				monitor.subTask(name);
			}

			public void setTaskPartName(String name) {
				monitor.subTask(taskName + ": " + name);
			}

			public void worked(double d) {
				monitor.internalWorked(d);
			}});
		
		monitor.beginTask("Generating Dotplot", services.length + 1);
		
		try {
			IContext workingContext = new SourceListContext(context
					.getSourceList());

			for (int i = 0; i < services.length; i++) {
				currentService = registry.get(services[i]);
				currentService.setErrorHandler(this.getErrorHandler());
				currentService.setFrameworkContext(context);
				currentService.setWorkingContext(workingContext);
				currentService.setTaskProcessor(this.getTaskProcessor());
				monitor.subTask(currentService.getID());
				currentService.run();
				
				if(monitor.isCanceled()){
					if(oldTable != null){
						context.setCurrentTypeTable(oldTable);
					}
					return true;
				}
				
				workingContext = currentService.getResultContext();
				if (workingContext instanceof FMatrixContext) {
					ITypeTable table = ((FMatrixContext) workingContext)
							.getTypeTableNavigator().getTypeTable();
					oldTable = context.getCurrentTypeTable();
					context.setCurrentTypeTable(table);
					context.setCurrentTypeTable(table);
				}
				//monitor.worked(1);
			}

			context.setCurrentDotplot(((QImageContext) workingContext)
					.getDotplot());
		}
		catch (UnknownIDException e) {
			e.printStackTrace();
			this.getErrorHandler().fatal(this, e);
			return false;
		}
		catch (IllegalContextException e) {
			e.printStackTrace();
			this.getErrorHandler().fatal(this, e);
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			this.getErrorHandler().fatal(this, e);
			return false;
		}
		return true;
	}

}
