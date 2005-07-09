/*
 * Created on 27.05.2004
 */
package org.dotplot.ui.configuration.controller;

import java.util.Observable;

import org.dotplot.DotplotCreator;
import org.dotplot.tokenizer.IConfiguration;
import org.dotplot.tokenizer.ITokenFilter;
import org.dotplot.tokenizer.tokenfilter.GeneralTokenFilter;
import org.dotplot.tokenizer.tokenfilter.TokenFilterContainer;
import org.dotplot.ui.configuration.views.ConfigurationView;
import org.dotplot.ui.configuration.views.SelectTokenScannerView;
import org.dotplot.ui.configuration.views.SelectTokenTypesView;

/**
 * Controller zu einem <code>SelectTokenTypesView</code>.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 29.6.04
 */
public class SelectTokenTypesController extends ViewController
{
   /**
    * Erzeugt einen <code>SelectTokenTypesController</code> mit ein bestimmen <code>DotplotCreator</code> und einem <code>ConfigurationView</code>.
    *
    * @param dotplotCreator - der <code>DotplotCreator</code>
    * @param view           - der <code>ConfigurationView</code>
    */
   public SelectTokenTypesController(DotplotCreator dotplotCreator, ConfigurationView view)
   {
      super(dotplotCreator, view);
   }

   /**
    * Update-Methode des Controllers.
    * Kümmert sich um die Auswahl der Tokentypen abhängig vom eingestellten Scanner
    * und die Änderungen an der Tokenizerkonfiguration des eingestellten Dotplotters
    *
    * @param o   - das observierte Objekt
    * @param arg - zusätzliche Argumente
    *
    * @see java.util.Observable
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   public void update(Observable o, Object arg)
   {
      IConfiguration config = this.getDotplotter().getTokenizerConfiguration();
      if (o instanceof SelectTokenTypesView)
      {
         SelectTokenTypesView view = (SelectTokenTypesView) this.getConfigurationView();
         TokenFilterContainer container = new TokenFilterContainer();
         container.addTokenFilter(view.getGeneralTokenFilter());
         container.addTokenFilter(view.getKeyWordFilter());
         container.addTokenFilter(view.getLineFilter());
         container.addTokenFilter(view.getSentenceFilter());
         this.getDotplotter().getTokenizerConfiguration().setTokenFilter(container);
      }
      else if (o instanceof SelectTokenScannerView)
      {
         if (config.getScanner() != null
               && (config.getScanner().getTokenTypes()
               != ((SelectTokenTypesView) this.getConfigurationView()).getTokentypes()))
         {
            config.setTokenFilter(this.removeGeneralTokenFilter(config.getTokenFilter()));
         }
         this.getConfigurationView().refresh();
      }
      this.getDotplotter().setDirty();
   }

   /**
    * Entfernt aus einem <code>TokenFlterContainer</code> den <code>GeneralTokenFilter</code>.
    * Wird ein andere Filter als Argument übergeben, wird dieser zurückgegeben.
    *
    * @param filter - der Tokenfilter
    *
    * @return der Filter der rein ging, oder NULL falls es ein GeneralTokenFilter war.
    */
   private ITokenFilter removeGeneralTokenFilter(ITokenFilter filter)
   {
      if (filter instanceof GeneralTokenFilter)
      {
         return null;
      }
      else if (filter instanceof TokenFilterContainer)
      {
         TokenFilterContainer container = new TokenFilterContainer();
         ITokenFilter[] filters = container.getTokenFilters();
         for (int i = 0; i < filters.length; i++)
         {
            container.addTokenFilter(this.removeGeneralTokenFilter(filters[i]));
         }
         filter = container;
      }
      return filter;
   }
}
