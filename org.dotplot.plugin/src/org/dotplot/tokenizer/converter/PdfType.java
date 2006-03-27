/**
 * 
 */
package org.dotplot.tokenizer.converter;

import org.dotplot.core.BaseType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class PdfType extends BaseType {

	public static final PdfType type = new PdfType(); 
	
	/**
	 * 
	 */
	public PdfType() {
		super("pdf");
	}

	public PdfType(String name){
		super(name);
	}
}
