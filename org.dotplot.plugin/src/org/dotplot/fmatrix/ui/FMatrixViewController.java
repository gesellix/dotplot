/*
 * Created on 01.07.2004
 */
package org.dotplot.fmatrix.ui;

import java.util.Observable;

import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;

/**
 * Controller for the FMatrix view.
 * 
 * @author hg12170
 */
public class FMatrixViewController extends ViewController {
	/**
	 * creates the view controller.
	 * 
	 * @param dotplotCreator
	 *            the dotplot plugin "controller"
	 * @param cv
	 *            the corresponding configuration view
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public FMatrixViewController(ConfigurationView cv) {
		super(cv);
	}

	@Override
	public void update(Observable o, Object arg) {
		// System.out.println("FMatrixController was used");
	}
}
