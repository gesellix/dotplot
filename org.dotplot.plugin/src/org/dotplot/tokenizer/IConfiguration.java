package org.dotplot.tokenizer;

import java.io.File;

import org.dotplot.tokenizer.scanner.IScanner;

/**
 * Interface for the configuration of the Tokenizer.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface IConfiguration
{
   /**
    * returns the current scanner.
    *
    * @return - the current scanner
    *
    * @see #setScanner(org.dotplot.tokenizer.scanner.IScanner)
    */
   public IScanner getScanner();

   /**
    * sets the current scanner.
    *
    * @param scanner - the new scanner
    *
    * @see #getScanner()
    */
   public void setScanner(IScanner scanner);

   /**
    * Sets a new FileList.
    *
    * @param fileList - the new FileList
    *
    * @see #getFileList()
    */
   public void setFileList(IFileList fileList);

   /**
    * Returns the current FileList.
    *
    * @return - the current FileList
    *
    * @see #setFileList(IFileList)
    */
   public IFileList getFileList();

   /**
    * Returns the current Tokenfilter.
    *
    * @return - The Tokenfilter
    *
    * @see #setTokenFilter(ITokenFilter)
    */
   public ITokenFilter getTokenFilter();

   /**
    * Sets the TokenFilter, which should be used.
    *
    * @param tokenFilter - the Tokenfilter, which should be used
    *
    * @see #getTokenFilter()
    */
   public void setTokenFilter(ITokenFilter tokenFilter);

   /**
    * Shows, if the standard scanner for the file type should be used.
    *
    * @return true, if the default scanner should be used
    *
    * @see #setUseDefaultScanner(boolean)
    */
   public boolean isUseDefaultScanner();

   /**
    * Configuration if the DefaultScannr for unknown file formats should be used or not.
    *
    * @param use true - use standard Scanner,  use false - don't use standard scanner
    *
    * @see #isUseDefaultScanner()
    */
   public void setUseDefaultScanner(boolean use);

   /**
    * Configuration if readable file formats should be converted.
    * Only files of which the format is known but are unreadable can be
    * converted with a known Converter.
    *
    * @param convert - the configuration
    *
    * @see #getConvertFiles()
    */
   public void setConvertFiles(boolean convert);

   /**
    * Proves if unreadable file formats should be converted.
    *
    * @return - the output
    *
    * @see #setConvertFiles(boolean)
    */
   public boolean getConvertFiles();

   /**
    * Creates a new directory, in which converted files should be stored.
    *
    * @param directory - the directory
    *
    * @see #getConvertDirectory()
    */
   public void setConvertDirectory(File directory);

   /**
    * Returns the directory, in which converted files should be stored.
    *
    * @return - the directory
    *
    * @see #setConvertDirectory(java.io.File)
    */
   public File getConvertDirectory();
}
