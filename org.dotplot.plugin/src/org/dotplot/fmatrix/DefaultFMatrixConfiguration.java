/**
 * 
 */
package org.dotplot.fmatrix;

import java.util.List;
import java.util.Vector;

import org.dotplot.core.IConfiguration;

/**
 * Defaultimplementation of the <code>IFMatrixConfiguration</code> interface.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DefaultFMatrixConfiguration implements IFMatrixConfiguration {

	/**
	 * 
	 */
	private List<String> regularExpressions;

	/**
	 * 
	 */
	private List<WeightingEntry> manualWeightedTokens;

	/**
	 * Creates a new <code>DefaultFMatrixConfiguration</code>.
	 */
	public DefaultFMatrixConfiguration() {
		super();
		this.regularExpressions = new Vector<String>();
		this.manualWeightedTokens = new Vector<WeightingEntry>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#copy()
	 */
	public IConfiguration copy() {
		DefaultFMatrixConfiguration clone = new DefaultFMatrixConfiguration();
		clone.getManualWeightedTokens().addAll(this.getManualWeightedTokens());
		clone.getRegularExpressions().addAll(this.getRegularExpressions());
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.IFMatrixConfiguration#getManualWeightedTokens()
	 */
	public List<WeightingEntry> getManualWeightedTokens() {
		return this.manualWeightedTokens;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.fmatrix.IFMatrixConfiguration#getRegularExpressions()
	 */
	public List<String> getRegularExpressions() {
		return this.regularExpressions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#objectForm(java.lang.String)
	 */
	public IConfiguration objectForm(String serivalizedForm)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#serializedForm()
	 */
	public String serializedForm() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
