/**
 * 
 */
package org.dotplot.core.system;

import org.dotplot.core.StorePreferencesJob;
import org.dotplot.core.UsePreferenceJob;
import org.dotplot.core.plugins.InitializerService;
import org.dotplot.core.plugins.PlugableService;
import org.dotplot.core.plugins.Plugin;
import org.dotplot.core.plugins.PluginIntegrationService;
import org.dotplot.core.plugins.PluginLoadingService;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.util.DuplicateRegistrationException;

/**
 * Instantces of this class provide the basic <code>Service</code>s and
 * <code>Job</code>s to load plugins into the system.
 * <p>
 * The <code>CoreSystem</code> provide three <code>Service</code>s:
 * <ol>
 * <li><code>PluginLoadingService</code> - for loading <code>Plugins</code></li>
 * <li><code>PluginIntegrationService</code> - for integrating the
 * <code>Service</code>s of the loaded <code>Plugins</code> into to system</li>
 * <li><code>InitializerService</code> - for initializing the integrated
 * <code>Service</code>s</li>
 * </ol>
 * </p>
 * <p>
 * The <code>CoreSystem</code> provides for tree <code>Job</code>s:
 * <ol>
 * <li><code>PluginLoadingJob</code> - loads the <code>Plugins</code></li>
 * <li><code>StartUpJob</code> - executes <code>Job</code>s assigned to the
 * <code>InizializerService</code> for startup.
 * <li><code>ShutDownJob</code> - executes <code>Job</code>s assigned to the
 * <code>InizializerService</code> for shutdown.
 * </ol>
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.core.plugins.PluginLoadingService
 * @see org.dotplot.core.plugins.PluginIntegrationService
 * @see org.dotplot.core.plugins.InitializerService
 * @see org.dotplot.core.system.PluginLoadingJob
 * @see org.dotplot.core.system.ShutdownJob
 * @see org.dotplot.core.system.StartUpJob
 */
public class CoreSystem extends Plugin {

	/**
	 * Id of the the <code>Plugin</code>.
	 */
	public static final String CORE_SYSTEM_ID = "org.dotplot.core.CoreSystem";

	/**
	 * Name of the <code>Plugin</code>.
	 */
	public static final String CORE_SYSTEM_NAME = "Core";

	/**
	 * Id of the <code>PluginLoadingService</code>.
	 */
	public static final String SERVICE_LOADER_ID = "org.dotplot.core.services.PluginLoader";

	/**
	 * Id of the <code>PluginIntegrationervice</code>.
	 */
	public static final String SERVICE_INTEGRATOR_ID = "org.dotplot.core.services.PluginIntegrator";

	/**
	 * Id of the <code>InitializerService</code>.
	 */
	public static final String SERVICE_INITIALIZER_ID = "org.dotplot.core.services.PluginIninitalizer";

	/**
	 * Id of the <code>PluginLoaderJob</code>.
	 */
	public static final String JOB_PLUGIN_LOADER_ID = "org.dotplot.core.jobs.Pluginloader";

	/**
	 * Id of the <code>StartUpJob</code>.
	 */
	public static final String JOB_STARTUP_ID = "org.dotplot.core.jobs.StartUp";

	/**
	 * Id of the <code>ShutdownJob</code>.
	 */
	public static final String JOB_SHUTDOWN_ID = "org.dotplot.core.jobs.ShutDown";

	/**
	 * Creates a new <code>CoreSystem</code>.
	 */
	public CoreSystem() {
		super(CORE_SYSTEM_ID, CORE_SYSTEM_NAME, "1.0",
				"FH Giessen-Friedberg (www.fh-giessen.de)",
				"Core of the system.");
		this.init();
	}

	/**
	 * Initializes the <code>Plugin</code>.
	 */
	private void init() {
		try {
			// create services
			PlugableService<?> loader = new PluginLoadingService(
					SERVICE_LOADER_ID);
			PlugableService<?> integrator = new PluginIntegrationService(
					SERVICE_INTEGRATOR_ID);
			PlugableService<?> initializer = new InitializerService(
					SERVICE_INITIALIZER_ID);

			// register services
			this.getServiceRegistry().register(SERVICE_LOADER_ID, loader);
			this.getServiceRegistry().register(SERVICE_INTEGRATOR_ID,
					integrator);
			this.getServiceRegistry().register(SERVICE_INITIALIZER_ID,
					initializer);

			// register jobs
			this.getJobRegistry().register(
					JOB_PLUGIN_LOADER_ID,
					new PluginLoadingJob(SERVICE_LOADER_ID,
							SERVICE_INTEGRATOR_ID, SERVICE_INITIALIZER_ID));
			this.getJobRegistry().register(JOB_STARTUP_ID, new StartUpJob());
			this.getJobRegistry().register(JOB_SHUTDOWN_ID, new ShutdownJob());

			// register extentions
			try {
				initializer.addExtention(InitializerService.HOTSPOT_ID_STARTUP,
						new Extention(this, new UsePreferenceJob()));
				initializer.addExtention(
						InitializerService.HOTSPOT_ID_SHUTDOWN, new Extention(
								this, new StorePreferencesJob()));
			}
			catch (UnknownServiceHotSpotException e) {
				// sollte nicht vorkommen
			}

			// invoke init at last
			loader.init();
			integrator.init();
			initializer.init();
		}
		catch (DuplicateRegistrationException e) {
			/* kann eigentlich nicht vorkommen */
		}
	}

	/**
	 * Returns allways <code>true</code>, becourse this <code>Plugin</code>
	 * could not be deactivated.
	 * 
	 * @returns <code>true</code>
	 */
	@Override
	public boolean isActivated() {
		return true;
	}
}
