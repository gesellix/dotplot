/**
 * 
 */
package org.dotplot.tokenizer.converter;

import org.dotplot.core.ISourceList;
import org.dotplot.core.services.IContext;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class SourceListContext implements IContext {

	private ISourceList list;
	
	/**
	 * 
	 */
	public SourceListContext(ISourceList list) {
		super();
		if(list == null) throw new NullPointerException();
		this.list = list;
	}

	public ISourceList getSourceList(){
		return this.list;
	}
}
