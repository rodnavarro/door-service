package com.sample.doorservice.model.responses;

import com.sample.doorservice.model.responses.ApiResponse;

public class JWTResponse extends ApiResponse {

    public String jwt;

    public JWTResponse(int status, String data, String jwt)  {
        super(status, data);
        this.jwt = jwt;
    }

}
