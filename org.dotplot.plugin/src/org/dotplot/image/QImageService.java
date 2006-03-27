/**
 * 
 */
package org.dotplot.image;

import java.util.Map;

import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.IDotplot;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.fmatrix.FMatrixContext;
import org.dotplot.grid.GridConfiguration;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class QImageService extends DotplotService {

	public static final String QIMAGE_CONFIGURATION_ID = "org.dotplot.qimage.Configuration";
	
	/**
	 * Id of the grid configuration.
	 */
	public static final String ID_GRID_CONFIGURATION = "org.dotplot.grid.Configuration";

	/**
	 * Creates a new <code>QImageService</code>.
	 * @param id
	 */
	public QImageService(String id) {
		super(id);

	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	@Override
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		if(registry == null) throw new NullPointerException();
		try {
			registry.register(QIMAGE_CONFIGURATION_ID, new QImageConfiguration());
			//grid configuration wird hier registriert bis das grid auch 
			//aushalb des image benutzt werden kann.
			registry.register(ID_GRID_CONFIGURATION, new GridConfiguration());
		}
		catch (DuplicateRegistrationException e) {
			//dann eben nicht
		}		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	@Override
	public boolean workingContextIsCompatible(Class contextClass) {
		return FMatrixContext.class.isAssignableFrom(contextClass);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	@Override
	public Class getResultContextClass() {
		return QImageContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	@Override
	public ITask createTask() {
		ITask task = new Task("Image Task", new ITaskResultMarshaler(){

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				return taskResult.get("Part 1");
			}}, false); 
		
		IQImageConfiguration config;
		try {
			config = (IQImageConfiguration)this.frameworkContext.getConfigurationRegistry().get(QIMAGE_CONFIGURATION_ID);
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this, e);
			return null;
		}
		FMatrixContext workingContext = (FMatrixContext)this.getWorkingContext();
		
		task.addPart(new QImageTaskPart("Part 1", workingContext.getTypeTableNavigator(), config));
		return task;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	@Override
	protected IContext createResultContext() {
		Object result = this.getTaskProcessor().getTaskResult(); 
		if(result  == null){
			return NullContext.context;
		}
		else {
			return new QImageContext((IDotplot)result);
		}
	}

}
