package org.dotplot.dpaas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity(name="Setting")
@NamedQuery(name="getSetting",query="SELECT s FROM Setting s WHERE s.name = :name")
public class Setting implements Serializable {

	@Id
	private String name;

	private String value;

	private static final long serialVersionUID = 1L;

	public Setting() {
		super();
	}
	
	public Setting( String name, String value ) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
