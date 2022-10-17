package com.ozzo.productivityapp.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping(path="/registration")
@CrossOrigin("*")
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		String token = null;
		try {
			token = registrationService.register(request);	
		} catch (HttpClientErrorException e) {
			throw new ResponseStatusException(e.getStatusCode(), e.getStatusText());	
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured", e);
		}
		
		return new ResponseEntity<>(
			      "Token : " + token, 
			      HttpStatus.OK);
 	}
	
	@GetMapping(path="/confirm")
	public @ResponseBody String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
	
}
