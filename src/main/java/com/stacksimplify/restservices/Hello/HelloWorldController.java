package com.stacksimplify.restservices.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	//@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	@GetMapping("/helloworld")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping("/helloworld-bean")
	public UserDetails helloWorldBean() {
		return new UserDetails("Dhananjay", "Jain", "Dhule");
	}
}
