package com.ozzo.productivityapp.registration.token;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
	
	private final ConfirmationTokenRepository confirmationTokenRepository;
	
	
	@Autowired
	public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}



	@Override
	public void saveConfirmationToken(RegistrationConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}
	
	@Override
	public RegistrationConfirmationToken getToken(String token) {
		return confirmationTokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalStateException("Token not found"));
	}

	@Override
	public void setConfirmedAt(String token) {
		
		RegistrationConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalStateException("Token not found"));
		confirmationToken.setConfirmedAt(LocalDateTime.now());
		confirmationTokenRepository.save(confirmationToken);
		
	}

}
