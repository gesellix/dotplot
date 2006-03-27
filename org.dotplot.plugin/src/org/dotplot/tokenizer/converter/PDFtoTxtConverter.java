/*
 * Created on 27.05.2004
 */
package org.dotplot.tokenizer.converter;

import org.apache.log4j.Logger;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.service.TextType;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
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

   private boolean overwrite = false;
   
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
      File destination = new File(directory.getParent() + File.separator + file.getName() + "." + "txt");
      if (destination.exists())
      {
         logger.debug("destination : " + destination.toString());

         if(this.overwrite){
        	 destination.delete();
        	 destination.createNewFile();
        	 return convertFile(file, destination);
         }
         else {
        	 return destination;
         }
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
      File destination = new File(directory + File.separator + file.getName() + "." + "txt");

      logger.debug("convert: destination - " + destination.toString());

      if (destination.exists())
      {
    	  if(overwrite){
    		  logger.debug("convert: destination overwritten! ");
    		  destination.delete();
    		  destination.createNewFile();
    		  return this.convertFile(file, destination);
    	  }
    	  else {
    		  logger.debug("convert: destination exists! ");
    		  return destination;
    	  }
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
      PDDocument document = null;
      int startPage = 1;
      int endPage = Integer.MAX_VALUE;

      logger.debug("convertFile starting conversion ");

      try
      {
         input = new FileInputStream(origin);
         document = parseDocument(input);

         stripper.setStartPage(startPage);
         stripper.setEndPage(endPage);
         output = new OutputStreamWriter(new FileOutputStream(destination), "UTF-8");
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
   private PDDocument parseDocument(InputStream input) throws IOException
   {
      PDFParser parser = new PDFParser(input);
      parser.parse();
      return parser.getPDDocument();
   }

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.converter.IConverter#convert(org.dotplot.core.IPlotSource)
	 */
	public IPlotSource convert(IPlotSource source) throws IOException {
		if(source == null) throw new NullPointerException();
		if(source instanceof DotplotFile){
			DotplotFile file = (DotplotFile)source;
			if(file.getType().getClass() != this.getSourceTye().getClass()){
				throw new IllegalArgumentException();
			}
			else {
				File result = this.convert(file.getFile());
				return new DotplotFile(result, this.getTargetTye());
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.converter.IConverter#convert(org.dotplot.core.IPlotSource, java.io.File)
	 */
	public IPlotSource convert(IPlotSource source, File directory) throws IOException {
		if(source == null || directory == null) throw new NullPointerException();
		if(source instanceof DotplotFile){
			DotplotFile file = (DotplotFile)source;
			if(file.getType().getClass() != this.getSourceTye().getClass()){
				throw new IllegalArgumentException();
			}
			else {
				File result = this.convert(file.getFile(), directory);
				return new DotplotFile(result, this.getTargetTye());
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.converter.IConverter#getSourceTye()
	 */
	public ISourceType getSourceTye() {
		return PdfType.type;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.converter.IConverter#getTargetTye()
	 */
	public ISourceType getTargetTye() {
		return TextType.type;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.tokenizer.converter.IConverter#setOverwrite()
	 */
	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
}
