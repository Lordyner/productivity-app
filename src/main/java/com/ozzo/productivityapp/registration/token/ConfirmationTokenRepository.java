package com.ozzo.productivityapp.registration.token;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<RegistrationConfirmationToken, Long> {

	Optional<RegistrationConfirmationToken> findByToken(String token);
	
}
