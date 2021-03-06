//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.29 at 11:18:10 PM CEST 
//

package org.dotplot.dpaas.wsdto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for luts.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="luts">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="matrix4dotplot"/>
 *     &lt;enumeration value="red"/>
 *     &lt;enumeration value="green"/>
 *     &lt;enumeration value="blue"/>
 *     &lt;enumeration value="cyan"/>
 *     &lt;enumeration value="magenta"/>
 *     &lt;enumeration value="yellow"/>
 *     &lt;enumeration value="gray"/>
 *     &lt;enumeration value="inverted_gray"/>
 *     &lt;enumeration value="two_levels"/>
 *     &lt;enumeration value="four_levels"/>
 *     &lt;enumeration value="eight_levels"/>
 *     &lt;enumeration value="sixteen_levels"/>
 *     &lt;enumeration value="thirty_two_levels"/>
 *     &lt;enumeration value="sixty_four_levels"/>
 *     &lt;enumeration value="hundred_twenty_eight_levels"/>
 *     &lt;enumeration value="red_cyan"/>
 *     &lt;enumeration value="green_magenta"/>
 *     &lt;enumeration value="blue_yellow"/>
 *     &lt;enumeration value="sin_rgb"/>
 *     &lt;enumeration value="sin_rbg"/>
 *     &lt;enumeration value="sin_grb"/>
 *     &lt;enumeration value="sin_gbr"/>
 *     &lt;enumeration value="sin_brg"/>
 *     &lt;enumeration value="sin_bgr"/>
 *     &lt;enumeration value="sin_rgb_0"/>
 *     &lt;enumeration value="sin_rbg_0"/>
 *     &lt;enumeration value="sin_grb_0"/>
 *     &lt;enumeration value="sin_gbr_0"/>
 *     &lt;enumeration value="sin_brg_0"/>
 *     &lt;enumeration value="sin_bgr_0"/>
 *     &lt;enumeration value="sqrt_rgb"/>
 *     &lt;enumeration value="sqrt_rbg"/>
 *     &lt;enumeration value="sqrt_grb"/>
 *     &lt;enumeration value="sqrt_gbr"/>
 *     &lt;enumeration value="sqrt_brg"/>
 *     &lt;enumeration value="sqrt_bgr"/>
 *     &lt;enumeration value="sqrt_rgb_0"/>
 *     &lt;enumeration value="sqrt_rbg_0"/>
 *     &lt;enumeration value="sqrt_brg_0"/>
 *     &lt;enumeration value="sqrt_bgr_0"/>
 *     &lt;enumeration value="sqrt_gbr_0"/>
 *     &lt;enumeration value="sqrt_grb_0"/>
 *     &lt;enumeration value="hue_rgb"/>
 *     &lt;enumeration value="hue_rbg"/>
 *     &lt;enumeration value="hue_grb"/>
 *     &lt;enumeration value="hue_gbr"/>
 *     &lt;enumeration value="hue_brg"/>
 *     &lt;enumeration value="hue_bgr"/>
 *     &lt;enumeration value="hue_rgb_0"/>
 *     &lt;enumeration value="hue_rbg_0"/>
 *     &lt;enumeration value="hue_grb_0"/>
 *     &lt;enumeration value="hue_gbr_0"/>
 *     &lt;enumeration value="hue_brg_0"/>
 *     &lt;enumeration value="hue_bgr_0"/>
 *     &lt;enumeration value="red_saw_2"/>
 *     &lt;enumeration value="red_saw_4"/>
 *     &lt;enumeration value="red_saw_8"/>
 *     &lt;enumeration value="green_saw_2"/>
 *     &lt;enumeration value="green_saw_4"/>
 *     &lt;enumeration value="green_saw_8"/>
 *     &lt;enumeration value="blue_saw_2"/>
 *     &lt;enumeration value="blue_saw_4"/>
 *     &lt;enumeration value="blue_saw_8"/>
 *     &lt;enumeration value="red_green_saw_2"/>
 *     &lt;enumeration value="red_green_saw_4"/>
 *     &lt;enumeration value="red_green_saw_8"/>
 *     &lt;enumeration value="red_blue_saw_2"/>
 *     &lt;enumeration value="red_blue_saw_4"/>
 *     &lt;enumeration value="red_blue_saw_8"/>
 *     &lt;enumeration value="green_blue_saw_2"/>
 *     &lt;enumeration value="green_blue_saw_4"/>
 *     &lt;enumeration value="green_blue_saw_8"/>
 *     &lt;enumeration value="random_256"/>
 *     &lt;enumeration value="random_32"/>
 *     &lt;enumeration value="random_8"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "luts")
@XmlEnum
public enum WSLuts {

	@XmlEnumValue("matrix4dotplot")
	MATRIX_4_DOTPLOT("matrix4dotplot"), @XmlEnumValue("red")
	RED("red"), @XmlEnumValue("green")
	GREEN("green"), @XmlEnumValue("blue")
	BLUE("blue"), @XmlEnumValue("cyan")
	CYAN("cyan"), @XmlEnumValue("magenta")
	MAGENTA("magenta"), @XmlEnumValue("yellow")
	YELLOW("yellow"), @XmlEnumValue("gray")
	GRAY("gray"), @XmlEnumValue("inverted_gray")
	INVERTED_GRAY("inverted_gray"), @XmlEnumValue("two_levels")
	TWO_LEVELS("two_levels"), @XmlEnumValue("four_levels")
	FOUR_LEVELS("four_levels"), @XmlEnumValue("eight_levels")
	EIGHT_LEVELS("eight_levels"), @XmlEnumValue("sixteen_levels")
	SIXTEEN_LEVELS("sixteen_levels"), @XmlEnumValue("thirty_two_levels")
	THIRTY_TWO_LEVELS("thirty_two_levels"), @XmlEnumValue("sixty_four_levels")
	SIXTY_FOUR_LEVELS("sixty_four_levels"), @XmlEnumValue("hundred_twenty_eight_levels")
	HUNDRED_TWENTY_EIGHT_LEVELS("hundred_twenty_eight_levels"), @XmlEnumValue("red_cyan")
	RED_CYAN("red_cyan"), @XmlEnumValue("green_magenta")
	GREEN_MAGENTA("green_magenta"), @XmlEnumValue("blue_yellow")
	BLUE_YELLOW("blue_yellow"), @XmlEnumValue("sin_rgb")
	SIN_RGB("sin_rgb"), @XmlEnumValue("sin_rbg")
	SIN_RBG("sin_rbg"), @XmlEnumValue("sin_grb")
	SIN_GRB("sin_grb"), @XmlEnumValue("sin_gbr")
	SIN_GBR("sin_gbr"), @XmlEnumValue("sin_brg")
	SIN_BRG("sin_brg"), @XmlEnumValue("sin_bgr")
	SIN_BGR("sin_bgr"), @XmlEnumValue("sin_rgb_0")
	SIN_RGB_0("sin_rgb_0"), @XmlEnumValue("sin_rbg_0")
	SIN_RBG_0("sin_rbg_0"), @XmlEnumValue("sin_grb_0")
	SIN_GRB_0("sin_grb_0"), @XmlEnumValue("sin_gbr_0")
	SIN_GBR_0("sin_gbr_0"), @XmlEnumValue("sin_brg_0")
	SIN_BRG_0("sin_brg_0"), @XmlEnumValue("sin_bgr_0")
	SIN_BGR_0("sin_bgr_0"), @XmlEnumValue("sqrt_rgb")
	SQRT_RGB("sqrt_rgb"), @XmlEnumValue("sqrt_rbg")
	SQRT_RBG("sqrt_rbg"), @XmlEnumValue("sqrt_grb")
	SQRT_GRB("sqrt_grb"), @XmlEnumValue("sqrt_gbr")
	SQRT_GBR("sqrt_gbr"), @XmlEnumValue("sqrt_brg")
	SQRT_BRG("sqrt_brg"), @XmlEnumValue("sqrt_bgr")
	SQRT_BGR("sqrt_bgr"), @XmlEnumValue("sqrt_rgb_0")
	SQRT_RGB_0("sqrt_rgb_0"), @XmlEnumValue("sqrt_rbg_0")
	SQRT_RBG_0("sqrt_rbg_0"), @XmlEnumValue("sqrt_brg_0")
	SQRT_BRG_0("sqrt_brg_0"), @XmlEnumValue("sqrt_bgr_0")
	SQRT_BGR_0("sqrt_bgr_0"), @XmlEnumValue("sqrt_gbr_0")
	SQRT_GBR_0("sqrt_gbr_0"), @XmlEnumValue("sqrt_grb_0")
	SQRT_GRB_0("sqrt_grb_0"), @XmlEnumValue("hue_rgb")
	HUE_RGB("hue_rgb"), @XmlEnumValue("hue_rbg")
	HUE_RBG("hue_rbg"), @XmlEnumValue("hue_grb")
	HUE_GRB("hue_grb"), @XmlEnumValue("hue_gbr")
	HUE_GBR("hue_gbr"), @XmlEnumValue("hue_brg")
	HUE_BRG("hue_brg"), @XmlEnumValue("hue_bgr")
	HUE_BGR("hue_bgr"), @XmlEnumValue("hue_rgb_0")
	HUE_RGB_0("hue_rgb_0"), @XmlEnumValue("hue_rbg_0")
	HUE_RBG_0("hue_rbg_0"), @XmlEnumValue("hue_grb_0")
	HUE_GRB_0("hue_grb_0"), @XmlEnumValue("hue_gbr_0")
	HUE_GBR_0("hue_gbr_0"), @XmlEnumValue("hue_brg_0")
	HUE_BRG_0("hue_brg_0"), @XmlEnumValue("hue_bgr_0")
	HUE_BGR_0("hue_bgr_0"), @XmlEnumValue("red_saw_2")
	RED_SAW_2("red_saw_2"), @XmlEnumValue("red_saw_4")
	RED_SAW_4("red_saw_4"), @XmlEnumValue("red_saw_8")
	RED_SAW_8("red_saw_8"), @XmlEnumValue("green_saw_2")
	GREEN_SAW_2("green_saw_2"), @XmlEnumValue("green_saw_4")
	GREEN_SAW_4("green_saw_4"), @XmlEnumValue("green_saw_8")
	GREEN_SAW_8("green_saw_8"), @XmlEnumValue("blue_saw_2")
	BLUE_SAW_2("blue_saw_2"), @XmlEnumValue("blue_saw_4")
	BLUE_SAW_4("blue_saw_4"), @XmlEnumValue("blue_saw_8")
	BLUE_SAW_8("blue_saw_8"), @XmlEnumValue("red_green_saw_2")
	RED_GREEN_SAW_2("red_green_saw_2"), @XmlEnumValue("red_green_saw_4")
	RED_GREEN_SAW_4("red_green_saw_4"), @XmlEnumValue("red_green_saw_8")
	RED_GREEN_SAW_8("red_green_saw_8"), @XmlEnumValue("red_blue_saw_2")
	RED_BLUE_SAW_2("red_blue_saw_2"), @XmlEnumValue("red_blue_saw_4")
	RED_BLUE_SAW_4("red_blue_saw_4"), @XmlEnumValue("red_blue_saw_8")
	RED_BLUE_SAW_8("red_blue_saw_8"), @XmlEnumValue("green_blue_saw_2")
	GREEN_BLUE_SAW_2("green_blue_saw_2"), @XmlEnumValue("green_blue_saw_4")
	GREEN_BLUE_SAW_4("green_blue_saw_4"), @XmlEnumValue("green_blue_saw_8")
	GREEN_BLUE_SAW_8("green_blue_saw_8"), @XmlEnumValue("random_256")
	RANDOM_256("random_256"), @XmlEnumValue("random_32")
	RANDOM_32("random_32"), @XmlEnumValue("random_8")
	RANDOM_8("random_8");
	public static WSLuts fromValue(String v) {
		for (WSLuts c : WSLuts.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	private final String value;

	WSLuts(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

}
