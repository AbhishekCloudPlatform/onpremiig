package com.iig.gcp.admin.dao;

import java.util.ArrayList;

import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;

public interface AdminDAO {

	String getUser(String user) throws Exception;

	ArrayList<Feature> getFeatures(String userid, String project) throws Exception;

	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription) throws ClassNotFoundException, Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	int getUserSequence(String userid)throws Exception;

	void onboardUser(int projectseq, int selectUser_Seq, String feature_seq)throws Exception;


}
