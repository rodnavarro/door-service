package com.sample.doorservice.repository;

import com.sample.doorservice.model.ACL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACLRepository extends JpaRepository<ACL, Integer> {
    ACL findByDoorUIDAndEmployeeId(String doorUID, int EmployeeId);
}
