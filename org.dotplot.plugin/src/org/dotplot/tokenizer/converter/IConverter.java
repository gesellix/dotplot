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
 * <p>
 * Convertes a format into another.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface IConverter {

	/**
	 * Converts a <code>PlotSource</code> into another <code>SourceType</code>
	 * specified by this <code>Converter</code>.
	 * 
	 * @param source
	 *            - the <code>PlotSource</code> to be converted.
	 * @return the <code>PlotSource</code> containing the converted data.
	 * @throws IOException
	 *             - if an io-problem occures.
	 * @throws NullPointerException
	 *             - if argument is <code>null</code>.
	 */
	public IPlotSource convert(IPlotSource source) throws IOException;

	/**
	 * Converts a <code>PlotSource</code> into another <code>SourceType</code>
	 * specified by this <code>Converter</code>.
	 * 
	 * @param source
	 *            - the <code>PlotSource</code> to be converted.
	 * @param directory
	 *            - The directory in which the converted <code>PlotSource</code>
	 *            should be saved in.
	 * @return the <code>PlotSource</code> containing the converted data.
	 * @throws IOException
	 *             - if an io-problem occures.
	 * @throws NullPointerException
	 *             - if argument is <code>null</code>.
	 */
	public IPlotSource convert(IPlotSource source, File directory)
			throws IOException;

	/**
	 * Returns the sourcetype of the converter.
	 * <p>
	 * The sourcetype indicates which types of data the converter could convert.
	 * </p>
	 * 
	 * @return the sourcetype.
	 */
	public ISourceType getSourceType();

	/**
	 * Returns the targettype of the converter.
	 * <p>
	 * The targettype is the data type of the converted source data.
	 * </p>
	 * 
	 * @return the targettype.
	 */
	public ISourceType getTargetType();

	/**
	 * Sets if an existing converted <code>PlotSource</code> should be
	 * overwritten.
	 * 
	 * @param overwrite
	 *            <code>true</code> if the converted <code>PlotSource</code>
	 *            should be overwritten, <code>false</code> otherwise.
	 */
	public void setOverwrite(boolean overwrite);
}
