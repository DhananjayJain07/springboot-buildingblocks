package com.stacksimplify.restservices.controller;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.entities.Views;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.service.UserService;

@RestController
@RequestMapping("/jsonview/users")
@Validated
public class UserJsonViewController {
	
	@Autowired
	private UserService userService;
	@JsonView(Views.External.class)
	@GetMapping("/external/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id){
		try {
		Optional<User> user = this.userService.getUserById(id);
		return user;
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	@JsonView(Views.Internal.class)
	@GetMapping("/internal/{id}")
	public Optional<User> getUserById2(@PathVariable("id") @Min(1) Long id){
		try {
		Optional<User> user = this.userService.getUserById(id);
		return user;
		}catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}

}
