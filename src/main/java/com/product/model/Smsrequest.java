package com.product.model;

public class Smsrequest {

	private String number;
	private String message;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Smsrequest(String number, String message) {
		super();
		this.number = number;
		this.message = message;
	}
	
}