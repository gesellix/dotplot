/**
 * 
 */
package org.dotplot.tokenizer.converter;

import java.io.File;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.dotplot.core.IConfiguration;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.service.TextType;

/**
 * Defaultimplementation of the <code>IConverterConfiguration</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DefaultConverterConfiguration implements IConverterConfiguration {

	/**
	 * 
	 */
	private boolean keepConvertedFiles;

	/**
	 * 
	 */
	private boolean overwriteFiles;

	/**
	 * 
	 */
	private File convertedDirectory;

	/**
	 * 
	 */
	private ISourceType targetType;

	private boolean convertFiles;

	private Map<ISourceType, String> converterRegistry;

	/**
	 * 
	 */
	public DefaultConverterConfiguration() {
		super();
		this.keepConvertedFiles = true;
		this.convertFiles = false;
		this.overwriteFiles = false;
		this.convertedDirectory = new File(".");
		this.targetType = TextType.type;
		this.converterRegistry = new TreeMap<ISourceType, String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#copy()
	 */
	public IConfiguration copy() {
		DefaultConverterConfiguration clone = new DefaultConverterConfiguration();
		clone.convertedDirectory = this.convertedDirectory;
		clone.converterRegistry.putAll(this.converterRegistry);
		clone.keepConvertedFiles = this.keepConvertedFiles;
		clone.overwriteFiles = this.overwriteFiles;
		clone.targetType = this.targetType;
		clone.convertFiles = this.convertFiles;
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dotplot.tokenizer.converter.IConverterConfiguration#
	 * getCovertedFilesDirectory()
	 */
	public File getConvertedFilesDirectory() {
		return this.convertedDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#getConverterID
	 * (org.dotplot.core.ISourceType)
	 */
	public String getConverterID(ISourceType type) {
		if (type == null) {
			throw new NullPointerException();
		}
		return this.converterRegistry.get(type);
	}

	public Map<ISourceType, String> getConverterRegistry() {
		return this.converterRegistry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#getConvertFiles()
	 */
	public boolean getConvertFiles() {
		return this.convertFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#getTargetType()
	 */
	public ISourceType getTargetType() {
		return this.targetType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#keppConvertedFiles
	 * ()
	 */
	public boolean keepConvertedFiles() {
		return this.keepConvertedFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#objectForm(java.lang.String)
	 */
	public IConfiguration objectForm(String serializedForm)
			throws UnsupportedOperationException {
		if (serializedForm == null) {
			throw new NullPointerException();
		}
		DefaultConverterConfiguration config = new DefaultConverterConfiguration();
		StringTokenizer tokenizer = new StringTokenizer(serializedForm, ";");

		try {
			String file = tokenizer.nextToken();
			String keepFiles = tokenizer.nextToken();
			String overideFiles = tokenizer.nextToken();
			config.setConvertedFilesDirectory(new File(file));
			config.setKeepConvertedFiles(new Boolean(keepFiles));
			config.setOverwriteConvertedFiles(new Boolean(overideFiles));
			if (tokenizer.hasMoreTokens()) {
				throw new IllegalArgumentException();
			}
			config.setConvertFiles(true);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dotplot.tokenizer.converter.IConverterConfiguration#
	 * overwriteConvertedFiles()
	 */
	public boolean overwriteConvertedFiles() {
		return this.overwriteFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#serializedForm()
	 */
	public String serializedForm() throws UnsupportedOperationException {
		if (this.convertFiles) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(this.getConvertedFilesDirectory());
			buffer.append(";");
			buffer.append(this.keepConvertedFiles);
			buffer.append(";");
			buffer.append(this.overwriteFiles);
			return buffer.toString();
		}
		else {
			return "";
		}
	}

	/**
	 * Sets the convertedDirectory.
	 * 
	 * @param convertedDirectory
	 *            The convertedDirectory to set.
	 */
	public void setConvertedFilesDirectory(File convertedDirectory) {
		if (convertedDirectory != null) {
			if (!(convertedDirectory.exists() && convertedDirectory
					.isDirectory())) {
				throw new IllegalArgumentException(convertedDirectory
						.getAbsolutePath()
						+ " does not exist!");
			}
		}
		this.convertedDirectory = convertedDirectory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#setConverter(
	 * org.dotplot.core.ISourceType, java.lang.String)
	 */
	public void setConverter(ISourceType type, String converterID) {
		if (type != null) {
			if (converterID == null) {
				this.converterRegistry.remove(type);
			}
			else {
				this.converterRegistry.put(type, converterID);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#setConvertFiles
	 * (boolean)
	 */
	public void setConvertFiles(boolean state) {
		this.convertFiles = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.tokenizer.converter.IConverterConfiguration#setKeepConvertedFiles
	 * (boolean)
	 */
	public void setKeepConvertedFiles(boolean keepConvertedFiles) {
		this.keepConvertedFiles = keepConvertedFiles;
	}

	/**
	 * Sets the overwriteFiles.
	 * 
	 * @param overwriteFiles
	 *            The overwriteFiles to set.
	 */
	public void setOverwriteConvertedFiles(boolean overwriteFiles) {
		this.overwriteFiles = overwriteFiles;
	}

	/**
	 * Sets the targetType.
	 * 
	 * @param targetType
	 *            The targetType to set.
	 */
	public void setTargetType(ISourceType targetType) {
		if (targetType == null) {
			throw new NullPointerException();
		}
		this.targetType = targetType;
	}
}
