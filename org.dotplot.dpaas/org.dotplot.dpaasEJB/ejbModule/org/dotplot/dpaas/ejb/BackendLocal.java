package org.dotplot.dpaas.ejb;

import java.util.List;

import javax.ejb.Local;

import org.dotplot.dpaas.entities.File;
import org.dotplot.dpaas.entities.Job;

@Local
public interface BackendLocal {

	public abstract void persistJob(Job job);

	public abstract void updateJob(Job job);

	public abstract void persistFile(File file);
	
	public abstract void setSetting(String name, String value);
	
	public abstract String setting(String name);
	
	public abstract File file(int fileid);
	
	public abstract List<Job> unfinishedJobs();

	public abstract Job job(int jobid);
}