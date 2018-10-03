package com.iig.gcp.login.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.login.dao.LoginDAO;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

@Service
public class LoginServiceImpl implements LoginService{

	
	@Autowired
	LoginDAO loginDAO;
	
	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getUserAccount();
	}

	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getProjects(username);
	}

	@Override
	public String getMenuCodes(String userId, String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getMenuCodes(userId,project);
	}

}
