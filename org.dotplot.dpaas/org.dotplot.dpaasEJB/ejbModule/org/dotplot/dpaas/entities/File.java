package org.dotplot.dpaas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name="File")
@NamedQueries({
		@NamedQuery(name="fileById",query="SELECT f FROM File f WHERE f.id=:id")
})
public class File implements Serializable {
	@Id
	@GeneratedValue
	private int id;

	private int jobid;
	
	private String name;

	private String type;
	
	@Lob
	private byte[] content;

	private static final long serialVersionUID = 1L;

	public File() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJobid() {
		return this.jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
