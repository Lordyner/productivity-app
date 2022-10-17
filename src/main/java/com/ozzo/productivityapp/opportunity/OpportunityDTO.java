package com.ozzo.productivityapp.opportunity;

import java.time.LocalDate;

import com.ozzo.productivityapp.user.User;

public class OpportunityDTO {
	
	private int idOpportunity;
	private User user;
	private String title;
	private String description;
	private boolean state;
	private LocalDate date;
	
	public int getIdOpportunity() {
		return idOpportunity;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setIdOpportunity(int idOpportunity) {
		this.idOpportunity = idOpportunity;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
	
	
	

}
