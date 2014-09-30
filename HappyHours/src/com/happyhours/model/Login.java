package com.happyhours.model;

import com.google.gson.annotations.Expose;

public class Login {
	

	@Expose
	private String token;
	@Expose
	private String code;
	@Expose
	private String message;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	


}
