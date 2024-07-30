package com.riskanalysisexpertsystem.authApp.service;

import org.springframework.stereotype.Service;
import com.riskanalysisexpertsystem.authApp.model.AuthenticationRequest;
import com.riskanalysisexpertsystem.authApp.model.RegisterRequest;
import com.riskanalysisexpertsystem.authApp.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import com.riskanalysisexpertsystem.authApp.entity.User;
import com.riskanalysisexpertsystem.authApp.model.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.riskanalysisexpertsystem.authApp.model.Status;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final AuthenticationRepository authenticationRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public void register(RegisterRequest request) {

		User user = User.builder()
				.email(request.getEmail())
				.firstName(request.getFirstname())
				.lastName(request.getLastname())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.status(Status.NEW)
				.build();

		authenticationRepository.save(user);
	}
	
	public String authenticate(AuthenticationRequest request) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		
		User user = authenticationRepository.findAll().stream()
						.filter(authenticateUser -> authenticateUser.getEmail().equals(request.getEmail()))
						.findFirst()
						.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

		String jwtToken = jwtService.generateToken(user);
		
		return jwtToken;
	
	}
	
	public Role getRoleByEmail(String email) {
		User user = authenticationRepository.findAll().stream()
				.filter(authenticateUser -> authenticateUser.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		
		return user.getRole();
	}
	
	public Status getStatusByEmail(String email) {
		User user = authenticationRepository.findAll().stream()
				.filter(authenticateUser -> authenticateUser.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		
		return user.getStatus();
	}
}
