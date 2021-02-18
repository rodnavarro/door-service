package com.sample.doorservice.model.responses;

import com.sample.doorservice.model.DoorEvent;

import java.util.List;

public class EventSaveResponse extends ApiResponse {
    public DoorEvent doorEvent;
    public EventSaveResponse(int status, String data, DoorEvent doorEvent) {
        super(status, data);
        this.doorEvent = doorEvent;
    }
}
