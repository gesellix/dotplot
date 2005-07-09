/*
 * Created on 01.07.2004
 */
package org.dotplot.ui.configuration.controller;

import java.util.Observable;

import org.dotplot.DotplotCreator;
import org.dotplot.ui.configuration.views.ConfigurationView;

/**
 * Controller for the FMatrix view.
 *
 * @author hg12170
 */
public class FMatrixViewController extends ViewController
{
   /**
    * creates the view controller.
    *
    * @param dotplotCreator the dotplot plugin "controller"
    * @param cv             the corresponding configuration view
    *
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   public FMatrixViewController(DotplotCreator dotplotCreator, ConfigurationView cv)
   {
      super(dotplotCreator, cv);
   }

   public void update(Observable o, Object arg)
   {
//      System.out.println("FMatrixController was used");
   }
}
