package com.sample.doorservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.doorservice.model.ACL;
import com.sample.doorservice.model.Door;
import com.sample.doorservice.repository.ACLRepository;
import com.sample.doorservice.repository.DoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoorsService implements UserDetailsService{

	@Autowired
	private DoorRepository repository;

	@Autowired
	private ACLRepository aclRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Door door = repository.findByUserName(username);
		return new org.springframework.security.core.userdetails.User(door.getUserName(), door.getPassword(), new ArrayList<>());
	}


	public void initData(){
		List<Door> doors = Stream.of(
				new Door(101, "main_building", "s3cr3t-main_building"),
				new Door(102, "lab_a", "s3cr3t-lab_a"),
				new Door(103, "lab_b", "s3cr3t-lab_b")
		).collect(Collectors.toList());
		repository.saveAll(doors);
	}

}
