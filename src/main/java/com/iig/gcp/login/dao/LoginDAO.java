package com.iig.gcp.login.dao;

import java.util.ArrayList;

import com.iig.gcp.login.dto.UserAccount;

public interface LoginDAO {


	ArrayList<UserAccount> getUserAccount() throws Exception;

	String getMenuCodes(String menu_id) throws Exception;
	
}
