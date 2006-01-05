/*
 * Created on 22.06.2004
 */
package org.dotplot.fmatrix;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dotplot.tokenizer.IFileList;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.ui.configuration.GlobalConfiguration;
import org.dotplot.ui.monitor.DotPlotProgressMonitor;
import org.dotplot.ui.monitor.MonitorablePlotUnit;

/**
 * controller class for the x- and y-Dimension of
 * the fmatrix and the bit matrix.
 * This class represents the "interface"(entry class)
 * to the fmatrix.  All other classes in this package
 * are controlled by this class.
 *
 * @author Constantin von Zitzewitz, Thorsten Ruehl
 * @version 0.4
 */
public class FMatrixManager implements MonitorablePlotUnit
{
   private TypeTable typeTable;
   private ITokenStream tokenStream;
   private TokenInformation tokenInformation;

   private int progress; // monitor progress
   private String monitorMessage; // monitor message

   private IFileList fileList;
   private Vector manualWeightedTokens;
   private Vector storedRegularExpressions;

   private final Logger logger = Logger.getLogger(FMatrixManager.class.getName());

   /**
    * Default Constructor taking a ITokenStream to construct
    * the fmatrix.
    *
    * @param tokenStream - the token stream(from tokenizer)
    */
   public FMatrixManager(ITokenStream tokenStream)
   {
      this.tokenStream = tokenStream;
      typeTable = new TypeTable(new TokenTable());
      tokenInformation = new TokenInformation();
      progress = 0;
      monitorMessage = null;
      fileList = null;

      GlobalConfiguration globalConfig = GlobalConfiguration.getInstance();
      manualWeightedTokens = (Vector) globalConfig.get(GlobalConfiguration.KEY_FMATRIX_TOKEN_WEIGHTS);
      storedRegularExpressions = (Vector) globalConfig.get(GlobalConfiguration.KEY_FMATRIX_REGULAR_EXPRESSIONS);
   }

   /**
    * adds the tokens from the stream to the fmatrix.
    *
    * @return boolean - success on adding
    */
   public boolean addTokens()
   {
      if (tokenStream == null)
      {
         return false;
      }

      Token token;
      LineInformation lineInformation = new LineInformation();
      File file = null;

      int lineIndex = 0;
      int firstTokenInLine = 0;
      List tokensInLine = new ArrayList();
      int fileCount = 0; // counts the files

      // monitor message
      monitorMessage = "processing Tokens...";

      try
      {
         while (true)
         {
            token = tokenStream.getNextToken();
            if (token == null)
            {
               logger.error("token == null");
               return false;
            }

            int tokenType = token.getType();
            if (tokenType == Token.TYPE_EOS)
            {
               break;
            }

            // ------------ store fileinformation
            if (file != token.getFile())
            {
               file = token.getFile();
               tokenInformation.addFileInformation(new FileInformation(firstTokenInLine, file));
            }

            // ignore line setting for EOF
            if (tokenType != Token.TYPE_EOF)
            {
               lineIndex = token.getLine();
            }

            switch (tokenType)
            {
               case Token.TYPE_EOL:
                  lineInformation.addLineInformation(firstTokenInLine, firstTokenInLine + tokensInLine.size(),
                        token.getLine(), tokensInLine);
                  firstTokenInLine += tokensInLine.size();
                  tokensInLine = new ArrayList();
                  break;
               case Token.TYPE_EOF:
                  // if EOF without EOL flush tokens before saving LineInformation
                  if (tokensInLine.size() > 0)
                  {
                     lineInformation.addLineInformation(firstTokenInLine, firstTokenInLine + tokensInLine.size(),
                           lineIndex, tokensInLine);
                     firstTokenInLine += tokensInLine.size();
                     tokensInLine = new ArrayList();
                  }
                  tokenInformation.addLineInformationContainer(lineInformation);
                  lineInformation = new LineInformation();

                  // count files for progress monitor
                  fileCount++;
                  progress = (fileCount / fileList.count() * 100);
                  DotPlotProgressMonitor.getInstance().update();
                  break;
               default:
                  typeTable.addType(token.getValue());
                  tokensInLine.add(token);
                  break;
            }
         }

         // mark end of fileInformationEntries and register the FileInformation to the typetable
//         tokenInformation.addFileInformation(new FileInformation(tokenIndex, null));
         typeTable.registerTokenInformation(tokenInformation);
      }
      catch (TokenizerException e)
      {
         logger.error("Error adding Tokens", e);
         return false;
      }

      typeTable.updateCalculatedWeights();
      restoreConfiguration();

      return true;
   }

   /**
    * Loading the userdefined token-weightings from the GlobalConfiguration
    */
   private void restoreConfiguration()
   {
      WeightingEntry weightingEntry;
      TokenType tokenType;
      Iterator iter;

      iter = manualWeightedTokens.iterator();
      while (iter.hasNext())
      {
         weightingEntry = (WeightingEntry) iter.next();

         tokenType = typeTable.getTokenType(weightingEntry.getTokenIndex());
         tokenType.setWeight(weightingEntry.getWeight());
      }

      iter = storedRegularExpressions.iterator();
      while (iter.hasNext())
      {
         typeTable.addRegularExpressionType((String) iter.next(), 0.75);
      }
   }

   /**
    * returns an ITypeTableNavigator object.
    *
    * @return ITypeTableNavigator - the object
    *
    * @see <code>ITypeTableNavigator</code>
    */
   public ITypeTableNavigator getTypeTableNavigator()
   {
      return typeTable.getNavigator();
   }

   /**
    * the function allows to specify a group of types via an regular expression wich should
    * have a special weight thus a group of tokentypes could be highlighted.
    *
    * @param regExp    the regular expression describing the wanted types
    * @param weighting the weight for the new type
    *
    * @return the index of the new regular-expression-type
    */
   public int addRegularExpressionType(String regExp, double weighting)
   {
      return typeTable.addRegularExpressionType(regExp, weighting);
   }

   /**
    * @see org.dotplot.ui.monitor.MonitorablePlotUnit#nameOfUnit()
    */
   public String nameOfUnit()
   {
      return "FMatrix Module";
   }

   /**
    * @see org.dotplot.ui.monitor.MonitorablePlotUnit#getProgress()
    */
   public int getProgress()
   {
      return progress;
   }

   /**
    * @see org.dotplot.ui.monitor.MonitorablePlotUnit#getMonitorMessage()
    */
   public String getMonitorMessage()
   {
      return monitorMessage;
   }

   /**
    * @see org.dotplot.ui.monitor.MonitorablePlotUnit#cancel()
    */
   public void cancel()
   {
   }

   /**
    * Specifies the file list value.
    *
    * @param fileList an IFileList object specifying the file list value
    */
   public void setFileList(IFileList fileList)
   {
      this.fileList = fileList;
   }

   /**
    * Returns an Object that hold several functions to manipulate the typetable.
    *
    * @return an ITypeTableManipulator object representing the type table manipulator value
    */
   public ITypeTableManipulator getTypeTableManipulator()
   {
      return new TypeTableManipulator(typeTable);
   }

   public void printTypeTable()
   {
      typeTable.print();
   }
}
