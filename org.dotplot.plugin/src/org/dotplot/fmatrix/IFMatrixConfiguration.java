/**
 * 
 */
package org.dotplot.fmatrix;

import java.util.List;

import org.dotplot.core.IConfiguration;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface IFMatrixConfiguration extends IConfiguration {
	public List<String> getRegularExpressions();
	public List<WeightingEntry> getManualWeightedTokens();
}
