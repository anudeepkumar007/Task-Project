package com.personal.project.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.project.entity.Users;
import com.personal.project.exception.UserNotFound;
import com.personal.project.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user=userRepository.findByEmail(email).orElseThrow(
				()->new UserNotFound(String.format("User with Email: %s is not found", email))
				);
		
		Set<String> roles= new HashSet<String>();
		roles.add("ROLES_ADMIN");
		return new User(user.getEmail(),user.getPassword(), userAuthorities(roles));
	}
	
	private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles){
		return roles.stream().map(
				role -> new SimpleGrantedAuthority(role)
				).collect(Collectors.toList());
	}

}
