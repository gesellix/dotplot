/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;
import java.util.Map;

import org.dotplot.util.ExecutionUnit;

/**
 * Invoking sequence;
 * <ol>
 * <li><code>constructor</code></li>
 * <li><code>setFrameworkContext()</code></li>
 * <li><code>setWorkingContext()</code></li>
 * <li><code>run()</code></li>
 * <li><code>getResultContext()</code></li>
 * </ol>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IService<F extends IFrameworkContext, S extends IServiceHotSpot>
	extends ExecutionUnit {
    /**
     * 
     * @param serviceHotSpotID
     * @param extention
     * @throws UnknownServiceHotSpotException
     * @throws IllegalArgumentException
     *             - if the extention is invalid
     */
    public void addExtention(String hotSpotID, Extention extention)
	    throws UnknownServiceHotSpotException;

    /**
     * 
     * @param serviceHotSpotID
     * @param plugin
     * @param extentionObjection
     * @throws UnknownServiceHotSpotException
     * @throws IllegalArgumentException
     *             - if the extention is invalid
     */
    public void addExtention(String hotSpotID,
	    IServiceExtentionActivator plugin, Object extentionObject)
	    throws UnknownServiceHotSpotException;

    public ITask createTask();

    public Collection<?> getExtentions(String hotSpotID)
	    throws UnknownServiceHotSpotException;

    public S getHotSpot(String hotSpotID) throws UnknownServiceHotSpotException;

    public Map<String, S> getHotSpots();

    public String getID();

    /**
     * Returns a <code>NullContext</code>-object until <code>run()</code> was
     * invoked and the resultcontext is created.
     * <p>
     * This method should never return <code>null</code>.<br>
     * In case a <code>Service</code> has no feasible resultcontext a
     * <code>NullContext</code>-object should be returned.
     * </p>
     * 
     * @return - A <code>NullContext</code>-object befor invoking of
     *         <code>run()</code>, and a resultcontext afterwards.
     */
    public IContext getResultContext();

    /**
     * Should not return <i>null</i>, use <code>NullContext.class</code>
     * instead!
     * 
     * @return
     */
    public Class<?> getResultContextClass();

    /**
     * Initializes the <code>Service</code>.
     * <p>
     * This method is invoked after adding new <code>Extentions</code> to the
     * <code>Service</code>. In normal cases the invokation takes place after
     * <b>all</b> <code>Extentions</code> had bin added.
     * </p>
     */
    public void init();

    public void setErrorHandler(IErrorHandler handler);

    public void setFrameworkContext(F context);

    public void setTaskProcessor(ITaskProcessor processor);

    public void setWorkingContext(IContext context)
	    throws IllegalContextException;

    public boolean workingContextIsCompatible(Class<?> contextClass);
}
