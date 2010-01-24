/**
 * 
 */
package org.dotplot.eclipse.actions;

import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.util.UnknownIDException;
import org.eclipse.jface.action.Action;

/**
 * An <code>Action</code> to executes <code>Job</code>s.
 * <p>
 * This <code>Action</code> could be used to execute a job by using the eclipse
 * userinterface. In this way menuentries or popupmenus could execute
 * <code>Job</code>s.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.core.services.IJob
 * @see org.dotplot.core.plugins.PluginContext#executeJob(String)
 */
public class JobAction extends Action {

	/**
	 * The id of the executed <code>Job</code>.
	 */
	private String jobID;

	/**
	 * Creates a new <code>JobAction</code>.
	 * 
	 * @param text
	 *            - label of the <code>Action</code>.
	 * @param jobID
	 *            - the if og the executed <code>Job</code>.
	 */
	public JobAction(String text, String jobID) {
		super(text);
		this.jobID = jobID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {
		DotplotContext context = ContextFactory.getContext();
		try {
			context.executeJob(this.jobID);
		}
		catch (UnknownIDException e) {
			// dann eben nicht
		}
	}

}
