package com.iig.gcp.login.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.login.dao.LoginDAO;
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
	public String getMenuCodesuser(String menu_id) throws Exception{
		// TODO Auto-generated method stub
		return loginDAO.getMenuCodes(menu_id);
	}

}
