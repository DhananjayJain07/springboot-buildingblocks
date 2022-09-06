package com.stacksimplify.restservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	//getAllUsers
	public List<User> getAllUsers(){
		
	return this.userRepository.findAll();
	}
	
	// create user
	public User createUser(User user)throws UserExistsException {
		
		User existingUser = this.userRepository.findByUsername(user.getUsername());
		if(existingUser != null) {
			throw new UserExistsException("User already exists in repository");
		}
		User user1 = this.userRepository.save(user);
		return user1;
	}
	
	//get user by id
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = this.userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found in user Repository");
		}
		return user;
	}
	
	//update user by id
	public User updateUserById(User user, Long id)throws UserNotFoundException{
		Optional<User> Optionaluser = this.userRepository.findById(id);
		if(!Optionaluser.isPresent()) {
			throw new UserNotFoundException("User not found in user Repository, Provide the correct user id");
		}
		user.setId(id);
		return this.userRepository.save(user);
	}
	
	//detele user by id
	public void deleteUserById(Long id) {
		Optional<User> Optionaluser = this.userRepository.findById(id);
		if(!Optionaluser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found in user Repository, Provide the correct user id");
		}
		this.userRepository.deleteById(id);
		
	}
	
	// find user by username
	public User getUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		return user;
	}
}





