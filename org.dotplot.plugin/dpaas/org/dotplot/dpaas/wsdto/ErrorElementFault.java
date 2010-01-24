package org.dotplot.dpaas.wsdto;

import javax.xml.ws.WebFault;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI
 * 2.1.3.1-hudson-749-SNAPSHOT Generated source version: 2.1
 * 
 */
@WebFault(name = "ErrorElement", targetNamespace = "http://dotplot.org/dpaas")
public class ErrorElementFault extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	/**
	 * Java type that goes as soapenv:Fault detail element.
	 * 
	 */
	private String faultInfo;

	public ErrorElementFault(String message) {
		super(message);
		this.faultInfo = message;
	}

	/**
	 * 
	 * @param message
	 * @param faultInfo
	 */
	public ErrorElementFault(String message, String faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	/**
	 * 
	 * @param message
	 * @param faultInfo
	 * @param cause
	 */
	public ErrorElementFault(String message, String faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	/**
	 * 
	 * @return returns fault bean: java.lang.String
	 */
	public String getFaultInfo() {
		return faultInfo;
	}

}
