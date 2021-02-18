package com.sample.doorservice.service;

import com.sample.doorservice.model.ACL;
import com.sample.doorservice.model.Employee;
import com.sample.doorservice.repository.ACLRepository;
import com.sample.doorservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ACLService {

	@Autowired
	private ACLRepository repository;

	public boolean isAuthorized(String doorUID, int employeeId) {
		return repository.findByDoorUIDAndEmployeeId(doorUID, employeeId) != null;
	}

	public void initData(){
		List<ACL> acls = Stream.of(
				new ACL(5000, "main_building",5),
				new ACL(5001, "main_building",6),
				new ACL(5002, "lab_a",5),
				new ACL(5003, "lab_b",6)
		).collect(Collectors.toList());
		repository.saveAll(acls);
	}

}
