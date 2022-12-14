package com.stacksimplify.restservices.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repository.OrderRepository;
import com.stacksimplify.restservices.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException{
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		return userOptional.get().getOrders();
	}
	
	@PostMapping("/{userid}/orders")
	public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		User user = userOptional.get();
		order.setUser(user);
		Order order2 = orderRepository.save(order);
		return order2;
	}
	
	@GetMapping("/{userid}/orders/{orderid}")
	public Order getOrderByOrderId(@PathVariable Long userid, @PathVariable Long orderid) throws Exception {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		User user = userOptional.get();
		List<Order> orders = user.getOrders();
		Optional<Order> order = this.orderRepository.findById(orderid);
		return order.get();
		
	}
	
}



