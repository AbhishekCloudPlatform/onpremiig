package com.iig.gcp.admin.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;

public interface AdminService {

	String getUser(String user) throws Exception;

	//ArrayList<Feature> getFeatures(HttpServletRequest request) throws Exception;

	void onBoardUser(@Valid String x,HttpServletRequest request)throws Exception;

	//ArrayList<Feature> getFeaturesAlready(HttpServletRequest request) throws Exception;

	ArrayList<Feature> getFeatures(String userid, String project)throws Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	int getUserSequence(String userid)throws Exception;

	


	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails, String userDetails) throws ClassNotFoundException, Exception;
	int getProjectSeq(@Valid String projectId) throws Exception;
	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;
}
