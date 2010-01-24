/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ServiceRegistry;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.IRegistry;

/**
 * Instances of this class represent plugins.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class Plugin implements IPlugin {

	/**
	 * The plugin id.
	 */
	private String id;

	/**
	 * The plugin version.
	 */
	private Version version;

	/**
	 * The plugin name.
	 */
	private String name;

	/**
	 * Informations about the plugin
	 */
	private String info;

	/**
	 * The provider of the plugin.
	 */
	private String provider;

	/**
	 * Indicates that the plugin is activated.
	 */
	private boolean activated;

	/**
	 * The <code>ServiceManager</code> to register the <code>Services</code> to
	 * the plugin.
	 */
	private IServiceRegistry services;

	/**
	 * The <code>JobRegistry</code>.
	 */
	private IJobRegistry jobs;

	/**
	 * Store for <code>Extentiins</code>.
	 */
	private Map<String, Map<String, Collection<Extention>>> extentions;

	/**
	 * Creates a new <code>Plugin</code>.
	 * <p>
	 * The plugin provider and information will be set to default values. The id
	 * is also used as name of the <code>Plugin</code>.
	 * </p>
	 * 
	 * @param id
	 *            - the plugin id.
	 * @param version
	 *            - the plugin version.
	 */
	public Plugin(String id, String version) {
		if (id == null || version == null) {
			throw new NullPointerException();
		}
		init(id, id, version, "", "");
	}

	/**
	 * Creates a new <code>Plugin</code>.
	 * <p>
	 * The plugin provider and information will be set to default values.
	 * </p>
	 * 
	 * @param id
	 *            - the plugin id.
	 * @param name
	 *            - the plugin name.
	 * @param version
	 *            - the plugin version.
	 */
	public Plugin(String id, String name, String version) {
		if (id == null || name == null || version == null) {
			throw new NullPointerException();
		}
		init(id, name, version, "", "");
	}

	/**
	 * Creates a new <code>Plugin</code>.
	 * 
	 * @param id
	 *            - the plugin id.
	 * @param name
	 *            - the plugin name.
	 * @param version
	 *            - the plugin version.
	 * @param provider
	 *            - the plugin provider.
	 * @param info
	 *            - information about the plugin.
	 */
	public Plugin(String id, String name, String version, String provider,
			String info) {
		if (id == null || name == null || version == null) {
			throw new NullPointerException();
		}
		init(id, name, version, provider, info);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.plugins.IRegistry#combine(org.dotplot.core.plugins.IRegistry
	 * )
	 */
	public void combine(IRegistry<IJob> registry)
			throws DuplicateRegistrationException {
		this.jobs.combine(registry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#getExtentions()
	 */
	public Map<String, Map<String, Collection<Extention>>> getExtentions() {
		return this.extentions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#getID()
	 */
	public String getID() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#getInfo()
	 */
	public String getInfo() {
		return info;
	}

	public IJobRegistry getJobRegistry() {
		return this.jobs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#getProvider()
	 */
	public String getProvider() {
		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#getServiceRegistry()
	 */
	public IServiceRegistry getServiceRegistry() {
		return this.services;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#getVersion()
	 */
	public Version getVersion() {
		return this.version;
	}

	/**
	 * Inits the <code>Plugin</code>.
	 * 
	 * @param id
	 *            - the plugin id.
	 * @param name
	 *            - the plugin name.
	 * @param version
	 *            - the plugin version.
	 * @param provider
	 *            - the plugin provider.
	 * @param info
	 *            - information about the plugin.
	 */
	private void init(String id, String name, String version, String provider,
			String info) {
		this.id = id;
		this.name = name;
		try {
			this.version = new Version(version);
		}
		catch (MalformedVersionException e) {
			try {
				this.version = new Version("1.0");
			}
			catch (MalformedVersionException e1) {
				/* sollte nicht vorkommen */
			}
		}
		if (provider == null) {
			this.provider = "";
		}
		else {
			this.provider = provider;
		}
		if (info == null) {
			this.info = "";
		}
		else {
			this.info = info;
		}
		this.activated = true;
		this.services = new ServiceRegistry();
		this.jobs = new JobRegistry();
		this.extentions = new TreeMap<String, Map<String, Collection<Extention>>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.services.IServicePlugin#isActivated()
	 */
	public boolean isActivated() {
		return this.activated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#setActivated(boolean)
	 */
	public void setActivated(boolean value) {
		this.activated = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPlugin#storeExtention(java.lang.String,
	 * org.dotplot.core.services.Extention)
	 */
	public void storeExtention(String serviceID, String hotSpotID,
			Extention extention) {
		if (serviceID == null || hotSpotID == null || extention == null) {
			throw new NullPointerException();
		}
		Map<String, Collection<Extention>> spots;
		Collection<Extention> extentions;
		if (this.extentions.containsKey(serviceID)) {
			spots = this.extentions.get(serviceID);
			if (spots.containsKey(hotSpotID)) {
				spots.get(hotSpotID).add(extention);
			}
			else {
				extentions = new Vector<Extention>();
				extentions.add(extention);
				spots.put(hotSpotID, extentions);
			}
		}
		else {
			spots = new TreeMap<String, Collection<Extention>>();
			extentions = new Vector<Extention>();
			extentions.add(extention);
			spots.put(hotSpotID, extentions);
			this.extentions.put(serviceID, spots);
		}
	}

}
