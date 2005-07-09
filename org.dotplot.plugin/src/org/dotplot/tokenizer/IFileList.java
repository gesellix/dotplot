package org.dotplot.tokenizer;

import java.io.File;
import java.util.Enumeration;

/**
 * Interface for a file list which will be handled from a tokenizer.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface IFileList
{
   /**
    * Sucht den mit Index spezifizierten Eintrag aus der Dateiliste.
    * Searches for indexed entry from the file list.
    *
    * @param index the index from the file
    *
    * @return the file
    */
   public File getFile(int index);

   /**
    * Gibt die Anzahl der Einträge in der Dateiliste zurück.
    * Returns the amount of the entrys of the file list.
    *
    * @return the amount
    */
   public int count();

   /**
    * creates a <code>Enumeration</code> of the file.
    *
    * @return - the filelist
    */
   public Enumeration getEnumeration();

   /**
    * convertes the file of the list.
    *
    * @param directory - the directory in which the converted file should be stored
    */
   public void convertFiles(File directory);
}
