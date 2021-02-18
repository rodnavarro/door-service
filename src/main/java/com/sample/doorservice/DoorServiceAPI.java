package com.sample.doorservice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.sample.doorservice.service.ACLService;
import com.sample.doorservice.service.DoorsService;
import com.sample.doorservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sample.doorservice.model.Door;
import com.sample.doorservice.repository.DoorRepository;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DoorServiceAPI {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DoorServiceAPI.class, args);

	}

	@Autowired
	private DoorsService doorsService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ACLService aclService;
	

	//Simple API Design here. Just putting some data in place for testings
	@PostConstruct
	public void initData()
	{
		doorsService.initData();
		employeeService.initData();
		aclService.initData();
	}



}
