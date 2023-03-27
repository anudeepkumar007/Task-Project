package com.personal.project.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.personal.project.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Securityconfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAutheticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	}
	
	
	@Bean
//	This bean is created for security configurations purpose: If user info is wrong it will give a exception
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/**").permitAll()
		.anyRequest()
		.authenticated();
		http.addFilterBefore(jwtAutheticationFilter,UsernamePasswordAuthenticationFilter.class);
	
		
		return http.build();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
			) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
}
