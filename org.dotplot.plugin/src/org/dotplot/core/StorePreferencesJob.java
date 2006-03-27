/**
 * 
 */
package org.dotplot.core;

import org.apache.log4j.Logger;
import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.eclipse.DotplotPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This <code>Job</code> storres the <code>Configurations</code> of the
 * <code>DotplotContext</code> in the Eclipse-preferences.
 * @author Christian Gerhardt <case42@gmx.net>
 * @see DotplotContext#getConfigurationRegistry()
 * @see IConfiguration#serializedForm()
 */
public class StorePreferencesJob extends AbstractJob<DotplotContext>  {

	/**
	 * Logger for debug messages.
	 */
	private static Logger logger = Logger.getLogger(StorePreferencesJob.class
			.getName());
	
	/**
	 * Creates a new <code>StorePreferencesJob</code>.
	 */
	public StorePreferencesJob() {
		super();

	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#process(C)
	 */
	public boolean process(DotplotContext context) {
		try {
			logger.debug("storing preferences");
			
			IPreferenceStore store = DotplotPlugin.getDefault().getPreferenceStore();
			IConfigurationRegistry registry = context.getConfigurationRegistry();
			for(String key : registry.getAll().keySet()){
				IConfiguration config = registry.get(key);
				try{
					String value = config.serializedForm();
					if(value != null){
						logger.debug("storing " + key + " -> "+value);
						store.setValue(key, value);
					}
				}
				catch(UnsupportedOperationException e){
					//no problem
				}
			}
			return true;
		}
		catch(Exception e){
			this.getErrorHandler().fatal(this, e);
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core.services.IServiceRegistry)
	 */
	public boolean validatePreconditions(IServiceRegistry registry) {
		return true;
	}
}
