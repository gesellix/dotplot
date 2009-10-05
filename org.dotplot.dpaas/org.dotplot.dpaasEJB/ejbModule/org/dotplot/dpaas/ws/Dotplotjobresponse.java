
package org.dotplot.dpaas.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dotplotjobresponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dotplotjobresponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Image" type="{http://dotplot.org/dpaas}File"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dotplotjobresponse", propOrder = {
    "image"
})
public class Dotplotjobresponse {

    @XmlElement(name = "Image", required = true)
    protected File image;

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link File }
     *     
     */
    public File getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link File }
     *     
     */
    public void setImage(File value) {
        this.image = value;
    }

}
