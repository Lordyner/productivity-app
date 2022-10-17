package com.ozzo.productivityapp.opportunity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ozzo.productivityapp.user.User;


@Entity
@Table(name="opportunity")
public class Opportunity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7565376387195506778L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOpportunity;
	
	@ManyToOne
    @JoinColumn(name = "idUser", nullable=false)
	private User user;
	
	private String title;
	private String description;
	private boolean state;
	private LocalDate date;
	
	
	
	public Opportunity() {
		super();
	}

	public int getIdOpportunity() {
		return idOpportunity;
	}

	public void setIdOpportunity(int idOpportunity) {
		this.idOpportunity = idOpportunity;
	}

	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public User getUser() {
		return user;
	}
	public LocalDate getDate() {
		return date;
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

	public Opportunity(String title, String description, boolean state, LocalDate date, User user) {
		this.title = title;
		this.description = description;
		this.state = state;
		this.date = date;
		this.user = user;
	}
	
	
	

	
	
	
	
	
	

}
