/**
 * 
 */
package org.dotplot.core;

import org.dotplot.core.plugins.IPluginHotSpot;
import org.dotplot.core.plugins.PlugableService;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.UnknownServiceHotSpotException;

/**
 * This class serves as superclass of all <code>Services</code> of the dotplot
 * system.
 * <p>
 * Subclasses must implement
 * {@link #registerDefaultConfiguration(IConfigurationRegistry)} to register
 * their configuration information to the <code>ConfigurationRegistry</code>.
 * </p>
 * <p>
 * There are two default <code>HotSpot</code>s assigned to the
 * <code>DotplotService</code>. These implement the Interceptor Design Pattern
 * to mke the architechture expendable. Through them the
 * <code>DotplotService</code> could be extended with new functionality by
 * modifying the their execution sequence. Every <code>Extention</code> made to
 * the Befor-<code>Hotspot</code> is executed <u>before</u> invoking the
 * <code>TaskProcessor</code>, and the <code>Extention</code>s made to the
 * After-<code>Hotspot</code> are invoked <u>after</u> the
 * <code>TaskProcessor</code>. Using this funktionality it is possible to
 * manipulate the working and resultcontext of the <code>DotplotService</code>.
 * </p>
 * <hr>
 * <h2>Hotspots</h2>
 * <p>
 * <b>Hotspot:</b> Befor<br>
 * <b>Extention:</b> {@link org.dotplot.core.IInterceptor}<br>
 * <b>Parameter:</b> none<br>
 * <br>
 * <b>Info:</b><br>
 * The Befor-<code>Interceptor</code> is executed <u>befor</u> invoking the
 * <code>TaskProcessor</code>.
 * </p>
 * <br>
 * <p>
 * <b>Hotspot:</b> After<br>
 * <b>Extention:</b> {@link org.dotplot.core.IInterceptor}<br>
 * <b>Parameter:</b> none<br>
 * <br>
 * <b>Info:</b><br>
 * The After-<code>Interceptor</code> is executed <u>after</u> invoking the
 * <code>TaskProcessor</code>.
 * </p>
 * <br>
 * <hr>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @see IInterceptor
 */
public abstract class DotplotService extends PlugableService<DotplotContext> {

    /**
     * Id suffix for the Before-<code>Interceptor</code>.
     */
    public static final String ID_INTERCEPTOR_BEFORE_SUFFIX = ".Before";

    /**
     * Id suffix for the After-<code>Interceptor</code>.
     */
    public static final String ID_INTERCEPTOR_AFTER_SUFFIX = ".After";

    /**
     * This service's result context.
     */
    private IContext resultContext = NullContext.context;

    /**
     * Creates a new <code>DotplotService</code>.
     * 
     * @param id
     *            The if of the <code>DotplotService</code>.
     */
    public DotplotService(String id) {
	super(id);
	this.addHotSpot(new PluginHotSpot(id + ID_INTERCEPTOR_BEFORE_SUFFIX,
		IInterceptor.class));
	this.addHotSpot(new PluginHotSpot(id + ID_INTERCEPTOR_AFTER_SUFFIX,
		IInterceptor.class));
    }

    /**
     * Creates the services's result context.
     * <p>
     * Subclasses should implement this method to create their result context.
     * This method is invoked right after invoking the
     * <code>TaskProcessor</code>.
     * </p>
     * 
     * @return The result context.
     */
    protected abstract IContext createResultContext();

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.IService#getResultContext()
     */
    @Override
    public final IContext getResultContext() {
	return this.resultContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.core.services.IService#init()
     */
    @Override
    public void init() {
	if (this.frameworkContext != null) {
	    this.registerDefaultConfiguration(this.frameworkContext
		    .getConfigurationRegistry());
	}
    }

    /**
     * Registers the <code>DotplotService</code>'s default configuration to the
     * <code>Configurationregistry</code>.
     * 
     * @param registry
     *            The <code>Configurationregistry</code>.
     */
    public abstract void registerDefaultConfiguration(
	    IConfigurationRegistry registry);

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	IPluginHotSpot hotSpot;
	try {
	    hotSpot = this.getHotSpot(this.getID()
		    + ID_INTERCEPTOR_BEFORE_SUFFIX);
	    for (Object extention : hotSpot.getObjectsFromActivatedExtentions()) {
		((IInterceptor) extention).execute(hotSpot, this, this
			.getWorkingContext(), this.frameworkContext);
	    }
	} catch (UnknownServiceHotSpotException e) {
	    /* should not be possible */
	}

	super.run();
	this.resultContext = this.createResultContext();

	try {
	    hotSpot = this.getHotSpot(this.getID()
		    + ID_INTERCEPTOR_AFTER_SUFFIX);
	    for (Object extention : hotSpot.getObjectsFromActivatedExtentions()) {
		((IInterceptor) extention).execute(hotSpot, this, this
			.getResultContext(), this.frameworkContext);
	    }
	} catch (UnknownServiceHotSpotException e) {
	    /* should not be possible */
	}
    }
}
