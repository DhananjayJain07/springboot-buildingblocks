package com.stacksimplify.restservices.controller;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.dtos.UserDtoV1;
import com.stacksimplify.restservices.dtos.UserDtoV2;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.service.UserService;

@RestController
@RequestMapping("/versioning/mediatype/users")
@Validated
public class UserMediaTypeVersioningController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//Version 1
	@GetMapping(value = "/{id}", produces="application/vnd.stacksimplify.app-v1+json" )
	public UserDtoV1 getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException{
		
		Optional<User> userOptional = this.userService.getUserById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User not Found");
		}
		User user = userOptional.get();
		
		 UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
		
		return userDtoV1;
		
	}
	
	//Version 2
		@GetMapping(value = "/{id}", produces="application/vnd.stacksimplify.app-v2+json" )
		public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException{
			
			Optional<User> userOptional = this.userService.getUserById(id);
			if(!userOptional.isPresent()) {
				throw new UserNotFoundException("User not Found");
			}
			User user = userOptional.get();
			
			 UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);
			
			return userDtoV2;
			
		}
	
}