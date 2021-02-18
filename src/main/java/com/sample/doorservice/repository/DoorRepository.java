package com.sample.doorservice.repository;

import com.sample.doorservice.model.Door;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorRepository extends JpaRepository<Door, Integer>{

	Door findByUserName(String username);

}
