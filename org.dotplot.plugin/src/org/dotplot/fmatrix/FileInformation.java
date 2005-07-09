/*
 * Created on Jun 25, 2004
 */
package org.dotplot.fmatrix;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Class stores all processed information of the used source files (dotplot sourcefiles)
 * for later retrieval.
 *
 * @author Thorsten Ruehl
 */
public class FileInformation implements Serializable
{
   private int startindex;
   private long filesize;
   private String filename;

   /**
    * Create a new FileInformation using the given values.
    *
    * @param _startindex the startindex
    * @param fileObject  the corresponding File
    */
   public FileInformation(int _startindex, File fileObject)
   {
      this.startindex = _startindex;
      if (fileObject != null)
      {
         try
         {
            filename = fileObject.getCanonicalPath();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
         filesize = fileObject.length();
      }
      else
      {
         filename = new String("no filename given");
         filesize = -1;
      }
   }

   /**
    * Returns the start index value.
    *
    * @return an int representing the start index value
    */
   public int getStartIndex()
   {
      return this.startindex;
   }

   /**
    * Gets this object's filename.
    *
    * @return a String representing the filename value
    */
   public String getFilename()
   {
      return this.filename;
   }

   /**
    * Gets this object's file size.
    *
    * @return a long representing the file size value
    */
   public long getFileSize()
   {
      return this.filesize;
   }

   public String toString()
   {
      return new String("filename: " + this.filename + "\nfilesize: " + this.filesize + " Bytes" + "\nstartindex: "
            + this.startindex);
   }
}
