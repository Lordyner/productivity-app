package com.ozzo.productivityapp.user;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.web.client.HttpClientErrorException;

public interface UserService {

	String signUpUser(User user) throws HttpClientErrorException;

	Optional<User> findByRefreshToken(String refreshToken);

	void updateRefreshToken(String refreshToken, String userId) throws DataAccessException;

	void enableUser(String email);

}
