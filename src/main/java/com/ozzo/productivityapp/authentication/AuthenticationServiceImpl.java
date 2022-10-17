package com.ozzo.productivityapp.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozzo.productivityapp.security.config.JwtUtils;
import com.ozzo.productivityapp.user.User;
import com.ozzo.productivityapp.user.UserServiceImpl;

import io.jsonwebtoken.JwtException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	UserServiceImpl userService;

	
	@Override
	public RefreshJwtResponse refresh(HttpServletRequest request, String refreshJwt) throws JwtException {

		RefreshJwtResponse response = new RefreshJwtResponse();
		if (refreshJwt != null && jwtUtils.validateJwtToken(refreshJwt)) {	
			User foundUser = userService.findByRefreshToken(refreshJwt).orElseThrow(() -> new SecurityException());
			
			if(foundUser != null) {
				String accessToken = jwtUtils.generateAccessToken(foundUser);
				response.setAccessToken(accessToken);
				response.setUser(foundUser);
			}	
		} 	 
		return response;
	}
	
	@Override
	public void updateRefreshToken(String refreshToken, String userId) {
		userService.updateRefreshToken(refreshToken, userId);
	}

}
