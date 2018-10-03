package com.iig.gcp.project.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.project.dao.ProjectDAO;

@Service
public class ProjectServiceImpl implements ProjectService {
		
		@Autowired
		ProjectDAO projectDAO;

	/* (non-Javadoc)
	 * @see com.iig.gcp.project.service.ProjectService#registerProject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails) throws ClassNotFoundException, Exception {
		return projectDAO.registerProject(projectId,projectName,projectOwner,projectDetails);
	}
}
