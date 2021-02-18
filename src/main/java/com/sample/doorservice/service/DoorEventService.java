package com.sample.doorservice.service;

import com.sample.doorservice.model.DoorEvent;
import com.sample.doorservice.repository.DoorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DoorEventService {
    @Autowired
    private DoorEventRepository repository;

    public DoorEvent saveDoorEvent(DoorEvent doorEvent){
        doorEvent.serverTime = new Date();
        doorEvent = repository.save(doorEvent);
        return doorEvent;
    }

    public List<DoorEvent> getAllEvents(){
        return repository.findAll();
    }

}
