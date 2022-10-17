package com.ozzo.productivityapp.opportunity;

import java.io.Serializable;
import java.time.LocalDate;

public class GetOpportunitiesRequest implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8804748676899458018L;
	private Integer userId;
	private LocalDate minDate;
	private LocalDate maxDate;
	private boolean isForToday;
	

	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public LocalDate getMinDate() {
		return minDate;
	}
	public LocalDate getMaxDate() {
		return maxDate;
	}
	
	public void setMinDate(LocalDate minDate) {
		this.minDate = minDate;
	}
	public void setMaxDate(LocalDate maxDate) {
		this.maxDate = maxDate;
	}
	public boolean isForToday() {
		return isForToday;
	}
	public void setForToday(boolean isForToday) {
		this.isForToday = isForToday;
	}
	public GetOpportunitiesRequest(int userId, LocalDate minDate, LocalDate maxDate, boolean isForToday) {
		this.userId = userId;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.isForToday = isForToday;
	}
	
	
	
	
	
	
}
