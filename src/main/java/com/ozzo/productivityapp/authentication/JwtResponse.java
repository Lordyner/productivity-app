package com.ozzo.productivityapp.authentication;

import java.util.List;

public class JwtResponse {
	
	private String accessToken;
	private Integer idUser;
	private String userName;
	private String email;
	private List<String> roles;
	
	public JwtResponse(String accessToken, Integer idUser, String userName, String email, List<String> roles) {
		this.accessToken = accessToken;
		this.idUser = idUser;
		this.userName = userName;
		this.email = email;
		this.roles = roles;
	}
	
	public JwtResponse(String accessToken, Integer idUser) {
		this.accessToken = accessToken;
		this.idUser = idUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	



}
