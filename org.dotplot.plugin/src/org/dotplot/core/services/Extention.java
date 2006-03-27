/**
 * 
 */
package org.dotplot.core.services;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class Extention {

	/**
	 * 
	 */
	private IServiceExtentionActivator activator;
	
	/**
	 * 
	 */
	private Object extentionObject;
	
	/**
	 * 
	 */
	private Map<String, String> parameter;

	/**
	 * Creates a new <code>Extention</code>.
	 * @param plugin
	 * @param extentionObject
	 */
	public Extention(IServiceExtentionActivator plugin, Object extentionObject) {
		if(plugin == null || extentionObject == null) throw new NullPointerException();
		this.activator = plugin;
		this.extentionObject = extentionObject;
		this.parameter = new TreeMap<String, String>();
	}
	
	/**
	 * 
	 * @return
	 */
	public IServiceExtentionActivator getActivator(){
		return this.activator;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getExtentionObject(){
		return this.extentionObject;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addParameter(String name, String value){
		if(name == null || value == null) throw new NullPointerException();
		this.parameter.put(name, value);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		if(name == null) throw new NullPointerException();
		return this.parameter.get(name);
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getParameters(){
		return this.parameter;
	}
}
