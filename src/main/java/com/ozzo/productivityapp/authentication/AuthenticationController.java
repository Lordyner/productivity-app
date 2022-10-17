package com.ozzo.productivityapp.authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ozzo.productivityapp.security.config.JwtUtils;
import com.ozzo.productivityapp.user.User;

import io.jsonwebtoken.JwtException;


@RestController
@RequestMapping("/authentication")
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest request, HttpServletResponse response) {
		Authentication authentication = null;
		String accessToken = null;
		String refreshToken = null;
		ResponseCookie refreshJwtCookie = null;
		User userDetails = null;
		List<String> roles = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			accessToken = jwtUtils.generateAccessToken(authentication);
			refreshToken = jwtUtils.generateRefreshToken(authentication);	
			userDetails = (User) authentication.getPrincipal();		
			authenticationService.updateRefreshToken(refreshToken, userDetails.getIdUser().toString());
			roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			
			refreshJwtCookie = ResponseCookie.from("refreshJwt", refreshToken)
								.httpOnly(true)
								.secure(true)
								.maxAge(86400)
								.build();
			
			Map<String, String> headers = new HashMap<>();
			headers.put(HttpHeaders.SET_COOKIE, refreshJwtCookie.toString());
			
		} catch(DisabledException e) {			
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A mail has been sent to confirm your registration", e);
		} catch(LockedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account locked", e);
		} catch(BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login or password incorrect");
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Data Access problem", e);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A problem occured", e);
		}
		
		
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshJwtCookie.toString()).body(new JwtResponse(accessToken,
				userDetails.getIdUser(), 
				userDetails.getUsername(), 
				userDetails.getEmail(), 
				roles));
	}
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			path="/refresh")
	@ResponseBody
	public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse Httpresponse, @CookieValue(value = "refreshJwt") String refreshJwt) {
		
		RefreshJwtResponse response = null;
		try {
			response = authenticationService.refresh(request, refreshJwt);
		} catch (JwtException e) {
			return new ResponseEntity<>(
					e.getMessage(), 
					HttpStatus.FORBIDDEN
					);
		}
		return ResponseEntity.ok(new JwtResponse(response.getAccessToken(), response.getUser().getIdUser()));
 	}
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			path="/logout")
	@ResponseBody
	public ResponseEntity<?> logout(HttpServletRequest request) {

		ResponseCookie refreshJwtCookie = ResponseCookie.from("refreshJwt", null)
				.httpOnly(true)
				.secure(true)
				.maxAge(0)
				.build();

		Map<String, String> headers = new HashMap<>();
		headers.put(HttpHeaders.SET_COOKIE, "");
		
		return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, refreshJwtCookie.toString()).build();
		
	}

}
