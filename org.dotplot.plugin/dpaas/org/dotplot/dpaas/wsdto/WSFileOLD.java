package org.dotplot.dpaas.wsdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.dotplot.dpaas.ByteRessource;
import org.dotplot.tokenizer.service.TextType;

/**
 * <p>
 * Java class for File complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;File&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;Filename&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *         &lt;element name=&quot;Content&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}base64Binary&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "File", propOrder = { "filename", "content" })
public class WSFileOLD {

	@XmlElement(name = "Filename", required = true)
	protected String filename;
	@XmlElement(name = "Content", required = true)
	protected byte[] content;

	/**
	 * Gets the value of the content property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Gets the value of the filename property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setContent(byte[] value) {
		this.content = (value);
	}

	/**
	 * Sets the value of the filename property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFilename(String value) {
		this.filename = value;
	}

	public ByteRessource toByteRessource() {

		ByteRessource tmp = new ByteRessource();
		tmp.setBytearray(content);
		tmp.setFilename(filename);
		tmp.setType(TextType.type); // TODO: make new types avail here?!
		return tmp;
	}
}
