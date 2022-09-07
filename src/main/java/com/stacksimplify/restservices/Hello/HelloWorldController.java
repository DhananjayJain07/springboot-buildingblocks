package com.stacksimplify.restservices.Hello;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	//@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	@GetMapping("/helloworld")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping("/helloworld-bean")
	public UserDetails helloWorldBean() {
		return new UserDetails("Dhananjay", "Jain", "Dhule");
	}
	
	@GetMapping("/hello-int")
	public String getMessagesI18NFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
		return messageSource.getMessage("label.hello",null, new Locale(locale));
	}
	
	@GetMapping("/hello-int2")
	public String getMessagesI18NFormat2() {
		return messageSource.getMessage("label.hello",null, LocaleContextHolder.getLocale());
	}
	
}
