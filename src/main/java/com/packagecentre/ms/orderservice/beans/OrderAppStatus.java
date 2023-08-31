package com.packagecentre.ms.orderservice.beans;

import java.util.Date;

public class OrderAppStatus {
	
	private String appStatus;
	private Date dateTime;
	
	public OrderAppStatus() {
		
	}
	
	public OrderAppStatus(String appStatus, Date dateTime) {
		super();
		this.appStatus = appStatus;
		this.dateTime = dateTime;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
