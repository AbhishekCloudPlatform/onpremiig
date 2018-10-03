package com.iig.gcp.project.dao;

import javax.validation.Valid;

public interface ProjectDAO {
public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription) throws ClassNotFoundException, Exception;
}
