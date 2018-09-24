package com.iig.gcp.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.service.LoginService;

@Controller
@SessionAttributes(value= {"menu_code","user"})
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public String homePage() {
        return "/index";
    }
	

	@RequestMapping(value = { "/feature"}, method = RequestMethod.GET)
    public String helpPage() {
        return "/cdg_home";
    }
	
	@RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public String login() {
        return "/login/login";
    }

	
	@RequestMapping(value = { "/loginsubmit"}, method = RequestMethod.POST)
    public ModelAndView authenticateUser(String username,String password,ModelMap modelMap ) {
		boolean flag=false;
		UserAccount user=null;
        try {
		ArrayList<UserAccount> arrUserAccount= loginService.getUserAccount();
		for(int i=0;i<arrUserAccount.size();i++) {
			 user=arrUserAccount.get(i);
			if(user.getUsername().equals(username)) {
				if(user.getPassword().equals(password)) {
					flag=true;
					break;
				}
				
			}
		}
		
		if(!flag) {
			modelMap.addAttribute("errorString","USERNAME/PASSWORD INCORRECT");
			return new ModelAndView("login/login");
		}else {
			
			String menu_code=loginService.getMenuCodesuser(user.getMenu_id());
			System.out.println("menu_code-----"+menu_code);
			modelMap.addAttribute("user",user);
			modelMap.addAttribute("menu_code",menu_code);
		}
		
        }catch(Exception e) {
        	e.printStackTrace();
        	modelMap.addAttribute("errorString",e.getMessage());
        	return new ModelAndView("login/login");
        }
		return new ModelAndView("cdg_home");
    }
}
