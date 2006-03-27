/*
 * Created on 23.05.2004
 */
package org.dotplot.tokenizer.converter;

import java.io.File;
import java.io.IOException;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;

/**
 * Interface for a converter.
 * <p />
 * Convertes a format into  another.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface IConverter
{
	
   public IPlotSource convert(IPlotSource source) throws IOException;

   public IPlotSource convert(IPlotSource source, File directory) throws IOException;
      
   public ISourceType getSourceTye();
   
   public ISourceType getTargetTye();
   
   public void setOverwrite(boolean overwrite);
}
