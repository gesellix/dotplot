/**
 * 
 */
package org.dotplot.core;

import org.dotplot.core.plugins.IPluginHotSpot;
import org.dotplot.core.services.IContext;

/**
 * An <code>Interceptor</code> intercepts <code>Service</code>-events.
 * <p>
 * The intercepted event is defined by the <code>Hotspot</code> who the
 * <code>Interceptor</code> is assigned to. The <code>Service</code> of the
 * <code>Hotspot</code> decides when the event is triggered. In that case is's
 * the <code>Serivce</code>'s task to execute the assigned
 * <code>Interceptors</code>.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IInterceptor {

    /**
     * Executes the <code>Interceptor</code>.
     * 
     * @param hotSpot
     *            The <code>Hotspot</code> which defines the intercepted event.
     * @param interceptedService
     *            The intercepted <code>Serivce</code>.
     * @param workingContext
     *            The intercepted <code>Serivce</code>'s workingcontext.
     * @param dotplotContext
     *            The <code>DotplotContext</code>.
     */
    public void execute(IPluginHotSpot hotSpot,
	    DotplotService interceptedService, IContext workingContext,
	    DotplotContext dotplotContext);
}
