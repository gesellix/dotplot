package org.dotplot.tokenizer;

import org.dotplot.tokenizer.exceptions.BadConfigurationException;
import org.dotplot.tokenizer.exceptions.NoFileListException;
import org.dotplot.tokenizer.exceptions.NoScannerException;
import org.dotplot.tokenizer.scanner.IScanner;

/**
 * Definiton of a Tokenizer.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class Tokenizer
{
   /**
    * The scanner which is used to scan the Token.
    */
   private IScanner scanner;

   /**
    * The current configuration
    */
   private IConfiguration configuration;

   /**
    * the file list which should be scanned.
    */
   private IFileList fileList;

   /**
    * From the configuration created token stream
    */
   private ITokenStream tokenStream;

   /**
    * the tokenfilter which should be used
    */
   private ITokenFilter tokenFilter;

   /**
    * Creates a Tokenizer.
    */
   public Tokenizer()
   {
      this(new DefaultConfiguration());
   }

   /**
    * Crates a Tokenizer with a configuration.
    *
    * @param configuration - the configuration
    */
   public Tokenizer(IConfiguration configuration)
   {
      this.configuration = configuration;
      useConfiguration();
   }

   /**
    * takes the configuration of the current configuration.
    */
   protected void useConfiguration()
   {
      scanner = configuration.getScanner();
      fileList = configuration.getFileList();
      tokenFilter = configuration.getTokenFilter();
   }

   /**
    * Returns the actual configuration.
    *
    * @return configuration -- the configuration
    *
    * @see #setConfiguration(IConfiguration)
    */
   public IConfiguration getConfiguration()
   {
      return configuration;
   }

   /**
    * Sets the current configuration.
    *
    * @param configuration -- the confiuration
    *
    * @see #getConfiguration()
    */
   public void setConfiguration(IConfiguration configuration)
   {
      this.configuration = configuration;
   }

   /**
    * Setup and creates the tokenstream of this tokenizer.
    *
    * @throws NoScannerException  - no Scanner existing
    * @throws NoFileListException - no FileList existing
    */
   protected void setup() throws TokenizerException
   {
      this.useConfiguration();

      if (scanner == null && !configuration.isUseDefaultScanner())
      {
         throw new NoScannerException();
      }

      if (fileList == null)
      {
         throw new NoFileListException();
      }

      if (configuration.getConvertFiles())
      {
         fileList.convertFiles(configuration.getConvertDirectory());
      }

      if (tokenFilter == null)
      {
         tokenStream = new FileTokenizer(fileList, scanner);
      }
      else
      {
         tokenStream = new FileTokenizer(fileList, scanner, tokenFilter);
      }
   }

   /**
    * Returns the token stream of a file
    * <p />
    * The token stream needs a configuration for it's information, which will be set
    * with the setConfiguration()-command.
    * Because the configuration contains a filelist, the Token of a file will be put out only
    * in one stream.
    * It will be devided only with a special EOFToken to indicate the end of a file.
    *
    * @return der Tokenstrom.
    *
    * @throws TokenizerException - explains what went wrong
    * @see EOFToken
    */
   public ITokenStream getTokenStream() throws TokenizerException
   {
      if (configuration == null)
      {
         throw new BadConfigurationException();
      }

      setup();

      return tokenStream;
   }
}
