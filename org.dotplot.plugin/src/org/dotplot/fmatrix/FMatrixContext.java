/**
 * 
 */
package org.dotplot.fmatrix;

import org.dotplot.core.services.IContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FMatrixContext implements IContext {

	private ITypeTableNavigator navigator;
	
	/**
	 * 
	 */
	public FMatrixContext(ITypeTableNavigator navigator) {
		super();
		if(navigator == null) throw new NullPointerException();
		this.navigator = navigator;
	}
	
	/**
	 * 
	 * @return
	 */
	public ITypeTableNavigator getTypeTableNavigator(){
		return this.navigator;
	}

}
