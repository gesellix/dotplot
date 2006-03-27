/**
 * 
 */
package org.dotplot.core.plugins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;

import org.dotplot.util.DOMTreeIterator;
import org.dotplot.util.JarFileClassLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Instances of this class represents Jar files of pluins.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PluginJarFile extends JarFile {

	/**
	 * 
	 */
	private ZipEntry pluginXMLEntry;
	
	/**
	 * 
	 */
	private Map<String, Version> dependencys;
	
	/**
	 * The name of the plugin.
	 */
	private String name;
	
	/**
	 * The informationstring of the plugin.
	 */
	private String info;
	
	/**
	 * The id of the plugin.
	 */
	private String id;
	
	/**
	 * The provider of the plugin.
	 */
	private String provider;
	
	/**
	 * 
	 */
	public static final String PLUGIN_XML_IDENT = "dotplotplugin.xml";

	/**
	 * Creates a new <code>PluginJarFile</code>.
	 * @param name
	 * @param schema
	 * @throws IOException
	 */
	public PluginJarFile(String name,Schema schema) throws IOException {
		super(name);
		this.init(schema);
	}

	/**
	 * Creates a new <code>PluginJarFile</code>.
	 * @param name
	 * @param schema
	 * @param verify
	 * @throws IOException
	 */
	public PluginJarFile(String name, Schema schema, boolean verify) throws IOException {
		super(name, verify);
		this.init(schema);
	}

	/**
	 * Creates a new <code>PluginJarFile</code>.
	 * @param file
	 * @param schema
	 * @throws IOException
	 */
	public PluginJarFile(File file,Schema schema) throws IOException {
		super(file);
		this.init(schema);
	}

	/**
	 * 
	 * Creates a new <code>PluginJarFile</code>.
	 * @param file
	 * @param schema
	 * @param verify
	 * @throws IOException
	 */
	public PluginJarFile(File file,Schema schema, boolean verify) throws IOException {
		super(file, verify);
		this.init(schema);
	}

	/**
	 * 
	 * @param schema
	 */
	private void init(Schema schema){
		Enumeration<JarEntry> en = this.entries();
		String entry;
		try{
			while(en.hasMoreElements()){
				entry = en.nextElement().getName();
				if(entry.endsWith(PLUGIN_XML_IDENT)){
					this.pluginXMLEntry = this.getEntry(entry);
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setValidating(false);
					factory.setCoalescing(true);
					factory.setIgnoringComments(true);
					factory.setIgnoringElementContentWhitespace(true);
					factory.setSchema(schema);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document document = builder.parse(this.getInputStream(this.pluginXMLEntry));
					
					Element element = (Element) document.getFirstChild();
					
					this.id = element.getAttribute("id");
					this.name = element.getAttribute("name");
					this.provider = element.getAttribute("provider");
					this.info = element.getAttribute("info");
					
					element =(Element) element.getFirstChild();					
					DOMTreeIterator iter = new DOMTreeIterator(element);
					this.dependencys = new TreeMap<String, Version>();
					String plugin, version;
					
					while(iter.hasNext()){
						element = (Element)iter.nextNode();
						if("Dependency".equals(element.getNodeName())){
							plugin = element.getAttribute("plugin");
							version = element.getAttribute("version");
							try {
								this.dependencys.put(plugin, new Version(version));
							}
							catch(MalformedVersionException e){
								this.dependencys.put(plugin, null);
							}
						}
						else {
							break;
						}						
					}										
					return;
				}
			}
		}
		catch(Exception e){
		}
		throw new IllegalArgumentException();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.util.zip.ZipFile#getName()
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the informationstring of the plugin.
	 * @return The informationsString.
	 */
	public String getInfo(){
		return this.info;
	}
	
	/**
	 * Returns the provider of the plugin.
	 * @return The provider.
	 */
	public String getProvider(){
		return this.provider;
	}

	/**
	 * Returns the id of the plugin.
	 * @return The id.
	 */
	public String getID(){
		return this.id;
	}
	
	/**
	 * 
	 * @return
	 */
	public InputStream getPluginXMLInputStream() throws IOException{
		return this.getInputStream(this.pluginXMLEntry);
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Version> getPluginDependencys(){		
		return this.dependencys;
	}
	
	/**
	 * Creates a <code>ClassLoader</code> for this <code>PluginJarFile</code>
	 * @param parentloader The parent <code>ClassLoader</code>.
	 * @return The created <code>ClassLoader</code>.
	 */
	public ClassLoader createClassLoader(ClassLoader parentloader){
		return new JarFileClassLoader(this, parentloader);
	}
}
