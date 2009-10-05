package org.dotplot.dpaas.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dotplot.dpaas.entities.File;
import org.dotplot.dpaas.entities.Job;
import org.dotplot.dpaas.entities.Setting;

/**
 * Session Bean implementation class Backend
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local(BackendLocal.class)
public class Backend implements BackendLocal {

	@PersistenceContext(unitName="dpaas")
	EntityManager entityManager;
	
	public Backend() {}
   
    public void persistJob( Job job ) {
		entityManager.persist(job);
	}
	
	public void updateJob( Job job ) {		
		entityManager.merge(job);
		entityManager.flush();
	}

	@Override
	public void persistFile(File file) {
		entityManager.persist(file);
	}
	
	public void setSetting( String name, String value ) {
		Setting s = new Setting(name, value);
		entityManager.merge(s);
		entityManager.flush();
	}
	
	public String setting( String name ) {
		Setting s = null;
		try {
			s = (Setting) entityManager.createNamedQuery("getSetting").setParameter("name", name).getSingleResult();
		} catch (Exception e) {	
			return null;
		}
		return s.getValue();
	}
	
	@Override
	public File file(int fileid) {
		try {
			return (File) entityManager.createNamedQuery("fileById")
				.setParameter("id", fileid)
				.getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Job job(int jobid) {
		try {
			return (Job) entityManager.createNamedQuery("jobById")
				.setParameter("id", jobid)
				.getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Job> unfinishedJobs() {
		try {
			return entityManager.createNamedQuery("unfinishedJobs").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}