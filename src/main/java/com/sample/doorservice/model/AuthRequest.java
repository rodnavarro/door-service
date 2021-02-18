package com.sample.doorservice.model;

public class AuthRequest {
	
	private String userName;
	private String password;
	private String shipCode;

	public AuthRequest(String userName, String password, String shipCode) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public AuthRequest() {
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
