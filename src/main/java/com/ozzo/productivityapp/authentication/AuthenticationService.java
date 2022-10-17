package com.ozzo.productivityapp.authentication;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.JwtException;

public interface AuthenticationService {

	RefreshJwtResponse refresh(HttpServletRequest request, String refreshJwt) throws JwtException;

	void updateRefreshToken(String refreshToken, String userId);

}