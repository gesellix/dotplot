/**
 * 
 */
package org.dotplot.core.plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.dotplot.core.plugins.ressources.DirectoryRessource;
import org.dotplot.core.services.AbstractTaskPart;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.IService;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.InsufficientRessourcesException;
import org.dotplot.core.services.ServiceException;
import org.dotplot.core.services.ServiceRegistry;
import org.dotplot.util.DOMTreeIterator;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class PluginLoadingTaskPart extends AbstractTaskPart {

	/**
	 * Logger for logging messages.
	 */
	private static Logger LOGGER = Logger.getLogger(PluginLoadingTaskPart.class
			.getName());

	/**
	 * 
	 * @param e
	 * @return
	 */
	private static Exception handleException(Exception e) {
		String message = null;
		if (e instanceof ClassCastException) {
			message = "Classcast: ";
		}
		else if (e instanceof ClassNotFoundException) {
			message = "Class not found: ";
		}
		else if (e instanceof SecurityException) {
			message = "Sevurity: ";
		}
		else if (e instanceof NoSuchMethodException) {
			message = "No such method: ";
		}
		else if (e instanceof IllegalArgumentException) {
			message = "Illegalargument: ";
		}
		else if (e instanceof InstantiationException) {
			message = "Instantiation: ";
		}
		else if (e instanceof IllegalAccessException) {
			message = "IllegalAccess: ";
		}
		else if (e instanceof InvocationTargetException) {
			message = "InvocationTarget: ";
		}
		else {
			return e;
		}

		return new ServiceException(message + e.getMessage());
	}

	private IPluginContext<? extends IPlugin> context;

	/**
	 * The directory to find plugin-jars.
	 */
	private File pluginDirectory;

	private IService<? extends IPluginContext, PluginHotSpot> currentService;

	private Extention currentExtention;

	private Map<String, IPlugin> plugins;

	private Collection<PluginJarFile> pluginFiles;

	private DocumentBuilder builder;

	private BatchJob currentJob;

	private String currentJobID;

	private IServiceRegistry combinedServiceRegistry;

	/**
	 * The ClassLoader to use.
	 */
	private ClassLoader currentClassLoader;

	/**
	 * Flag that indicates an error.
	 */
	private boolean errorOccured;

	/**
	 * @param id
	 */
	public PluginLoadingTaskPart(String id, DirectoryRessource pluginDirectory,
			IPluginContext<? extends IPlugin> context) {
		super(id);
		if (pluginDirectory == null || context == null) {
			throw new NullPointerException();
		}
		this.errorOccured = false;
		Vector<IRessource> v = new Vector<IRessource>();
		v.add(pluginDirectory);
		this.setRessources(v);
		this.context = context;
		this.pluginFiles = new Vector<PluginJarFile>();
		this.combinedServiceRegistry = new ServiceRegistry();
		this.currentClassLoader = this.getClass().getClassLoader();
		try {
			this.combinedServiceRegistry.combine(this.context
					.getServiceRegistry());
		}
		catch (DuplicateRegistrationException e) {
			LOGGER.error("Error in Contructor", e);
			/* sollte nicht vorkommen */
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setCoalescing(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setSchema(this.context.getPluginSchema());
		try {
			this.builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			this.errorOccured = true;
			this.getErrorhandler().fatal(
					this,
					new ServiceException("ParserConfigurationException:"
							+ e.getMessage()));
		}
	}

	/**
	 * 
	 * @param files
	 * @return
	 */
	private Collection<PluginJarFile> checkDependencys(
			Collection<PluginJarFile> files) {
		LOGGER.debug("checkDependencys: size=" + files.size());
		Collection<PluginJarFile> jars = new Vector<PluginJarFile>();
		Iterator<PluginJarFile> iter = files.iterator();
		PluginJarFile jar;
		Map<String, IPlugin> plugins = new TreeMap<String, IPlugin>();
		plugins.putAll(this.plugins);
		plugins.putAll(this.context.getPluginRegistry().getAll());
		Set<String> deps;
		while (iter.hasNext()) {
			jar = iter.next();
			deps = jar.getPluginDependencys().keySet();
			if (deps.size() > 0) { // nur falls dependencys vorhanden muss
				// überprüft werden!
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("checking dependencys:" + jar.getName());
					for (String key : deps) {
						LOGGER.debug(key
								+ ":"
								+ jar.getPluginDependencys().get(key)
										.toString());
					}
				}
				if (plugins.keySet().containsAll(deps)) {
					if (this.checkDependencysDeep(plugins, jar
							.getPluginDependencys())) {
						jars.add(jar);
					}
				}
			}
			else {
				jars.add(jar);
			}
		}
		return jars;

	}

	/**
	 * 
	 * @param plugins
	 * @param dependencys
	 * @return
	 */
	private boolean checkDependencysDeep(Map<String, IPlugin> plugins,
			Map<String, Version> dependencys) {
		IPlugin plugin;
		for (String key : dependencys.keySet()) {
			plugin = plugins.get(key);
			LOGGER.debug(plugin.getVersion() + "  is subversion of =>"
					+ dependencys.get(key));
			if (!plugin.getVersion().isSubVersion(dependencys.get(key))) {
				LOGGER.debug("not checked");
				return false;
			}
		}
		LOGGER.debug("checked");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.ITaskPart#errorOccured()
	 */
	public boolean errorOccured() {
		return this.errorOccured;
	}

	/**
	 * 
	 * @param id
	 * @param plugin
	 * @return
	 */
	private IService<? extends IPluginContext, PluginHotSpot> findService(
			String id, IPlugin plugin) {
		try {
			return this.context.getServiceRegistry().get(id);
		}
		catch (UnknownIDException e) {
		}

		try {
			return plugin.getServiceRegistry().get(id);
		}
		catch (UnknownIDException e) {
		}

		Iterator<IPlugin> iter = this.plugins.values().iterator();
		while (iter.hasNext()) {
			try {
				return iter.next().getServiceRegistry().get(id);
			}
			catch (UnknownIDException e) {
				continue;
			}
		}
		return null;
	}

	/**
	 * Returns the loaded plugins.
	 * 
	 * @return a Map of the loaded plugins.
	 */
	public Object getResult() {
		return this.plugins;
	}

	/**
	 * 
	 * @param element
	 * @param plugin
	 * @return
	 */
	private IPlugin handleElement(Element element, IPlugin plugin) {
		String name, id, info, version, provider, className, factory, hotspot, value;
		String nodeName = element.getNodeName();
		try {
			if ("Dotplotplugin".equals(nodeName)) {
				name = element.getAttribute("name");
				id = element.getAttribute("id");
				info = element.getAttribute("info");
				version = element.getAttribute("version");
				provider = element.getAttribute("provider");
				plugin = new Plugin(id, name, version, provider, info);
			}
			else if ("Service".equals(nodeName)) {
				id = element.getAttribute("id");
				className = element.getAttribute("class");
				this.currentService = this.findService(id, plugin);
				if (this.currentService == null) {
					Class<?> serviceClass;
					serviceClass = this.currentClassLoader.loadClass(className);
					Constructor<?> constructor = serviceClass
							.getConstructor(new Class[] { String.class });
					this.currentService = (IService<? extends IPluginContext, PluginHotSpot>) constructor
							.newInstance(new Object[] { id });
					plugin.getServiceRegistry().register(
							this.currentService.getID(), this.currentService);

					// die service-liste fortführen um den batchjobs richtig auf
					// konsistenz
					// prüfen zu können.
					this.combinedServiceRegistry.register(this.currentService
							.getID(), this.currentService);
				}
			}
			else if ("Extention".equals(nodeName)) {
				hotspot = element.getAttribute("hotspot");
				className = element.getAttribute("class");
				factory = element.getAttribute("factory");

				Class<?> extentionClass = this.currentClassLoader
						.loadClass(className);
				if (this.currentService.getHotSpot(hotspot).isValidExtention(
						extentionClass)) {
					if (factory.equals("")) {
						// wenn keine factory angegeben ist, das objekt ganz
						// normal erzeugen
						this.currentExtention = new Extention(plugin,
								extentionClass.newInstance());
					}
					else {
						// sonst die factory damit beauftragen
						Class<?> factoryClass = this.currentClassLoader
								.loadClass(factory);
						IExtentionFactory factoryObj = (IExtentionFactory) factoryClass
								.newInstance();
						Object obj = factoryObj.createObject();
						if (extentionClass.isAssignableFrom(obj.getClass())) {
							this.currentExtention = new Extention(plugin, obj);
						}
						else {
							throw new ServiceException("Invalid factory: "
									+ factory + " for " + className);
						}
					}
					plugin.storeExtention(this.currentService.getID(), hotspot,
							this.currentExtention);
				}
				else {
					throw new ServiceException("Invalid extention: "
							+ className + " for " + hotspot);
				}
			}
			else if ("Parameter".equals(nodeName)) {
				name = element.getAttribute("name");
				value = element.getAttribute("value");
				this.currentExtention.addParameter(name, value);
			}
			else if ("Batchjob".equals(nodeName)) {
				this.currentJob = null;
				this.currentJobID = null;
				id = element.getAttribute("id");
				try {
					this.context.getJobRegistry().get(id);
					throw new DuplicateRegistrationException(id);
				}
				catch (UnknownIDException e) {
					this.currentJob = new BatchJob();
					this.currentJobID = id;
					plugin.getJobRegistry().register(id, this.currentJob);
				}
			}
			else if ("Task".equals(nodeName)) {
				if (this.currentJob != null) {
					id = element.getAttribute("serviceid");
					try {
						if (this.findService(id, plugin) != null) {
							this.currentJob.addService(id);
							if (!this.currentJob
									.validatePreconditions(this.combinedServiceRegistry)) {
								throw new UnknownIDException(id);
							}
						}
						else {
							throw new UnknownIDException(id);
						}
					}
					catch (UnknownIDException e) {
						plugin.getJobRegistry().unregister(this.currentJobID);
						this.currentJob = null;
						this.currentJobID = null;
						throw e;
					}
				}
			}
			else if ("Job".equals(nodeName)) {
				id = element.getAttribute("id");
				className = element.getAttribute("class");
				Class<?> jobClass = this.currentClassLoader
						.loadClass(className);
				try {
					this.context.getJobRegistry().get(id);
					throw new DuplicateRegistrationException(id);
				}
				catch (UnknownIDException e) {
					plugin.getJobRegistry().register(id,
							(IJob<?>) jobClass.newInstance());
				}
			}
		}
		catch (Exception e) {
			Exception e1 = handleException(e);
			this.getErrorhandler().warning(this, e1);
		}
		return plugin;
	}

	/**
	 * 
	 * @param pluginFile
	 * @return
	 */
	private IPlugin loadPlugin(PluginJarFile pluginFile) {
		LOGGER.debug(("loadPlugin: " + pluginFile.getName()));
		Document pluginxml = null;
		IPlugin plugin = null;
		try {
			pluginxml = this.builder
					.parse(pluginFile.getPluginXMLInputStream());
			DOMTreeIterator iterator = new DOMTreeIterator(pluginxml);
			Node node;
			while (iterator.hasNext()) {
				node = iterator.nextNode();
				if (node instanceof Element) {
					plugin = this.handleElement((Element) node, plugin);
					if (plugin == null) {
						iterator.moveToNextSibling();
					}
				}
			}
		}
		catch (SAXException e) {
			this.getErrorhandler().fatal(this,
					new ServiceException("SAXException:" + e.getMessage()));
		}
		catch (IOException e) {
			this.getErrorhandler().fatal(this,
					new ServiceException("IOException:" + e.getMessage()));
		}
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		LOGGER.debug("run()");
		this.plugins = new TreeMap<String, IPlugin>();
		Collection<PluginJarFile> filesToCheck = this.pluginFiles;
		Collection<PluginJarFile> filesChecked;
		IPlugin plugin;
		do {
			filesChecked = this.checkDependencys(filesToCheck);
			for (PluginJarFile file : filesChecked) {
				this.currentClassLoader = file
						.createClassLoader(this.currentClassLoader);
				plugin = this.loadPlugin(file);
				if (plugin != null) {
					this.plugins.put(plugin.getID(), plugin);
				}
			}
			filesToCheck.removeAll(filesChecked);
		} while (!filesChecked.isEmpty());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.ITaskPart#setLocalRessources(java.util.Collection)
	 */
	public void setLocalRessources(Collection<? extends IRessource> ressouceList)
			throws InsufficientRessourcesException {
		if (ressouceList == null) {
			throw new NullPointerException();
		}
		if (ressouceList.size() == 0) {
			throw new InsufficientRessourcesException("Plugindirectory needed.");
		}
		else {
			IRessource[] ress = ressouceList.toArray(new IRessource[0]);
			if (ress[0] instanceof DirectoryRessource) {
				try {
					this.pluginDirectory = new File(ress[0].getURL().toURI())
							.getCanonicalFile();
					LOGGER.debug("Search Files in Plugin Directory: "
							+ this.pluginDirectory.getName());
					File[] files = this.pluginDirectory
							.listFiles(new FilenameFilter() {

								public boolean accept(File dir, String name) {
									return name.endsWith(".jar");
								}
							});
					LOGGER.debug("Number of found files:" + files.length);
					for (int i = 0; i < files.length; i++) {
						try {
							this.pluginFiles.add(new PluginJarFile(files[i]
									.getCanonicalFile(), this.context
									.getPluginSchema()));
						}
						catch (Exception e) {
							LOGGER.debug("skip File: "
									+ files[i].getAbsolutePath());
							LOGGER.error("skip File cause", e);
							this.getErrorhandler().warning(
									this,
									new ServiceException("Skipping Jarfile:"
											+ files[i].getAbsolutePath()));
						}
					}
				}
				catch (Exception e) {
					throw new InsufficientRessourcesException(
							"Plugindirectory needed.");
				}
			}
			else {
				throw new InsufficientRessourcesException(
						"Plugindirectory needed.");
			}
		}
	}

}
