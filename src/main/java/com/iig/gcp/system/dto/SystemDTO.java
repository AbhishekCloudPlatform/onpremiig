package com.iig.gcp.system.dto;

public class SystemDTO {
	private int system_sequence;
	private String system_eim;
	private String system_name;
	private String system_region;
	private String system_country;
	private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String platform_type;
	private String owner;
	private String target_project;
	private String service_account;
	private String target_bucket;
	private String knox_url;
	private String hadoop_port;
	private String host_name;
	private String file_port;
	private String environment_type;
	
	public int getSystem_sequence() {
		return system_sequence;
	}
	public void setSystem_sequence(int system_sequence) {
		this.system_sequence = system_sequence;
	}
	public String getSystem_eim() {
		return system_eim;
	}
	public void setSystem_eim(String system_eim) {
		this.system_eim = system_eim;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getSystem_region() {
		return system_region;
	}
	public void setSystem_region(String system_region) {
		this.system_region = system_region;
	}
	public String getSystem_country() {
		return system_country;
	}
	public void setSystem_country(String system_country) {
		this.system_country = system_country;
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
	public String getPlatform_type() {
		return platform_type;
	}
	public void setPlatform_type(String platform_type) {
		this.platform_type = platform_type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTarget_project() {
		return target_project;
	}
	public void setTarget_project(String target_project) {
		this.target_project = target_project;
	}
	public String getService_account() {
		return service_account;
	}
	public void setService_account(String service_account) {
		this.service_account = service_account;
	}
	public String getTarget_bucket() {
		return target_bucket;
	}
	public void setTarget_bucket(String target_bucket) {
		this.target_bucket = target_bucket;
	}
	public String getKnox_url() {
		return knox_url;
	}
	public void setKnox_url(String knox_url) {
		this.knox_url = knox_url;
	}
	public String getHadoop_port() {
		return hadoop_port;
	}
	public void setHadoop_port(String hadoop_port) {
		this.hadoop_port = hadoop_port;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public String getFile_port() {
		return file_port;
	}
	public void setFile_port(String file_port) {
		this.file_port = file_port;
	}
	public String getEnvironment_type() {
		return environment_type;
	}
	public void setEnvironment_type(String environment_type) {
		this.environment_type = environment_type;
	}
}
