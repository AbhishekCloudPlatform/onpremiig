package com.iig.gcp.admin.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;

public interface AdminService {

	String getUser(String user) throws Exception;

	ArrayList<Feature> getFeatures() throws Exception;

	void onBoardUser(@Valid String x,HttpServletRequest request)throws Exception;
	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails) throws ClassNotFoundException, Exception;

}
