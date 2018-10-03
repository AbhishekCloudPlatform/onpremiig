package com.iig.gcp.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.admin.dao.AdminDAO;
import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.login.dto.UserAccount;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO admindao;
	
	@Override
	public String getUser(String user) throws Exception {
		// TODO Auto-generated method stub
		return admindao.getUser(user);
	}

	@Override
	public void onBoardUser(@Valid String x,HttpServletRequest request) throws Exception {
		System.out.println(x);
		JSONObject jsonObject=null;
		try {
		jsonObject= new JSONObject(x);
		}catch(JSONException e) {
			throw new Exception("Please Select User ID for Onboarding");
		}
		String feature_seq=jsonObject.getString("target");
		String username=jsonObject.getString("username");
		int selectUser_Seq=admindao.getUserSequence(username);
		System.out.println("selectUser_Seq"+selectUser_Seq);
		HashMap<String,Integer> hsmap=(HashMap<String,Integer>)request.getSession().getAttribute("projectFeatureMap");
		String hsKey=jsonObject.getString("projects");
		int projectseq=hsmap.get(hsKey);
		//System.out.println("projectseq"+hsmap.get(jsonObject.getString("projects")));

		System.out.println("featureSq"+feature_seq);
		if(feature_seq!=null) {
			admindao.onboardUser(projectseq,selectUser_Seq,feature_seq);
		}
	}
	
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails, String user) throws ClassNotFoundException, Exception {
		return admindao.registerProject(projectId,projectName,projectOwner,projectDetails,user);
	}

	@Override
	public int getProjectSeq(@Valid String projectId) throws Exception{
		// TODO Auto-generated method stub
		return admindao.getProjectSeq(projectId);
	}

	@Override
	public String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception {
		// TODO Auto-generated method stub
		return admindao.registerAddAdminAccess(projectSeq,user_sequence);
	}

	@Override
	public ArrayList<Feature> getFeatures(String userid,String project) throws Exception {
		
		return admindao.getFeatures(userid,project);
	}

	@Override
	public ArrayList<Feature> getFeaturesAlready(String userid,String project) throws Exception {
		
		return admindao.getFeaturesAlready(userid,project);
	}

	@Override
	public int getUserSequence(String userid) throws Exception {
		
		return admindao.getUserSequence(userid);
	}

}
