package com.sample.doorservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="employees")
public class Employee {
	@Id
	public int id;
	public String fullName;

	public Employee(int id, String fullName){
		this.id = id;
		this.fullName = fullName;
	}

	public Employee() {
	}
}
