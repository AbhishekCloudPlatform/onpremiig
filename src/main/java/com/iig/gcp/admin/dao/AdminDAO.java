package com.iig.gcp.admin.dao;

import java.util.ArrayList;

import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;

public interface AdminDAO {

	String getUser(String user) throws Exception;

	ArrayList<Feature> getFeatures() throws Exception;

	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription) throws ClassNotFoundException, Exception;


}
