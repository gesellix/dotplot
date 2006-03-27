package org.dotplot.eclipse;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dotplot.core.ContextFactory;
import org.dotplot.core.DotplotContext;
import org.dotplot.core.system.CoreSystem;
import org.dotplot.util.UnknownIDException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * The <code>WorkbenchAdvisor</code> of the dotplot system.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotAdvisor extends WorkbenchAdvisor {

	/**
	 * Logger for debugging.
	 */
	private static Logger logger = Logger.getLogger(DotplotAdvisor.class
			.getName());
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#getInitialWindowPerspectiveId()
	 */
	public String getInitialWindowPerspectiveId() {		
		return EclipseConstants.ID_PERSPECTIVE_DOTPLOT;
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#initialize(org.eclipse.ui.application.IWorkbenchConfigurer)
	 */
	public void initialize(IWorkbenchConfigurer configurer){
		
		logger.debug("inizializing workbench");
		
		//Location for DotplotPlugins
		Path pluginsPath = new Path("/plugins");
		//Location for Plugins Shemafile
		Path shemaPath = new Path("/ressources/dotplotschema.xsd");
		
		//Geting Platformindependend URL
		URL indepPluginsURL = DotplotPlugin.getDefault().find(pluginsPath);
		URL indepShemaURL = DotplotPlugin.getDefault().find(shemaPath);
		
		try {
			//Resolving Platformdependent URL
			URL depPluginsURL = Platform.resolve(indepPluginsURL);
			URL depShemaURL = Platform.resolve(indepShemaURL);
						
			ContextFactory.setPluginDirectory(depPluginsURL.getPath());
			ContextFactory.setShemaFile(depShemaURL.getPath());
			ContextFactory.getContext();
			
		}
		catch (IOException e) {
			/*very evil*/
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#preShutdown()
	 */
	public boolean preShutdown(){
		
		logger.debug("pre shut down");
		
		DotplotContext context = ContextFactory.getContext();
		try {
			context.executeJob(CoreSystem.JOB_SHUTDOWN_ID);
		}
		catch (UnknownIDException e) {
			//bad luck, but shutdown anyway
		}
		return true;		
	}
	
	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#createWorkbenchWindowAdvisor(org.eclipse.ui.application.IWorkbenchWindowConfigurer)
	 */
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer){
		
		logger.debug("creating workbench window advisor");
		
		return new DotplotWindowAdvisor(configurer);
	}

}
