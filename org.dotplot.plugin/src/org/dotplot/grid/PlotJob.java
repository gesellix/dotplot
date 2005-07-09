package org.dotplot.grid;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Vector;

import org.dotplot.fmatrix.TypeTable;
import org.dotplot.image.QImageConfiguration;

/**
 * Represents a Job. Mainly acts as wrapper for the TypeTable, say F-Matrix, and corresponding parameters.
 *
 * @author Tobias Gesellchen
 */
public class PlotJob implements Serializable
{
   private QImageConfiguration imageConfig;
   private GridConfiguration gridConfig;
   private TypeTable typeTable;
   private Vector collaborators;
   private Dimension targetSize;

   /**
    * Needed for the ability to be Serializable. DO NOT REMOVE!
    */
   public PlotJob()
   {
   }

   /**
    * Constructs a PlotJob.
    *
    * @param imageConfig a QImageConfiguration object
    * @param typeTable   a TypeTable object
    */
   public PlotJob(QImageConfiguration imageConfig, TypeTable typeTable)
   {
      this(imageConfig, null, typeTable);
   }

   /**
    * Constructs a PlotJob.
    *
    * @param imageConfig a QImageConfiguration object
    * @param gridConfig  a GridConfiguration object
    * @param typeTable   a TypeTable object
    */
   public PlotJob(QImageConfiguration imageConfig, GridConfiguration gridConfig, TypeTable typeTable)
   {
      this.imageConfig = imageConfig;
      this.gridConfig = gridConfig;
      this.typeTable = typeTable;
   }

   /**
    * returns the QImageConfiguration, if set.
    *
    * @return the QImageConfiguration, or null
    *
    * @see #setImageConfig(org.dotplot.image.QImageConfiguration)
    */
   public QImageConfiguration getImageConfig()
   {
      return imageConfig;
   }

   /**
    * sets the QImageConfiguration to be used for this Plot.
    *
    * @param imageConfig the QImageConfiguration
    *
    * @see #getImageConfig()
    */
   public void setImageConfig(QImageConfiguration imageConfig)
   {
      this.imageConfig = imageConfig;
   }

   /**
    * returns the GridConfiguration, if set.
    *
    * @return the GridConfiguration, or null
    *
    * @see #setGridConfig(GridConfiguration)
    */
   public GridConfiguration getGridConfig()
   {
      return gridConfig;
   }

   /**
    * sets the GridConfiguration to be used for this Plot.
    *
    * @param gridConfig the GridConfiguration
    *
    * @see #getGridConfig()
    */
   public void setGridConfig(GridConfiguration gridConfig)
   {
      this.gridConfig = gridConfig;
   }

   /**
    * returns the TypeTable containing the Matches.
    *
    * @return the TypeTable
    *
    * @see #setTypeTable(org.dotplot.fmatrix.TypeTable)
    */
   public TypeTable getTypeTable()
   {
      return typeTable;
   }

   /**
    * sets the TypeTable to be used for this Plot.
    *
    * @param typeTable the TypeTable
    *
    * @see #getTypeTable()
    */
   public void setTypeTable(TypeTable typeTable)
   {
      this.typeTable = typeTable;
   }

   /**
    * returns the Collaborators, involved in this Plot.
    *
    * @return the list of Collaborators
    *
    * @see #setCollaborators(java.util.Vector)
    */
   public Vector getCollaborators()
   {
      return collaborators;
   }

   /**
    * sets the Collaborators, that are involved in this Plot.
    *
    * @param collaborators the Collaborators
    *
    * @see #getCollaborators()
    */
   public void setCollaborators(Vector collaborators)
   {
      this.collaborators = collaborators;
   }

   /**
    * returns the target size, if previously set. If none was set, the size of the TypeTable is returned.
    *
    * @return the target size
    *
    * @see #setTargetSize(java.awt.Dimension)
    */
   public Dimension getTargetSize()
   {
      if (targetSize == null)
      {
         targetSize = getTypeTable().getNavigator().getSize();
      }

      return targetSize;
   }

   /**
    * sets the target size.
    *
    * @param targetSize the new target size.
    *
    * @see #getTargetSize()
    */
   public void setTargetSize(Dimension targetSize)
   {
      this.targetSize = targetSize;
   }
}
