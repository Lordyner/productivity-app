package com.ozzo.productivityapp.authentication;

import com.ozzo.productivityapp.user.User;

public class RefreshJwtResponse {
	
	private User user;
	private String accessToken;
	
	public User getUser() {
		return user;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public RefreshJwtResponse(User user, String accessToken) {
		this.user = user;
		this.accessToken = accessToken;
	}
	public RefreshJwtResponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
