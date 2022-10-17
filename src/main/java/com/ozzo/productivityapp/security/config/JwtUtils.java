package com.ozzo.productivityapp.security.config;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ozzo.productivityapp.user.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${productivity.app.jwtSecret}")
	private String jwtSecret;

	@Value("${productivity.app.accessTokenExpirationMs}")
	private int accessTokenExpirationMs;
	@Value("${productivity.app.refreshTokenExpirationMs}")
	private int refreshTokenExpirationMs;

	public String generateAccessToken(Authentication authentication) {
		
		User userPrincipal = (User)authentication.getPrincipal();
		return generateAccessToken(userPrincipal);
	}
	
	public String generateAccessToken(User user) {

		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder()
				.setSubject((user.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + accessTokenExpirationMs))
				.signWith(key)
				.compact();
	}
	public String generateRefreshToken(Authentication authentication) {

		User userPrincipal = (User)authentication.getPrincipal();

		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setExpiration(new Date((new Date()).getTime() + refreshTokenExpirationMs))
				.signWith(key)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.build().parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
			.build()
			.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}