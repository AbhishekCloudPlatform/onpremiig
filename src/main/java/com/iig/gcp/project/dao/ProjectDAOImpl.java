package com.iig.gcp.project.dao;

import java.sql.Connection;
import java.sql.Statement;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.utils.ConnectionUtils;

@Component
public class ProjectDAOImpl implements ProjectDAO {
	
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String PROJECT_MASTER_TABLE ="juniper_project_master";

	
	/*
	 * This method accepts inputs from project registration form and add in project master table.
	 * (non-Javadoc)
	 * @see com.iig.gcp.project.dao.ProjectDAO#registerProject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails) throws ClassNotFoundException, Exception 
	{
		Connection conn = ConnectionUtils.getConnection();
		try {
			String registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + PROJECT_MASTER_TABLE
					+ SPACE
					+ "(project_sequence,project_id,project_name,project_owner,project_details)" + "VALUES (project_sequence "+ COMMA
					+ QUOTE + projectId+ QUOTE  + COMMA
					+ QUOTE + projectName+ QUOTE  + COMMA
					+ QUOTE + projectOwner+ QUOTE  + COMMA
					+ QUOTE + projectDetails+ QUOTE + ")";
			
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			ConnectionUtils.closeQuietly(conn);
			return "Project Registration Completed successfully";

		} catch (Exception e) {
			e.printStackTrace();
			return "Project Registration Failed";
		}
	}
}
