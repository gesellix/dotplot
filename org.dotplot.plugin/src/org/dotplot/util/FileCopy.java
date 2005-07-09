package org.dotplot.util;

// This example is from the book _Java in a Nutshell_ by David Flanagan.
// Written by David Flanagan.  Copyright (c) 1996 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or implied.

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copies files.
 */
public class FileCopy
{
   private static final Logger logger = Logger.getLogger(FileCopy.class.getName());

   /**
    * Copies a file to the given <code>dest_name</code>.
    *
    * @param source_name the source file
    * @param dest_name   the destination file
    *
    * @throws IOException if the copy fails
    */
   public static void copy(String source_name, String dest_name)
         throws IOException
   {
      copy(source_name, dest_name, false, 0);
   }

   /**
    * Copies a file to the given <code>dest_name</code>.
    * <p />
    * overwriteMode can be one of the following values:
    * <ul>
    * <li>1 - ignore existing files, overwrite them</li>
    * <li>2 - update existing files, this action will be logged</li>
    * </ul>
    * If no value matches, the user will be asked, if he wants to overwrite.
    *
    * @param source_name             the source file
    * @param dest_name               the destination file
    * @param dontBreakOnNotOverwrite if set to true, don't throw an exception if overwriting fails
    * @param overwriteMode           mode to handle existing files
    *
    * @throws IOException if the copy fails
    */
   public static void copy(String source_name, String dest_name, boolean dontBreakOnNotOverwrite, int overwriteMode)
         throws IOException
   {
      File source_file = new File(source_name);
      File destination_file = new File(dest_name);

      FileInputStream source = null;
      FileOutputStream destination = null;

      byte[] buffer;
      int bytes_read;

      try
      {
         // First make sure the specified source file exists, is a file, and is readable.
         if (!source_file.exists() || !source_file.isFile())
         {
            throw new FileCopyException("FileCopy: no such source file: " + source_name);
         }
         if (!source_file.canRead())
         {
            throw new FileCopyException("FileCopy: source file is unreadable: " + source_name);
         }

         // If the destination exists, make sure it is a writeable file
         // and ask before overwriting it.  If the destination doesn't
         // exist, make sure the directory exists and is writeable.
         if (destination_file.exists())
         {
            if (destination_file.isFile())
            {
//               DataInputStream in = new DataInputStream(System.in);
               BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

               String response;

               if (!destination_file.canWrite())
               {
                  throw new FileCopyException("FileCopy: destination file is unwriteable: " + dest_name);
               }

               switch (overwriteMode)
               {
                  case 1: // "overwrite"
                     logger.warn("file already exists, it will be overwritten");
                     break;

                  case 2: // "update"
                     if (source_file.lastModified() > destination_file.lastModified())
                     {
                        logger.warn("file already exists, but source file is newer");
                        break;
                     }
                     else if (dontBreakOnNotOverwrite)
                     {
                        return;
                     }
                     else
                     {
                        throw new FileCopyException("FileCopy: copy cancelled.");
                     }

                  default: // "ask, whether overwrite or not"
                     System.out.print("File " + dest_name + " already exists. Overwrite? (Y/N): ");
                     System.out.flush();
                     response = in.readLine();
                     if (!response.equals("Y") && !response.equals("y"))
                     {
                        if (dontBreakOnNotOverwrite)
                        {
                           return;
                        }
                        else
                        {
                           throw new FileCopyException("FileCopy: copy cancelled.");
                        }
                     }
               }
            }
            else
            {
               throw new FileCopyException("FileCopy: destination is not a file: " + dest_name);
            }
         }
         else
         {
            File parentdir = parent(destination_file);
            if (!parentdir.exists())
            {
               throw new FileCopyException("FileCopy: destination directory doesn't exist: " + dest_name);
            }
            if (!parentdir.canWrite())
            {
               throw new FileCopyException("FileCopy: destination directory is unwriteable: " + dest_name);
            }
         }

//         System.out.println(source_name);
//         System.out.flush();

         // If we've gotten this far, then everything is okay; we can copy the file.
         source = new FileInputStream(source_file);
         destination = new FileOutputStream(destination_file);
         buffer = new byte[1024];
         while (true)
         {
            bytes_read = source.read(buffer);
            if (bytes_read == -1) break;
            destination.write(buffer, 0, bytes_read);
         }
      }
//             No matter what happens, always close any streams we've opened.
      finally
      {
         if (source != null)
         {
            try
            {
               source.close();
            }
            catch (IOException e)
            {
               ;
            }
         }
         if (destination != null)
         {
            try
            {
               destination.close();
            }
            catch (IOException e)
            {
               ;
            }
         }
      }
   }

   // File.getParent() can return null when the file is specified without
   // a directory or is in the root directory.
   // This method handles those cases.
   private static File parent(File f)
   {
      String dirname = f.getParent();
      if (dirname == null)
      {
         if (f.isAbsolute())
         {
            return new File(File.separator);
         }
         else
         {
            return new File(System.getProperty("user.dir"));
         }
      }
      return new File(dirname);
   }

   /**
    * Provides access from the console.
    *
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      if (args.length != 2)
      {
         System.err.println("Usage: java FileCopy <source file> <destination file>");
      }
      else
      {
         try
         {
            copy(args[0], args[1]);
         }
         catch (IOException e)
         {
            System.err.println(e.getMessage());
         }
      }
   }
}

class FileCopyException extends IOException
{
   public FileCopyException(String msg)
   {
      super(msg);
   }
}
