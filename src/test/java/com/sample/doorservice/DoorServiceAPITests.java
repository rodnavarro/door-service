package com.sample.doorservice;
import static org.assertj.core.api.Assertions.assertThat;
import com.sample.doorservice.api.DoorApi;
import com.sample.doorservice.repository.ACLRepository;
import com.sample.doorservice.repository.DoorRepository;
import com.sample.doorservice.repository.EmployeeRepository;
import com.sample.doorservice.service.ACLService;
import com.sample.doorservice.service.DoorEventService;
import com.sample.doorservice.service.EmployeeService;
import com.sample.doorservice.service.LogService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class DoorServiceAPITests {


	@Autowired
	private DoorApi controller;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ACLService aclService;
	@Autowired
	private DoorEventService doorEventService;
	@Autowired
	private LogService logService;
	@Autowired
	private DoorRepository doorRepository;
	@Autowired
	private ACLRepository aclRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	//Testing we have all necessary beans/context
	@Test
	void contextLoads() {
		Assert.isTrue(controller != null && employeeService != null && doorEventService != null && logService != null && aclRepository != null && doorRepository != null) ;
	}



}
