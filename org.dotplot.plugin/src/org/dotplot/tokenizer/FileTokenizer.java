/*
 * Created on 29.04.2004
 */
package org.dotplot.tokenizer;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.dotplot.tokenizer.exceptions.NoFileListException;
import org.dotplot.tokenizer.exceptions.NoScannerException;
import org.dotplot.tokenizer.scanner.IScanner;
import org.dotplot.tokenizer.tokenfilter.TokenFilterContainer;
import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Compiles a tokenstream from the entire of a file.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class FileTokenizer implements ITokenStream
{
   /**
    * Logger of FileTokenizers.
    */
   private static Logger logger = Logger.getLogger(FileTokenizer.class.getName());

   /**
    * If the standard scanner should be used
    */
   private boolean useDefaultScanners;

   /**
    * The file, which is currently compiled
    */
   private File currentFile;

   /**
    * An Accounting of the file list
    */
   private Enumeration fileListEnum;

   /**
    * the scanner, which should be used
    */
   private IScanner scanner;

   /**
    * the tokenfilter, which should be used
    */
   private ITokenFilter tokenFilter;

   /**
    * Creates a Filetokenizer.
    *
    * @param fileList file list to be used
    * @param scanner  scanner to be used
    *
    * @throws NoFileListException if no file or filelist was found
    */
   public FileTokenizer(IFileList fileList, IScanner scanner) throws NoFileListException
   {
      this(fileList, scanner, new TokenFilterContainer(scanner));
   }

   /**
    * Creates a Filetokenizer.
    *
    * @param fileList    file list to be used
    * @param scanner     scanner to be used
    * @param tokenFilter token filter to be used
    */
   public FileTokenizer(IFileList fileList, IScanner scanner, ITokenFilter tokenFilter)
   {
      fileListEnum = fileList.getEnumeration();
      currentFile = (File) fileListEnum.nextElement();

      try
      {
         if (scanner == null)
         {
            useDefaultScanners = true;
            chooseScanner();
         }
         else
         {
            useDefaultScanners = false;
            this.scanner = scanner;
         }

         this.tokenFilter = tokenFilter;
         this.scanner.setFile(currentFile);
      }
      catch (NoSuchElementException e)
      {
         throw new NoFileListException();
      }
      catch (Exception e)
      {
         logger.error("Error creating FileTokenizer", e);
      }
   }

   /**
    * returns the next token, after it has run through the Tokenfilter.
    *
    * @return - the next Token
    *
    * @throws TokenizerException if an error occured
    */
   public Token getNextToken() throws TokenizerException
   {
      Token token = tokenFilter.getNextToken(scanner);

      final boolean endOfTokenStream = token.getType() == Token.TYPE_EOS;

      if (endOfTokenStream && currentFile != null)
      {
         token = new EOFToken(currentFile);

         if (fileListEnum.hasMoreElements())
         {
            currentFile = (File) fileListEnum.nextElement();

            logger.debug("getNextToken: new Current File - " + currentFile);

            chooseScanner();

            try
            {
               scanner.setFile(currentFile);
            }
            catch (FileNotFoundException e)
            {
               logger.error("File not found", e);
               throw new TokenizerException(e.getMessage());
            }
         }
         else if (currentFile == null)
         {
            return new EOSToken();
         }
         else
         {
            currentFile = null;
         }
      }

      return token;
   }

   /**
    * Chooses the scanner which should be used from a scannerlist in the Defaultconfiguration
    *
    * @throws TokenizerException
    */
   protected void chooseScanner() throws TokenizerException
   {
      if (useDefaultScanners)
      {
         final GlobalConfiguration config = GlobalConfiguration.getInstance();

         TreeMap defScan = (TreeMap) config.get(GlobalConfiguration.KEY_TOKENIZER_TEXT_TYPES);

         String filename = currentFile.getName();
         StringTokenizer st = new StringTokenizer(filename, ".");
         String prefix = "";

         while (st.hasMoreElements())
         {
            prefix = st.nextToken();
         }

         prefix = ".".concat(prefix);
         String scannerName = (String) defScan.get(prefix);
         if (scannerName == null)
         {
            scannerName = (String) config.get(GlobalConfiguration.KEY_TOKENIZER_DEFAULT_SCANNER);
         }

         String scannerClassName = getClass().getPackage().getName() + ".scanner." + scannerName;
         try
         {
            scanner = (IScanner) Class.forName(scannerClassName).newInstance();
         }
         catch (InstantiationException e)
         {
            scanner = null;

            logger.error("no scanner found: " + scannerClassName, e);
            throw new NoScannerException("no scanner found: " + scannerClassName);
         }
         catch (ClassNotFoundException e)
         {
            scanner = null;

            logger.error("no scanner found: " + scannerClassName, e);
            throw new NoScannerException("no scanner found: " + scannerClassName);
         }
         catch (IllegalAccessException e)
         {
            logger.error("Error choosing scanner for current file", e);
         }
      }
   }

   /**
    * returns the current scanner.
    * If <code>null</code> returns the current file hasn't been assigned a Scanner yet.
    *
    * @return the current scanner
    */
   public IScanner getActualScanner()
   {
      return scanner;
   }
}
