/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import org.dotplot.tokenizer.DefaultFileList;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.ITokenStream;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.Tokenizer;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.tokenfilter.LineFilter;
import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Test Class for PDF converting and performance measurement
 *
 * @author Christian Gerhardt Jan Schillings Sebastian Lorberg
 * @version 1.0
 */
public class Test
{
   public static void main(String args[]) throws IOException
   {

      ConfigurationTestFramework cf = new ConfigurationTestFramework();

      SelectionListener plot = new SelectionAdapter()
      {
         public void widgetSelected(SelectionEvent e)
         {
            ((Button) e.widget).getShell().dispose();
         }
      };

      cf.setPlotListener(plot);

      cf.doModal(cf.init());

      Tokenizer tokenizer = new Tokenizer();
      //IConfiguration configuration = new DefaultConfiguration();
      IConfiguration configuration = GlobalConfiguration.getInstance().getDotplotCreator().getTokenizerConfiguration();
      DefaultFileList list = new DefaultFileList();

      double startTime;
      double endTime;
      double currentTimeMinute;
      double currentTimeSecond;
      double MILLISECONDTOMINUTE = 60.0;
      double MILLISECONDTOSECOND = 1000.0;
      startTime = System.currentTimeMillis();

      list.add(new File("pdf/examples/Hausübung1.pdf"));

/*
      FileReader fr = new FileReader(new File("txt/bild/1.txt"));
      int c;
      while (true)
      {
         c = fr.read();
         System.out.print(Character.getNumericValue((char) c));
         System.out.println(" - " + (char) c);
         if (c == -1) break;
      }
*/

      configuration.setFileList(list);
      //configuration.setConvertFiles(true);
      //configuration.setConvertDirectory(new File(""));
      //configuration.setScanner(new TextScanner());
      LineFilter lf = new LineFilter();
      lf.doReturnEmptyLines();
      configuration.setTokenFilter(lf);
      tokenizer.setConfiguration(configuration);

      ITokenStream stream = null;
      Token token;
      FileWriter fw = new FileWriter("src/org/dotplot/tokenizer/tests/TokensOut.txt");
      PrintWriter out = new PrintWriter(fw);
      int anzahlTokens = 0;

      System.out.println(fw.getEncoding());
      try
      {
         stream = tokenizer.getTokenStream();
         System.out.println("Datei wird gescannt");
         while (true)
         {
            token = stream.getNextToken();
            anzahlTokens++;
            out.write(token.getType());

            if (token instanceof EOSToken)
            {
               System.out.println("Datei ist fertig gescannt");
               out.close();
               endTime = System.currentTimeMillis();

               currentTimeSecond = ((endTime - startTime) / MILLISECONDTOSECOND);
               currentTimeMinute = (currentTimeSecond / 60.0);
               System.out.println("Verbrauchte Zeit: " + currentTimeMinute + " Minuten");
               System.out.println("Verbrauchte Zeit: " + currentTimeSecond + " Sekunden");
               System.out.println("Tokens :" + anzahlTokens);
               System.exit(0);
            }
            out.println(token);
         }
      }
      catch (TokenizerException e)
      {
         System.out.println(e.getMessage());
         System.exit(1);
      }
   }
}
