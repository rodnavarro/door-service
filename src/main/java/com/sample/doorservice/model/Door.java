package com.sample.doorservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="doors")
public class Door {
	
	@Id
	private int id;
	private String userName;
	private String password;
	private String email;

	public Door(int id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public Door() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
