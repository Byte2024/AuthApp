package com.riskanalysisexpertsystem.authApp.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.riskanalysisexpertsystem.authApp.model.RegisterRequest;
import com.riskanalysisexpertsystem.authApp.model.Role;
import com.riskanalysisexpertsystem.authApp.model.Status;
import com.riskanalysisexpertsystem.authApp.model.AuthenticationRequest;
import com.riskanalysisexpertsystem.authApp.model.AuthenticationResponse;
import com.riskanalysisexpertsystem.authApp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/authentication/authorize/")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@PostMapping("/registration")
	public ResponseEntity<String> registration(
			@RequestBody RegisterRequest request
			){
		try {
			authenticationService.register(request);
			return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("We were unable to create the new user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
			){
		Status status = authenticationService.getStatusByEmail(request.getEmail());
		
		if(status.equals(Status.NEW)) {
			AuthenticationResponse authenticationResponse = new AuthenticationResponse(
					"Unable to authenticate new user", null, null, null);
			return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
		}else {
			Role role = authenticationService.getRoleByEmail(request.getEmail());
			String token =  authenticationService.authenticate(request);
			
			AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
															.message("User Authenticated Successfully")
															.token(token)
															.email(request.getEmail())
															.role(role )
															.build();
			
			return ResponseEntity.ok(authenticationResponse);
		}
		
	}
}
