package com.iig.gcp.login.service;

import java.util.ArrayList;

import com.iig.gcp.login.dto.UserAccount;

public interface LoginService {

ArrayList<UserAccount> getUserAccount() throws Exception;

String getMenuCodesuser(String menu_id) throws Exception;
	
}
