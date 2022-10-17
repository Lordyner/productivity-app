package com.ozzo.productivityapp.registration.token;

public interface ConfirmationTokenService {

	void saveConfirmationToken(RegistrationConfirmationToken token);

	RegistrationConfirmationToken getToken(String token);

	void setConfirmedAt(String token);

}