package br.com.moradas291.site.business.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RequestUser {
	
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RequestUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public RequestUser() {

	}	
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	

}
