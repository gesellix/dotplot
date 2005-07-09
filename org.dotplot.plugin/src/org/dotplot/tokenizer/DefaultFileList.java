package org.dotplot.tokenizer;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Stadard file List on the base of a Vector.
 *
 * @author Christian Gerhardt <case421gmx.net>
 * @version 1.0 3.5.04
 */
public class DefaultFileList extends Vector implements IFileList
{
   /**
    * The Logger for the DefaultFileList.
    */
   private static Logger logger = Logger.getLogger(DefaultFileList.class.getName());

   /**
    * Gibt die ein über index spezifizierte Datei aus der Liste aus.
    * Returns an index specified file from the list
    *
    * @param index --the index amount of the direcoty.
    *
    * @return --the file
    *
    * @see org.dotplot.tokenizer.IFileList#getFile(int)
    */
   public File getFile(int index)
   {
      if (index >= this.size())
      {
         logger.error("index " + index + " >= size " + size());
         throw new IllegalArgumentException("index out of bounds " + index + ">=" + size());
      }
      else
      {
         return (File) this.elementAt(index);
      }
   }

   /**
    * Computes the amount of the files in the list.
    *
    * @see org.dotplot.tokenizer.IFileList#count()
    */
   public int count()
   {
      return this.size();
   }

   /**
    * Creats a String of the content of the <code>FileList</code>.
    *
    * @return - the String.
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer("DefaultFileList\n");

      Enumeration e = this.elements();
      while (e.hasMoreElements())
      {
         sb.append(e.nextElement().toString() + '\n');
      }

      return sb.toString();
   }

   /**
    * Creats an Enumeration Object of the content of the <code>FileList</code>.
    *
    * @return - the Object
    *
    * @see org.dotplot.tokenizer.IFileList#getEnumeration()
    */
   public Enumeration getEnumeration()
   {
      return this.elements();
   }

   /**
    * Convertes convertable files from the list und saves these files in a directory.
    * If there is given a Null for the directory, the Temp-directory of the system will be used.
    *
    * @param directory - the directory
    */
   public void convertFiles(File directory)
   {
      logger.debug("convertFile: start");

      //datei im temp-verzeichnis rauskriegen
      if (directory == null)
      {
         File tempFile;

         try
         {
            tempFile = File.createTempFile("temp", "tmp");
         }
         catch (IOException e)
         {
            // ignore silently
            logger.debug("error creating tempFile");
            tempFile = new File("");
         }

         directory = new File(tempFile.getParent());
         logger.debug("convertFile: found tempdir - " + directory.toString());
         tempFile.delete();
      }

      Enumeration e = this.elements();
      File file;
      DotplotFile dpFile;
      StringTokenizer tz;
      String suffix = new String("");
      String converter;
      IConverter theRealConverter;

      TreeMap converters = (TreeMap) GlobalConfiguration.getInstance().get(
            GlobalConfiguration.KEY_TOKENIZER_TEXT_CONVERTERS);

      //die liste durchgehen.
      while (e.hasMoreElements())
      {
         file = (File) e.nextElement();

         //das zu konvertierende File aus der liste nehmen
         this.remove(file);

         if (file instanceof DotplotFile)
         {
            dpFile = (DotplotFile) file;
         }
         else
         {
            dpFile = new DotplotFile(file.getAbsolutePath());
         }

         //die dateiendung erkennen
         tz = new StringTokenizer(dpFile.getName(), ".");
         while (tz.hasMoreTokens())
         {
            suffix = tz.nextToken();
         }
         suffix = "." + suffix.toLowerCase();

         //den dazugehörigen konverter erzeugen.
         converter = (String) converters.get(suffix);
         if (converter != null)
         {
            try
            {
               //den konverter erzeugen
               theRealConverter
                     = (IConverter) Class.forName("org.dotplot.tokenizer.".concat(converter)).newInstance();
               //die datei mit dem konverter konvertieren.
               dpFile.setConverter(theRealConverter);
               dpFile.convertFile(directory);
            }
            catch (Exception e1)
            {
               //dann wird eben nicht convertiert!
               logger.error("conversion failed", e1);
            }
         }

         //das konvertierte File wieder einfügen
         this.add(dpFile);
      }
      logger.debug("convertFile: done.");
   }
}
