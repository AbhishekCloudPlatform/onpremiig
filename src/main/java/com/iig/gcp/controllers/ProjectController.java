package com.iig.gcp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.project.service.ProjectService;


@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;


	/**
	 * This method redirects the usert to project registation form.
	 * @param modelMap
	 * @return ModelAndView
	 */
	@RequestMapping(value = { "/admin/project"}, method = RequestMethod.GET)
    public ModelAndView register(ModelMap modelMap) {
		return new ModelAndView("project/registerproject");
    }
	
	
	/**
	 * This method accepts the inputs from project registration form and pass then to service layer
	 * @param projectId
	 * @param projectName
	 * @param projectOwner
	 * @param projectDetails
	 * @param modelMap
	 * @return String message if operation is successful or failure.
	 */
	@RequestMapping(value = { "/admin/addProjectDetails" }, method = RequestMethod.POST)
	public ModelAndView registerProject(@Valid @RequestParam("project_id") String projectId,
			@RequestParam("project_name") String projectName,@RequestParam("project_owner") String projectOwner,
			@RequestParam("project_details") String projectDetails, ModelMap modelMap) {
		String message = null;
		try {
			message = projectService.registerProject(projectId, projectName,projectOwner,projectDetails);
			modelMap.addAttribute("successString", message);
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", message);
		}
		return new ModelAndView("project/registerproject");
	}
}
