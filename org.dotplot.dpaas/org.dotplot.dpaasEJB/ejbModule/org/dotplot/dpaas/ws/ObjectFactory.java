
package org.dotplot.dpaas.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


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

    private final static QName _ErrorElement_QNAME = new QName("http://dotplot.org/dpaas", "ErrorElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dotplot.dpaas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Filelist }
     * 
     */
    public Filelist createFilelist() {
        return new Filelist();
    }

    /**
     * Create an instance of {@link Dotplotjob }
     * 
     */
    public Dotplotjob createDotplotjob() {
        return new Dotplotjob();
    }

    /**
     * Create an instance of {@link File }
     * 
     */
    public File createFile() {
        return new File();
    }

    /**
     * Create an instance of {@link Filter }
     * 
     */
    public Filter createFilter() {
        return new Filter();
    }

    /**
     * Create an instance of {@link Filters }
     * 
     */
    public Filters createFilters() {
        return new Filters();
    }

    /**
     * Create an instance of {@link Configuration }
     * 
     */
    public Configuration createConfiguration() {
        return new Configuration();
    }

    /**
     * Create an instance of {@link Dotplotjobresponse }
     * 
     */
    public Dotplotjobresponse createDotplotjobresponse() {
        return new Dotplotjobresponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dotplot.org/dpaas", name = "ErrorElement")
    public JAXBElement<String> createErrorElement(String value) {
        return new JAXBElement<String>(_ErrorElement_QNAME, String.class, null, value);
    }

}
