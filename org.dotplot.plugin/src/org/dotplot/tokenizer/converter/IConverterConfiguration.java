/**
 * 
 */
package org.dotplot.tokenizer.converter;

import java.io.File;

import org.dotplot.core.IConfiguration;
import org.dotplot.core.ISourceType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface IConverterConfiguration extends IConfiguration {
	public boolean overwriteConvertedFiles();
	public void setOverwriteConvertedFiles(boolean overwriteFiles);
	
	public boolean keepConvertedFiles();
	public void setKeepConvertedFiles(boolean keepFiles);
	
	public File getConvertedFilesDirectory();
	public void setConvertedFilesDirectory(File directory);
	
	public ISourceType getTargetType();
	public void setTargetType(ISourceType targetType);
	
	public String getConverterID(ISourceType type);
	public void setConverter(ISourceType type, String converterID);
	
	public void setConvertFiles(boolean state);
	public boolean getConvertFiles();
}
