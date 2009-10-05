package org.dotplot.dpaas.beans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.dotplot.dpaas.ejb.BackendLocal;
import org.dotplot.dpaas.entities.File;
import org.dotplot.dpaas.entities.Job;
import org.dotplot.dpaas.ws.Luts;


public class Create {
	
	@EJB
	BackendLocal backend;
	
	private boolean _tosAccepted;	
	
	private Job _job;
	@SuppressWarnings("unused")
	private File _file;
	
	//private UploadedFile _upFile;
	private List<UploadedFile> _upFiles;
	
	public Create() {
		_job = new Job();
		_upFiles = new ArrayList<UploadedFile>();
	}
	
	public String finish() {
		persistFile( _upFiles );
		backend.persistJob(_job);

		return "finished";
	}
	
	private void persistFile( List<UploadedFile> files ) {
		try {
			for (UploadedFile file : files ) {
				InputStream stream = file.getInputStream();
				long size = file.getSize();
				byte [] buffer = new byte[(int)size];
				stream.read(buffer, 0, (int)size);
							
				File fobj = new File();
				fobj.setContent(buffer);
				fobj.setJobid(_job.getId());
				fobj.setName(file.getName());
				_file = fobj;
				
				_job.addFile(fobj);
				stream.close();
				
				System.out.println("OK");
			}
		} catch ( Exception e ) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * File
	 */
	public UploadedFile getFile() {
		return null;
	}
	public void setFile(UploadedFile file) {
		_upFiles.add(file);
	}
	
	/*
	 * Name
	 */
	public String getName() {
		return _job.getName();
	}
	public void setName(String name) {
		_job.setName(name);
	}
	
	/*
	 * EMail
	 */
	public String getEmail() {
		return _job.getEmail();
	}
	public void setEmail(String email) {
		_job.setEmail(email);
	}
	
	/*
	 * Terms Of Service Accepted
	 */
	public int getTosAccepted() {
		return (_tosAccepted) ? 1 : 0;
	}
	public void setTosAccepted(int tos) {
		_tosAccepted = (tos != 0) ? true : false;
	}
	
	/*
	 * Terms Of Service Accepted
	 */
	public byte[] getImage() {
		return _job.getImage();
	}
	
	public int getJobId() {
		return _job.getId();
	}
	
	/*
	 * Terms Of Service Accepted
	 */
	public String getJobLuts() {
		if (_job.getLuts() == Luts.RED) return "Rot";
		if (_job.getLuts() == Luts.YELLOW) return "Gelb";
		if (_job.getLuts() == Luts.GREEN) return "Grün";
		if (_job.getLuts() == Luts.BLUE) return "Blau";
		
		return "Blau";
	}
	
	public void setJobLuts(String jobluts) {
		_job.setLuts(Luts.BLUE);
		
		if (jobluts.equals("Rot")) _job.setLuts(Luts.RED);
		if (jobluts.equals("Gelb")) _job.setLuts(Luts.YELLOW);
		if (jobluts.equals("Grün")) _job.setLuts(Luts.GREEN);
		if (jobluts.equals("Blau")) _job.setLuts(Luts.BLUE);
	}
	
	/*
	 * Terms Of Service Accepted
	 */
	public String getJobType() {
		return _job.getJobtype();
	}
	
	public void setJobType(String jobtype) {
		_job.setJobtype(jobtype);
	}
	
	/*
	 * Terms Of Service Accepted
	 */
	public List<String> getMaxFiles() {
		Integer maxFiles = Integer.parseInt(backend.setting("maxFiles"));
		List<String> res = new ArrayList<String>();
		
		for (int i = 1; i <= maxFiles; i++) res.add(maxFiles.toString());
		
		return res;
	}
	
	/*
	 * Validatoren
	 * 
	 */
	
	public void nameValidate(FacesContext context,
			UIComponent component, 
			Object val) 
	throws ValidatorException {
		Matcher m = Pattern.compile(".*").matcher((CharSequence) val);
		
		if (!m.matches())
			throw new ValidatorException( new FacesMessage("Name ist erforderlich") );
	}
	
	public void emailValidate(FacesContext context,
			UIComponent component, 
			Object val) 
	throws ValidatorException {
		Matcher m = Pattern.compile(".*\\@.*\\.[a-zA-Z]{2,4}").matcher((CharSequence) val);
		
		if (!m.matches())
			throw new ValidatorException( new FacesMessage("Keine gültige EMail-Adresse") );
	}
	
	public void tosValidate(FacesContext context,
			UIComponent component, 
			Object val) 
	throws ValidatorException {
		if ((Integer)val != 1 )
			throw new ValidatorException( new FacesMessage("Sie müssen den Bedingungen zustimmen!") );
	}
}