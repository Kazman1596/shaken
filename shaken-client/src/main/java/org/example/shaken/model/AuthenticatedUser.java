package org.example.shaken.model;

public class AuthenticatedUser {
	
	private String token;
	private Account user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Account getUser() {
		return user;
	}
	public void setUser(Account user) {
		this.user = user;
	}
}
