package com.sample.doorservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="acls")
public class ACL {
	@Id
	public int id;
	public String doorUID;
	public int employeeId;

	public ACL(int id, String doorUID, int employeeId ){
		this.id = id;
		this.doorUID = doorUID;
		this.employeeId = employeeId;
	}

	public ACL() {
	}
}
