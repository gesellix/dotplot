//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.29 at 11:18:10 PM CEST 
//


package org.dotplot.dpaas.wsdto;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.dotplot.dpaas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dotplot.dpaas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WSFilelist }
     * 
     */
    public WSFilelist createFilelist() {
        return new WSFilelist();
    }

    /**
     * Create an instance of {@link WSDotplotjobresponse }
     * 
     */
    public WSDotplotjobresponse createDotplotjobresponse() {
        return new WSDotplotjobresponse();
    }

    /**
     * Create an instance of {@link WSFilters }
     * 
     */
    public WSFilters createFilters() {
        return new WSFilters();
    }

    /**
     * Create an instance of {@link WSConfiguration }
     * 
     */
    public WSConfiguration createConfiguration() {
        return new WSConfiguration();
    }

    /**
     * Create an instance of {@link WSDotplotjob }
     * 
     */
    public WSDotplotjob createDotplotjob() {
        return new WSDotplotjob();
    }

    /**
     * Create an instance of {@link WSFile }
     * 
     */
    public WSFile createFile() {
        return new WSFile();
    }

    /**
     * Create an instance of {@link WSFilter }
     * 
     */
    public WSFilter createFilter() {
        return new WSFilter();
    }

}