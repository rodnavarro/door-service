package com.sample.doorservice.model.responses;

import com.sample.doorservice.model.DoorEvent;
import com.sample.doorservice.model.responses.ApiResponse;

import java.util.List;

public class EventListResponse extends ApiResponse {
    public List<DoorEvent> doorEvents;
    public EventListResponse(int status, String data, List<DoorEvent> doorEvents) {
        super(status, data);
        this.doorEvents = doorEvents;
    }
}
