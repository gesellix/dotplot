
package org.dotplot.dpaas.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "WebServicePort", targetNamespace = "http://dpaas.dotplot.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WebServicePort {


    /**
     * 
     * @param arg0
     * @return
     *     returns org.dotplot.dpaas.Dotplotjobresponse
     * @throws ErrorElementFault
     */
    @WebMethod
    @WebResult(partName = "return")
    public Dotplotjobresponse doDotPlot(
        @WebParam(name = "arg0", partName = "arg0")
        Dotplotjob arg0)
        throws ErrorElementFault
    ;

}
