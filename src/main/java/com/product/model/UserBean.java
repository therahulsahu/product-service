package com.product.model;
 

public class UserBean {
	private String userName;
	private String userPassword;
	private String errorCode;
	private String errorMessage;

	public Object getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Object getUserPassword() {
		// TODO Auto-generated method stub
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}