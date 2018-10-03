package com.iig.gcp.controllers;

import java.util.ArrayList;

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
import com.iig.gcp.login.service.LoginService;

@Controller
@SessionAttributes(value= {"user","arrProject","menu_code","project","user_sq"})
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = { "/admin/user"}, method = RequestMethod.GET)
    public ModelAndView onBoardUser(ModelMap modelMap) {

		return new ModelAndView("admin/onboardUser");
	}

	
	@RequestMapping(value = { "/admin/selectuser"}, method = RequestMethod.POST)
    public ModelAndView selectUser(@Valid @RequestParam(value = "user", required = true) String user,ModelMap modelMap,HttpServletRequest request) {
		try {
			String project=(String)request.getSession().getAttribute("project");
			String statuserid=	adminService.getUser(user);	
			String stat=statuserid.substring(0, 1);
			String userid=statuserid.substring(1, statuserid.length());
			System.out.println("userid"+userid);
			ArrayList<Feature> arrFeature = adminService.getFeatures(userid,project);
			
			
			ArrayList<Feature> arrFeatureAlready = adminService.getFeaturesAlready(userid,project);
	
			modelMap.addAttribute("stat",Integer.parseInt(stat));
			modelMap.addAttribute("userid",userid);
			modelMap.addAttribute("arrFeature", arrFeature);
			modelMap.addAttribute("arrFeatureAlready", arrFeatureAlready);
			
		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
		}
		return new ModelAndView("admin/userdiv");
	}

	@RequestMapping(value = { "/admin/onboarduser"}, method = RequestMethod.POST)
    public ModelAndView submitUser(@Valid String x,ModelMap modelMap,HttpServletRequest request) {
		
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
			@RequestParam("project_details") String projectDetails, HttpServletRequest request, ModelMap modelMap) {
		String message = null;
		String message1 = null;
		try {
			UserAccount user = (UserAccount)request.getSession().getAttribute("user");
			message = adminService.registerProject(projectId, projectName,projectOwner,projectDetails,user.getUser_id() );
			int projectSeq = adminService.getProjectSeq(projectId);
			message1 = adminService.registerAddAdminAccess(projectSeq, user.getUser_sequence());
			
			modelMap.addAttribute("successString", message);
			ArrayList<Project> arrProject = loginService.getProjects(user.getUser_id());
			modelMap.addAttribute("arrProject",arrProject);
			
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", message);
			e.printStackTrace();
		}
		return new ModelAndView("admin/onboardProject");
	}
}
