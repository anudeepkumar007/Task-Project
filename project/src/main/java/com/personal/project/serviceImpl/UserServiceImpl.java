package com.personal.project.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.project.entity.Users;
import com.personal.project.payload.UserDto;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Users user=modelMapper.map(userDto, Users.class);
		
		Users savedUser=userRepository.save(user);
		
		return modelMapper.map(savedUser, UserDto.class);
		
		

	}
	
	

}
