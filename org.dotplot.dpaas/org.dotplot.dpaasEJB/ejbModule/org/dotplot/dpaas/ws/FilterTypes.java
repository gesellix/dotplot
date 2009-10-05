
package org.dotplot.dpaas.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FilterTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="org.dotplot.filter.LineFilter"/>
 *     &lt;enumeration value="org.dotplot.filter.KeyWordFilter"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FilterTypes")
@XmlEnum
public enum FilterTypes {

    @XmlEnumValue("org.dotplot.filter.LineFilter")
    ORG_DOTPLOT_FILTER_LINE_FILTER("org.dotplot.filter.LineFilter"),
    @XmlEnumValue("org.dotplot.filter.KeyWordFilter")
    ORG_DOTPLOT_FILTER_KEY_WORD_FILTER("org.dotplot.filter.KeyWordFilter");
    private final String value;

    FilterTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FilterTypes fromValue(String v) {
        for (FilterTypes c: FilterTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
