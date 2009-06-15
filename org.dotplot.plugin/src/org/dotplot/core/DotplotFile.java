/**
 * 
 */
package org.dotplot.core;

import java.io.File;
import java.io.IOException;

import org.dotplot.core.plugins.ressources.FileRessource;
import org.dotplot.tokenizer.service.TextType;

/**
 * Instances serves as <code>IPlotSource</code> representation of standard
 * files.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotFile extends FileRessource implements IPlotSource {

    /**
     * The <code>SourceType</code> of the file.
     */
    private ISourceType type;

    /**
     * Creates a new <code>DotplotFile</code>.
     * <p>
     * <code>TextType</code> is assigned as <code>SourceType</code>.
     * </p>
     * 
     * @param file
     *            The represented file.
     * @throws NullPointerException
     *             if file is <code>null</code>.
     * @throws IllegalArgumentException
     *             if file is not a valid file.
     * @see TextType
     */
    public DotplotFile(File file) {
	this(file, TextType.type);
    }

    /**
     * Creates a new <code>DotplotFile</code>.
     * 
     * @param file
     *            The represented file.
     * @param type
     *            <code>SourceType</code> of the file
     * @throws NullPointerException
     *             if file is <code>null</code>.
     * @throws NullPointerException
     *             if type is <code>null</code>.
     * @throws IllegalArgumentException
     *             if file is not a valid file.
     */
    public DotplotFile(File file, ISourceType type) {
	super(file);
	if (type == null) {
	    throw new NullPointerException();
	}
	this.type = type;
    }

    /**
     * Creates a new <code>DotplotFile</code>.
     * <p>
     * <code>TextType</code> is assigned as <code>SourceType</code>.
     * </p>
     * 
     * @param path
     *            Path to the represented file.
     * @throws NullPointerException
     *             if path is <code>null</code>.
     * @throws IllegalArgumentException
     *             if path points not to a valid file.
     * @see TextType
     */
    public DotplotFile(String path) {
	this(path, TextType.type);
    }

    /**
     * Creates a new <code>DotplotFile</code>.
     * 
     * @param path
     *            Path to the represented file.
     * @param type
     *            <code>SourceType</code> of the file
     * @throws NullPointerException
     *             if path is <code>null</code>.
     * @throws NullPointerException
     *             if type is <code>null</code>.
     * @throws IllegalArgumentException
     *             if path points not to a valid file.
     */
    public DotplotFile(String path, ISourceType type) {
	this(new File(path), type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IPlotSource#getName()
     */
    public String getName() {
	try {
	    return this.getFile().getCanonicalPath();
	} catch (IOException e) {
	    return this.getFile().getAbsolutePath();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IPlotSource#getType()
     */
    public ISourceType getType() {
	return this.type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.IPlotSource#size()
     */
    public long size() {
	return this.getFile().length();
    }
}
