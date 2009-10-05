
package org.dotplot.dpaas.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Configuration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Configuration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="LUTs" type="{http://dotplot.org/dpaas}luts"/>
 *         &lt;element name="Filter" type="{http://dotplot.org/dpaas}Filters"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Configuration", propOrder = {

})
public class Configuration {

    @XmlElement(name = "LUTs", required = true)
    protected Luts luTs;
    @XmlElement(name = "Filter", required = true)
    protected Filters filter;

    /**
     * Gets the value of the luTs property.
     * 
     * @return
     *     possible object is
     *     {@link Luts }
     *     
     */
    public Luts getLUTs() {
        return luTs;
    }

    /**
     * Sets the value of the luTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Luts }
     *     
     */
    public void setLUTs(Luts value) {
        this.luTs = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filters }
     *     
     */
    public Filters getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filters }
     *     
     */
    public void setFilter(Filters value) {
        this.filter = value;
    }

}
