/*
 * Created on 26.05.2004
 */
package org.dotplot.ui.configuration;

import java.util.Iterator;
import java.util.TreeMap;

import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * A container for <code>ConfigurationView</code> objects in form of a <code>TreeMap</code>.
 *
 * @author Christian Gerhardt <case423@gmx.net
 * @see org.dotplot.ui.configuration.views.ConfigurationView
 */
public class ConfigurationViews extends TreeMap
{
   /**
    * Notifies all attached <code>Observers</code> of the <code>ConfigurationViews</code> in the container.
    */
   public void notifyObservers()
   {
      Iterator iter = this.values().iterator();
      while (iter.hasNext())
      {
         ((ConfigurationView) iter.next()).notifyObservers();
      }
   }
}
