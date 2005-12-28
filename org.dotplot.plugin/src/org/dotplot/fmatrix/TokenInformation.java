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

   /**
    * Returns the line number by index.
    *
    * @param fileID the file ID
    * @param index  the index
    *
    * @return an int representing the line number
    */
   public int getLineIndex(int fileID, int index)
   {
      if (lineInformations.size() < 1)
      {
         return -1;
      }

      return ((LineInformation) lineInformations.elementAt(fileID)).getLineIndex(index);
   }

   // ------------------------- FileInformation Access Functions ---------------------------

   /**
    * Returns the file id by index.
    *
    * @param index the index
    *
    * @return an int representing the file ID
    */
   public int getFileIdByIndex(int index)
   {
      int fileIndex = -1;
      Iterator fileInfoIter = fileInformations.iterator();
      while (fileInfoIter.hasNext())
      {
         FileInformation fileInfo = (FileInformation) fileInfoIter.next();
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
    * @param fileID the file ID
    *
    * @return the corresponding filename
    */
   public String getFileName(int fileID)
   {
      if (fileInformations.size() < 1)
      {
         return null;
      }

      return ((FileInformation) fileInformations.elementAt(fileID)).getFilename();
   }

   /**
    * Returns the file size by file id.
    *
    * @param fileID the file ID
    *
    * @return the corresponding file size
    */
   public long getFileSize(int fileID)
   {
      if (fileInformations.size() < 1)
      {
         return -1;
      }

      return ((FileInformation) fileInformations.elementAt(fileID)).getFileSize();
   }

   /**
    * Returns the start index by file id.
    *
    * @param fileID the file ID
    *
    * @return the corresponding start index
    */
   public int getStartIndex(int fileID)
   {
      if (fileInformations.size() < 1)
      {
         return -1;
      }

      return ((FileInformation) fileInformations.elementAt(fileID)).getStartIndex();
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
         indices.add(new Integer(((FileInformation) fileInformationsIter.next()).getStartIndex()));
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
   public void addFileInformation(FileInformation container)
   {
      fileInformations.add(container);
      if (container.getFileSize() > -1)
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
