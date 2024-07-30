package com.riskanalysisexpertsystem.authApp.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private String message;
	private String token;
	private String email;
	private Role role; 
	
}