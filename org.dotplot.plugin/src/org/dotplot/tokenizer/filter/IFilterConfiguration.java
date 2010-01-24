/**
 * 
 */
package org.dotplot.tokenizer.filter;

import java.util.List;
import java.util.Map;

import org.dotplot.core.IConfiguration;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public interface IFilterConfiguration extends IConfiguration {
	public void clear();

	public List<String> getFilterList();

	public Map<String, ?> getFilterParameter(String filterName);

	public void setFilterList(List<String> list);

	public void setFilterParameter(String filterName, Map<String, ?> parameter);
}
