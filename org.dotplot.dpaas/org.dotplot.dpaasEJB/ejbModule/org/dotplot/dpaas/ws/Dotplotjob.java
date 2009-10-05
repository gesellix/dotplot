
package org.dotplot.dpaas.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dotplotjob complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dotplotjob">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Filelist" type="{http://dotplot.org/dpaas}Filelist"/>
 *         &lt;element name="Configuration" type="{http://dotplot.org/dpaas}Configuration"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dotplotjob", propOrder = {

})
public class Dotplotjob {

    @XmlElement(name = "Filelist", required = true)
    protected Filelist filelist;
    @XmlElement(name = "Configuration", required = true)
    protected Configuration configuration;

    /**
     * Gets the value of the filelist property.
     * 
     * @return
     *     possible object is
     *     {@link Filelist }
     *     
     */
    public Filelist getFilelist() {
        return filelist;
    }

    /**
     * Sets the value of the filelist property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filelist }
     *     
     */
    public void setFilelist(Filelist value) {
        this.filelist = value;
    }

    /**
     * Gets the value of the configuration property.
     * 
     * @return
     *     possible object is
     *     {@link Configuration }
     *     
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the value of the configuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Configuration }
     *     
     */
    public void setConfiguration(Configuration value) {
        this.configuration = value;
    }

}
