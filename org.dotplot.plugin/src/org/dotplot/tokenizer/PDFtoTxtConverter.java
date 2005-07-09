/*
 * Created on 27.05.2004
 */
package org.dotplot.tokenizer;

import org.apache.log4j.Logger;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Convertes a PDF-file into a TXT-file with the PDF-Box.
 *
 * @author Sebastian Lorberg
 * @author Christian Gerhardt
 * @version 1.0
 */
public class PDFtoTxtConverter implements IConverter
{
   /**
    * Logger for the <code>PDFtoTxtConverter</code>
    */
   private static Logger logger = Logger.getLogger(PDFtoTxtConverter.class.getName());

   /**
    * Convertes a file and writes it into the Temp-directory of the system.
    *
    * @param file - The file which should be converted
    *
    * @return - The created ASCII-file
    *
    * @throws IOException if conversion fails
    */
   public File convert(File file) throws IOException
   {
      logger.debug("convert file : " + file.toString());

      File directory = File.createTempFile("temp", "tmp");
      File destination = new File(directory.getParent() + File.separator + file.getName() + "." + "cnv");
      if (destination.exists())
      {
         logger.debug("destination : " + destination.toString());

         return destination;
      }
      else
      {
         destination.createNewFile();

         logger.debug("destination : " + destination.toString());

         return convertFile(file, destination);
      }
   }

   /**
    * Convertes a file and writes it into a special directory.
    *
    * @param file      - The file which should be converted
    * @param directory -  The directory in which the new file should be saved
    *
    * @return - The created ASCII-file
    *
    * @throws IOException if conversion fails
    */
   public File convert(File file, File directory) throws IOException
   {
      logger.debug("convert: file - " + file.toString());
      logger.debug("convert: directory - " + directory.toString());

      directory = directory.getCanonicalFile();
      File destination = new File(directory + File.separator + file.getName() + "." + "cnv");

      logger.debug("convert: destination - " + destination.toString());

      if (destination.exists())
      {
         logger.debug("convert: destination exists! ");
         return destination;
      }
      else
      {
         logger.debug("convert: creating new destination - " + destination);

         try
         {
            destination.createNewFile();
         }
         catch (IOException e)
         {
            logger.error("convert: Exception - ", e);
            throw e;
         }
         logger.debug("convert: creating complete");

         File converted = this.convertFile(file, destination);

         logger.debug("convert: conversion complete");
         return converted;
      }
   }

   /**
    * Converts a Pdf - File into an target ASCII File.
    *
    * @param origin      - the original file
    * @param destination -the empty file-
    *
    * @return destination -the filled file-
    *
    * @throws IOException in case if something goes wrong
    */
   public File convertFile(File origin, File destination) throws IOException
   {
      logger.debug("convertFile origin : " + origin);
      logger.debug("convertFile destination : " + destination);

      PDFTextStripper stripper = null;
      try
      {
         stripper = new PDFTextStripper();
      }
      catch (Exception e)
      {
         logger.error("error stripping text", e);
      }

      InputStream input = null;
      Writer output = null;
      COSDocument document = null;
      int startPage = 1;
      int endPage = Integer.MAX_VALUE;

      logger.debug("convertFile starting conversion ");

      try
      {
         input = new FileInputStream(origin);
         document = parseDocument(input);

         stripper.setStartPage(startPage);
         stripper.setEndPage(endPage);
         output = new OutputStreamWriter(new FileOutputStream(destination), "UTF-16");
         stripper.writeText(document, output);
      }
      catch (IOException e)
      {
         throw e;
      }
      finally
      {
         if (input != null)
         {
            input.close();
         }
         if (output != null)
         {
            output.close();
         }
         if (document != null)
         {
            document.close();
         }
      }

      logger.debug("convertFile conversion finished");

      return destination;
   }

   /**
    * Parsing of the document.
    *
    * @throws IOException
    */
   private COSDocument parseDocument(InputStream input) throws IOException
   {
      PDFParser parser = new PDFParser(input);
      parser.parse();
      return parser.getDocument();
   }
}
