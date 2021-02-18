package com.sample.doorservice.service;

import com.sample.doorservice.model.Door;
import com.sample.doorservice.model.Employee;
import com.sample.doorservice.repository.DoorRepository;
import com.sample.doorservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public void initData(){
		List<Employee> employees = Stream.of(
				new Employee(5, "Rod N"),
				new Employee(6, "Charles A")
		).collect(Collectors.toList());
		repository.saveAll(employees);
	}

}
