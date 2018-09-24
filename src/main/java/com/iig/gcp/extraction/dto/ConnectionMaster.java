package com.iig.gcp.extraction.dto;

public class ConnectionMaster {
	
	private int connection_id   ;
	private String connection_name ;
	private String connection_type;
	private String host_name;
	private String port_no;
	private String username;
	private String password;
	private String database_name;
	private String service_name;

	public int getConnection_id() {
		return connection_id;
	}
	public void setConnection_id(int connection_id) {
		this.connection_id = connection_id;
	}
	public String getConnection_name() {
		return connection_name;
	}
	public void setConnection_name(String connection_name) {
		this.connection_name = connection_name;
	}
	public String getConnection_type() {
		return connection_type;
	}
	public void setConnection_type(String connection_type) {
		this.connection_type = connection_type;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public String getPort_no() {
		return port_no;
	}
	public void setPort_no(String port_no) {
		this.port_no = port_no;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase_name() {
		return database_name;
	}
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}	
}
