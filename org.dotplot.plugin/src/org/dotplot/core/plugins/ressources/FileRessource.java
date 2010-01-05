/**
 * 
 */
package org.dotplot.core.plugins.ressources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dotplot.core.services.IRessource;

/**
 * Instances of this class represent files.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class FileRessource implements IRessource {

	/**
	 * The represented file.
	 */
	private File file;

	/**
	 * Creates a new <code>FileRessource</code>.
	 * 
	 * @param file
	 *            The file to be represented
	 * @throws IllegalArgumentException
	 *             if file is not a file.
	 * @throws NullPointerException
	 *             if file is <code>null</code>.
	 */
	public FileRessource(File file) {
		if (file == null) {
			throw new NullPointerException();
		}
		this.setFile(file);
	}

	/**
	 * Creates a new <code>FileRessource</code>.
	 * 
	 * @param path
	 *            - the path of the represented file.
	 * @throws IllegalArgumentException
	 *             if the path points not to a file.
	 */
	public FileRessource(String path) {
		this(new File(path));
	}

	/**
	 * Returns the represented file.
	 * 
	 * @return THe file.
	 */
	public File getFile() {
		return this.file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ressources.IRessource#getInputStream()
	 */
	public InputStream getInputStream() {
		try {
			return new FileInputStream(this.file);
		}
		catch (FileNotFoundException e) {
			/* sollte nie vorkommen */
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.ressources.IRessource#getURL()
	 */
	public URL getURL() {
		try {
			return this.file.toURI().toURL();
		}
		catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Sets the file.
	 * 
	 * @param file
	 *            The file.
	 * @throws IllegalArgumentException
	 *             if file is not a file.
	 */
	private void setFile(File file) {
		try {
			File f = file.getCanonicalFile();
			if (!f.isFile()) {
				throw new IllegalArgumentException(file.getAbsolutePath()
						+ " is not a file!");
			}
			else if (!f.exists()) {
				throw new IllegalArgumentException(file.getAbsolutePath()
						+ " does not exist!");
			}
			else {
				this.file = f;
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
}
