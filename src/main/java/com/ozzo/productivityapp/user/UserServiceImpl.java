package com.ozzo.productivityapp.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.ozzo.productivityapp.registration.token.RegistrationConfirmationToken;
import com.ozzo.productivityapp.registration.token.ConfirmationTokenService;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	private final static String USER_NOT_FOUND_MSG = 
			"User with email %s not found";
	private final UserRepository userRepository;
	private final ConfirmationTokenService confirmationTokenService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ConfirmationTokenService confirmationTokenService) {
		super();
		this.userRepository = userRepository;
		this.confirmationTokenService = confirmationTokenService;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userRepository.findByUserName(userName)
				.orElseThrow(() -> 
				new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, userName)));
	}

	@Override
	public String signUpUser(User user) throws HttpClientErrorException {
			
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();	
		if(userExists) {
			
			//TODO check of attributes are the same and
			//TODO if email not confirmed, send confirmation email
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Email already taken");
		}
		
			
		/*Save user in DB */
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		
		/* Save token in DB */
		String token = UUID.randomUUID().toString();
		RegistrationConfirmationToken confirmationToken = new RegistrationConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		return token;
	}

	@Override
	public void enableUser(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("Unknown user"));
		
		user.setEnabled(true);
		userRepository.save(user);
		
	}
	
	@Override
	public void updateRefreshToken(String refreshToken, String userId) {
		userRepository.updateRefreshToken(refreshToken, userId);
	}
	
	@Override
	public Optional<User> findByRefreshToken(String refreshToken) {
		return userRepository.findByRefreshToken(refreshToken);
	}
	
	
	

	
	
	

}
