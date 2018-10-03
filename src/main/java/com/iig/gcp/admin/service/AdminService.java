package com.iig.gcp.admin.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;

public interface AdminService {

	String getUser(String user) throws Exception;

	ArrayList<Feature> getFeatures() throws Exception;

	void onBoardUser(@Valid String x,HttpServletRequest request)throws Exception;
	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails, String userDetails) throws ClassNotFoundException, Exception;
	int getProjectSeq(@Valid String projectId) throws Exception;
	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;
}
