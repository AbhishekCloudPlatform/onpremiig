package com.iig.gcp.login.dto;

import java.io.Serializable;

public class UserAccount implements Serializable {
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int user_sequence; 
	private String user_id;
	private String user_domain ;
	private String user_pass;
	private String created_by; 
	private String created_date;
	private String updated_by ;
	private String updated_date;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_domain() {
		return user_domain;
	}
	public void setUser_domain(String user_domain) {
		this.user_domain = user_domain;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public int getUser_sequence() {
		return user_sequence;
	}
	public void setUser_sequence(int user_sequence) {
		this.user_sequence = user_sequence;
	}
	
	
}