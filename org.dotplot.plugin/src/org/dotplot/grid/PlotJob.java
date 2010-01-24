package org.dotplot.grid;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Vector;

import org.dotplot.core.ITypeTable;
import org.dotplot.image.IQImageConfiguration;

/**
 * Represents a Job. Mainly acts as wrapper for the TypeTable, say F-Matrix, and
 * corresponding parameters.
 * 
 * @author Tobias Gesellchen
 */
public class PlotJob implements Serializable {
	/**
	 * for being Serializable
	 */
	private static final long serialVersionUID = 8503709291982027179L;

	private IQImageConfiguration imageConfig;

	private GridConfiguration gridConfig;

	private ITypeTable typeTable;

	private Vector collaborators;

	private Dimension targetSize;

	/**
	 * Needed for the ability to be Serializable. DO NOT REMOVE!
	 */
	public PlotJob() {
	}

	/**
	 * Constructs a PlotJob.
	 * 
	 * @param imageConfig
	 *            a QImageConfiguration object
	 * @param gridConfig
	 *            a GridConfiguration object
	 * @param typeTable
	 *            a TypeTable object
	 */
	public PlotJob(IQImageConfiguration imageConfig,
			GridConfiguration gridConfig, ITypeTable typeTable) {
		this.imageConfig = imageConfig;
		this.gridConfig = gridConfig;
		this.typeTable = typeTable;
	}

	/**
	 * Constructs a PlotJob.
	 * 
	 * @param imageConfig
	 *            a QImageConfiguration object
	 * @param typeTable
	 *            a TypeTable object
	 */
	public PlotJob(IQImageConfiguration imageConfig, ITypeTable typeTable) {
		this(imageConfig, null, typeTable);
	}

	/**
	 * returns the Collaborators, involved in this Plot.
	 * 
	 * @return the list of Collaborators
	 * 
	 * @see #setCollaborators(java.util.Vector)
	 */
	public Vector getCollaborators() {
		return collaborators;
	}

	/**
	 * returns the GridConfiguration, if set.
	 * 
	 * @return the GridConfiguration, or null
	 * 
	 * @see #setGridConfig(GridConfiguration)
	 */
	public GridConfiguration getGridConfig() {
		return gridConfig;
	}

	/**
	 * returns the QImageConfiguration, if set.
	 * 
	 * @return the QImageConfiguration, or null
	 * 
	 * @see #setImageConfig(org.dotplot.image.QImageConfiguration)
	 */
	public IQImageConfiguration getImageConfig() {
		return imageConfig;
	}

	/**
	 * returns the target size, if previously set. If none was set, the size of
	 * the TypeTable is returned.
	 * 
	 * @return the target size
	 * 
	 * @see #setTargetSize(java.awt.Dimension)
	 */
	public Dimension getTargetSize() {
		if (targetSize == null) {
			targetSize = getTypeTable().createNavigator().getSize();
		}

		return targetSize;
	}

	/**
	 * returns the TypeTable containing the Matches.
	 * 
	 * @return the TypeTable
	 * 
	 * @see #setTypeTable(org.dotplot.fmatrix.TypeTable)
	 */
	public ITypeTable getTypeTable() {
		return typeTable;
	}

	/**
	 * sets the Collaborators, that are involved in this Plot.
	 * 
	 * @param collaborators
	 *            the Collaborators
	 * 
	 * @see #getCollaborators()
	 */
	public void setCollaborators(Vector collaborators) {
		this.collaborators = collaborators;
	}

	/**
	 * sets the GridConfiguration to be used for this Plot.
	 * 
	 * @param gridConfig
	 *            the GridConfiguration
	 * 
	 * @see #getGridConfig()
	 */
	public void setGridConfig(GridConfiguration gridConfig) {
		this.gridConfig = gridConfig;
	}

	/**
	 * sets the QImageConfiguration to be used for this Plot.
	 * 
	 * @param imageConfig
	 *            the QImageConfiguration
	 * 
	 * @see #getImageConfig()
	 */
	public void setImageConfig(IQImageConfiguration imageConfig) {
		this.imageConfig = imageConfig;
	}

	/**
	 * sets the target size.
	 * 
	 * @param targetSize
	 *            the new target size.
	 * 
	 * @see #getTargetSize()
	 */
	public void setTargetSize(Dimension targetSize) {
		this.targetSize = targetSize;
	}

	/**
	 * sets the TypeTable to be used for this Plot.
	 * 
	 * @param typeTable
	 *            the TypeTable
	 * 
	 * @see #getTypeTable()
	 */
	public void setTypeTable(ITypeTable typeTable) {
		this.typeTable = typeTable;
	}
}
