package org.dotplot.core.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.core.services.NullContext;
import org.dotplot.util.UnknownIDException;

/**
 * Instances of this class are <code>Job</code>s for batch-processing.
 * <p>
 * Services are added by assigning their id to the <code>BatchJob</code>. This
 * services are executed in order of their assignement.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class BatchJob extends AbstractJob<IFrameworkContext> {

	/**
	 * A <code>List</code> to manage the batch.
	 */
	private List<String> batch;

	/**
	 * Creates a new <code>BatchJob</code>.
	 */
	public BatchJob() {
		super();
		this.batch = new Vector<String>();
	}

	/**
	 * Adds a <code>Service</code> to the batch.
	 * 
	 * @param serviceID
	 *            ID of the service to add.
	 */
	public final void addService(final String serviceID) {
		if (serviceID == null) {
			throw new NullPointerException();
		}
		this.batch.add(serviceID);
	}

	/**
	 * Adds a <code>serviceID</code> to a certain position in the batch.
	 * <p>
	 * The <code>serviceID</code> currently holding the position will be moved
	 * up one position just as the ids above.
	 * </p>
	 * <p>
	 * If <code>position</code> is greater then the current batchsize, the
	 * <code>serviceID</code> will be put on top of the batch.
	 * </p>
	 * 
	 * @param serviceID
	 *            - the <code>serviceID</code> to put on the batch.
	 * @param position
	 *            - the position to put the <code>serviceID</code> in the batch
	 */
	public final void addService(final String serviceID, int position) {
		if (position < 0) {
			throw new IllegalArgumentException(String.valueOf(position));
		}
		if (serviceID == null) {
			throw new NullPointerException();
		}
		if (position > this.batch.size()) {
			position = this.batch.size();
		}
		this.batch.add(position, serviceID);
	}

	/**
	 * Returns the batch as an array of serviceids.
	 * 
	 * @return - the batch.
	 */
	public final List<String> getServiceBatch() {
		List<String> list = new ArrayList<String>();
		list.addAll(this.batch);
		return list;
	}

	/**
	 * @see org.dotplot.services.IJob#process(org.dotplot.services.ServiceManager,
	 *      org.dotplot.services.FrameworkContext)
	 * 
	 * @param context
	 *            the <code>IFrameworkContext</code> to run the Job in.
	 * @return if the job is run succesfully.
	 */
	public final boolean process(final IFrameworkContext context) {
		if (context == null) {
			throw new NullPointerException();
		}
		List<String> serviceBatch = this.getServiceBatch();
		IContext currentContext = new NullContext();
		IService<IFrameworkContext, ?> currentService = null;

		this.getTaskProcessor().setErrorHandler(this.getErrorHandler());

		for (String service : serviceBatch) {
			try {
				currentService = context.getServiceRegistry().get(service);
			}
			catch (UnknownIDException e) {
				this.getErrorHandler().fatal(this, e);
				return false;
			}
			currentService.setErrorHandler(this.getErrorHandler());
			currentService.setFrameworkContext(context);
			try {
				currentService.setWorkingContext(currentContext);
			}
			catch (IllegalContextException e) {
				this.getErrorHandler().fatal(this, e);
				return false;
			}
			currentService.setTaskProcessor(this.getTaskProcessor());
			currentService.run();

			currentContext = currentService.getResultContext();
		}
		return true;
	}

	/**
	 * Removes the last <code>Service</code> of the batch.
	 * 
	 * @return - the removed <code>Service</code>.
	 */
	public final String removeService() {
		if (this.batch.size() == 0) {
			return null;
		}
		else {
			return this.batch.remove(this.batch.size() - 1).toString();
		}
	}

	/**
	 * Removes a serviceid in a specified position in the batch.
	 * 
	 * @param position
	 *            - the position of the id to be removed.
	 * @return - the removed serviceid.
	 * @throws IllegalArgumentException
	 */
	public final String removeService(final int position) {
		if (position < 0) {
			throw new IllegalArgumentException(String.valueOf(position));
		}
		return this.batch.remove(position).toString();
	}

	/**
	 * 
	 * @see org.dotplot.services.IJob#validatePreconditions(org.dotplot.services.
	 *      ServiceManager, org.dotplot.services.FrameworkContext)
	 */
	public final boolean validatePreconditions(final IServiceRegistry manager) {
		if (manager == null) {
			throw new NullPointerException();
		}
		IService<?, ?> currentService;
		Class<?> currentContext = NullContext.class;

		List<String> serviceBatch = this.getServiceBatch();
		for (String service : serviceBatch) {
			try {
				currentService = manager.get(service);
			}
			catch (UnknownIDException e) {
				return false;
			}
			if (currentService.workingContextIsCompatible(currentContext)) {
				currentContext = currentService.getResultContextClass();
			}
			else {
				return false;
			}
		}
		return true;
	}
}
