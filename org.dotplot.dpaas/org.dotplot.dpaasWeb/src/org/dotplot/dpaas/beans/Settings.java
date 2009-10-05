package org.dotplot.dpaas.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.dotplot.dpaas.ejb.BackendLocal;
import org.dotplot.dpaas.ejb.WSAgentLocal;

public class Settings {
	
	@EJB
	BackendLocal backend;
	
	@EJB
	WSAgentLocal wsagent;
	
	private String url;
	private String name;
	private String zip;
	private String place;
	private String email;
	private String maxFiles;
	private String pw1;
	private String pw2;
	
	private void persist(String name, String value) {
		backend.setSetting(name, value);
	}
	
	public String saveSettings() {
		if (url.isEmpty()) 
			return "URL Not Set";
		else
			persist("url",url);
		
		if (name.isEmpty()) 
			return "URL Not Set";
		else
			persist("name",name);
		
		if (zip.isEmpty()) 
			return "URL Not Set";
		else
			persist("zip",zip);
		
		if (place.isEmpty()) 
			return "URL Not Set";
		else
			persist("place",place);
		
		if (email.isEmpty()) 
			return "URL Not Set";
		else
			persist("email",email);
		
		if (maxFiles.isEmpty()) 
			return "URL Not Set";
		else
			persist("maxFiles",maxFiles);
		
		
		if (((pw1.isEmpty())||(pw2.isEmpty()))) {
			return "Password Not Set";
		} else {
			persist("password",pw1);
		}
		
		return "SettingsSaved";
	}

	// Get-Set
	
	public String getUrl() {
		return backend.setting("url");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return backend.setting("name");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZip() {
		return backend.setting("zip");
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPlace() {
		return backend.setting("place");
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getEmail() {
		return backend.setting("email");
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMaxFiles() {
		return backend.setting("maxFiles");
	}

	public void setMaxFiles(String maxFiles) {
		this.maxFiles = maxFiles;
	}

	public List<String> getMaxFilesRange() {
		List<String> range = new ArrayList<String>();
		range.add("1");
		range.add("5");
		range.add("9");
		
		return range;
	}

	public void setPw1(String pw) {
		this.pw1 = pw;
	}

	public String getPw1() {
		return backend.setting("password");
	}
	
	public void setPw2(String pw) {
		this.pw2 = pw;
	}

	public String getPw2() {
		return backend.setting("password");
	}
	
	public String startQueue() {
		wsagent.start();
		return "Started";
	}
	
	public String stopQueue() {
		wsagent.stop();
		return "Stopped";
	}
	
	public String getQueueStatus() {
		if (wsagent.state()) return "RUNNING";
		return "STOPPED";
	}
}
