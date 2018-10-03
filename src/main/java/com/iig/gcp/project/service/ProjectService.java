package com.iig.gcp.project.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails) throws ClassNotFoundException, Exception;
}
