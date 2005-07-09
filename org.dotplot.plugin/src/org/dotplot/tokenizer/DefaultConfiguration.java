package org.dotplot.tokenizer;

import java.io.File;

import org.dotplot.tokenizer.scanner.DefaultScanner;
import org.dotplot.tokenizer.scanner.IScanner;

/**
 * Baseimplemtation of the IConfiguration - Interfaceses.
 * It is used a Defaultscanner and a defaultFilelist
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class DefaultConfiguration implements IConfiguration
{
   /**
    * The directory in which the converted files are stored temporarly
    */
   private File convertedFilesDirectory;

   /**
    * If not readable files should be converted
    */
   private boolean convertFiles;

   /**
    * If a standard scanner should be used
    */
   private boolean useDefaultScanner;

   /**
    * The scanner, which should be used in this configuration
    */
   private IScanner scanner;

   /**
    * The file list of this configuration
    */
   private IFileList fileList;

   /**
    * the Tokenfilter, which should be used
    */
   private ITokenFilter TokenFilter;

   /**
    * Creates a base configuration.
    * This base configuration uses a simple DefaultScanner and a DefaultFileList.
    */
   public DefaultConfiguration()
   {
      this.scanner = new DefaultScanner();
      this.fileList = new DefaultFileList();
      this.TokenFilter = null;
      this.useDefaultScanner = true;
      this.convertedFilesDirectory = null;
      this.convertFiles = false;
   }

   /**
    * Sets a new Scanner for this configuration.
    *
    * @param scanner - der neue Scanner;
    *
    * @see #getScanner()
    */
   public void setScanner(IScanner scanner)
   {
      this.scanner = scanner;
   }

   /**
    * returns the current Scanner.
    *
    * @return -- der Scanner
    *
    * @see #setScanner(org.dotplot.tokenizer.scanner.IScanner)
    */
   public IScanner getScanner()
   {
      return this.scanner;
   }

   /**
    * Sets a new FileList for this configuration.
    *
    * @param fileList - the new Filelist
    *
    * @see #getFileList()
    */
   public void setFileList(IFileList fileList)
   {
      this.fileList = fileList;
   }

   /**
    * Returns the current FileList.
    *
    * @return - the Filelist
    *
    * @see #setFileList(IFileList)
    */
   public IFileList getFileList()
   {
      return this.fileList;
   }

   /**
    * Returns the current Tokenfilter.
    *
    * @return - The Tokenfilter
    *
    * @see #setTokenFilter(ITokenFilter)
    * @see org.dotplot.tokenizer.IConfiguration#getTokenFilter()
    */
   public ITokenFilter getTokenFilter()
   {
      return this.TokenFilter;
   }

   /**
    * Configures the current Tokenfilter.
    *
    * @param tokenFilter - the current Tokenfilter
    *
    * @see #getTokenFilter()
    * @see org.dotplot.tokenizer.IConfiguration#setTokenFilter(org.dotplot.tokenizer.ITokenFilter)
    */
   public void setTokenFilter(ITokenFilter tokenFilter)
   {
      this.TokenFilter = tokenFilter;
   }

   /**
    * Proves, if the Defaultscanner should be used for unknown file formats.
    *
    * @return useDefaultScanner - the current result
    *
    * @see #setUseDefaultScanner(boolean)
    */
   public boolean isUseDefaultScanner()
   {
      return useDefaultScanner;
   }

   /**
    * Configures, if the Defaultscanner should be used or not for unknown file formats.
    *
    * @param use - the Configuration
    *
    * @see #isUseDefaultScanner()
    */
   public void setUseDefaultScanner(boolean use)
   {
      useDefaultScanner = use;
   }

   /**
    * Configures, if Files which can not be read, should be converted.
    *
    * @param convert - the configuration
    *
    * @see #getConvertFiles()
    */
   public void setConvertFiles(boolean convert)
   {
      this.convertFiles = convert;
   }

   /**
    * Prueft ob dateien die nicht gelesen werden koennen konvertiert werden sollen.
    *
    * @return - das Ergebnis.
    *
    * @see #setConvertFiles(boolean)
    */
   public boolean getConvertFiles()
   {
      return this.convertFiles;
   }

   /**
    * Setzt das Verzeinis in das die konvertierten Dateien gespeichert werden sollen.
    *
    * @param directory - das Verzeichnis.
    *
    * @see #getConvertDirectory()
    */
   public void setConvertDirectory(File directory)
   {
      this.convertedFilesDirectory = directory;
   }

   /**
    * Gibt das Verzeichnis zurueck, in das die konvertieren Dateien gespeichert werden sollen.
    * returns the directory, in which the converted files are kept.
    *
    * @return - the directory
    *
    * @see #setConvertDirectory(java.io.File)
    */
   public File getConvertDirectory()
   {
      return this.convertedFilesDirectory;
   }
}
