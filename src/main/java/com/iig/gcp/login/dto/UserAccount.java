package com.iig.gcp.login.dto;

import java.io.Serializable;

public class UserAccount implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int login_id;
	private String username;
	private String email_id;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String group_id;
	private String reservoir_id;
	private String menu_id;
	
	public int getLogin_id() {
		return login_id;
	}
	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getReservoir_id() {
		return reservoir_id;
	}
	public void setReservoir_id(String reservoir_id) {
		this.reservoir_id = reservoir_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	} 
}