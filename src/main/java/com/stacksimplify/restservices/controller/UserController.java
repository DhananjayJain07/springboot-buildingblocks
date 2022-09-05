package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.User;
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
	public User createUser(@RequestBody User user) {
		User createUser = this.userService.createUser(user);
		return createUser;
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") Long id){
		Optional<User> user = this.userService.getUserById(id);
		return user;
	}
	
	//update user by id
	@PutMapping("/users/{id}")
	public User updateUserById(@RequestBody User user, @PathVariable("id") Long id) {
		User updatedUser = this.userService.updateUserById(user, id);
		return updatedUser;
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



