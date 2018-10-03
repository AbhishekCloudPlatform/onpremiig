package com.iig.gcp.login.service;

import java.util.ArrayList;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

public interface LoginService {

ArrayList<UserAccount> getUserAccount() throws Exception;

String getMenuCodes(String userId, String project) throws Exception;

ArrayList<Project> getProjects(String username)throws Exception;
	
}
