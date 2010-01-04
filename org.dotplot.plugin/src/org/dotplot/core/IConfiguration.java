/**
 * 
 */
package org.dotplot.core;

/**
 * This interface serves as supertype of all configurations used in the dotplot
 * system.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IConfiguration {

    /**
     * Returns a copy of the <code>Configuration</code>.
     * <p>
     * The copy must be independent to the original, so that modifying the copy
     * does not affect the original.
     * </p>
     * 
     * @return A copy of the <code>Configuration</code>.
     */
    public IConfiguration copy();

    /**
     * Creates an <code>Configuration</code> object of a serialized form.
     * <p>
     * This statement must always be true:<br>
     * <code>
	 * configuration.equals(configuration.objectForm(configuration.serializedForm))) == true
	 * </code>
     * </p>
     * 
     * @param serivalizedForm
     *            The serialized form of a <code>Configuration</code>
     * @return The <code>Configuration</code> object of the serialized form.
     * @throws UnsupportedOperationException
     *             if the operation is not supported.
     * @see #serializedForm()
     */
    public IConfiguration objectForm(String serivalizedForm)
	    throws UnsupportedOperationException;

    /**
     * Returns the serialized form of the <code>Configuration</code>.
     * <p>
     * This statement must always be true:<br>
     * <code>
	 * configuration.equals(configuration.objectForm(configuration.serializedForm))) == true
	 * </code>
     * </p>
     * 
     * @return The configuration in a serialized form.
     * @throws UnsupportedOperationException
     *             if the operation is not supported.
     * @see #objectForm(String)
     */
    public String serializedForm() throws UnsupportedOperationException;
}
