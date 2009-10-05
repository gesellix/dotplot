package org.dotplot.dpaas.beans;

public class Check {

	private int jobId;
	
	public String newJob() {
		return "create";
	}
	
	public String checkJob() {
		return "result";
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getJobId() {
		return jobId;
	}
}
