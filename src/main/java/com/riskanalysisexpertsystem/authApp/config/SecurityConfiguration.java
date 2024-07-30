package com.riskanalysisexpertsystem.authApp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{		
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/**").permitAll()
		             .anyRequest()
		             .authenticated()
		     )
			.sessionManagement((sessionManagement) ->
				sessionManagement
					.sessionConcurrency((sessionConcurrency) ->
						sessionConcurrency
							.maximumSessions(1)
							.expiredUrl("/login?expired")
					)
			)
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
			
		return httpSecurity.build();
	}
}