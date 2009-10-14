package org.dotplot.dpaas.beans;
import javax.ejb.EJB;

import org.dotplot.dpaas.ejb.BackendLocal;

public class Login {
	
	@EJB
	BackendLocal backend;
	
	private String password;
	private String username;
	private boolean authenticated = false;
	
	public String authenticate() {
		if ((backend.setting("password") == null) || (backend.setting("password").equals(""))) {
			if (username.equals("admin") && password.equals("admin")) {
				authenticated = true;
				return "loginSuccess";
			}
		} else {
			if (username.equals("admin") && password.equals(backend.setting("password"))) {
				authenticated = true;
				return "loginSuccess";
			}
		}
		return "loginFail";
	}
	
	public String getPassword() {
		return null;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return null;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
}
