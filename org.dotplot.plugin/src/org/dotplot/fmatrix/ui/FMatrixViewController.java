/*
 * Created on 01.07.2004
 */
package org.dotplot.fmatrix.ui;

import java.util.Observable;

import org.dotplot.fmatrix.FMatrixService;
import org.dotplot.fmatrix.IFMatrixConfiguration;
import org.dotplot.ui.ConfigurationView;
import org.dotplot.ui.ViewController;
import org.dotplot.util.UnknownIDException;

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

	/*
	 *  (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		FMatrixConfigurationView view = (FMatrixConfigurationView) this
				.getConfigurationView();
		try {
			IFMatrixConfiguration config = (IFMatrixConfiguration) view
					.getRegistry().get(FMatrixService.ID_CONFIGURATION_FMATRIX);
			//config.getManualWeightedTokens().add()
			
			System.out.println("Fmatrixcontroller updated");
		}
		catch (UnknownIDException e) {
			// TODO Auto-generated catch block
		}

	}
}
