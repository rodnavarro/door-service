package com.sample.doorservice.repository;

import com.sample.doorservice.model.DoorEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorEventRepository extends JpaRepository<DoorEvent, Integer> {
}
