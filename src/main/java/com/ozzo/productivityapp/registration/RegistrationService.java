package com.ozzo.productivityapp.registration;

import org.springframework.web.client.HttpClientErrorException;

public interface RegistrationService {

	String register(RegistrationRequest request) throws HttpClientErrorException;

	String confirmToken(String token);

}