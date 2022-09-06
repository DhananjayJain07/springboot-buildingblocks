package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		List<User> users = this.userService.getAllUsers();
		return users;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
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
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") Long id){
		try {
		Optional<User> user = this.userService.getUserById(id);
		return user;
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	//update user by id
	@PutMapping("/users/{id}")
	public User updateUserById(@RequestBody User user, @PathVariable("id") Long id) {
		try {
		User updatedUser = this.userService.updateUserById(user, id);
		return updatedUser;
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Long id) {
		this.userService.deleteUserById(id);
	}
	
	@GetMapping("/users/byusername/{username}")
	public User getUserByUsername(@PathVariable String username) {
		User userByUsername = this.userService.getUserByUsername(username);
		return userByUsername;
	}

}



