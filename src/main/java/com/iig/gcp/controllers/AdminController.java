package com.iig.gcp.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.admin.service.AdminService;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

@Controller
@SessionAttributes(value= {"user","arrProject","menu_code","arrFeature"})
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	
	@RequestMapping(value = { "/admin/user"}, method = RequestMethod.GET)
    public ModelAndView onBoardUser(ModelMap modelMap) {
		try {
		ArrayList<Feature> arrFeature = adminService.getFeatures();
		ArrayList<String> feat=new ArrayList<String>();
		for(Feature f:arrFeature) {
			System.out.println(""+f.getFeature_name());
			feat.add(f.getFeature_name());
		}
		modelMap.addAttribute("arrFeature", arrFeature);
		}catch(Exception e){
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
		}
		return new ModelAndView("admin/onboardUser");
	}

	
	@RequestMapping(value = { "/admin/selectuser"}, method = RequestMethod.POST)
    public ModelAndView selectUser(@Valid @RequestParam(value = "user", required = true) String user,ModelMap modelMap) {
		try {
			String userid=	adminService.getUser(user);
			modelMap.addAttribute("userid",userid);

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
		}
		return new ModelAndView("admin/userdiv");
	}
	
	@RequestMapping(value = { "/admin/onboarduser"}, method = RequestMethod.POST)
    public ModelAndView submitUser(@Valid @RequestParam(value = "x", required = true) String x,ModelMap modelMap,HttpServletRequest request) {
		
		try {
			
			adminService.onBoardUser(x,request);
			modelMap.addAttribute("successString","User Onboarded");

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
			
		}
		return new ModelAndView("admin/onboardUser");
	}

	
	@RequestMapping(value = { "/admin/project"}, method = RequestMethod.GET)
    public ModelAndView onBoardProject(ModelMap modelMap) {
	return new ModelAndView("admin/onboardProject");
	}
	
	@RequestMapping(value = { "/admin/saveProjectDetailsForm"}, method = RequestMethod.POST)
    public ModelAndView saveProjectDetails(ModelMap modelMap) {
	return new ModelAndView("admin/onboardProject");
	}
	
	@RequestMapping(value = { "/admin/saveSystemDetailsForm"}, method = RequestMethod.POST)
    public ModelAndView saveSystemDetails(ModelMap modelMap) {
	return new ModelAndView("admin/onboardProject");
	}
	
	
	
	@RequestMapping(value = { "/admin/addProjectDetails" }, method = RequestMethod.POST)
	public ModelAndView registerProject(@Valid @RequestParam("project_id") String projectId,
			@RequestParam("project_name") String projectName,@RequestParam("project_owner") String projectOwner,
			@RequestParam("project_details") String projectDetails, ModelMap modelMap) {
		String message = null;
		try {
			message = adminService.registerProject(projectId, projectName,projectOwner,projectDetails);
			modelMap.addAttribute("successString", message);
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", message);
		}
		return new ModelAndView("project/registerproject");
	}
}
