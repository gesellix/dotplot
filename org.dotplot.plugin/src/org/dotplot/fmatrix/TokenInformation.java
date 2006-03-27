/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Stores additional token information, such as Filedetails, row info., etc.
 *
 * @author Oguz Huryasar
 * @version 0.1
 */
public class TokenInformation implements Serializable
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = 5804235101860601425L;
   private Vector lineInformations; // holds lineInformation
   private Vector fileInformations; // hold fileInformation
   private int fileInfoNumber;

   /**
    * Default Constructor.
    */
   public TokenInformation()
   {
      lineInformations = new Vector();
      fileInformations = new Vector();
      fileInfoNumber = 0;
   }

   // ------------------------- LineInformation Access Functions ---------------------------

   /**
    * adds a LineInformation(one per file).
    *
    * @param container - the LineInformation object to add.
    */
   public void addLineInformationContainer(LineInformation container)
   {
      lineInformations.add(container);
   }

   /**
    * returns the size(number of files) of the container.
    *
    * @return an int representing the line info size
    */
   public int getLineInfoSize()
   {
      return lineInformations.size();
   }

   public String getToken(int fileIndex, int index)
   {
      if (getLineInfoSize() < 1)
      {
         return "";
      }

      return ((LineInformation) lineInformations.elementAt(fileIndex)).getToken(index);
   }

   /**
    * Returns the line number by index.
    *
    * @param fileIndex the file ID
    * @param index     the index
    *
    * @return an int representing the line number
    */
   public int getLineIndex(int fileIndex, int index)
   {
      if (getLineInfoSize() < 1)
      {
         return -1;
      }

      return ((LineInformation) lineInformations.elementAt(fileIndex)).getLineIndex(index);
   }

   // ------------------------- FileInformation Access Functions ---------------------------

   /**
    * Returns the file id by index.
    *
    * @param index the index
    *
    * @return an int representing the file ID
    */
   public int getFileIndex(int index)
   {
      int fileIndex = -1;
      Iterator fileInfoIter = fileInformations.iterator();
      while (fileInfoIter.hasNext())
      {
         SourceInformation fileInfo = (SourceInformation) fileInfoIter.next();
         if (fileInfo.getStartIndex() <= index)
         {
            fileIndex++;
         }
         else
         {
            break;
         }
      }

      return fileIndex;
   }

   /**
    * Returns the file name by file id.
    *
    * @param fileIndex the file ID
    *
    * @return the corresponding filename
    */
   public String getFileName(int fileIndex)
   {
      if (fileInformations.size() < 1)
      {
         return null;
      }

      return ((SourceInformation) fileInformations.elementAt(fileIndex)).getSourcename();
   }

   /**
    * Returns the file size by file id.
    *
    * @param fileIndex the file ID
    *
    * @return the corresponding file size
    */
   public long getFileSize(int fileIndex)
   {
      if (fileInformations.size() < 1)
      {
         return -1;
      }

      return ((SourceInformation) fileInformations.elementAt(fileIndex)).getSize();
   }

   /**
    * Returns the start index by file id.
    *
    * @param fileIndex the file ID
    *
    * @return the corresponding start index
    */
   public int getStartIndex(int fileIndex)
   {
      if (fileInformations.size() < 1)
      {
         return -1;
      }

      return ((SourceInformation) fileInformations.elementAt(fileIndex)).getStartIndex();
   }

   /**
    * Returns all start indices.
    *
    * @return an array of ints representing all start indices
    */
   public int[] getAllStartIndices()
   {
      Vector indices = new Vector();

      Iterator fileInformationsIter = fileInformations.iterator();
      while (fileInformationsIter.hasNext())
      {
         indices.add(new Integer(((SourceInformation) fileInformationsIter.next()).getStartIndex()));
      }

      int i = 0;
      int[] ix = new int[indices.size()];
      Iterator indexIter = indices.iterator();
      while (indexIter.hasNext())
      {
         ix[i++] = ((Integer) indexIter.next()).intValue();
      }

      return ix;
   }

   /**
    * Adds a new file information.
    *
    * @param container a FileInformation object
    */
   public void addSourceInformation(SourceInformation container)
   {
      fileInformations.add(container);
      if (container.getSize() > -1)
      {
         fileInfoNumber++;
      }
   }

   /**
    * Returns the number of file informations.
    *
    * @return an int representing the number of file informations
    */
   public int getFileInfoNumber()
   {
      return fileInfoNumber;
   }
}
