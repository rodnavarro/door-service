package com.sample.doorservice.model.responses;


import java.sql.Timestamp;

public class ApiResponse {
    public int status;
    public String message;
    public Timestamp timestamp;

    public ApiResponse(int status, String message)  {
        this.status = status;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());

    }
}
