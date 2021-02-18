package com.sample.doorservice;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sample.doorservice.api.DoorApi;
import com.sample.doorservice.model.*;
import com.sample.doorservice.repository.ACLRepository;
import com.sample.doorservice.repository.DoorRepository;
import com.sample.doorservice.repository.EmployeeRepository;
import com.sample.doorservice.util.JwtUtil;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootTest
@AutoConfigureMockMvc
public class DoorApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoorRepository doorRepository;

    @Autowired
    private DoorApi doorApiController;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ACLRepository aclRepository;

    //Testing if Authentication Endpoint is Accessible
    @Test
    public void authenticationEndpointExists() throws Exception {

        AuthRequest ar = new AuthRequest();
        ar.setUserName("test");
        ar.setPassword("test");
        this.mockMvc.perform(post("/authenticate",ar)).andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void authenticationIsSuccessful() throws Exception {

        String testDoorUID = "test_door_2021";
        String password = "test_pass_2021";


        List<Door> doors = Stream.of(
                new Door(9000, testDoorUID, password)
        ).collect(Collectors.toList());
        doorRepository.saveAll(doors);

        AuthRequest ar = new AuthRequest();
        ar.setUserName(testDoorUID);
        ar.setPassword(password);
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                            post("/authenticate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(ar))
                            )
        .andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(content().string(containsString(doorApiController.AUTHENTICATION_SUCCEEDED)));


    }//authenticationIsSuccessful


    @Test
    public void postEventWithAuthorizedUser() throws Exception {

        //Setup Data
        String doorUID = "test_door";
        int employeeId = 200;
        List<Door> doors = Stream.of(
                new Door(100, doorUID, "test_pass")
        ).collect(Collectors.toList());
        doorRepository.saveAll(doors);

        List<Employee> employees = Stream.of(
                new Employee(employeeId, "Test Employee")
        ).collect(Collectors.toList());
        employeeRepository.saveAll(employees);

        List<ACL> acls = Stream.of(
                new ACL(300, doorUID,employeeId)
        ).collect(Collectors.toList());
        aclRepository.saveAll(acls);

        //Now create the token for the door we just added. Need this door to authorize user later on
        String token = "Bearer " + jwtUtil.generateToken(doorUID);

        DoorEvent event = new DoorEvent();
        event.eventType = DoorEventType.ENTER.name();
        event.employeeId = employeeId;
        event.localTime = new Date();
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/events")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(event))
        ).andDo(print())
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(content().string(containsString(doorApiController.EVENT_SAVED)));;


    }//authenticationIsSuccessful



}

