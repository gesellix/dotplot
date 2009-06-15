/**
 * 
 */
package org.dotplot.core.plugins.ressources;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dotplot.core.services.IRessource;

/**
 * Instances of this class represent directorys.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DirectoryRessource implements IRessource {

    /**
     * The represented directory.
     */
    private File directory;

    /**
     * Creates a new <code>DirectoryRessource</code>.
     * 
     * @param directory
     *            The directory to represent
     * @throws NullPointerException
     *             if directory is <code>null</code>
     * @throws IllegalArgumentException
     *             if directory is not a directory.
     */
    public DirectoryRessource(File directory) {
	if (directory == null) {
	    throw new NullPointerException();
	}
	this.setDirectory(directory);
    }

    /**
     * Creates a new <code>DirectoryRessource</code>.
     * 
     * @param path
     *            of the represented directory.
     * @throws NullPointerException
     *             if directory is <code>null</code>
     * @throws IllegalArgumentException
     *             if directory is not a directory.
     */
    public DirectoryRessource(String path) {
	if (path == null) {
	    throw new NullPointerException();
	}
	this.setDirectory(new File(path));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.ressources.IRessource#getInputStream()
     */
    public InputStream getInputStream() {
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.ressources.IRessource#getURL()
     */
    public URL getURL() {
	try {
	    return this.directory.toURI().toURL();
	} catch (MalformedURLException e) {
	    /* sollte nicht vorkommen */
	    return null;
	}
    }

    /**
     * Sets the directory.
     * 
     * @param file
     *            The dirextory to be set.
     * @throws IllegalArgumentException
     *             if file is not a directory.
     */
    private void setDirectory(File file) {
	if (!file.isDirectory()) {
	    throw new IllegalArgumentException();
	} else if (!file.exists()) {
	    throw new IllegalArgumentException();
	} else {
	    this.directory = file;
	}
    }
}
