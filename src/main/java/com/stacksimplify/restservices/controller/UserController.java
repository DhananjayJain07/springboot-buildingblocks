package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(tags = "User Management Restful Services", value = "UserController", description = "Controller for user management services")
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Retrieve list of users")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers(){
		List<User> users = this.userService.getAllUsers();
		return users;
	}
	
	@ApiOperation(value = "Create a new user")
	@PostMapping("/")
	public ResponseEntity<Void> createUser(@ApiParam("User information for a new user to be created.") @Valid @RequestBody User user, UriComponentsBuilder builder) {
		try {
		this.userService.createUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
		}catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	//
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") @Min(1) Long id){
		try {
		Optional<User> userOptional = this.userService.getUserById(id);
		return userOptional.get();
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	//update user by id
	@PutMapping("/{id}")
	public User updateUserById(@RequestBody User user, @PathVariable("id") Long id) {
		try {
		User updatedUser = this.userService.updateUserById(user, id);
		return updatedUser;
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable Long id) {
		this.userService.deleteUserById(id);
	}
	
	@GetMapping("/byusername/{username}")
	public User getUserByUsername(@PathVariable String username) throws UserNameNotFoundException {
		User user = this.userService.getUserByUsername(username);
		if(user == null) {
			throw new UserNameNotFoundException("Username: '"+username+ "' not found in User respository");
		}
		return user;
		
	}

}









