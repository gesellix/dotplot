/*
 * Created on 23.05.2004
 */
package org.dotplot.tokenizer;

import java.io.File;
import java.io.IOException;

/**
 * Interface for a converter.
 * <p />
 * Convertes a format into  another.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface IConverter
{
   /**
    * Convertes a file and writes this into the Temp-directory of the system.
    *
    * @param file - The file which should be converted
    *
    * @return - The new created ASCII file.
    *
    * @throws IOException if conversion fails
    */
   public File convert(File file) throws IOException;

   /**
    * Convertes a file and writes it in a special directory.
    *
    * @param file      - The file which should be converted
    * @param directory the directory in which the new file should be saved
    *
    * @return - The new created ASCII file
    *
    * @throws IOException if conversion fails
    */
   public File convert(File file, File directory) throws IOException;
}
