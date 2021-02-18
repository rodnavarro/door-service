package com.sample.doorservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.doorservice.model.DoorEvent;
import com.sample.doorservice.model.LogType;
import com.sample.doorservice.model.responses.EventListResponse;
import com.sample.doorservice.model.responses.EventSaveResponse;
import com.sample.doorservice.model.responses.JWTResponse;
import com.sample.doorservice.service.ACLService;
import com.sample.doorservice.service.DoorEventService;
import com.sample.doorservice.service.DoorsService;
import com.sample.doorservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.sample.doorservice.model.AuthRequest;
import com.sample.doorservice.util.JwtUtil;

import java.util.List;

@RestController
public class DoorApi {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LogService logService;

	@Autowired
	private DoorEventService doorEventService;

	@Autowired
	private DoorsService doorsService;

	@Autowired
	private ACLService aclService;

	@Autowired
	private AuthenticationManager authenticationManager;

	//Define here to use in this class and optimize unit testings as well
	public static final String AUTHENTICATION_SUCCEEDED = "AUTHENTICATION_SUCCEEDED";
	public static final String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
	public static final String EVENT_SAVED = "EVENT_SAVED";


	@PostMapping("/authenticate")
	public JWTResponse generateToken(@RequestBody AuthRequest authrequest) throws Exception {

		//Try to Authenticate against DB or LDAP. Return "can't do message" if error
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword()));
		}
		catch (Exception e) {
			return new JWTResponse(HttpStatus.UNAUTHORIZED.value(), AUTHENTICATION_FAILED, null);
		}

		return new JWTResponse(HttpStatus.OK.value(), AUTHENTICATION_SUCCEEDED, "Bearer " + jwtUtil.generateToken(authrequest.getUserName()) );
	}

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public EventListResponse event() throws Exception {

		logService.log(LogType.INFO, "GET All Events");
		List<DoorEvent> doorEvents;

		try{
			doorEvents = doorEventService.getAllEvents();
		}catch(Exception ex){
			logService.log(LogType.ERROR, ex.getMessage());
			return new EventListResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null );
		}

		String successMessage = "Events Fetched";
		logService.log(LogType.INFO, successMessage);
		return new EventListResponse(HttpStatus.OK.value(), successMessage, doorEvents );
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public EventSaveResponse event(@RequestBody DoorEvent doorEvent) throws Exception {

		String returnMessage;

		//Get the door userName from the SecurityContext. This is comming from the JWT token
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String doorUID = authentication.getName();
		doorEvent.doorUID = doorUID;
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			returnMessage = "Token is not valid";
			logService.log(LogType.ERROR, returnMessage);
			return new EventSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), returnMessage,  null );
		}

		logService.log(LogType.INFO, "Saving event: " + doorEvent.eventType);

		//Does this event contains valid data?
		if(!doorEvent.isValid()){
			returnMessage = "EmployeeId, LocalTime and EventType must be valid";
			logService.log(LogType.ERROR, returnMessage);
			return new EventSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), returnMessage,  null );
		}

		//Can this Employee Access this door?
		if(!aclService.isAuthorized(doorUID, doorEvent.employeeId)){
			returnMessage = "Not Authorized to " + doorEvent.eventType + " in " + doorUID;
			logService.log(LogType.ERROR, returnMessage);
			return new EventSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), returnMessage,  null );
		}

		try{
			doorEvent = doorEventService.saveDoorEvent(doorEvent);
		}catch (Exception ex){
			logService.log(LogType.ERROR, ex.getMessage());
			return new EventSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null );
		}

		ObjectMapper mapper = new ObjectMapper();
		returnMessage = EVENT_SAVED;
		logService.log(LogType.INFO, returnMessage + " " + mapper.writeValueAsString(doorEvent));
		return new EventSaveResponse(HttpStatus.OK.value(), returnMessage, doorEvent);
	}



}
