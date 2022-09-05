package com.stacksimplify.restservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.User;
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
	public User createUser(User user) {
		User user1 = this.userRepository.save(user);
		return user1;
	}
	
	//get user by id
	public Optional<User> getUserById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user;
	}
	
	//update user by id
	public User updateUserById(User user, Long id){
		user.setId(id);
		return this.userRepository.save(user);
	}
	
	//detele user by id
	public void deleteUserById(Long id) {
		if(userRepository.findById(id).isPresent()) {
		this.userRepository.deleteById(id);
		}
	}
	
	// find user by username
	public User getUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		return user;
	}
}





