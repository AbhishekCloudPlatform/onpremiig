package com.iig.gcp.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.scheduler.dto.ArchiveJobsDTO;
import com.iig.gcp.utils.ConnectionUtils;

@Component
public class AdminDAOImpl implements AdminDAO{

	@Override
	public String getUser(String user) throws Exception {
		Connection connection=null;
		//int stat=0;
		String userid=null;
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select user_id from juniper_user_master where user_id='"+user+"'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				userid=rs.getString(1);
				break;
			}
		
		ConnectionUtils.closeQuietly(connection);
		return userid;
	}

	@Override
	public ArrayList<Feature> getFeatures() throws Exception {
		Connection connection=null;
		Feature feature=null;
		 ArrayList<Feature> arrFeatures =new  ArrayList<Feature>();
			connection = ConnectionUtils.getConnection();
			PreparedStatement pstm = connection.prepareStatement("select feature_name,feature_order, feature_level,feature_sequence  from juniper_feature_master order by feature_sequence");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				feature=new Feature();
				feature.setFeature_name(rs.getString(1));
				feature.setFeature_order(rs.getInt(2));
				feature.setFeature_level(rs.getInt(3));
				feature.setFeature_sequence(rs.getInt(4));

				arrFeatures.add(feature);
			}
		
		ConnectionUtils.closeQuietly(connection);
		return arrFeatures;
	}
	
	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String SEMICOLON = ";";
	private static String QUOTE = "\'";
	private static String DATABASE_NAME = "iigs_scheduler_db";
	private static String PROJECT_MASTER_TABLE ="juniper_project_master";
	private static String PROJECT_LINK_TABLE ="juniper_pro_u_feat_master";

	
	/*
	 * This method accepts inputs from project registration form and add in project master table.
	 * (non-Javadoc)
	 * @see com.iig.gcp.project.dao.ProjectDAO#registerProject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails, String user) throws ClassNotFoundException, Exception 
	{
		Connection conn = ConnectionUtils.getConnection();
		try {
			String registerProjectQuery = "INSERT INTO" + SPACE + DATABASE_NAME + "." + PROJECT_MASTER_TABLE
					+ SPACE
					+ "(project_sequence,project_id,project_name,project_owner,project_details,created_by,created_date )" + "VALUES (project_sequence "+ COMMA
					+ QUOTE + projectId+ QUOTE  + COMMA
					+ QUOTE + projectName+ QUOTE  + COMMA
					+ QUOTE + projectOwner+ QUOTE  + COMMA
					+ QUOTE + projectDetails+ QUOTE + COMMA
					+ QUOTE + user+ QUOTE + COMMA + "now())";
			
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			ConnectionUtils.closeQuietly(conn);
			return "Project Registration Completed successfully";

		} catch (Exception e) {
			e.printStackTrace();
			return "Project Registration Failed";
		}
	}

	@Override
	public int getProjectSeq(@Valid String projectId) throws Exception{
		int projectSeq=0;
		Connection conn = ConnectionUtils.getConnection();
		String query = "select project_sequence from juniper_project_master where project_id = ?";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setString(1, projectId);
		ResultSet rs = pstm.executeQuery();
		
		while (rs.next()) {
			projectSeq=rs.getInt(1);
		}
		ConnectionUtils.closeQuietly(conn);
		return projectSeq;
	}

	@Override
	public String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = ConnectionUtils.getConnection();
		String query = "select feature_sequence from juniper_feature_master order by feature_sequence";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int featureId=rs.getInt(1);
			String addProject = "INSERT INTO" + SPACE + DATABASE_NAME + "." + PROJECT_LINK_TABLE
					+ SPACE
					+ "(juniper_pro_u_feat_sequence ,user_sequence,project_sequence,feature_sequence)" + "VALUES (juniper_pro_u_feat_sequence "+ COMMA
					+ QUOTE + user_sequence + QUOTE  + COMMA
					+ QUOTE + projectSeq + QUOTE + COMMA
					+ QUOTE + featureId + QUOTE  + ")";
			Statement statement = conn.createStatement();
			statement.execute(addProject);
		}
		ConnectionUtils.closeQuietly(conn);
		return "Success";
	}

}
