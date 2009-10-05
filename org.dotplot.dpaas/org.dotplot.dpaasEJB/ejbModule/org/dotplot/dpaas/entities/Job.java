package org.dotplot.dpaas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.dotplot.dpaas.ws.Luts;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity(name="Job")
@NamedQueries({
		@NamedQuery(name="unfinishedJobs", query="SELECT j FROM Job j WHERE j.finished IS NULL"),
		@NamedQuery(name="jobById", query="SELECT j FROM Job j WHERE j.id = :id")
})
public class Job implements Serializable {
	@Id 
	@GeneratedValue
	private int id;
	private String name;
	private String email;
	private byte[] image;
	@Temporal(TIMESTAMP)
	private Date created;
	@Temporal(TIMESTAMP)
	private Date finished;
	private Luts luts;
	private String jobtype;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<File>();

	private static final long serialVersionUID = 1L;

	public Job() {
		super();
		this.created = new Date();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public byte[] getImage() {
		return this.image;
	}
	
	public void setImage( byte[] image ) {
		this.image = image;
		this.finished = new Date();
	}
	
	public Date getCreated() {
		return this.created;
	}

	public Date getFinished() {
		return this.finished;
	}
	
	public Luts getLuts() {
		return this.luts;
	}

	public void setLuts(Luts luts) {
		this.luts = luts;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getJobtype() {
		return jobtype;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	public void addFile(File file) {
		this.files.add(file);
	}

	public List<File> getFiles() {
		return files;
	}
}
