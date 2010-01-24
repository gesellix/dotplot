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
	public File getConvertedFilesDirectory();

	public String getConverterID(ISourceType type);

	public boolean getConvertFiles();

	public ISourceType getTargetType();

	public boolean keepConvertedFiles();

	public boolean overwriteConvertedFiles();

	public void setConvertedFilesDirectory(File directory);

	public void setConverter(ISourceType type, String converterID);

	public void setConvertFiles(boolean state);

	public void setKeepConvertedFiles(boolean keepFiles);

	public void setOverwriteConvertedFiles(boolean overwriteFiles);

	public void setTargetType(ISourceType targetType);
}
