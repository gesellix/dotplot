/*
 * Created on 26.05.2004
 */
package org.dotplot.ui.configuration.controller;

import java.util.Observable;
import java.util.Observer;

import org.dotplot.IDotplotCreator;
import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * Controller zu einem <code>ConfigurationView</code>.
 * Dies ist der Controller aus der Model-View-Controller Architektur.
 * Gehandhabt wird er als <code>Observer</code>, der einen oder mehreren
 * <code>ConfigurationViews</code> zugeteilt ist. Der Controller soll
 * auf Aenderungen im View reagieren und dem entsprechend aenderungen im
 * Model (dem zugehoerigen <code>DotplotCreator</code>) ausloesen.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @see org.dotplot.DotplotCreator
 * @see org.dotplot.ui.configuration.views.ConfigurationView
 */
public abstract class ViewController implements Observer
{
   /**
    * Der <code>DotplotCreator</code> der zu dem dieser ViewController gehoert.
    */
   private IDotplotCreator dotplotCreator;

   /**
    * Der <code>ConfigurationView</code> der zu diesem <code>ViewController</code> gehoert.
    */
   private ConfigurationView configurationView;

   /**
    * Erzeugt einen neuen <code>ViewController</code> mit einem <code>DotplotCreator</code> und einem <code>ConfigurationView</code>.
    *
    * @param dotplotCreator - der </code>DotplotCreator</code>
    * @param cv             - der <code>ConfigurationView</code>
    */
   public ViewController(IDotplotCreator dotplotCreator, ConfigurationView cv)
   {
      this.dotplotCreator = dotplotCreator;
      this.configurationView = cv;
   }

   /**
    * Gibt den eingestellten DotplotCreator zurueck.
    *
    * @return dotplotCreator - der DotplotCreator
    */
   public IDotplotCreator getDotplotter()
   {
      return dotplotCreator;
   }

   /**
    * Liefert den zum Controller gehoerenden <code>ConfigurationView</code>.
    *
    * @return - der <code>ConfigurationView</code>.
    */
   public ConfigurationView getConfigurationView()
   {
      return configurationView;
   }

   /**
    * Updates the Observer.
    *
    * @param object - the observable-object.
    * @param arg    - additional arguments
    *
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   public abstract void update(Observable object, Object arg);
}
