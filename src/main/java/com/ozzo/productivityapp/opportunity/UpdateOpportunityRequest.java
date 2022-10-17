package com.ozzo.productivityapp.opportunity;

import java.io.Serializable;
import java.time.LocalDate;

public class UpdateOpportunityRequest implements Serializable {
	
	private static final long serialVersionUID = -6514773416123416112L;
	
	private LocalDate date;
	private String description;
	private String state;
	private String title;
	private Integer idUser;
	private int idOpportunity;
	
	
	public int getIdOpportunity() {
		return idOpportunity;
	}
	public void setIdOpportunity(int idOpportunity) {
		this.idOpportunity = idOpportunity;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getState() {
		return state;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setState(String state) {
		this.state = state;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
	

}
