package com.iig.gcp.admin.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	public ArrayList<Feature> getFeatures() throws Exception {
		// TODO Auto-generated method stub
		return admindao.getFeatures();
	}

	@Override
	public void onBoardUser(@Valid String x,HttpServletRequest request) throws Exception {
		System.out.println(x);
		UserAccount user = (UserAccount)request.getSession().getAttribute("user");

		System.out.println("userSeq"+user.getUser_sequence());

		JSONObject jsonObject= new JSONObject(x);
		String feature_seq=jsonObject.getString("target");
		HashMap<String,Integer> hsmap=(HashMap<String,Integer>)request.getSession().getAttribute("projectFeatureMap");
		System.out.println("projectseq"+hsmap.get(jsonObject.getString("projects")));

		System.out.println("featureSq"+feature_seq);
		
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

}
