package com.bitwise.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;

public class UserCredentials {
	private String userName;
	private String password;
	private Map<String, String> userCredentials = new HashMap<String, String>();

	public UserCredentials() {
		userCredentials.put("a@gmail.com", "1234");
		userCredentials.put("b@gmail.com", "1234");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean Validate(String userName, String password) {
		return ((userCredentials.containsKey(userName)) && userCredentials.get(userName).toString().equals(password));
	}

	

}
