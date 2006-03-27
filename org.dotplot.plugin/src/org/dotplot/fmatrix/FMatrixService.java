/**
 * 
 */
package org.dotplot.fmatrix;

import java.util.Map;

import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.ISourceList;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TokenStreamContext;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FMatrixService extends DotplotService {

	public static final String ID_FMATRIX_CONFIGURATION = "org.dotplot.fmatrix.Configuration";
	
	/**
	 * @param id
	 */
	public FMatrixService(String id) {
		super(id);

	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		if(registry == null) throw new NullPointerException();
		try {
			registry.register(ID_FMATRIX_CONFIGURATION, new DefaultFMatrixConfiguration());
		}
		catch (DuplicateRegistrationException e) {
			this.getErrorHandler().warning(this, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	public boolean workingContextIsCompatible(Class contextClass) {
		if(contextClass == null) throw new NullPointerException();		
		return TokenStreamContext.class.isAssignableFrom(contextClass);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	public Class getResultContextClass() {
		return FMatrixContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	public ITask createTask() {
		final String TASK_ID = "FMatrix Task";
		final String PART_ID = "FMatrix Part";
		try {
			Task task = new Task(TASK_ID,new ITaskResultMarshaler(){

			public Object marshalResult(Map<String, ? extends Object> taskResult) {				
				return taskResult.get(PART_ID);
			}} ,false);
		
			IFMatrixConfiguration config = (IFMatrixConfiguration)this.frameworkContext.getConfigurationRegistry().get(ID_FMATRIX_CONFIGURATION);
			ITokenStream stream = ((TokenStreamContext)this.getWorkingContext()).getTokenStream();
			ISourceList list = ((TokenStreamContext)this.getWorkingContext()).getSourceList();
			FMatrixTaskPart part = new FMatrixTaskPart(PART_ID,stream, config);
			part.setRessources(list);			
			task.addPart(part);
			return task;
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this, e);
			return null;
		}		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	@Override
	protected IContext createResultContext() {
		Object result = this.getTaskProcessor().getTaskResult();
		if(result == null){
			return NullContext.context;
		}
		else {
			ITypeTableNavigator navigator = (ITypeTableNavigator)result;
			return new FMatrixContext(navigator);
		}
	}

}
