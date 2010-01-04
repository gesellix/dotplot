package org.dotplot.dpaas;

import java.util.Iterator;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.UnknownServiceHotSpotException;

public class WebService extends DotplotService {

    private Endpoint enp;
    private WebServicePort wsp;
    private IWebserviceConfiguration config;

    public final static String DPAAS_BINDING_ID = "org.dotplot.dpaas"
	    + ".webservice.binding";

    private static final Logger LOGGER = Logger.getLogger(WebService.class
	    .getName());

    public WebService(String id) {
	super(id);
	LOGGER.debug("Webservice is being spawned.Please be patient :)");
	this.addHotSpot(new PluginHotSpot(DPAAS_BINDING_ID, Object.class));
	/**
	 * Work A Round 2 be able to use ipv4 on sunx46000
	 */
	System.setProperty("java.net.preferIPv4Stack", "true");
    }

    @Override
    protected IContext createResultContext() {
	return null;
    }

    @Override
    public ITask createTask() {
	return null;
    }

    @Override
    public Class<?> getResultContextClass() {
	return null;
    }

    @Override
    public void registerDefaultConfiguration(IConfigurationRegistry registry) {
	if (this.enp != null) {
	    return; // dirty dirty dirty
	}
	LOGGER.debug("Webservice getting config...");
	String endp = "no URL set!";

	try {
	    Iterator<Extention> extlist = this.getHotSpot(DPAAS_BINDING_ID)
		    .getActiveExtentions().iterator();
	    while (extlist.hasNext()) {
		Extention exti = extlist.next();
		endp = exti.getParameter("endpoint");
	    }
	} catch (UnknownServiceHotSpotException e) {
	    e.printStackTrace();
	    LOGGER.error("Error setting up WS :S . Incorrect HotspotSetup");
	    return;
	}

	LOGGER.debug("Webservice setting up with Configuration: ");
	LOGGER.debug(endp);

	try {
	    wsp = new WebServicePort();
	    // if your Endpoint is incorrectly set the plugin will break here!!
	    // example: enp = Endpoint.publish("http://localhost:8080/dpaas",
	    // wsp);
	    enp = Endpoint.publish(endp, wsp);
	    enp.getBinding();
	} catch (Exception e) {
	    e.printStackTrace();
	    LOGGER
		    .error("Webservice Endpoint not setup. Got problems with hostname?");
	    return;
	}
	LOGGER.debug("Webservice up and running :)");
    }

    @Override
    public boolean workingContextIsCompatible(Class<?> contextClass) {
	return false;
    }

}
