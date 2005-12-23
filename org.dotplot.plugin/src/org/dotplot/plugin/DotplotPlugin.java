package org.dotplot.plugin;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The main plugin class to be used in the desktop.
 */
public class DotplotPlugin extends AbstractUIPlugin {
	// The shared instance.
	private static DotplotPlugin plugin;

	// Resource bundle.
	private ResourceBundle resourceBundle = null;

	// Bundle (OSGi Bundle, since Eclipse 3.x)
	private static Bundle bundle = null;

	private static final Logger logger = Logger.getLogger(DotplotPlugin.class
			.getName());

	/**
	 * The constructor.
	 */
	public DotplotPlugin() {		
		init();
	}

	private void init() {
		if(plugin == null){
			plugin = this;
			bundle = getPluginBundle();
	
			if(bundle.getState() == Bundle.INSTALLED){
				try {
					bundle.start();
				}
				catch (BundleException e) {
					e.printStackTrace();
				}
			}
		
			logger.info("--== Matrix4.plot ==--");

		// configureLogging();

		// TODO localisation
//		 try
//		 {
//			 resourceBundle =
//			 ResourceBundle.getBundle("org.dotplot.plugin.DotPlotPluginResources");
//		 }
//		 catch (MissingResourceException x)
//		 {
//			 resourceBundle = null;
//			 logger.error("MissingResourceException -> resourceBundle = null");
//		 }
		}
		else {
			logger.warn("Another DotplotPlugin object was created.");
		}
	}
	
	public static String getVersionInfo() {
		return "Matrix4.plot v."
				+ getPluginBundle().getHeaders().get("Bundle-Version");
	}

	public static Bundle getPluginBundle() {
		if (bundle == null) {
			bundle = Platform.getBundle("org.dotplot.plugin");
		}
		return bundle;
	}

	public static URL getResource(String resource) throws IOException {
		return Platform.asLocalURL(getPluginBundle().getEntry(resource));
	}

	public void loadSplashScreen() {
		// Splash screen
		try {
			new DotPlotSplashScreen(
					getResource("icons/dp_splash_400x360_v0_2.jpg"));
		}
		catch (MalformedURLException e) {
			// ignore
			// e.printStackTrace();
		}
		catch (IOException e) {
			// ignore
			// e.printStackTrace();
		}
	}

	public static void configureLogging() {
		String loggingFile = System.getProperty(
				"java.util.logging.config.file", "log4j.properties");
		InputStream is = DotplotPlugin.class.getResourceAsStream(loggingFile);
		if (is == null) {
			// try file-Access
			try {
				is = new FileInputStream(loggingFile);
			}
			catch (FileNotFoundException e) {
				// Silently catch an exception here
			}
		}

		if (null != is) {
			try {
				String fileURl = (null != DotplotPlugin.class
						.getResource(loggingFile)) ? DotplotPlugin.class
						.getResource(loggingFile).toExternalForm()
						: loggingFile;
				logger.info("use logging.properties at " + fileURl);
				Properties prop = new Properties();
				prop.load(is);
				PropertyConfigurator.configure(prop);
				logger.info("test");
				return;
			}
			catch (Exception ex) {
				logger.fatal("While reading Logging-Configuration", ex);
			}
		}
		else {
			BasicConfigurator.configure();
			logger.warn("User log4j default properties\nLogFile '"
					+ loggingFile + "' doesn't exist");
		}
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return shared instance
	 */
	public static DotplotPlugin getDefault() {
		if (plugin == null) {
			new DotplotPlugin();
		}
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 * 
	 * @return the workspace
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 * 
	 * @param key
	 *            key of resource
	 * 
	 * @return name name of the found resource
	 */
	public static String getResourceString(String key) {
		final ResourceBundle resourceBundle = DotplotPlugin.getDefault()
				.getResourceBundle();
		if (resourceBundle == null) {
			return key;
		}

		try {
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle.
	 * 
	 * @return plugin's bundle
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
